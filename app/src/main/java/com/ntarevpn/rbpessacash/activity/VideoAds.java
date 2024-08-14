package com.ntarevpn.rbpessacash.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyZone;
import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinSdk;
import com.facebook.ads.Ad;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.ntarevpn.rbpessacash.AppsConfig;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.ntarevpn.rbpessacash.util.AdsIDController;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.SomeEarnController;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.startapp.sdk.adsbase.StartAppAd;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;


public class VideoAds extends AppCompatActivity implements IUnityAdsShowListener {
    private static final String PLACEMENT_ID_SKIPPABLE_VIDEO = "video";
    private TextView Section5Title, Section1Title, Section1PointText, Section2Title, Section2PointText, Section3Title, Section3PointText, Section4Title, Section4PointText, RewardedAdsName1, RewardedAdsName2, RewardedAdsName3, RewardedAdsName4, RewardedAdsName5, RewardedAdsName6, Section5PointText, Section6Title, Section6PointText, Section7Title, RewardedAdsName7, Section7PointText, Section8Title, RewardedAdsName8, Section8PointText;
    private ImageButton Section1Btn, Section2Btn, Section3Btn, Section4Btn, Section5Btn, Section6Btn, Section7Btn, Section8Btn;

    private String placementID;

    private Dialog UnityDialog;
    private Dialog dialog;
    private Dialog dialogAdcolony;
    private Dialog dialogStartAppVideoAds;
    private Dialog dialogFacebookVideoAds;
    private AppLovinInterstitialAdDialog appLovinInterstitialAd;

    private AdColonyInterstitial rewardAdColony;
    private AdColonyInterstitialListener rewardListener;
    private AdColonyAdOptions rewardAdOptions;
    private static boolean isRewardLoaded;

