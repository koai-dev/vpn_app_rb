package com.ntarevpn.rbpessacash.activity;

import android.app.Activity;
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

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.ntarevpn.rbpessacash.services.PointsService;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.ntarevpn.rbpessacash.util.AdsIDController;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.LockableScrollView;
import com.ntarevpn.rbpessacash.util.SomeEarnController;
import com.pollfish.builder.Params;
import com.pollfish.callback.PollfishClosedListener;
import com.pollfish.callback.PollfishOpenedListener;
import com.pollfish.callback.PollfishSurveyCompletedListener;
import com.pollfish.callback.PollfishSurveyNotAvailableListener;
import com.pollfish.callback.PollfishSurveyReceivedListener;
import com.pollfish.callback.PollfishUserNotEligibleListener;
import com.pollfish.callback.PollfishUserRejectedSurveyListener;
import com.pollfish.callback.SurveyInfo;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Pollfish extends Activity implements
        PollfishSurveyCompletedListener,
        PollfishOpenedListener,
        PollfishClosedListener,
        PollfishSurveyReceivedListener,
        PollfishSurveyNotAvailableListener,
        PollfishUserNotEligibleListener,
        PollfishUserRejectedSurveyListener {

    private static final String TAG = "Main";


    private Context activity;
    private Toolbar toolbar;
    private TextView PollfishOfferWall_count_textView, points_textView, points_text;
    boolean first_time = true, PollfishOfferWall_first = true;
    private int PollfishOfferWall_count = 1;
    private String random_points;
    public int poiints = 0;
    public boolean rewardShow = true, interstitialShow = true;
    private LockableScrollView scrollView;


    AdsController ads_controller;
    SomeEarnController someEarn_controller;

    private Dialog dialog;
    AdsIDController ads_id_controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pollfish_layout_indratech);

        activity = this;
        ads_controller = new AdsController(this);
        someEarn_controller = new SomeEarnController(this);
        ads_id_controller = new AdsIDController(this);


        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog_indratech);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        initPollfish();



        points_text = findViewById(R.id.textView_points_show);
        PollfishOfferWall_count_textView = findViewById(R.id.limit_text);

        if (Constant.isNetworkAvailable(activity)) {

            Constant.loadInterstitialAd(this);
            Constant.loadRewardedVideo(this);
        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }
        onInit();





        if (PollfishOfferWall_first) {
            PollfishOfferWall_first = false;
            Log.e(TAG, "onOfferWallRewardStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";

                random_points = String.valueOf(someEarn_controller.getPollfish_point());
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

        String PollfishOfferWallCount = Constant.getString(activity, Constant.OfferWall_Reward_COUNT);
        if (PollfishOfferWallCount.equals("0")) {
            PollfishOfferWallCount = "";
            Log.e("TAG", "onInit: scratch card 0");
        }
        if (PollfishOfferWallCount.equals("")) {
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
            setScratchCount(PollfishOfferWallCount);
        }
    }

    private void setScratchCount(String string) {
        if (string != null || !string.equalsIgnoreCase(""))
            PollfishOfferWall_count_textView.setText("Your daily limit left to collect this reward is = " + string);
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
                PollfishOfferWall_first = true;

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
                            Toast.makeText(activity, ">>>>>You get " + finalPoint + " coins! <<<<<", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Pollfish.this, Main.class);
                            startActivity(i);
                            finish();

                        } catch (NumberFormatException ex) {
                            Log.e("TAG", "onScratchComplete: " + ex.getMessage());
                        }
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }
                if (PollfishOfferWall_count == Integer.parseInt(someEarn_controller.getRewarded_and_interstitial_count())) {
                    if (rewardShow) {
                        Log.e(TAG, "onReachTarget: rewaded ads showing method");
                        showDailog();
                        rewardShow = false;
                        interstitialShow = true;
                        PollfishOfferWall_count = 1;
                    } else if (interstitialShow) {
                        Log.e(TAG, "onReachTarget: interstital ads showing method");
                        Constant.showInterstitialAds();
                        rewardShow = true;
                        interstitialShow = false;
                        PollfishOfferWall_count = 1;
                    }
                } else {
                    PollfishOfferWall_count += 1;
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



    public void initPollfish() {
        Params params = new Params.Builder(ads_id_controller.getPollfishKey())
                .rewardMode(true)
                .offerwallMode(true)
                .build();

        com.pollfish.Pollfish.initWith(this, params);
    }

    @Override
    public void onPollfishSurveyCompleted(@NotNull SurveyInfo surveyInfo) {
        points_text.setText(getString(R.string.win_coins, surveyInfo.getRewardValue() == null ? 100 : surveyInfo.getRewardValue()));
        if (first_time) {
            first_time = false;
            Log.e("onCollectReward", "Complete");
            Log.e("onCollectReward", "Complete" + random_points);
            String count = PollfishOfferWall_count_textView.getText().toString();
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
    public void onPollfishSurveyReceived(SurveyInfo surveyInfo) {
        dialog.dismiss();
        com.pollfish.Pollfish.show();

        Toast.makeText(activity, getString(R.string.survey_received), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPollfishClosed() {

    }

    @Override
    public void onPollfishOpened() {

    }

    @Override
    public void onPollfishSurveyNotAvailable() {
        Toast.makeText(activity, "Pollfish Survey Not Available", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserNotEligible() {
          }

    @Override
    public void onUserRejectedSurvey() {
           }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Pollfish.this, Main.class);
        startActivity(i);
        finish();
    }
}
