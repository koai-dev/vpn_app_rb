package com.ntarevpn.rbpessacash.activity;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.multidex.BuildConfig;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ntarevpn.rbpessacash.viewModels.LoginViewModel;
import com.ntarevpn.rbpessacash.ApiBaseUrl;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.models.User;
import com.ntarevpn.rbpessacash.state.LoginState;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.ntarevpn.rbpessacash.util.AdsIDController;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.Helper;
import com.ntarevpn.rbpessacash.util.PaymentController;
import com.ntarevpn.rbpessacash.util.SomeEarnController;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import org.json.JSONObject;

public class Splash extends AppCompatActivity {
    boolean LOGIN = false;
    private AppUpdateManager appUpdateManager;
    public static final int RC_APP_UPDATE = 101;
    Splash activity;

    AdsController adsController;
    SomeEarnController someEarnController;
    PaymentController paymentController;
    AdsIDController adsIDController;

    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        initLoginViewModel();
        initAppSdk();

        checkIfLogin();
        checkVersion();

        initAppController();
        fetchAppData();

    }

    private void initLoginViewModel() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        listenToLoginState();
    }

    private void listenToLoginState() {
        loginViewModel.getLoginState().observe(this, state -> {
            if (state instanceof LoginState.Success) {
                LoginState.Success successState = (LoginState.Success) state;
                User user = successState.getResult();
                Constant.SetUserData(this, user, null);
                Constant.SetUserPoint(this, user);

                if (Constant.getString(activity, Constant.USER_BLOCKED).equals("0")) {
                    Constant.showBlockedDialog(activity, getResources().getString(R.string.app_you_are_blocked));
                } else {
                    Log.e("TAG", "onInit: login pART");
                    startActivity(new Intent(this, Main.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                }
            } else if (state instanceof LoginState.Failure) {
                Log.e("TAG", "onInit: user_information from database");
                Constant.setString(activity, Constant.IS_LOGIN, "");
                startActivity(new Intent(this, Login.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                finish();
            }
        });
    }

    private void initAppSdk() {
        adsIDController = new AdsIDController(this);
        StartAppSDK.init(this, adsIDController.getStartaAppAppId(), true);
        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);
        StartAppAd.disableSplash();
        StartAppAd.enableConsent(this, false);
        StartAppSDK.setTestAdsEnabled(true);
    }

    private void checkIfLogin() {
        String isLogin = Constant.getString(this, Constant.IS_LOGIN);
        if (isLogin.equals("true")) {
            LOGIN = true;
        }
    }

    private void checkVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e("TAG", "onCreate:if part activarte ");
            appUpdateManager = AppUpdateManagerFactory.create(this);
            updateApp();
        } else {
            Log.e("TAG", "onCreate:else part activarte ");
            onInit();
        }
    }

    private void onInit() {
        if (Constant.isNetworkAvailable(this)) {
            if (LOGIN) {
                String email = Constant.getString(activity, Constant.USER_EMAIL);
                String password = Constant.getString(activity, Constant.USER_PASSWORD);

                loginViewModel.login(email, password);
            } else {
                if (Constant.getString(activity, Constant.USER_BLOCKED).equals("0")) {
                    Constant.showBlockedDialog(activity, getResources().getString(R.string.app_you_are_blocked));
                    return;
                }
                Log.e("TAG", "onInit: else part of no login");
                startActivity(new Intent(this, Login.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                finish();
            }
        } else {
            Constant.showInternetErrorDialog(this, getString(R.string.internet_connection_of_text));
        }
    }

    private void updateApp() {
        try {
            Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
            appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo, IMMEDIATE, activity, RC_APP_UPDATE);
                        Log.e("TAG", "onCreate:startUpdateFlowForResult part activarte ");
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("TAG", "onCreate:startUpdateFlowForResult else part activarte ");
                    onInit();
                }
            }).addOnFailureListener(e -> {
                e.printStackTrace();
                Log.e("TAG", "onCreate:addOnFailureListener else part activarte ");
                onInit();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAppController() {
        adsController = new AdsController(this);
        someEarnController = new SomeEarnController(this);
        paymentController = new PaymentController(this);
    }

    public void fetchAppData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiBaseUrl.APP_DATA_API_URL
                + "?user=" + Helper.getUserAccount(this)
                + "&did=" + Helper.getDeviceId(this)
                + "&" + ApiBaseUrl.EXTRA_PARAMS,
                response -> {
                    try {
                        JSONObject data = new JSONObject(response);
                        if (!data.getBoolean("success")) {
                            return;
                        }
                        String collect_reward_ads = data.getString("collect_reward_ads");
                        String Open_Reward_ads = data.getString("Open_Reward_ads");
                        String Everyday_gift_ads = data.getString("Everyday_gift_ads");
                        String Scratch_win_ads1 = data.getString("Scratch_win_ads1");
                        String Scratch_win_ads2 = data.getString("Scratch_win_ads2");
                        String Scratch_win_ads3 = data.getString("Scratch_win_ads3");
                        String TicTAcToe_Ads = data.getString("TicTAcToe_Ads");
                        String gold_reward_ads = data.getString("gold_reward_ads");
                        String king_pot_ads = data.getString("king_pot_ads");
                        String pay_earn_gift_ads = data.getString("pay_earn_gift_ads");
                        String daily_check_reward_ads = data.getString("daily_check_reward_ads");
                        String spin_reward_ads = data.getString("spin_reward_ads");
                        String Banner_Ads = data.getString("Banner_Ads");
                        String Int_Ads = data.getString("Int_Ads");
                        String signup_bonus = data.getString("signup_bonus");
                        String Refer_Point = data.getString("Refer_Point");
                        String Contact_us_email = data.getString("Contact_us_email");
                        String spin_Interstitial_ads = data.getString("spin_Interstitial_ads");
                        String collect_Interstitial_ads = data.getString("collect_Interstitial_ads");
                        String Open_Interstitial_ads = data.getString("Open_Interstitial_ads");
                        String Everyday_gift_Interstitial_ads = data.getString("Everyday_gift_Interstitial_ads");
                        String Scratch_win_ads1_Interstitial = data.getString("Scratch_win_ads1_Interstitial");
                        String Scratch_win_ads2_Interstitial = data.getString("Scratch_win_ads2_Interstitial");
                        String Scratch_win_ads3_Interstitial = data.getString("Scratch_win_ads3_Interstitial");
                        String TicTAcToe_Interstitial_Ads = data.getString("TicTAcToe_Interstitial_Ads");
                        String BannerAdsControl = data.getString("BannerAdsControl");
                        String privacypolicyurl = data.getString("privacypolicyurl");

                        AdsController ads_controller = new AdsController(this);
                        ads_controller.dataStore(collect_reward_ads,
                                Open_Reward_ads,
                                Everyday_gift_ads,
                                Scratch_win_ads1,
                                Scratch_win_ads2,
                                Scratch_win_ads3,
                                TicTAcToe_Ads,
                                gold_reward_ads,
                                king_pot_ads,
                                pay_earn_gift_ads,
                                daily_check_reward_ads,
                                spin_reward_ads,
                                Banner_Ads,
                                Int_Ads,
                                signup_bonus,
                                Refer_Point,
                                Contact_us_email,
                                spin_Interstitial_ads,
                                collect_Interstitial_ads,
                                Open_Interstitial_ads,
                                Everyday_gift_Interstitial_ads,
                                Scratch_win_ads1_Interstitial,
                                Scratch_win_ads2_Interstitial,
                                Scratch_win_ads3_Interstitial,
                                TicTAcToe_Interstitial_Ads,
                                BannerAdsControl,
                                privacypolicyurl
                        );


                        String Payment_section_Image1 = data.getString("Payment_section_Image1");
                        String Payment_section_Dollar_1 = data.getString("Payment_section_Dollar_1");
                        String Payment_section_CostCOint1 = data.getString("Payment_section_CostCOint1");
                        String Payment_section_Image2 = data.getString("Payment_section_Image2");
                        String Payment_section_Dollar_2 = data.getString("Payment_section_Dollar_2");
                        String Payment_section_CostCOint2 = data.getString("Payment_section_CostCOint2");
                        String Payment_section_Image3 = data.getString("Payment_section_Image3");
                        String Payment_section_Dollar_3 = data.getString("Payment_section_Dollar_3");
                        String Payment_section_CostCOint3 = data.getString("Payment_section_CostCOint3");
                        String Payment_section_Dollar_4 = data.getString("Payment_section_Dollar_4");
                        String Payment_section_Image4 = data.getString("Payment_section_Image4");
                        String Payment_section_CostCOint4 = data.getString("Payment_section_CostCOint4");
                        String Payment_section_Image5 = data.getString("Payment_section_Image5");
                        String Payment_section_Dollar_5 = data.getString("Payment_section_Dollar_5");
                        String Payment_section_CostCOint5 = data.getString("Payment_section_CostCOint5");
                        String Payment_section_Image6 = data.getString("Payment_section_Image6");
                        String Payment_section_Dollar_6 = data.getString("Payment_section_Dollar_6");
                        String Payment_section_CostCOint6 = data.getString("Payment_section_CostCOint6");
                        PaymentController payment_controller = new PaymentController(this);
                        payment_controller.dataStore(Payment_section_Image1, Payment_section_Dollar_1,
                                Payment_section_CostCOint1,
                                Payment_section_Image2,
                                Payment_section_Dollar_2,
                                Payment_section_CostCOint2,
                                Payment_section_Image3,
                                Payment_section_Dollar_3,
                                Payment_section_CostCOint3,
                                Payment_section_Dollar_4,
                                Payment_section_Image4,
                                Payment_section_CostCOint4,
                                Payment_section_Image5,
                                Payment_section_Dollar_5,
                                Payment_section_CostCOint5,
                                Payment_section_Image6,
                                Payment_section_Dollar_6,
                                Payment_section_CostCOint6);


                        String CollectReward = data.getString("CollectReward");
                        String WatchVideo = data.getString("WatchVideo");
                        String OpenReward = data.getString("OpenReward");
                        String EverydayGifts_Count = data.getString("EverydayGifts_Count");
                        String CollectRewardCount = data.getString("CollectRewardCount");
                        String OpenRewardCount = data.getString("OpenRewardCount");
                        String SpinCount = data.getString("SpinCount");
                        String TicTacCount = data.getString("TicTacCount");
                        String TicTacReward = data.getString("TicTacReward");
                        String daily_check = data.getString("daily_check");
                        String GoldRewardPoint = data.getString("GoldRewardPoint");
                        String KingPotPoint = data.getString("KingPotPoint");
                        String PayEarnGift = data.getString("PayEarnGift");
                        String Pollfish_point = data.getString("Pollfish_point");
                        String Fyber_point = data.getString("Fyber_point");
                        String ironSource_point = data.getString("ironSource_point");
                        String Video_Ads_Available_Views = data.getString("Video_Ads_Available_Views");
                        String Vungle_Rewarded = data.getString("Vungle_Rewarded");
                        String UnityAds_Rewarded = data.getString("UnityAds_Rewarded");
                        String AppLovin_Rewarded = data.getString("AppLovin_Rewarded");
                        String AdColony_Rewarded = data.getString("AdColony_Rewarded");
                        String Admob_Rewarded = data.getString("Admob_Rewarded");
                        String Startapp_Rewarded = data.getString("Startapp_Rewarded");
                        String Facebook_Rewarded = data.getString("Facebook_Rewarded");
                        String Slide_image1 = data.getString("Slide_image1");
                        String Slide_image2 = data.getString("Slide_image2");
                        String Slide_image3 = data.getString("Slide_image3");
                        String CompleteSurvey_reward = data.getString("CompleteSurvey_reward");
                        String Additional_Scratch_Chances = data.getString("Additional_Scratch_Chances");
                        String Additional_Scratch_Point = data.getString("Additional_Scratch_Point");
                        String Extra_Scratch_Chances = data.getString("Extra_Scratch_Chances");
                        String Extra_Scratch_Point = data.getString("Extra_Scratch_Point");
                        String Great_Scratch_Chances = data.getString("Great_Scratch_Chances");
                        String Great_Scratch_Point = data.getString("Great_Scratch_Point");
                        String rewarded_and_interstitial_count = data.getString("rewarded_and_interstitial_count");
                        String Slide1_openurl = data.getString("Slide1_openurl");
                        String Slide1_url_control = data.getString("Slide1_url_control");
                        String Slide2_openurl = data.getString("Slide2_openurl");
                        String Slide2_url_control = data.getString("Slide2_url_control");
                        String Slide3_openurl = data.getString("Slide3_openurl");
                        String Slide3_url_control = data.getString("Slide3_url_control");
                        SomeEarnController someEarn_controller = new SomeEarnController(this);
                        someEarn_controller.dataStore(CollectReward, WatchVideo, OpenReward, EverydayGifts_Count, CollectRewardCount, OpenRewardCount, SpinCount, TicTacCount, TicTacReward, daily_check, GoldRewardPoint, KingPotPoint, PayEarnGift, Pollfish_point, Fyber_point, ironSource_point, Video_Ads_Available_Views, Vungle_Rewarded, UnityAds_Rewarded, AppLovin_Rewarded, AdColony_Rewarded, Admob_Rewarded, Startapp_Rewarded, Facebook_Rewarded, Slide_image1, Slide_image2, Slide_image3, CompleteSurvey_reward, Additional_Scratch_Chances, Additional_Scratch_Point, Extra_Scratch_Chances, Extra_Scratch_Point, Great_Scratch_Chances, Great_Scratch_Point, rewarded_and_interstitial_count, Slide1_openurl, Slide1_url_control, Slide2_openurl, Slide2_url_control, Slide3_openurl, Slide3_url_control);


                        String admob_app_id = data.getString("admob_app_id");
                        String admob_banner_id = data.getString("admob_banner_id");
                        String Admob_Interstitial_id = data.getString("Admob_Interstitial_id");
                        String Admob_rewarded_id = data.getString("Admob_rewarded_id");
                        String Facebook_app_id = data.getString("Facebook_app_id");
                        String Facebook_Interstitial_id = data.getString("Facebook_Interstitial_id");
                        String Facebook_rewarded_id = data.getString("Facebook_rewarded_id");
                        String Fyber_app_id = data.getString("Fyber_app_id");
                        String Fyber_security_key = data.getString("Fyber_security_key");
                        String Vungle_key = data.getString("Vungle_key");
                        String Vungle_InterstitialPlacementID = data.getString("Vungle_InterstitialPlacementID");
                        String Applovin_SDK_Key = data.getString("Applovin_SDK_Key");
                        String AdcolonyAPP_ID = data.getString("AdcolonyAPP_ID");
                        String AdcolonyREWARD_ZONE_ID = data.getString("AdcolonyREWARD_ZONE_ID");
                        String AdcolonyINT_ZONE_ID = data.getString("AdcolonyINT_ZONE_ID");
                        String IronSource_App_Key = data.getString("IronSource_App_Key");
                        String pollfish_key = data.getString("pollfish_key");
                        String Unity_Game_Id = data.getString("Unity_Game_Id");
                        String StartaApp_app_id = data.getString("StartaApp_app_id");
                        String Tapjoy_SDK_KEY = data.getString("Tapjoy_SDK_KEY");
                        String Tapjoy_PLACEMENT_OFFERWALL = data.getString("Tapjoy_PLACEMENT_OFFERWALL");

                        AdsIDController ads_id_controller = new AdsIDController(this);
                        ads_id_controller.dataStore(
                                admob_app_id,
                                admob_banner_id,
                                Admob_Interstitial_id,
                                Admob_rewarded_id,
                                Facebook_app_id,
                                Facebook_Interstitial_id,
                                Facebook_rewarded_id,
                                Fyber_app_id,
                                Fyber_security_key,
                                Vungle_key,
                                Vungle_InterstitialPlacementID,
                                Applovin_SDK_Key,
                                AdcolonyAPP_ID,
                                AdcolonyREWARD_ZONE_ID,
                                AdcolonyINT_ZONE_ID,
                                IronSource_App_Key,
                                pollfish_key,
                                Unity_Game_Id,
                                StartaApp_app_id,
                                Tapjoy_SDK_KEY,
                                Tapjoy_PLACEMENT_OFFERWALL
                        );


                    } catch (Exception error) {
                        Log.e("TAG", error.getMessage());
                    }
                }, error -> {});
        queue.add(stringRequest);
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkIfUpdated();
    }

    private void checkIfUpdated() {
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, IMMEDIATE, activity, RC_APP_UPDATE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                onInit();
            } else {
                onInit();
            }
        }
    }
}
