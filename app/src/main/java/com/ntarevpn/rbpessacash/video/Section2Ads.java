package com.ntarevpn.rbpessacash.video;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ntarevpn.rbpessacash.activity.Main;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.ntarevpn.rbpessacash.util.AdsIDController;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.SomeEarnController;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Section2Ads extends AppCompatActivity implements IUnityAdsInitializationListener, IUnityAdsShowListener {
    private static final String DEFAULT_GAME_ID = "14851";
    private static final String TAG_UNITY_ADS_DEMO = "UnityAdsDemo";
    private static final String PLACEMENT_ID_SKIPPABLE_VIDEO = "video";
    private static final String PLACEMENT_ID_REWARDED_VIDEO = "rewardedVideo";

    private Context activity;


    private TextView Video_Ads_View_count_textView, points_textView, points_text;
    boolean first_time = true, Video_Ads_View_first = true;
    private int Video_Ads_View_count = 1;
    private final String TAG = "VideoAds";
    private String random_points;
    public int poiints = 0;
    AdsController ads_controller;
    SomeEarnController someEarn_controller;
    AdsIDController ads_id_controller;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.section2_ads);


        activity = this;


        someEarn_controller = new SomeEarnController(activity);
        ads_id_controller = new AdsIDController(activity);


        Video_Ads_View_count_textView = findViewById(R.id.limit_text);
        points_text = findViewById(R.id.textView_points_show);

        if (UnityAds.isInitialized()) {
            UnityAds.show(this, PLACEMENT_ID_SKIPPABLE_VIDEO, this);
        } else {
            UnityAds.initialize(activity, ads_id_controller.getUnityGameId());
        }

        onEarningInit();

        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog_indratech);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        if (Constant.isNetworkAvailable(activity)) {
                } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }


        if (Video_Ads_View_first) {
            Video_Ads_View_first = false;
            Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";

                random_points = String.valueOf(someEarn_controller.getUnityAds_Rewarded());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
            }
        }
    }

    private void onEarningInit() {

        String Video_Ads_ViewCount = Constant.getString(activity, Constant.Video_Ads_View_Reward_COUNT);
        if (Video_Ads_ViewCount.equals("0")) {
            Video_Ads_ViewCount = "";
            Log.e("TAG", "onInit: scratch card 0");
        }
        if (Video_Ads_ViewCount.equals("")) {
            Log.e("TAG", "onInit: scratch card empty vala part");
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(activity, Constant.LAST_DATE_Video_Ads_View_Reward);
            Log.e("TAG", "Lat date" + last_date);
            if (last_date.equals("")) {
                Log.e("TAG", "onInit: last date empty part");
                setVideoAdsViewCount(someEarn_controller.getVideo_Ads_Available_Views());
                Constant.setString(activity, Constant.Video_Ads_View_Reward_COUNT, someEarn_controller.getVideo_Ads_Available_Views());
                Constant.setString(activity, Constant.LAST_DATE_Video_Ads_View_Reward, currentDate);
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
                        Constant.setString(activity, Constant.LAST_DATE_Video_Ads_View_Reward, currentDate);
                        Constant.setString(activity, Constant.Video_Ads_View_Reward_COUNT, someEarn_controller.getVideo_Ads_Available_Views());
                        setVideoAdsViewCount(Constant.getString(activity, Constant.Video_Ads_View_Reward_COUNT));
                        Log.e("TAG", "onClick: today date added to preference" + currentDate);
                    } else {
                        setVideoAdsViewCount("0");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("TAG", "onInit: scracth card in preference part");
            setVideoAdsViewCount(Video_Ads_ViewCount);
        }
    }

    private void setVideoAdsViewCount(String string) {
        if (string != null || !string.equalsIgnoreCase(""))
            Video_Ads_View_count_textView.setText(string);
    }

    private void CallCoinAdded() {
        if (first_time) {
            first_time = false;
            Log.e("onVideo_Ads_View", "Complete");
            Log.e("onVideo_Ads_View", "Complete" + random_points);
            Log.e(TAG, "onVideo_Ads_ViewComplete: " + Video_Ads_View_count_textView.getText().toString());
            int counter = Integer.parseInt(Video_Ads_View_count_textView.getText().toString().trim());
            if (counter == 0) {
                RewardVideoPointAdded(0, "0", counter);
            } else {
                RewardVideoPointAdded(1, points_text.getText().toString(), counter);
            }
        }
    }

    private void RewardVideoPointAdded(final int addOrNoAddValue, final String points, final int counter) {

        first_time = true;
        Video_Ads_View_first = true;

        poiints = 0;
        if (addOrNoAddValue == 1) {
            if (points.equals("0")) {
                String current_counter = String.valueOf(counter - 1);
                Constant.setString(activity, Constant.Video_Ads_View_Reward_COUNT, current_counter);
                setVideoAdsViewCount(Constant.getString(activity, Constant.Video_Ads_View_Reward_COUNT));
            } else {
                String current_counter = String.valueOf(counter - 1);
                Constant.setString(activity, Constant.Video_Ads_View_Reward_COUNT, current_counter);
                setVideoAdsViewCount(Constant.getString(activity, Constant.Video_Ads_View_Reward_COUNT));
                try {
                    int finalPoint;
                    if (points.equals("")) {
                        finalPoint = 0;
                    } else {
                        finalPoint = Integer.parseInt(points);
                    }
                    poiints = finalPoint;
                    Constant.addPoints(activity, finalPoint, 0);

                    Intent i = new Intent(Section2Ads.this, Main.class);
                    startActivity(i);
                    finish();
                } catch (NumberFormatException ex) {

                }
            }
        } else {
        }
        if (Video_Ads_View_count == Integer.parseInt(someEarn_controller.getRewarded_and_interstitial_count())) {

        } else {
            Video_Ads_View_count += 1;
        }

    }

    private void initUnityAds() {
        String mGameId = ads_id_controller.getUnityGameId();
        UnityAds.setDebugMode(true);
        UnityAds.initialize(getApplicationContext(), mGameId, true, this);
    }

    private void showSkippableVideoAd() {
        if (!UnityAds.isInitialized()) {

            return;
        }
        UnityAds.show(this, PLACEMENT_ID_SKIPPABLE_VIDEO, this);
    }

    private void showRewardedVideoAd() {
        if (!UnityAds.isInitialized()) {
            return;
        }
        UnityAds.show(this, PLACEMENT_ID_REWARDED_VIDEO, this);

    }

    @Override
    public void onInitializationComplete() {
        final Timer AdTimer = new Timer();
        AdTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> showSkippableVideoAd());
            }
        }, 5000);
    }

    @Override
    public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
    }

    @Override
    public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
    }

    @Override
    public void onUnityAdsShowStart(String placementId) {
    }

    @Override
    public void onUnityAdsShowClick(String placementId) {
    }

    @Override
    public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
        CallCoinAdded();
        finish();
        Toast.makeText(this, ">>>>>You get " + points_text.getText().toString() + " coins!<<<<<", Toast.LENGTH_SHORT).show();
    }
}
