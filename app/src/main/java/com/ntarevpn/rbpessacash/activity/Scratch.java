package com.ntarevpn.rbpessacash.activity;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.ntarevpn.rbpessacash.services.PointsService;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.ntarevpn.rbpessacash.util.AdsIDController;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.SomeEarnController;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dev.skymansandy.scratchcardlayout.listener.ScratchListener;
import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout;

public class Scratch extends AppCompatActivity implements ScratchListener {

    private LinearLayout adLayout;
    private Context activity;
    private TextView collectReward_count_textView, points_textView, points_text;
    boolean first_time = true, collectReward_first = true;
    private int collectReward_count = 1;
    private final String TAG = "Scratch";
    private String random_points;
    public int poiints = 0;
    public boolean rewardShow = true, interstitialShow = true;
    ScratchCardLayout scratchCardLayout;


    AdsController ads_controller;
    SomeEarnController someEarn_controller;
    AdsIDController ads_id_controller;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scratchcard_activity_indratech);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Additional Scratch");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));


        activity = this;
        ads_controller = new AdsController(this);
        someEarn_controller = new SomeEarnController(this);
        ads_id_controller = new AdsIDController(this);

        adLayout = findViewById(R.id.banner_container);
        points_text = findViewById(R.id.textView_points_show);
        collectReward_count_textView = findViewById(R.id.limit_text);
        points_textView = findViewById(R.id.Balance_TextView);

        if (ads_controller.getBannerAdsControl().equals("Admob")) {
            loadBannerAdmob();
        } else if (ads_controller.getBannerAdsControl().equals("Facebook")) {
            loadBannerFacebook();
        }


        if (Constant.isNetworkAvailable(activity)) {

            String typeOfAds = ads_controller.getScratchWinAds1Interstitial();
            switch (typeOfAds) {
                case "Admob":
                    Constant.LoadAdmobInterstitialAd(this);
                    break;
                case "Facebook":
                    Constant.LoadFacebookInterstitialAd();
                    break;
                case "Vungle":
                    Constant.initVungleInterstitial();
                    Constant.loadVungleAdInterstitial();
                    break;

                case "AdColony":
                    Constant.AdcolonyInitInterstitialAd();
                    break;
                case "Startapp":
                    Constant.LoadStartAppInterstitial(this);
                    break;
                case "IronSource":
                    Constant.IronSourceInterstitialInt(this);
                    break;

            }

        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }
        onInit();
        setPointsText();




        if (collectReward_first) {
            collectReward_first = false;
            Log.e(TAG, "onCollectRewardStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";

                random_points = String.valueOf(someEarn_controller.getAdditional_Scratch_Point());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onCollectRewardStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
            }
        }

        scratchCardLayout = findViewById(R.id.scratch_view_layout);
        scratchCardLayout.setScratchListener(this);


        DateToday();
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();


            String typeOfAds = ads_controller.getCollectRewardAds();
            switch (typeOfAds) {
                case "Admob":
                    Constant.showRewardedAdmobAds(this);
                    break;
                case "Facebook":
                    Constant.showRewardedFacebookAds(this);
                    break;
                case "Vungle":
                    Constant.showRewardedVungleAds(this);
                    break;

                case "UnityAds":

                    Constant.ShowUnityAds(this);
                    break;
                case "AdColony":
                    Constant.ShowAdcolonyAds(this);

                    break;
                case "Startapp":
                    Constant.StartappRewardedVideo(this);

                    break;
                case "applovin":
                    Constant.ApplovinShowAds(this);

                    break;
            }
        });


        Constant.initVungle(this);
        Constant.loadAdVungle(this);


        Constant.initRewardedAdAdColony(this);


        Constant.IntUnityAds(this);

    }

    private void DateToday() {
        TextView text_view_date_activity = findViewById(R.id.text_view_date_activity);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        text_view_date_activity.setText(dateFormat.format(date));
    }

    private void setPointsText() {
        if (points_textView != null) {
            String userPoints = Constant.getString(activity, Constant.USER_POINTS);
            if (userPoints.equalsIgnoreCase("")) {
                userPoints = "0";
            }
            points_textView.setText(userPoints);
        }
    }

    private void onInit() {

        String CollectRewardCount = Constant.getString(activity, Constant.Additional_Scratch_COUNT);
        if (CollectRewardCount.equals("0")) {
            CollectRewardCount = "";
            Log.e("TAG", "onInit: scratch card 0");
        }
        if (CollectRewardCount.equals("")) {
            Log.e("TAG", "onInit: scratch card empty vala part");
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(activity, Constant.LAST_DATE_Additional_SCRATCH);
            Log.e("TAG", "Lat date" + last_date);
            if (last_date.equals("")) {
                Log.e("TAG", "onInit: last date empty part");
                setScratchCount(someEarn_controller.getAdditional_Scratch_Chances());
                Constant.setString(activity, Constant.Additional_Scratch_COUNT, someEarn_controller.getAdditional_Scratch_Chances());
                Constant.setString(activity, Constant.LAST_DATE_Additional_SCRATCH, currentDate);
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
                        Constant.setString(activity, Constant.LAST_DATE_Additional_SCRATCH, currentDate);
                        Constant.setString(activity, Constant.Additional_Scratch_COUNT, someEarn_controller.getAdditional_Scratch_Chances());
                        setScratchCount(Constant.getString(activity, Constant.Additional_Scratch_COUNT));
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
            setScratchCount(CollectRewardCount);
        }
    }

    private void setScratchCount(String string) {
        if (string != null || !string.equalsIgnoreCase(""))
            collectReward_count_textView.setText("" + string);
    }

    private void loadBannerAdmob() {

        final AdView adView = new AdView(activity);
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adView.setAdUnitId(ads_id_controller.getAdmobBannerId());
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adLayout.addView(adView);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.v("admob", "error" + loadAdError.getMessage());
            }
        });
        adView.loadAd(new AdRequest.Builder().build());

    }

    private void loadBannerFacebook() {
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(activity, "", com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250);
        adView.loadAd();
        adLayout.addView(adView);

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


                String typeOfAds = ads_controller.getScratchWinAds1Interstitial();
                switch (typeOfAds) {
                    case "Admob":
                        Constant.ShowAdmobInterstitialAds(Scratch.this);
                        break;
                    case "Facebook":
                        Constant.ShowFacebookInterstitialAds();
                        break;
                    case "Vungle":
                        Constant.playVungleAdInterstitial();
                        break;
                    case "AdColony":
                        Constant.AdcolonyDisplayInterstitialAd();
                        break;
                    case "Startapp":
                        Constant.startAppInterstitialShow();
                        break;
                    case "IronSource":
                        Constant.IronSourceInterstitialShow();
                        break;

                }

                first_time = true;
                collectReward_first = true;
                scratchCardLayout.resetScratch();
                poiints = 0;
                if (addOrNoAddValue == 1) {
                    if (points.equals("0")) {
                        String current_counter = String.valueOf(counter - 1);
                        Constant.setString(activity, Constant.Additional_Scratch_COUNT, current_counter);
                        setScratchCount(Constant.getString(activity, Constant.Additional_Scratch_COUNT));
                        dialog.dismiss();
                    } else {
                        String current_counter = String.valueOf(counter - 1);
                        Constant.setString(activity, Constant.Additional_Scratch_COUNT, current_counter);
                        setScratchCount(Constant.getString(activity, Constant.Additional_Scratch_COUNT));
                        try {
                            int finalPoint;
                            if (points.equals("")) {
                                finalPoint = 0;
                            } else {
                                finalPoint = Integer.parseInt(points);
                            }
                            poiints = finalPoint;
                            Constant.addPoints(activity, finalPoint, 0);
                            setPointsText();
                        } catch (NumberFormatException ex) {
                            Log.e("TAG", "onScratchComplete: " + ex.getMessage());
                        }
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }
                if (collectReward_count == Integer.parseInt(someEarn_controller.getRewarded_and_interstitial_count())) {
                    if (rewardShow) {
                        Log.e(TAG, "onReachTarget: rewaded ads showing method");
                        showDailog();
                        rewardShow = false;
                        interstitialShow = true;
                        collectReward_count = 1;
                    } else if (interstitialShow) {
                        Log.e(TAG, "onReachTarget: interstital ads showing method");


                        String typeOfAdsTimer = ads_controller.getScratchWinAds1Interstitial();
                        switch (typeOfAdsTimer) {
                            case "Admob":
                                Constant.ShowAdmobInterstitialAds(Scratch.this);
                                break;
                            case "Facebook":
                                Constant.ShowFacebookInterstitialAds();
                                break;
                            case "Vungle":
                                Constant.playVungleAdInterstitial();
                                break;
                            case "AdColony":
                                Constant.AdcolonyDisplayInterstitialAd();
                                break;
                            case "Startapp":
                                Constant.startAppInterstitialShow();
                                break;
                            case "IronSource":
                                Constant.IronSourceInterstitialShow();
                                break;

                        }

                        rewardShow = true;
                        interstitialShow = false;
                        collectReward_count = 1;
                    }
                } else {
                    collectReward_count += 1;
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

            String typeOfAds = ads_controller.getScratchWinAds();
            switch (typeOfAds) {
                case "Admob":
                    Constant.showRewardedAdmobAds(this);
                    break;
                case "Facebook":
                    Constant.showRewardedFacebookAds(this);
                    break;
                case "Vungle":
                    Constant.showRewardedVungleAds(this);
                    break;

                case "UnityAds":

                    Constant.ShowUnityAds(this);
                    break;
                case "AdColony":
                    Constant.ShowAdcolonyAds(this);

                    break;
                case "Startapp":
                    Constant.StartappRewardedVideo(this);

                    break;
                case "applovin":
                    Constant.ApplovinShowAds(this);

                    break;
            }
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
                setPointsText();
            }
        });

        dialog.show();
    }


    @Override
    public void onScratchComplete() {
        if (first_time) {
            first_time = false;
            Log.e("onCollectReward", "Complete");
            Log.e("onCollectReward", "Complete" + random_points);

            int counter = Integer.parseInt(collectReward_count_textView.getText().toString());
            if (counter == 0) {
                showDialogPoints(0, "0", counter);

            } else {
                showDialogPoints(1, points_text.getText().toString(), counter);
            }
        }

    }

    @Override
    public void onScratchProgress(@NonNull ScratchCardLayout scratchCardLayout, int i) {

        if (collectReward_first) {
            collectReward_first = false;
            Log.e(TAG, "onCollectRewardStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";
                random_points = String.valueOf(someEarn_controller.getAdditional_Scratch_Point());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onCollectRewardStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
            }
        }
    }

    @Override
    public void onScratchStarted() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

        String typeOfAds = ads_controller.getScratchWinAds();
        switch (typeOfAds) {
            case "Admob":
                Constant.showRewardedAdmobAds(this);
                break;
            case "Facebook":
                Constant.showRewardedFacebookAds(this);
                break;
            case "Vungle":
                Constant.showRewardedVungleAds(this);
                break;

            case "UnityAds":

                Constant.ShowUnityAds(this);

                break;
            case "AdColony":
                Constant.ShowAdcolonyAds(this);

                break;
            case "Startapp":
                Constant.StartappRewardedVideo(this);

                break;
            case "applovin":
                Constant.ApplovinShowAds(this);

                break;
        }
    }
}