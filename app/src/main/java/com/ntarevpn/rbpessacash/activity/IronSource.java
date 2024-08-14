package com.ntarevpn.rbpessacash.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.ntarevpn.rbpessacash.services.PointsService;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.ntarevpn.rbpessacash.util.AdsIDController;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.LockableScrollView;
import com.ntarevpn.rbpessacash.util.SomeEarnController;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.unity3d.ironsourceads.rewarded.RewardedAd;
import com.unity3d.ironsourceads.rewarded.RewardedAdListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IronSource extends AppCompatActivity  implements RewardedAdListener {
    private Context activity;
    private static final String TAG = "Custom_Ads_Tag";
    private Dialog dialog;


    private TextView IronSourceOfferWall_count_textView, points_textView, points_text;
    boolean first_time = true, IronSourceOfferWall_first = true;
    private int IronSourceOfferWall_count = 1;
    private String random_points;
    public int poiints = 0;
    public boolean rewardShow = true, interstitialShow = true;
    private LockableScrollView scrollView;


    AdsController ads_controller;
    SomeEarnController someEarn_controller;

    AdsIDController ads_id_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iron_source_indratech);


        activity = this;
        ads_controller = new AdsController(this);
        someEarn_controller = new SomeEarnController(this);
        ads_id_controller = new AdsIDController(this);

        initIronSource();
        com.ironsource.mediationsdk.IronSource.showRewardedVideo();

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog_indratech);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        points_text = findViewById(R.id.textView_points_show);
        IronSourceOfferWall_count_textView = findViewById(R.id.limit_text);
        points_text.setText(someEarn_controller.getironSource_point());
        if (Constant.isNetworkAvailable(activity)) {

            Constant.loadInterstitialAd(this);
            Constant.loadRewardedVideo(this);
        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }
        onInit();




        if (IronSourceOfferWall_first) {
            IronSourceOfferWall_first = false;
            Log.e(TAG, "onOfferWallRewardStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";

                random_points = String.valueOf(someEarn_controller.getironSource_point());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }

                Log.e(TAG, "onOfferWallRewardStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
            }
        }


    }



    private void onInit() {

        String IronSourceOfferWallCount = Constant.getString(activity, Constant.OfferWall_Reward_COUNT);
        if (IronSourceOfferWallCount.equals("0")) {
            IronSourceOfferWallCount = "";
            Log.e("TAG", "onInit: scratch card 0");
        }
        if (IronSourceOfferWallCount.equals("")) {
            Log.e("TAG", "onInit: scratch card empty vala part");
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(activity, Constant.LAST_DATE_OfferWall_Reward);
            Log.e("TAG", "Lat date" + last_date);
            if (last_date.equals("")) {
                Log.e("TAG", "onInit: last date empty part");
                setScratchCount("10000");
                Constant.setString(activity, Constant.OfferWall_Reward_COUNT, "10000");
                Constant.setString(activity, Constant.LAST_DATE_OfferWall_Reward, currentDate);
            } else {
                Log.e("TAG", "onInit: last date not empty part");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date current_date = sdf.parse(currentDate);
                    Date lastDate = sdf.parse(last_date);
                    long diff = current_date.getTime() - lastDate.getTime();
                    long difference_In_Days = (diff / (1000 * 60 * 60 * 24)) % 365;
                    Log.e("TAG", "onClick: Days Difference" + difference_In_Days);
                    if (difference_In_Days > 0) {
                        Constant.setString(activity, Constant.LAST_DATE_OfferWall_Reward, currentDate);
                        Constant.setString(activity, Constant.OfferWall_Reward_COUNT, "10000");
                        setScratchCount(Constant.getString(activity, Constant.OfferWall_Reward_COUNT));
                        Log.e("TAG", "onClick: today date added to preference" + currentDate);
                    } else {
                        setScratchCount("0");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("TAG", "onInit: scracth card in preference part");
            setScratchCount(IronSourceOfferWallCount);
        }
    }

    private void setScratchCount(String string) {
        if (string != null || !string.equalsIgnoreCase(""))
            IronSourceOfferWall_count_textView.setText("Your daily limit left to collect this reward is = " + string);
    }

    private void showDialogPoints(final int addOrNoAddValue, final String points, final int counter) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.points_dialog_indratech);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);
        if (Constant.isNetworkAvailable(activity)) {
            if (addOrNoAddValue == 1) {
                if (points.equals("0")) {
                    Log.e("TAG", "showDialogPoints: 0 points");
                    imageView.setImageResource(R.drawable.sad_ic);
                    title_text.setText(getResources().getString(R.string.better_luck_next_time));
                    points_text.setVisibility(View.VISIBLE);
                    points_text.setText(points);
                    add_btn.setText(getResources().getString(R.string.ok_text));
                } else {
                    Log.e("TAG", "showDialogPoints: points");

                    title_text.setText(getResources().getString(R.string.you_win));
                    points_text.setVisibility(View.VISIBLE);
                    points_text.setText(points);
                    add_btn.setText(getResources().getString(R.string.account_add_to_wallet));
                }
            } else {
                Log.e("TAG", "showDialogPoints: chance over");
                imageView.setImageResource(R.drawable.donee);
                title_text.setText(getResources().getString(R.string.today_work_is_over));
                points_text.setVisibility(View.GONE);
                add_btn.setText(getResources().getString(R.string.ok_text));
            }
            add_btn.setOnClickListener(view -> {

                first_time = true;
                IronSourceOfferWall_first = true;

                poiints = 0;
                if (addOrNoAddValue == 1) {
                    if (points.equals("0")) {
                        String current_counter = String.valueOf(counter - 1);
                        Constant.setString(activity, Constant.OfferWall_Reward_COUNT, current_counter);
                        setScratchCount(Constant.getString(activity, Constant.OfferWall_Reward_COUNT));
                        dialog.dismiss();
                    } else {
                        String current_counter = String.valueOf(counter - 1);
                        Constant.setString(activity, Constant.OfferWall_Reward_COUNT, current_counter);
                        setScratchCount(Constant.getString(activity, Constant.OfferWall_Reward_COUNT));
                        try {
                            int finalPoint;
                            if (points.equals("")) {
                                finalPoint = 0;
                            } else {
                                finalPoint = Integer.parseInt(points);
                            }
                            poiints = finalPoint;
                            Constant.addPoints(activity, finalPoint, 0);
                        } catch (NumberFormatException ex) {
                            Log.e("TAG", "onScratchComplete: " + ex.getMessage());
                        }
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }
                if (IronSourceOfferWall_count == Integer.parseInt(someEarn_controller.getRewarded_and_interstitial_count())) {
                    if (rewardShow) {
                        Log.e(TAG, "onReachTarget: rewaded ads showing method");
                        showDailog();
                        rewardShow = false;
                        interstitialShow = true;
                        IronSourceOfferWall_count = 1;
                    } else if (interstitialShow) {
                        Log.e(TAG, "onReachTarget: interstital ads showing method");
                        Constant.showInterstitialAds();
                        rewardShow = true;
                        interstitialShow = false;
                        IronSourceOfferWall_count = 1;
                    }
                } else {
                    IronSourceOfferWall_count += 1;
                }
            });
        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }
        dialog.show();
    }

    public void showDailog() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.points_dialog_indratech);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);
        AppCompatButton cancel_btn = dialog.findViewById(R.id.cancel_btn);
        cancel_btn.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.video_ads);
        add_btn.setText("Yes");
        title_text.setText("Watch Full Video");
        points_text.setText("To Unlock this Reward Points");

        add_btn.setOnClickListener(v -> {
            dialog.dismiss();
            Constant.showRewardedAdmobAds(this);
        });

        cancel_btn.setOnClickListener(v -> {
            dialog.dismiss();
            if (poiints != 0) {
                String po = Constant.getString(activity, Constant.USER_POINTS);
                if (po.equals("")) {
                    po = "0";
                }
                int current_Points = Integer.parseInt(po);
                int finalPoints = current_Points - poiints;
                Constant.setString(activity, Constant.USER_POINTS, String.valueOf(finalPoints));
                Intent serviceIntent = new Intent(activity, PointsService.class);
                serviceIntent.putExtra("points", Constant.getString(activity, Constant.USER_POINTS));
                activity.startService(serviceIntent);
            }
        });

        dialog.show();
    }


    private void initIronSource() {
        com.ironsource.mediationsdk.IronSource.init(this, ads_id_controller.getIronSourceAppKey(), com.ironsource.mediationsdk.IronSource.AD_UNIT.REWARDED_VIDEO);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        com.ironsource.mediationsdk.IronSource.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        com.ironsource.mediationsdk.IronSource.onPause(this);
    }


    @Override
    public void onRewardedAdClicked(@NonNull RewardedAd rewardedAd) {

    }

    @Override
    public void onRewardedAdShown(@NonNull RewardedAd rewardedAd) {
        dialog.dismiss();
    }

    @Override
    public void onRewardedAdFailedToShow(@NonNull RewardedAd rewardedAd, @NonNull IronSourceError ironSourceError) {
        Intent i = new Intent(IronSource.this, Main.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onUserEarnedReward(@NonNull RewardedAd rewardedAd) {
        Toast.makeText(this, "Added Coin", Toast.LENGTH_SHORT).show();
        if (first_time) {
            first_time = false;
            Log.e("onCollectReward", "Complete");
            Log.e("onCollectReward", "Complete" + random_points);
            String count = IronSourceOfferWall_count_textView.getText().toString();
            String[] counteee = count.split("=", 2);
            String ran = counteee[1];
            Log.e(TAG, "onCollectRewardComplete: " + ran);
            int counter = Integer.parseInt(ran.trim());
            if (counter == 0) {
                showDialogPoints(0, "0", counter);
            } else {

                showDialogPoints(1, points_text.getText().toString(), counter);
            }
        }
    }

    @Override
    public void onRewardedAdDismissed(@NonNull RewardedAd rewardedAd) {
        Intent i = new Intent(IronSource.this, Main.class);
        startActivity(i);
        finish();
    }
}