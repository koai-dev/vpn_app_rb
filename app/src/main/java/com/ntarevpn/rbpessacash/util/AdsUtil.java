package com.ntarevpn.rbpessacash.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.appodeal.ads.Appodeal;
import com.appodeal.ads.RewardedVideoCallbacks;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.ntarevpn.rbpessacash.activity.PurchaseActivity;
import  com.ntarevpn.rbpessacash.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.ntarevpn.rbpessacash.databinding.PremiumDialogIndratechBinding;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

import ph.gemeaux.materialloadingindicator.MaterialCircularIndicator;

public class AdsUtil implements IUnityAdsShowListener, RewardedVideoAdListener, RewardedVideoCallbacks, IUnityAdsInitializationListener, AdEventListener, MaxRewardedAdListener {
    private static final String TAG = "ADS-UTIL";

    private final Activity activity;
    private final AdsController adsController;
    private final AdsIDController adsIDController;
    private final Dialog dialog;
    private final MaterialCircularIndicator progressIndicator;
    private OnAdFinishedListener listener;
    private RewardedVideoAd rewardedVideoAd;
    private StartAppAd startAppAd;
    private MaxRewardedAd rewardedAd;
    private RewardedAd mRewardedAd;
    private Boolean facebookAd = false, startAd = false, isRewardVideoLoaded = false;

    public AdsUtil(Activity activity) {
        this.activity = activity;
        this.progressIndicator = new MaterialCircularIndicator(activity);
        this.dialog = new Dialog(activity);
        this.adsController = new AdsController(activity);
        this.adsIDController = new AdsIDController(activity);

        initializeMiscellaneous();
        initializeDialog();
    }

    private void initializeMiscellaneous() {
        progressIndicator.setTrackCornerRadius(10);
        progressIndicator.setCanceleable(false);
    }

