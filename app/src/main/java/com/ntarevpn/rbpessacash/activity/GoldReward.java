package com.ntarevpn.rbpessacash.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.ntarevpn.rbpessacash.util.AdsIDController;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.PointsUtil;
import com.ntarevpn.rbpessacash.util.SomeEarnController;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;

import java.util.Timer;
import java.util.TimerTask;

public class GoldReward extends AppCompatActivity {

    private static final String PLACEMENT_ID_SKIPPABLE_VIDEO = "video";

    private Context activity;
    private TextView pointsTextView;
    private AdsController adsController;
    private LinearLayout adLayout;
    private AdsIDController adsIDController;
    private PointsUtil pointsUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gold_reward_indratech);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        activity = this;
        CardView getMyCoin = findViewById(R.id.GetMyCoin);
        TextView goldRewardTextView = findViewById(R.id.GoldRewardTextView);
        pointsTextView = findViewById(R.id.points_text_in_toolbar);
        adsController = new AdsController(this);
        adLayout = findViewById(R.id.banner_container);

        adsIDController = new AdsIDController(this);
        pointsUtil = new PointsUtil(this);
        pointsUtil.initializePointsDialog(getLayoutInflater());
        pointsUtil.setListener(points -> {
            setPointsText();
        });

        try {
            int points = Integer.parseInt(Constant.getString(this, Constant.USER_POINTS));
            pointsUtil.setDailyPoints(points);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getMyCoin.setOnClickListener(view -> pointsUtil.checkDaily());
        SomeEarnController someEarnController = new SomeEarnController(this);
        goldRewardTextView.setText(someEarnController.getGoldRewardPoint());
        setPointsText();


        Constant.initVungle(this);
        Constant.loadAdVungle(this);


        Constant.initRewardedAdAdColony(this);

        if (adsController.getBannerAdsControl().equals("Admob")) {
            loadBannerAdmob();
        } else if (adsController.getBannerAdsControl().equals("Facebook")) {
            loadBannerFacebook();
        }


        Constant.initUnityAds(this);

    }

    private void setPointsText() {
        if (pointsTextView != null) {
            String userPoints = Constant.getString(activity, Constant.USER_POINTS);
            if (userPoints.equalsIgnoreCase("")) {
                userPoints = "0";
            }
            pointsTextView.setText(userPoints);
        }
    }


    private void initUnityAds() {
        String mGameId = adsIDController.getUnityGameId();
        UnityAds.setDebugMode(true);
        UnityAds.initialize(getBaseContext(), mGameId, true, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
                final java.util.Timer AdTimer = new Timer();
                AdTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(GoldReward.this::showUnitySkippAbleVideoAd);
                    }
                }, 5000);
            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {

            }
        });
    }

    private void showUnitySkippAbleVideoAd() {
        if (!UnityAds.isInitialized()) {

            return;
        }
        UnityAds.show(this, PLACEMENT_ID_SKIPPABLE_VIDEO, new IUnityAdsShowListener() {
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

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

        String typeOfAds = adsController.getCollectRewardAds();
        Constant.showAds(this, typeOfAds);
    }

    private void loadBannerAdmob() {
        final AdView adView = new AdView(activity);
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adView.setAdUnitId(adsIDController.getAdmobBannerId());
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
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(activity, getResources().getString(R.string.facebook_banner_ads_id), com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250);
        adView.loadAd();
        adLayout.addView(adView);

    }
}