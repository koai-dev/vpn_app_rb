package com.ntarevpn.rbpessacash.util;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.multidex.BuildConfig;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyZone;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.QueryPurchasesParams;
import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinSdk;
import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.ntarevpn.rbpessacash.activity.Login;
import com.ntarevpn.rbpessacash.models.User;
import com.ntarevpn.rbpessacash.services.PointsService;
import com.ntarevpn.rbpessacash.sharedPref.PrefManager;
import com.ntarevpn.rbpessacash.AppsConfig;
import  com.ntarevpn.rbpessacash.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.ironsource.mediationsdk.IronSource;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.VideoListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.vungle.warren.AdConfig;
import com.vungle.warren.InitCallback;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.error.VungleException;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class Constant {

    public static final String IS_LOGIN = "IsLogin";
    public static final String USER_BLOCKED = "user_blocked";
    public static final String USER_NAME = "user_name";
    public static final String USER_NUMBER = "user_number";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_POINTS = "user_points";
    public static final String USER_REFFER_CODE = "user_reffer_code";
    public static final String LAST_DATE = "Last_Date";
    public static final String DEACTIVATE_FEEDBACK_REMINDER = "feedback_reminder";
    public static final String GoldReward_DATE = "GoldReward_Date";
    public static final String King_Pot_DATE = "King_Pot_Date";
    public static final String Pay_Earn_Gift_DATE = "Pay_Earn_Gift_Date";
    public static final String LAST_TIME_ADD_TO_SERVER = "last_time_added";
    public static final String REFER_CODE = "refer_code";
    public static final String USER_IMAGE = "user_image";
    public static final String Additional_Scratch_COUNT = "additional_scratch";
    public static final String LAST_DATE_Additional_SCRATCH = "last_date_additional_scratch";
    public static final String Great_Scratch_COUNT = "great_scratch";
    public static final String LAST_DATE_Great_SCRATCH = "last_date_great_scratch";
    public static final String Extra_Scratch_COUNT = "extra_scratch";
    public static final String LAST_DATE_Extra_SCRATCH = "last_date_extra_scratch";
    public static final String Collect_Reward_COUNT = "collect_reward";
    public static final String LAST_DATE_Collect_Reward = "last_date_collect_reward";
    public static final String EverydayGift_Reward_COUNT = "EverydayGift_reward";
    public static final String LAST_DATE_EverydayGift_Reward = "last_date_EverydayGift_reward";
    public static final String OfferWall_Reward_COUNT = "OfferWall_reward";
    public static final String LAST_DATE_OfferWall_Reward = "last_date_OfferWall_reward";
    public static final String CompleteSurvey_Reward_COUNT = "CompleteSurvey_reward";
    public static final String LAST_DATE_CompleteSurvey_Reward = "last_date_CompleteSurvey_reward";
    public static final String Video_Ads_View_Reward_COUNT = "Video_Ads_View_reward";
    public static final String LAST_DATE_Video_Ads_View_Reward = "last_date_Video_Ads_View_reward";
    public static final String Open_Reward_COUNT = "open_reward";
    public static final String LAST_DATE_Open_Reward = "last_date_open_reward";
    public static final String SPIN_COUNT = "spin_count";
    public static final String LAST_DATE_SPIN = "last_date_spin";
    public static final String TICTAC_COUNT = "tictac_count";
    public static final String LAST_DATE_TICTAC = "last_date_tictac";
    public static final String USER_ID = "user_id";
    public static final String IS_UPDATE = "user_update";
    public static final String USER_PASSWORD = "password";
    public static final String TAG = "Constant";
    private static final String UNITY_PLACEMENT_ID_REWARDED_VIDEO = "rewardedVideo";
    private final static String APP_ID = "app7dd43d11ae344f74af";
    private final static String BANNER_ZONE_ID = "vz5ccc44c8de0c43f7a8";
    private final static String INTERSTITIAL_ZONE_ID = "vz896de92b61e14e6695";
    private static final StartAppAd startAppAd = new StartAppAd(AppsConfig.getContext());
    private static final String PLACEMENT_INTERSTITIAL = "DefaultInterstitial";
    public static InterstitialAd interstitial_Ad;
    public static com.facebook.ads.InterstitialAd interstitialAd;
    public static RewardedAd rewarded_ad;
    public static boolean isShowInterstitial = true;
    public static boolean isShowRewarded = true;
    public static boolean isShowfacebookRewarded = true;
    public static boolean isAttachedInterstitial = true;
    public static boolean isAttachedfacebookInterstitial = true;
    public static boolean isShowFacebookInterstitial = true;
    public static RewardedAdLoadCallback adLoadCallback;
    public static InterstitialAdListener interstitialAdListener;
    public static RewardedVideoAd rewardedVideoAd;
    public static ProgressDialog alertDialog;
    public static AdsController ads_controller;
    public static AdsIDController ads_id_controller;
    public static Activity context;
    public static AppLovinAd appLovinAd;
    public static ConnectivityManager cm;
    public static android.net.NetworkInfo wifi;
    public static android.net.NetworkInfo datac;
    private static String placementID, Vungel_InterstitialPlacementID;
    private static String mGameId;
    private static Dialog dialogVpn;
    private static Dialog dialogAdcolony;
    private static Dialog dialogAdmobVideoAds;
    private static Dialog dialogStartAppVideoAds;
    private static Dialog dialogFacebookVideoAds;
    private static Dialog ApplovinDialog;
    private static PrefManager prefManager;
    private static AdColonyInterstitial rewardAdColony;
    private static AdColonyInterstitialListener rewardListener;
    private static AdColonyAdOptions rewardAdOptions;
    private static boolean isRewardLoaded;
    private static AdColonyInterstitial interstitialAdColony;
    private static AdColonyInterstitialListener interstitialListener;
    private static AdColonyAdOptions interstitialAdOptions;
    private static boolean isInterstitialLoaded;
    private static LinearLayout adLayout;
    public SomeEarnController someEarn_controller;

    public static void initVungleInterstitial() {
        ads_id_controller = new AdsIDController(AppsConfig.getContext());
        Vungle.init(ads_id_controller.getVungleKey(), AppsConfig.getContext().getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {

                Collection<String> placements = Vungle.getValidPlacements();
                String[] placementsArray = placements.toArray(new String[0]);

                Vungel_InterstitialPlacementID = placementsArray[0];
            }

            @Override
            public void onError(VungleException e) {

            }

            @Override
            public void onAutoCacheAdAvailable(String pid) {

            }
        });
    }

    public static void loadVungleAdInterstitial() {
        ads_id_controller = new AdsIDController(AppsConfig.getContext());
        Vungel_InterstitialPlacementID = ads_id_controller.getVungleInterstitialPlacementID();
        Vungle.loadAd(Vungel_InterstitialPlacementID, new LoadAdCallback() {
            @Override
            public void onAdLoad(String id) {

            }

            @Override
            public void onError(String id, VungleException e) {

            }
        });
    }

    public static void playVungleAdInterstitial() {
        ads_id_controller = new AdsIDController(AppsConfig.getContext());
        Vungel_InterstitialPlacementID = ads_id_controller.getVungleInterstitialPlacementID();
        Vungle.playAd(Vungel_InterstitialPlacementID, new AdConfig(), new PlayAdCallback() {
            @Override
            public void onAdStart(String placementReferenceID) {

            }

            @Override
            public void onAdViewed(String placementReferenceID) {

            }


            @Override
            public void onAdEnd(String id, boolean completed, boolean isCTAClicked) {


            }

            @Override
            public void onAdEnd(String placementReferenceID) {

            }

            @Override
            public void onAdClick(String placementReferenceID) {

            }

            @Override
            public void onAdRewarded(String placementReferenceID) {

            }

            @Override
            public void onAdLeftApplication(String placementReferenceID) {

            }

            @Override
            public void creativeId(String creativeId) {

            }

            @Override
            public void onError(String id, VungleException e) {

            }
        });


    }


    public static void IronSourceInterstitialInt(Activity activity) {
        ads_id_controller = new AdsIDController(AppsConfig.getContext());
        IronSource.init(activity, ads_id_controller.getIronSourceAppKey(), IronSource.AD_UNIT.INTERSTITIAL);
        IronSource.loadInterstitial();
    }

    public static void IronSourceInterstitialShow() {
        IronSource.showInterstitial(PLACEMENT_INTERSTITIAL);
    }

    public static void startAppInterstitialShow() {
        startAppAd.showAd();
    }

    public static void LoadStartAppInterstitial(Activity activity) {
        ads_id_controller = new AdsIDController(AppsConfig.getContext());
        StartAppSDK.init(activity, ads_id_controller.getStartaAppAppId(), true);
        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);
        StartAppAd.disableSplash();
        StartAppAd.enableConsent(activity, false);
        StartAppSDK.setTestAdsEnabled(false);

        startAppAd.loadAd();
    }

    public static void StartappRewardedVideo(final Activity context) {

        dialogStartAppVideoAds = new Dialog(context);
        dialogStartAppVideoAds.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogStartAppVideoAds.setContentView(R.layout.loading_dialog_indratech);
        dialogStartAppVideoAds.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogStartAppVideoAds.show();

        final StartAppAd rewardedVideo = new StartAppAd(context);
        rewardedVideo.setVideoListener((VideoListener) () -> Toast.makeText(context, "Video Completed", Toast.LENGTH_SHORT).show());

        rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
            @Override
            public void onReceiveAd(@NonNull com.startapp.sdk.adsbase.Ad ad) {
                rewardedVideo.showAd();
                dialogStartAppVideoAds.dismiss();
            }

            @Override
            public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

            }
        });
    }

    public static void ApplovinShowAds(final Activity context) {

        ApplovinDialog = new Dialog(context);
        ApplovinDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ApplovinDialog.setContentView(R.layout.loading_dialog_indratech);
        ApplovinDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ApplovinDialog.show();

        AppLovinInterstitialAdDialog appLovinInterstitialAd = AppLovinInterstitialAd.create(AppLovinSdk.getInstance(context), context);
        appLovinInterstitialAd.show();
        appLovinInterstitialAd.setAdDisplayListener(new AppLovinAdDisplayListener() {
            @Override
            public void adDisplayed(AppLovinAd ad) {
                ApplovinDialog.dismiss();
            }

            @Override
            public void adHidden(AppLovinAd ad) {

            }
        });

    }


    public static void initRewardedAdAdColony(final Activity activity) {
        ads_id_controller = new AdsIDController(AppsConfig.getContext());
        AdColonyAppOptions appOptions = new AdColonyAppOptions().setKeepScreenOn(true);
        AdColony.configure(activity, appOptions, ads_id_controller.getAdcolonyAppId(), ads_id_controller.getAdcolonyRewardZoneId(), ads_id_controller.getAdcolonyIntZoneId());

        AdColony.setRewardListener(reward -> {
            if (reward.success()) {
                Toast.makeText(activity, "Reward Earned", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Reward Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        rewardListener = new AdColonyInterstitialListener() {
            @Override
            public void onRequestFilled(AdColonyInterstitial adReward) {
                rewardAdColony = adReward;
                isRewardLoaded = true;
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
            }

            @Override
            public void onOpened(AdColonyInterstitial ad) {
                super.onOpened(ad);
                dialogAdcolony.dismiss();
            }

            @Override
            public void onClosed(AdColonyInterstitial ad) {
                super.onClosed(ad);
                AdColony.requestInterstitial(ads_id_controller.getAdcolonyRewardZoneId(), rewardListener, rewardAdOptions);

            }

            @Override
            public void onClicked(AdColonyInterstitial ad) {
                super.onClicked(ad);
            }

            @Override
            public void onLeftApplication(AdColonyInterstitial ad) {
                super.onLeftApplication(ad);
            }

            @Override
            public void onExpiring(AdColonyInterstitial ad) {
                super.onExpiring(ad);
            }
        };
        rewardAdOptions = new AdColonyAdOptions()
                .enableConfirmationDialog(false)
                .enableResultsDialog(false);
        AdColony.requestInterstitial(ads_id_controller.getAdcolonyRewardZoneId(), rewardListener, rewardAdOptions);


    }

    public static void AdcolonyDisplayInterstitialAd() {
        if (interstitialAdColony != null && isInterstitialLoaded) {
            interstitialAdColony.show();
            isInterstitialLoaded = false;
        } else {

        }
    }

    public static void AdcolonyInitInterstitialAd() {

        interstitialListener = new AdColonyInterstitialListener() {
            @Override
            public void onRequestFilled(AdColonyInterstitial adIn) {
                interstitialAdColony = adIn;
                isInterstitialLoaded = true;
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                super.onRequestNotFilled(zone);
            }

            @Override
            public void onOpened(AdColonyInterstitial ad) {
                super.onOpened(ad);
            }

            @Override
            public void onClosed(AdColonyInterstitial ad) {
                super.onClosed(ad);
                AdColony.requestInterstitial(INTERSTITIAL_ZONE_ID, interstitialListener, interstitialAdOptions);
            }

            @Override
            public void onClicked(AdColonyInterstitial ad) {
                super.onClicked(ad);
            }

            @Override
            public void onLeftApplication(AdColonyInterstitial ad) {
                super.onLeftApplication(ad);
            }

            @Override
            public void onExpiring(AdColonyInterstitial ad) {
                super.onExpiring(ad);
            }
        };
        interstitialAdOptions = new AdColonyAdOptions();
        AdColony.requestInterstitial(INTERSTITIAL_ZONE_ID, interstitialListener, interstitialAdOptions);
    }


    public static void ShowAdcolonyAds(final Activity activity) {

        dialogAdcolony = new Dialog(activity);
        dialogAdcolony.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAdcolony.setContentView(R.layout.loading_dialog_indratech);
        dialogAdcolony.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogAdcolony.show();
        if (rewardAdColony != null && isRewardLoaded) {
            rewardAdColony.show();
            dialogAdcolony.dismiss();
        } else {
            Toast.makeText(activity, "Reward Ad Is Not Loaded Yet Please Try Again ", Toast.LENGTH_SHORT).show();
        }

    }

    public static void FacebookRewardedVideoAd(final Activity context) {
        ads_id_controller = new AdsIDController(AppsConfig.getContext());

        rewardedVideoAd = new RewardedVideoAd(AppsConfig.getContext(), ads_id_controller.getAdmobRewardedId());
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                Log.e(TAG, "Rewarded video ad failed to load: " + adError.getErrorMessage());
                dialogFacebookVideoAds.dismiss();
                Toast.makeText(context, "Rewarded Video ad Failed to Load", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                dialogFacebookVideoAds.dismiss();
                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");
                if (!isShowfacebookRewarded) {
                    showRewardedAdmobAds(context);
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.d(TAG, "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.d(TAG, "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                Toast.makeText(context, "Rewarded video completed", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Rewarded video completed!");
            }

            @Override
            public void onRewardedVideoClosed() {
                Log.d(TAG, "Rewarded video ad closed!");
            }
        };
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());


    }

    public static void ShowFacebookVideo(final Activity context) {

        dialogFacebookVideoAds = new Dialog(context);
        dialogFacebookVideoAds.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFacebookVideoAds.setContentView(R.layout.loading_dialog_indratech);
        dialogFacebookVideoAds.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFacebookVideoAds.show();

        if (rewardedVideoAd != null && rewardedVideoAd.isAdLoaded()) {
            rewardedVideoAd.show();
            isShowfacebookRewarded = true;
        } else {
            isShowfacebookRewarded = false;
            Constant.showToastMessage(context, "Video Ad not Ready");
        }
    }

    public static void LoadAdmobInterstitialAd(Activity activity) {
        ads_id_controller = new AdsIDController(AppsConfig.getContext());

        if (interstitial_Ad == null) {

            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(AppsConfig.getContext(), ads_id_controller.getAdmob_Interstitial_id(), adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            interstitial_Ad = interstitialAd;

                            Log.i("INTERSTITIAL", "onAdLoaded");

                            if (interstitial_Ad != null) {
                                interstitial_Ad.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        Log.d("TAG", "The ad was dismissed.");

                                    }

                                    public void onAdFailedToShowFullScreenContent(com.facebook.ads.AdError adError) {
                                        Log.d("TAG", "The ad failed to show.");

                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        interstitial_Ad = null;
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });

                            } else {
                                Log.d("TAG", "The interstitial ad wasn't ready yet.");

                            }
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.i("INTERSTITIAL", loadAdError.getMessage());
                            interstitial_Ad = null;
                        }
                    });

            isAttachedInterstitial = false;
        } else {
            interstitial_Ad.show(activity);
        }
    }

    public static void LoadFacebookInterstitialAd() {
        ads_id_controller = new AdsIDController(AppsConfig.getContext());
        if (interstitialAd == null) {
            interstitialAd = new com.facebook.ads.InterstitialAd(AppsConfig.getContext(), ads_id_controller.getFacebookInterstitialId());
            AttachFacebookListner();
            interstitialAd.loadAd(
                    interstitialAd.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());
            isAttachedfacebookInterstitial = false;
        } else {
            interstitialAd.loadAd();
        }
    }

    public static void loadInterstitialAd(Activity activity) {
        ads_controller = new AdsController(AppsConfig.getContext());
        ads_id_controller = new AdsIDController(AppsConfig.getContext());
        String typeOfAds = ads_controller.getIntAdsApp();
        switch (typeOfAds) {
            case "Facebook":
                if (interstitialAd == null) {
                    interstitialAd = new com.facebook.ads.InterstitialAd(AppsConfig.getContext(), ads_id_controller.getFacebookInterstitialId());
                    AttachFacebookListner();
                    interstitialAd.loadAd(
                            interstitialAd.buildLoadAdConfig()
                                    .withAdListener(interstitialAdListener)
                                    .build());
                    isAttachedfacebookInterstitial = false;
                } else {
                    interstitialAd.loadAd();
                }
                break;
            case "Admob":
                if (interstitial_Ad == null) {

                    AdRequest adRequest = new AdRequest.Builder().build();
                    InterstitialAd.load(AppsConfig.getContext(), ads_id_controller.getAdmob_Interstitial_id(), adRequest,
                            new InterstitialAdLoadCallback() {
                                @Override
                                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                    interstitial_Ad = interstitialAd;
                                    hideProgressDialog();
                                    Log.i("INTERSTITIAL", "onAdLoaded");

                                    if (interstitial_Ad != null) {

                                        interstitial_Ad.show((Activity) AppsConfig.getContext());
                                        interstitial_Ad.setFullScreenContentCallback(new FullScreenContentCallback() {
                                            @Override
                                            public void onAdDismissedFullScreenContent() {
                                                Log.d("TAG", "The ad was dismissed.");

                                            }

                                            public void onAdFailedToShowFullScreenContent(com.facebook.ads.AdError adError) {
                                                Log.d("TAG", "The ad failed to show.");

                                            }

                                            @Override
                                            public void onAdShowedFullScreenContent() {
                                                interstitial_Ad = null;
                                                Log.d("TAG", "The ad was shown.");
                                            }
                                        });

                                    } else {
                                        Log.d("TAG", "The interstitial ad wasn't ready yet.");

                                    }
                                }

                                @Override
                                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                    Log.i("INTERSTITIAL", loadAdError.getMessage());
                                    interstitial_Ad = null;
                                    hideProgressDialog();
                                }
                            });

                    isAttachedInterstitial = false;
                } else {
                    interstitial_Ad.show(activity);
                }
                break;
            case "StartApp":
                startAppAd.loadAd();
                break;


        }
    }

    public static void AttachFacebookListner() {
        if (isAttachedInterstitial) {
            interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                    Log.e(TAG, "Interstitial ad displayed.");
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {

                    Log.e(TAG, "Interstitial ad dismissed.");
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                    if (!isShowFacebookInterstitial) {
                        showInterstitialAds();
                    }
                }

                @Override
                public void onAdClicked(Ad ad) {
                    Log.d(TAG, "Interstitial ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    Log.d(TAG, "Interstitial ad impression logged!");
                }
            };
        }
    }

    public static void hideProgressDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    public static void ShowAdmobInterstitialAds(Activity activity) {
        if (interstitial_Ad != null) {
            hideProgressDialog();
            interstitial_Ad.show(activity);

            isShowInterstitial = true;
        } else {
            hideProgressDialog();
            isShowInterstitial = false;
        }
    }

    public static void ShowFacebookInterstitialAds() {
        if (interstitialAd != null && interstitialAd.isAdLoaded()) {
            hideProgressDialog();
            interstitialAd.show();
            isShowFacebookInterstitial = true;
        } else {
            hideProgressDialog();
            isShowFacebookInterstitial = false;
        }
    }

    public static void showInterstitialAds() {
        ads_controller = new AdsController(AppsConfig.getContext());
        String typeOfAds = ads_controller.getIntAdsApp();
        if (typeOfAds.equals("Admob")) {
            if (interstitial_Ad != null) {
                hideProgressDialog();
                interstitial_Ad.show((Activity) AppsConfig.getContext());

                isShowInterstitial = true;
            } else {
                hideProgressDialog();
                isShowInterstitial = false;
            }
        } else if (typeOfAds.equals("Facebook")) {
            if (interstitialAd != null && interstitialAd.isAdLoaded()) {
                hideProgressDialog();
                interstitialAd.show();
                isShowFacebookInterstitial = true;
            } else {
                hideProgressDialog();
                isShowFacebookInterstitial = false;
            }
        }
    }

    public static void showRewardedAdmobAds(final Activity context) {

        if (rewarded_ad != null) {
            rewarded_ad.show(context, rewardItem -> {
                isShowRewarded = true;
                Log.d("TAG", "The user earned the reward.");

            });
        } else {
            isShowRewarded = false;
            Constant.showToastMessage(context, "Video Ad not Ready");
        }
    }

    public static void showRewardedFacebookAds(final Activity context) {
        if (rewardedVideoAd != null && rewardedVideoAd.isAdLoaded()) {
            rewardedVideoAd.show();
            isShowfacebookRewarded = true;
        } else {
            isShowfacebookRewarded = false;
            Constant.showToastMessage(context, "Video Ad not Ready");
        }
    }


    public static void showRewardedVungleAds(final Activity context) {
        playAdVungle(context);
    }


    public static void loadRewardedVideo(final Activity activity) {
        ads_id_controller = new AdsIDController(AppsConfig.getContext());


        if (rewarded_ad != null) {
            AdRequest adRequest = new AdRequest.Builder().build();

            RewardedAd.load(activity, ads_id_controller.getAdmobRewardedId(),
                    adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                            rewarded_ad = null;
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            rewarded_ad = rewardedAd;

                            showRewardedAdmobAds(activity);

                            rewarded_ad.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdShowedFullScreenContent() {

                                    Log.d(TAG, "Ad was shown.");
                                    rewarded_ad = null;
                                }

                                @Override
                                public void onAdDismissedFullScreenContent() {

                                    Log.d(TAG, "Ad was dismissed.");
                                    rewarded_ad = null;
                                }
                            });
                        }
                    });
        }

        if (rewardedVideoAd != null) {
            try {
                rewardedVideoAd = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rewardedVideoAd = new RewardedVideoAd(AppsConfig.getContext(), ads_id_controller.getAdmobRewardedId());
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                Log.e(TAG, "Rewarded video ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {

                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");
                if (!isShowfacebookRewarded) {
                    showRewardedAdmobAds(activity);
                }

            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.d(TAG, "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.d(TAG, "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {

                Log.d(TAG, "Rewarded video completed!");
            }

            @Override
            public void onRewardedVideoClosed() {
                Log.d(TAG, "Rewarded video ad closed!");
            }
        };
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());

    }

    public static void gotoNextActivity(Context context, Class<?> nextActivity, String msg) {
        if (context != null && nextActivity != null) {
            if (msg == null) {
                msg = "";
            }
            Intent intent = new Intent(context, nextActivity);
            intent.putExtra("Intent", msg);
            context.startActivity(intent);
        }
    }

    public static void showAds(Activity activity, @NonNull String typeOfAds) {
        switch (typeOfAds) {
            case "Admob":
                Constant.showRewardedAdmobAds(activity);
                break;
            case "Facebook":
                Constant.showRewardedFacebookAds(activity);
                break;
            case "Vungle":
                Constant.showRewardedVungleAds(activity);
                break;
            case "UnityAds":
                break;
            case "AdColony":
                Constant.ShowAdcolonyAds(activity);
                break;
            case "Startapp":
                Constant.StartappRewardedVideo(activity);
                break;
            case "applovin":
                Constant.ApplovinShowAds(activity);
                break;
        }
    }

    public static void loadSmallAdBanner(LinearLayout adLayout) {
        loadAdsBanner(adLayout, AdSize.BANNER, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
    }

    public static void loadRectAdBanner(LinearLayout adLayout) {
        loadAdsBanner(adLayout, AdSize.MEDIUM_RECTANGLE, com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250);
    }

    private static void loadAdsBanner(LinearLayout adLayout, AdSize adSize, com.facebook.ads.AdSize fbAdSize) {
        if (adLayout.getChildCount() > 0) return;

        final AdsController adsController = new AdsController(adLayout.getContext());
        final String bannerController = adsController.getBannerAdsControl();
        switch (bannerController) {
            case "Admob":
                loadAdBanner(adLayout, adSize);
                break;
            case "Facebook":
                loadFbAdBanner(adLayout, fbAdSize);
        }
    }

    private static void loadAdBanner(LinearLayout adLayout, AdSize adSize) {
        if (adLayout.getChildCount() > 0) return;
        final Context context = adLayout.getContext();
        final AdsIDController adsIDController = new AdsIDController(context);
        final AdView adView = new AdView(context);
        adView.setAdSize(adSize);
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

    private static void loadFbAdBanner(LinearLayout adLayout, com.facebook.ads.AdSize adSize) {
        if (adLayout.getChildCount() > 0) return;
        final Context context = adLayout.getContext();
        com.facebook.ads.AdView fbAdView = new com.facebook.ads.AdView(context, context.getString(R.string.facebook_banner_ads_id), adSize);
        adLayout.addView(fbAdView);
    }

    public static boolean isEmailAddressNotValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        boolean isMatches = pattern.matcher(email).matches();
        Log.e("Boolean Value", "" + isMatches);
        return !isMatches;
    }

    public static void showToastMessage(Context context, String message) {
        if (context != null && message != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void setString(Context context, String preKey, String setString) {
        if (prefManager == null) {
            prefManager = new PrefManager(context);
        }
        prefManager.setString(preKey, setString);
    }

    public static String getString(Context context, String prefKey) {
        if (prefManager == null) {
            prefManager = new PrefManager(context);
        }
        return prefManager.getString(prefKey);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void addPoints(Context context, int points, int type) {
        if (context != null) {
            String po = Constant.getString(context, Constant.USER_POINTS);
            if (po.equals("")) {
                po = "0";
            }
            if (type == 1) {
                Constant.setString(context, Constant.USER_POINTS, po);
                Intent serviceIntent = new Intent(context, PointsService.class);
                serviceIntent.putExtra("points", Constant.getString(context, Constant.USER_POINTS));
                context.startService(serviceIntent);
            } else {
                int current_Points = Integer.parseInt(po);
                String total_points = String.valueOf(current_Points + points);
                Constant.setString(context, Constant.USER_POINTS, total_points);
                Intent serviceIntent = new Intent(context, PointsService.class);
                serviceIntent.putExtra("points", Constant.getString(context, Constant.USER_POINTS));
                context.startService(serviceIntent);
            }
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showInternetErrorDialog(Context context, String msg) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.points_dialog_indratech);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);

        imageView.setImageResource(R.drawable.ic_internet);
        title_text.setText(msg);
        points_text.setVisibility(View.GONE);
        add_btn.setText(context.getResources().getString(R.string.ok_text));

        add_btn.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    public static void showBlockedDialog(final Context context, String msg) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.points_dialog_indratech);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);

        imageView.setImageResource(R.drawable.ic_close);
        title_text.setText(msg);
        points_text.setVisibility(View.GONE);
        add_btn.setText(context.getResources().getString(R.string.ok_text));

        add_btn.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    public static void referApp(Context context, String refer_code) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.share_text) + "' " + refer_code + " '" + " Download Link = " + " https://play.google.com/store/apps/details?id=" + context.getPackageName());
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);
        } catch (Exception e) {
            Log.e("TAG", "referApp: " + e.getMessage());
        }
    }


    public static void initVungle(final Activity activity) {
        ads_id_controller = new AdsIDController(AppsConfig.getContext());
        Vungle.init(ads_id_controller.getVungleKey(), activity.getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {

                Collection<String> placements = Vungle.getValidPlacements();
                String[] placementsArray = placements.toArray(new String[0]);

                placementID = placementsArray[0];

            }

            @Override
            public void onError(VungleException e) {

            }

            @Override
            public void onAutoCacheAdAvailable(String pid) {

            }
        });
    }

    public static void loadAdVungle(final Activity activity) {

        Vungle.loadAd(placementID, new LoadAdCallback() {
            @Override
            public void onAdLoad(String id) {

            }

            @Override
            public void onError(String id, VungleException e) {

            }
        });

    }

    public static void playAdVungle(final Activity activity) {

        Vungle.playAd(placementID, new AdConfig(), new PlayAdCallback() {
            @Override
            public void onAdStart(String placementReferenceID) {

            }

            @Override
            public void onAdViewed(String placementReferenceID) {

            }


            @Override
            public void onAdEnd(String id, boolean completed, boolean isCTAClicked) {


            }

            @Override
            public void onAdEnd(String placementReferenceID) {


            }

            @Override
            public void onAdClick(String placementReferenceID) {

            }

            @Override
            public void onAdRewarded(String placementReferenceID) {

            }

            @Override
            public void onAdLeftApplication(String placementReferenceID) {


            }

            @Override
            public void creativeId(String creativeId) {

            }

            @Override
            public void onError(String id, VungleException e) {

            }
        });
    }


    public static void initUnityAds(final Activity activity) {
        ads_id_controller = new AdsIDController(AppsConfig.getContext());
        UnityAds.initialize(activity, ads_id_controller.getUnityGameId());

        if (UnityAds.isInitialized()) {
            UnityAds.show(activity, UNITY_PLACEMENT_ID_REWARDED_VIDEO, new IUnityAdsShowListener() {
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
        } else {
            UnityAds.initialize(activity, ads_id_controller.getUnityGameId());
        }

    }

    public static void showRewardedVideoAd(final Activity activity) {
        if (!UnityAds.isInitialized()) {
            return;
        }
        UnityAds.show(activity, UNITY_PLACEMENT_ID_REWARDED_VIDEO, (IUnityAdsShowListener) activity);

    }

    public static void IntUnityAds(Activity activity) {
        ads_id_controller = new AdsIDController(AppsConfig.getContext());
        UnityAds.initialize(activity, ads_id_controller.getUnityGameId());

        if (UnityAds.isInitialized()) {

        } else {
            UnityAds.initialize(activity, ads_id_controller.getUnityGameId());
        }


    }

    public static void ShowUnityAds(Activity activity) {

        Dialog unityDialog = new Dialog(activity);
        unityDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        unityDialog.setContentView(R.layout.loading_dialog_indratech);
        unityDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        unityDialog.show();

        ads_id_controller = new AdsIDController(AppsConfig.getContext());
        if (UnityAds.isInitialized()) {
            UnityAds.show(activity, UNITY_PLACEMENT_ID_REWARDED_VIDEO, new IUnityAdsShowListener() {
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
            unityDialog.dismiss();
        } else {
            UnityAds.initialize(activity, ads_id_controller.getUnityGameId());
        }

    }

    public static void SetUserData(Context context, User user, @Nullable String password) {
        if (password != null) {
            setString(context, Constant.USER_PASSWORD, password);
        }
        if (user.getId() != null) {
            setString(context, Constant.USER_ID, user.getId());
            Log.e("TAG", "onDataChange: " + user.getId());
        }
        if (user.getName() != null) {
            setString(context, Constant.USER_NAME, user.getName());
            Log.e("TAG", "onDataChange: " + user.getName());
        }
        if (user.getNumber() != null) {
            setString(context, Constant.USER_NUMBER, user.getNumber());
            Log.e("TAG", "onDataChange: " + user.getNumber());
        }
        if (user.getEmail() != null) {
            setString(context, Constant.USER_EMAIL, user.getEmail());
            Log.e("TAG", "onDataChange: " + user.getEmail());
        }
        if (user.getPoints() != null) {
            setString(context, Constant.USER_POINTS, "0");
            Log.e("TAG", "onDataChange: " + user.getPoints());
        }
        if (user.getImage() != null) {
            setString(context, Constant.USER_IMAGE, user.getImage());
            Log.e("TAG", "onDataChange: " + user.getImage());
        }
        if (user.getReferCode() != null) {
            setString(context, Constant.REFER_CODE, user.getReferCode());
            Log.e("TAG", "onDataChange: " + user.getReferCode());
        }
        if (user.getIsBLocked() != null) {
            setString(context, Constant.USER_BLOCKED, user.getIsBLocked());
            Log.e("TAG", "onDataChange: " + user.getIsBLocked());
        }
        if (user.getUserReferCode() != null) {
            setString(context, Constant.USER_REFFER_CODE, user.getUserReferCode());
            Log.e("TAG", "onDataChange: " + user.getUserReferCode());
        }
    }

    public static void SetUserPoint(Context context, User user) {
        if (user.getPoints() != null) {
            if (!Constant.getString(context, Constant.LAST_TIME_ADD_TO_SERVER).equals("")) {
                Log.e("TAG", "onDataChange: Last time not empty");
                if (!Constant.getString(context, Constant.USER_POINTS).equals("")) {
                    Log.e("TAG", "onDataChange: user points not empty");
                    if (Constant.getString(context, Constant.IS_UPDATE).equalsIgnoreCase("")) {
                        Constant.setString(context, Constant.USER_POINTS, Constant.getString(context, Constant.USER_POINTS));
                        Log.e("TAG", "onDataChange: " + user.getPoints());
                        Constant.setString(context, Constant.IS_UPDATE, "true");
                    } else {
                        Constant.setString(context, Constant.IS_UPDATE, "true");
                        Constant.setString(context, Constant.USER_POINTS, user.getPoints());
                        Log.e("TAG", "onDataChange: " + user.getPoints());
                    }
                }
            }
        }
    }

    public static void logoutUser(Context context) {
        resetUser(context);
        Intent i = new Intent(context, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
    }

    public static void resetUser(Context context) {
        setString(context, Constant.USER_ID, "");
        setString(context, Constant.USER_PASSWORD, "");
        setString(context, Constant.USER_NAME, "");
        setString(context, Constant.USER_NUMBER, "");
        setString(context, Constant.USER_EMAIL, "");
        setString(context, Constant.USER_IMAGE, "");
        setString(context, Constant.USER_POINTS, "");
        setString(context, Constant.REFER_CODE, "");
        setString(context, Constant.USER_BLOCKED, "");
        setString(context, Constant.USER_REFFER_CODE, "");
        setString(context, Constant.IS_LOGIN, "");
    }

    public static boolean isStorageReadPermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {
                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    public static boolean isMediaReadPermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {
                Log.v("TAG", "Permission is revoked");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
                } else {
                    return isStorageReadPermissionGranted(activity);
                }
                return false;
            }
        } else {
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    public static void queryPurchases(BillingClient billingClient, List<String> productIds) {
        QueryPurchasesParams queryParams = QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build();

        billingClient.queryPurchasesAsync(queryParams, (billingResult1, purchases) -> {
            if (!purchases.isEmpty()) {
                int i = 0;
                boolean purchased = false;
                for (Purchase purchase : purchases) {
                    String product = purchase.getProducts().get(i);
                    if (productIds.contains(product) && !purchased) {
                        purchased = true;
                    }
                    i++;
                }

                if (purchased) {
                    Config.vip_subscription = true;
                    Config.all_subscription = true;
                } else {
                    Config.vip_subscription = false;
                    Config.all_subscription = false;

                }
            }
        });
    }
}