    public void initializeAds() {
        progressIndicator.show();
        String adsType = WebAPI.ADS_TYPE;

        switch (adsType) {
            case WebAPI.ADS_TYPE_ADMOB:
                initializeAdMobAd();
                break;
            case WebAPI.ADS_TYPE_FACEBOOK_ADS:
                rewardedVideoAd = new RewardedVideoAd(activity, WebAPI.ADMOB_REWARD_ID);
                rewardedVideoAd.loadAd(
                        rewardedVideoAd.buildLoadAdConfig()
                                .withAdListener(this)
                                .build());
                break;
            case WebAPI.TYPE_APV:
                rewardedAd = MaxRewardedAd.getInstance(WebAPI.ADMOB_REWARD_ID, activity);
                rewardedAd.loadAd();
                break;
            case WebAPI.ADS_TYPE_STARTAPP:
                startAppAd = new StartAppAd(activity);
                startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, this);
                break;
            case WebAPI.TYPE_UT:
                if (!UnityAds.isInitialized()) {
                    AdsIDController adsIDController = new AdsIDController(activity);
                    UnityAds.initialize(activity.getApplicationContext(), adsIDController.getUnityGameId(), this);
                } else {
                    progressIndicator.dismiss();
                }
            case WebAPI.TYPE_MP:
                progressIndicator.dismiss();
                showFailed();
            case WebAPI.ADS_TYPE_APPODEAL:
                Appodeal.initialize(activity, WebAPI.ADMOB_REWARD_ID, Appodeal.REWARDED_VIDEO);
                progressIndicator.show();
                if(WebAPI.rewardedVideoLoaded) {
                    progressIndicator.dismiss();
                    isRewardVideoLoaded = true;
                }

        }
    }

    private void initializeAdMobAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(activity, WebAPI.ADMOB_REWARD_ID,
                adRequest, new RewardedAdLoadCallback(){
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mRewardedAd = null;
                        progressIndicator.dismiss();
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        progressIndicator.dismiss();

                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                Log.d(TAG, "Ad was shown.");
                                mRewardedAd = null;
                                listener.onFinished();
                                progressIndicator.dismiss();
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Log.d(TAG, "Ad was dismissed.");
                                mRewardedAd = null;
                                progressIndicator.dismiss();
                            }
                        });
                    }
                });
    }
    
    private void initializeDialog() {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        PremiumDialogIndratechBinding binding = PremiumDialogIndratechBinding.inflate(layoutInflater);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(binding.getRoot());
        
        binding.purchase.setOnClickListener(v -> {
            Intent i = new Intent(activity, PurchaseActivity.class);
            activity.startActivity(i);
        });
        
        binding.watchAd.setOnClickListener(v -> watchAds());
    }

    public void watchAds() {
        switch (WebAPI.ADS_TYPE) {
            case WebAPI.ADS_TYPE_ADMOB:
                if (mRewardedAd != null) {
                    mRewardedAd.show(activity, rewardItem -> {
                        Log.d(TAG, "The user earned the reward.");
                        int rewardAmount = rewardItem.getAmount();

                        Constant.addPoints(activity, rewardAmount, 0);
                        listener.onFinished();
                    });
                } else {
                    showFailed();
                }
                break;
            case WebAPI.ADS_TYPE_FACEBOOK_ADS:
                if (facebookAd)
                    rewardedVideoAd.show();
                else {
                    showFailed();
                }

                break;
            case WebAPI.ADS_TYPE_STARTAPP:
                if (startAd) {
                    startAd = false;
                    startAppAd.showAd();

                    startAppAd.setVideoListener(() -> {
                        Log.d(TAG, ": Rewarded video completed!");
                        listener.onFinished();
                        startAppAd = null;
                    });
                } else {
                    showFailed();
                }
                break;
            case WebAPI.TYPE_UT:
                UnityAds.show(activity, "rewardedVideo", new UnityAdsShowOptions(), this);
                break;
            case WebAPI.TYPE_APV:
                if (rewardedAd.isReady()) {
                    rewardedAd.showAd();
                } else {
                    showFailed();
                }
                break;
            case WebAPI.ADS_TYPE_APPODEAL:
                if (isRewardVideoLoaded) {
                    Log.v("APPODEAL", "reward loaded");
                    Appodeal.show(activity, Appodeal.REWARDED_VIDEO);
                } else {
                    showFailed();
                }
                break;
            case WebAPI.TYPE_MP:
            default:
                showFailed();
                break;
        }
    }
    
    public void showDialog() {
        dialog.show();
    }
    
    public void hideDialog() {
        dialog.hide();
    }

    public void setListener(OnAdFinishedListener listener) {
        this.listener = listener;
    }

    private void showFailed() {
        Toast.makeText(activity, "Ads Not Available Or Buy Subscription", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitializationComplete() {
        progressIndicator.dismiss();
    }

    @Override
    public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
        progressIndicator.dismiss();
        Log.d(TAG, "Failed to load Unity Ads: " + message);
    }

    @Override
    public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
        Log.e(TAG, "onUnityAdsShowFailure: " + error + " - " + message);
        showFailed();
    }

    @Override
    public void onUnityAdsShowStart(String placementId) {
        Log.v(TAG, "onUnityAdsShowStart: " + placementId);
    }

    @Override
    public void onUnityAdsShowClick(String placementId) {
        Log.v(TAG,"onUnityAdsShowClick: " + placementId);
    }

    @Override
    public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
        Log.v(TAG,"onUnityAdsShowComplete: " + placementId);
        listener.onFinished();
    }

    @Override
    public void onError(Ad ad, AdError error) {
        Log.e(TAG, "FB Rewarded video ad failed to load: " + error.getErrorMessage());
        progressIndicator.dismiss();
    }

    @Override
    public void onAdLoaded(Ad ad) {
        Log.d(TAG, "FB Rewarded video ad is loaded and ready to be displayed!");
        facebookAd = true;
        progressIndicator.dismiss();
    }

    @Override
    public void onAdClicked(Ad ad) {
        Log.d(TAG, "FB Rewarded video ad clicked!");
    }

    @Override
    public void onLoggingImpression(Ad ad) {
        Log.d(TAG, "FB Rewarded video ad impression logged!");
    }

    @Override
    public void onRewardedVideoCompleted() {
        Log.d(TAG, "FB Rewarded video completed!");
        facebookAd = false;
        listener.onFinished();

    }

    @Override
    public void onRewardedVideoClosed() {
        Log.d(TAG, "FB Rewarded video ad closed!");
    }

    @Override
    public void onReceiveAd(@NonNull com.startapp.sdk.adsbase.Ad ad) {
        startAd = true;
        progressIndicator.dismiss();
    }

    @Override
    public void onFailedToReceiveAd(@Nullable com.startapp.sdk.adsbase.Ad ad) {
        progressIndicator.dismiss();
        showFailed();
    }

    @Override
    public void onRewardedVideoStarted(MaxAd ad) {

    }

    @Override
    public void onRewardedVideoCompleted(MaxAd ad) {
        listener.onFinished();
    }

    @Override
    public void onUserRewarded(MaxAd ad, MaxReward reward) {

    }

    @Override
    public void onAdLoaded(MaxAd ad) {
        Log.d("APPLOVINADSTATUS", " reward ad loaded");
        progressIndicator.dismiss();
    }

    @Override
    public void onAdDisplayed(MaxAd ad) {

    }

    @Override
    public void onAdHidden(MaxAd ad) {

    }

    @Override
    public void onAdClicked(MaxAd ad) {

    }

    @Override
    public void onAdLoadFailed(String adUnitId, MaxError error) {
        Log.e(TAG, "Reward ad not loaded");
        progressIndicator.dismiss();
    }

    @Override
    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

    }

    @Override
    public void onRewardedVideoLoaded(boolean isPrecache) {
        isRewardVideoLoaded = true;
        WebAPI.rewardedVideoLoaded = true;
        progressIndicator.dismiss();
    }
    @Override
    public void onRewardedVideoFailedToLoad() {
        progressIndicator.dismiss();
        Log.v("APPODEAL", "reward not loaded");
    }
    @Override
    public void onRewardedVideoShown() {
        progressIndicator.dismiss();
        Log.v("APPODEAL", "reward shown");
    }
    @Override
    public void onRewardedVideoShowFailed() {
        progressIndicator.dismiss();
        Log.v("APPODEAL", "reward failed");
    }
    @Override
    public void onRewardedVideoClicked() {
    }
    @Override
    public void onRewardedVideoFinished(double amount, String name) {
        Log.v("APPODEAL", "reward finish");
    }
    @Override
    public void onRewardedVideoClosed(boolean finished) {
        listener.onFinished();

        Log.v("APPODEAL", "reward closed");
    }
    @Override
    public void onRewardedVideoExpired() {
    }

    public interface OnAdFinishedListener {
        void onFinished();
    }
}