    public RewardedAd rewarded_ad;
    public RewardedAdLoadCallback adLoadCallback;
    public boolean isShowRewarded = true;
    public boolean isShowfacebookRewarded = true;
    private TextView Video_Ads_View_count_textView, points_textView, points_text;
    boolean first_time = true, Video_Ads_View_first = true;
    private int Video_Ads_View_count = 1;
    private final String TAG = "VideoAds";
    private String random_points;
    public int poiints = 0;
    AdsController ads_controller;
    SomeEarnController someEarn_controller;
    public RewardedVideoAd rewardedVideoAd;
    AdsIDController ads_id_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.video_ads_layout_indratech);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Video Ads");
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Section1Title = findViewById(R.id.Section1Title);
        Section1PointText = findViewById(R.id.Section1PointText);
        Section2Title = findViewById(R.id.Section2Title);
        Section2PointText = findViewById(R.id.Section2PointText);
        Section3Title = findViewById(R.id.Section3Title);
        Section3PointText = findViewById(R.id.Section3PointText);
        Section4Title = findViewById(R.id.Section4Title);
        Section4PointText = findViewById(R.id.Section4PointText);

        Section1Btn = findViewById(R.id.Section1Btn);
        Section2Btn = findViewById(R.id.Section2Btn);
        Section3Btn = findViewById(R.id.Section3Btn);
        Section4Btn = findViewById(R.id.Section4Btn);
        RewardedAdsName1 = findViewById(R.id.RewardedAdsName1);
        RewardedAdsName2 = findViewById(R.id.RewardedAdsName2);
        RewardedAdsName3 = findViewById(R.id.RewardedAdsName3);
        RewardedAdsName4 = findViewById(R.id.RewardedAdsName4);
        RewardedAdsName5 = findViewById(R.id.RewardedAdsName5);
        RewardedAdsName6 = findViewById(R.id.RewardedAdsName6);
        Section5PointText = findViewById(R.id.Section5PointText);
        Section6Title = findViewById(R.id.Section6Title);
        Section6PointText = findViewById(R.id.Section6PointText);
        Section5Btn = findViewById(R.id.Section5Btn);
        Section6Btn = findViewById(R.id.Section6Btn);
        Section5Title = findViewById(R.id.Section5Title);
        Section7Title = findViewById(R.id.Section7Title);
        RewardedAdsName7 = findViewById(R.id.RewardedAdsName7);
        Section7PointText = findViewById(R.id.Section7PointText);
        Section8Title = findViewById(R.id.Section8Title);
        RewardedAdsName8 = findViewById(R.id.RewardedAdsName8);
        Section8PointText = findViewById(R.id.Section8PointText);
        Section7Btn = findViewById(R.id.Section7Btn);
        Section8Btn = findViewById(R.id.Section8Btn);
        points_textView = findViewById(R.id.user_points_text_view);
        points_text = findViewById(R.id.textView_points_show);
        Video_Ads_View_count_textView = findViewById(R.id.limit_text);

        ads_controller = new AdsController(this);
        someEarn_controller = new SomeEarnController(this);
        ads_id_controller = new AdsIDController(this);

        if (Constant.isNetworkAvailable(this)) {
            Constant.loadInterstitialAd(this);
            Constant.loadRewardedVideo(this);
        } else {
            Constant.showInternetErrorDialog(this, getResources().getString(R.string.internet_connection_of_text));
        }
        onEarningInit();
        setPointsText();


        OnclickView();
        SetAll();

        initViews();
        initVungle();

        initAdColonySdk();
        initRewardedAdAdColony();

        LoadRewardedVideoAdmob();

        points_textView.setText(Constant.getString(this, Constant.USER_POINTS));

        TextView text_view_date_activity = findViewById(R.id.text_view_date_activity);
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_formet));
        Date date = new Date();
        text_view_date_activity.setText(dateFormat.format(date));

        Constant.IntUnityAds(this);
    }

    @SuppressLint("SetTextI18n")
    private void SetAll() {

        Section1PointText.setText(someEarn_controller.getVungle_Rewarded());
        Section2PointText.setText(someEarn_controller.getUnityAds_Rewarded());
        Section3PointText.setText(someEarn_controller.getAppLovin_Rewarded());
        Section4PointText.setText(someEarn_controller.getAdColony_Rewarded());
        Section5PointText.setText(someEarn_controller.getAdmob_Rewarded());
        Section6PointText.setText(someEarn_controller.getStartapp_Rewarded());
        Section7PointText.setText(someEarn_controller.getFacebook_Rewarded());
        Section8PointText.setText(someEarn_controller.getAppLovin_Rewarded());

        Section1Title.setText("Watch Great \nVideos Bonus");
        Section2Title.setText("Additional \nPoints ");
        Section4Title.setText(R.string.Extra_Bonus);
        Section3Title.setText(R.string.Extra_points);
        Section5Title.setText(R.string.watch_great);
        Section6Title.setText("Additional \nBonus ");
        Section7Title.setText("Extra Points ");
        Section8Title.setText("Extra Points ");

        RewardedAdsName1.setText("Vungle");
        RewardedAdsName2.setText("UnityAds");
        RewardedAdsName3.setText("AppLovin");
        RewardedAdsName4.setText("AdColony");
        RewardedAdsName5.setText("Admob");
        RewardedAdsName6.setText("Startapp");
        RewardedAdsName7.setText("Facebook");
        RewardedAdsName8.setText("AppLovin");


    }

    private void OnclickView() {
        Section1Btn.setOnClickListener(view -> {
            String count = Video_Ads_View_count_textView.getText().toString();
            if (count.equals("0")) {
                Toast.makeText(this, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
            } else {
                loadAdVungle();
                playAdVungle();
                VungleEarningPointAddSection();
            }

        });


        Section2Btn.setOnClickListener(view -> {
            String count = Video_Ads_View_count_textView.getText().toString();
            if (count.equals("0")) {
                Toast.makeText(this, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
            } else {
                UnityDialog = new Dialog(this);
                UnityDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                UnityDialog.setContentView(R.layout.loading_dialog_indratech);
                UnityDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                UnityDialog.show();

                if (UnityAds.isInitialized()) {
                    UnityAds.show(this, PLACEMENT_ID_SKIPPABLE_VIDEO, this);
                    UnityEarningPointAddSection();
                    UnityDialog.dismiss();
                    CallCoinAdded();
                } else {
                    UnityAds.initialize(this, ads_id_controller.getUnityGameId());
                }
            }

        });
        Section3Btn.setOnClickListener(view -> {
            String count = Video_Ads_View_count_textView.getText().toString();
            if (count.equals("0")) {
                Toast.makeText(this, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
            } else {
                dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.loading_dialog_indratech);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                ApplovinEarningPointAddSection();
                appLovinInterstitialAd = AppLovinInterstitialAd.create(AppLovinSdk.getInstance(this), this);
                appLovinInterstitialAd.show();
                appLovinInterstitialAd.setAdDisplayListener(new AppLovinAdDisplayListener() {
                    @Override
                    public void adDisplayed(AppLovinAd ad) {
                        dialog.dismiss();
                    }

                    @Override
                    public void adHidden(AppLovinAd ad) {
                        CallCoinAdded();
                    }
                });
            }
        });
        Section4Btn.setOnClickListener(view -> {
            String count = Video_Ads_View_count_textView.getText().toString();
            if (count.equals("0")) {
                Toast.makeText(this, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
            } else {
                dialogAdcolony = new Dialog(this);
                dialogAdcolony.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogAdcolony.setContentView(R.layout.loading_dialog_indratech);
                dialogAdcolony.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogAdcolony.show();
                AdcolonyEarningPointAddSection();
                if (rewardAdColony != null && isRewardLoaded) {
                    rewardAdColony.show();
                    isRewardLoaded = false;
                } else {
                    Toast.makeText(this, "Reward Ad Is Not Loaded Yet Please Try Again ", Toast.LENGTH_SHORT).show();
                }
            }

        });
        Section5Btn.setOnClickListener(view -> {
            String count = Video_Ads_View_count_textView.getText().toString();
            if (count.equals("0")) {
                Toast.makeText(this, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
            } else {
                showVideoAdsSectionRewardedAds();
                AdmobEarningPointAddSection();
            }
        });
        Section6Btn.setOnClickListener(view -> {
            String count = Video_Ads_View_count_textView.getText().toString();
            if (count.equals("0")) {
                Toast.makeText(this, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
            } else {
                StartappRewardedVideo();
                StartappEarningPointAddSection();
            }
        });
        Section7Btn.setOnClickListener(view -> {
            String count = Video_Ads_View_count_textView.getText().toString();
            if (count.equals("0")) {
                Toast.makeText(this, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
            } else {
                FacebookRewardedVideoAd();
                ShowFacebookVideo();
                FacebookEarningPointAddSection();
            }
        });
        Section8Btn.setOnClickListener(view -> {
            String count = Video_Ads_View_count_textView.getText().toString();
            if (count.equals("0")) {
                Toast.makeText(this, getString(R.string.adds_video_unavailable), Toast.LENGTH_SHORT).show();
            } else {
                dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.loading_dialog_indratech);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                ApplovinEarningPointAddSection();
                appLovinInterstitialAd = AppLovinInterstitialAd.create(AppLovinSdk.getInstance(this), this);
                appLovinInterstitialAd.show();
                appLovinInterstitialAd.setAdDisplayListener(new AppLovinAdDisplayListener() {
                    @Override
                    public void adDisplayed(AppLovinAd ad) {
                        dialog.dismiss();

                    }

                    @Override
                    public void adHidden(AppLovinAd ad) {
                        CallCoinAdded();
                    }
                });
            }
        });
    }

    private void VungleEarningPointAddSection() {

        if (Video_Ads_View_first) {
            Video_Ads_View_first = false;
            Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            if (Constant.isNetworkAvailable(this)) {
                random_points = "";

                random_points = String.valueOf(someEarn_controller.getVungle_Rewarded());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(this, getResources().getString(R.string.internet_connection_of_text));
            }
        }

    }

    private void ApplovinEarningPointAddSection() {

        if (Video_Ads_View_first) {
            Video_Ads_View_first = false;
            Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            if (Constant.isNetworkAvailable(this)) {
                random_points = "";

                random_points = String.valueOf(someEarn_controller.getAppLovin_Rewarded());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(this, getResources().getString(R.string.internet_connection_of_text));
            }
        }

    }

    private void UnityEarningPointAddSection() {

        if (Video_Ads_View_first) {
            Video_Ads_View_first = false;
            Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            if (Constant.isNetworkAvailable(this)) {
                random_points = "";

                random_points = String.valueOf(someEarn_controller.getUnityAds_Rewarded());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(this, getResources().getString(R.string.internet_connection_of_text));
            }
        }

    }

    private void AdcolonyEarningPointAddSection() {

        if (Video_Ads_View_first) {
            Video_Ads_View_first = false;
            Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            if (Constant.isNetworkAvailable(this)) {
                random_points = "";

                random_points = String.valueOf(someEarn_controller.getAdColony_Rewarded());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(this, getResources().getString(R.string.internet_connection_of_text));
            }
        }


    }

    private void setPointsText() {
        if (points_textView != null) {
            String userPoints = Constant.getString(this, Constant.USER_POINTS);
            if (userPoints.equalsIgnoreCase("")) {
                userPoints = "0";
            }
            points_textView.setText(userPoints);
        }
    }


    private void onEarningInit() {

        String Video_Ads_ViewCount = Constant.getString(this, Constant.Video_Ads_View_Reward_COUNT);
        if (Video_Ads_ViewCount.equals("0")) {
            Video_Ads_ViewCount = "";
            Log.e("TAG", "onInit: scratch card 0");
        }
        if (Video_Ads_ViewCount.equals("")) {
            Log.e("TAG", "onInit: scratch card empty vala part");
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(this, Constant.LAST_DATE_Video_Ads_View_Reward);
            Log.e("TAG", "Lat date" + last_date);
            if (last_date.equals("")) {
                Log.e("TAG", "onInit: last date empty part");
                setVideoAdsViewCount(someEarn_controller.getVideo_Ads_Available_Views());
                Constant.setString(this, Constant.Video_Ads_View_Reward_COUNT, someEarn_controller.getVideo_Ads_Available_Views());
                Constant.setString(this, Constant.LAST_DATE_Video_Ads_View_Reward, currentDate);
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
                        Constant.setString(this, Constant.LAST_DATE_Video_Ads_View_Reward, currentDate);
                        Constant.setString(this, Constant.Video_Ads_View_Reward_COUNT, someEarn_controller.getVideo_Ads_Available_Views());
                        setVideoAdsViewCount(Constant.getString(this, Constant.Video_Ads_View_Reward_COUNT));
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
                Toast.makeText(this, ">>>>>You get " + points_text.getText().toString() + " coins! <<<<<", Toast.LENGTH_SHORT).show();
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
                Constant.setString(this, Constant.Video_Ads_View_Reward_COUNT, current_counter);
                setVideoAdsViewCount(Constant.getString(this, Constant.Video_Ads_View_Reward_COUNT));
            } else {
                String current_counter = String.valueOf(counter - 1);
                Constant.setString(this, Constant.Video_Ads_View_Reward_COUNT, current_counter);
                setVideoAdsViewCount(Constant.getString(this, Constant.Video_Ads_View_Reward_COUNT));
                try {
                    int finalPoint;
                    if (points.equals("")) {
                        finalPoint = 0;
                    } else {
                        finalPoint = Integer.parseInt(points);
                    }
                    poiints = finalPoint;
                    Constant.addPoints(this, finalPoint, 0);
                    setPointsText();
                } catch (NumberFormatException ignored) {

                }
            }
        }
        if (Video_Ads_View_count == Integer.parseInt(someEarn_controller.getRewarded_and_interstitial_count())) {

        } else {
            Video_Ads_View_count += 1;
        }

    }

    private void initAdColonySdk() {
        AdColonyAppOptions appOptions = new AdColonyAppOptions().setKeepScreenOn(true);
        AdColony.configure(this, appOptions, ads_id_controller.getAdcolonyAppId(), ads_id_controller.getAdcolonyRewardZoneId());
    }

    private void initRewardedAdAdColony() {

        AdColony.setRewardListener(reward -> {
            if (reward.success()) {
                Toast.makeText(this, "Reward Earned", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Reward Cancelled", Toast.LENGTH_SHORT).show();
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
                CallCoinAdded();
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

    private void initViews() {

    }

    private void initVungle() {

        Vungle.init(ads_id_controller.getVungleKey(), this.getApplicationContext(), new InitCallback() {
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

    private void loadAdVungle() {

        Vungle.loadAd(placementID, new LoadAdCallback() {
            @Override
            public void onAdLoad(String id) {
                Toast.makeText(VideoAds.this, "Congratulation Ads Loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String id, VungleException e) {
                showToastMessage("Ad Load Error : Please Try Again");
            }
        });
    }

    private void playAdVungle() {

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

                CallCoinAdded();
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


    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void AdmobEarningPointAddSection() {

        if (Video_Ads_View_first) {
            Video_Ads_View_first = false;
            Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            if (Constant.isNetworkAvailable(this)) {
                random_points = "";

                random_points = String.valueOf(someEarn_controller.getAdmob_Rewarded());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(this, getResources().getString(R.string.internet_connection_of_text));
            }
        }


    }


    public void LoadRewardedVideoAdmob() {
        if (rewarded_ad != null) {
            if (rewarded_ad != null) {
                AdRequest adRequest = new AdRequest.Builder().build();

                RewardedAd.load(this, ads_id_controller.getAdmobRewardedId(),
                        adRequest, new RewardedAdLoadCallback() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                                rewarded_ad = null;
                            }

                            @Override
                            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                                rewarded_ad = rewardedAd;

                                showVideoAdsSectionRewardedAds();

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
        }
    }


    public void showVideoAdsSectionRewardedAds() {

        Dialog dialogAdmobVideoAds = new Dialog(this);
        dialogAdmobVideoAds.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAdmobVideoAds.setContentView(R.layout.loading_dialog_indratech);
        dialogAdmobVideoAds.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogAdmobVideoAds.show();

        if (rewarded_ad != null) {
            rewarded_ad.show(this, rewardItem -> {
                isShowRewarded = true;
                Log.d("TAG", "The user earned the reward.");

            });
        } else {
            isShowRewarded = false;
        }
    }

    private void StartappEarningPointAddSection() {

        if (Video_Ads_View_first) {
            Video_Ads_View_first = false;
            Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            if (Constant.isNetworkAvailable(this)) {
                random_points = "";

                random_points = String.valueOf(someEarn_controller.getStartapp_Rewarded());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(this, getResources().getString(R.string.internet_connection_of_text));
            }
        }


    }

    public void StartappRewardedVideo() {

        dialogStartAppVideoAds = new Dialog(this);
        dialogStartAppVideoAds.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogStartAppVideoAds.setContentView(R.layout.loading_dialog_indratech);
        dialogStartAppVideoAds.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogStartAppVideoAds.show();
        final StartAppAd rewardedVideo = new StartAppAd(this);
        rewardedVideo.setVideoListener((VideoListener) () -> CallCoinAdded());

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

    private void FacebookEarningPointAddSection() {

        if (Video_Ads_View_first) {
            Video_Ads_View_first = false;
            Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            if (Constant.isNetworkAvailable(this)) {
                random_points = "";

                random_points = String.valueOf(someEarn_controller.getFacebook_Rewarded());
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onVideo_Ads_ViewStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(this, getResources().getString(R.string.internet_connection_of_text));
            }
        }


    }

    public void FacebookRewardedVideoAd() {

        rewardedVideoAd = new RewardedVideoAd(AppsConfig.getContext(), ads_id_controller.getAdmobRewardedId());
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                Log.e(TAG, "Rewarded video ad failed to load: " + adError.getErrorMessage());
                dialogFacebookVideoAds.dismiss();
                Toast.makeText(VideoAds.this, "Rewarded Video ad Failed to Load", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                dialogFacebookVideoAds.dismiss();
                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");
                if (!isShowfacebookRewarded) {
                    ShowFacebookVideo();
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
                Toast.makeText(VideoAds.this, "Rewarded video completed", Toast.LENGTH_SHORT).show();
                CallCoinAdded();
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

    public void ShowFacebookVideo() {

        dialogFacebookVideoAds = new Dialog(this);
        dialogFacebookVideoAds.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFacebookVideoAds.setContentView(R.layout.loading_dialog_indratech);
        dialogFacebookVideoAds.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFacebookVideoAds.show();

        if (rewardedVideoAd != null && rewardedVideoAd.isAdLoaded()) {
            rewardedVideoAd.show();
            isShowfacebookRewarded = true;
        } else {
            isShowfacebookRewarded = false;
            Constant.showToastMessage(this, "Video Ad not Ready");
        }

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

    }
}