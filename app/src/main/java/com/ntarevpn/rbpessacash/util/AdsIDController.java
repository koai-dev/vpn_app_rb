package com.ntarevpn.rbpessacash.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class AdsIDController {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public AdsIDController(Context context) {
        sharedPreferences = context.getSharedPreferences("Ads_ID_Controller", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void setDate(int date) {
        editor.putInt("date", date);
        editor.commit();
    }

    public int getDate() {
        return sharedPreferences.getInt("date", 0);
    }

    public void dataStore(String admob_app_id,
                          String admob_banner_id,
                          String Admob_Interstitial_id,
                          String Admob_rewarded_id,
                          String Facebook_app_id,
                          String Facebook_Interstitial_id,
                          String Facebook_rewarded_id,
                          String Fyber_app_id,
                          String Fyber_security_key,
                          String Vungle_key,
                          String Vungle_InterstitialPlacementID,
                          String Applovin_SDK_Key,
                          String AdcolonyAPP_ID,
                          String AdcolonyREWARD_ZONE_ID,
                          String AdcolonyINT_ZONE_ID,
                          String IronSource_App_Key,
                          String pollfish_key,
                          String Unity_Game_Id,
                          String StartaApp_app_id,
                          String Tapjoy_SDK_KEY,
                          String Tapjoy_PLACEMENT_OFFERWALL) {
        editor.putString("admob_app_id", admob_app_id);
        editor.putString("admob_banner_id", admob_banner_id);
        editor.putString("Admob_Interstitial_id", Admob_Interstitial_id);
        editor.putString("Admob_rewarded_id", Admob_rewarded_id);
        editor.putString("Facebook_app_id", Facebook_app_id);
        editor.putString("Facebook_Interstitial_id", Facebook_Interstitial_id);
        editor.putString("Facebook_rewarded_id", Facebook_rewarded_id);
        editor.putString("Fyber_app_id", Fyber_app_id);
        editor.putString("Fyber_security_key", Fyber_security_key);
        editor.putString("Vungle_key", Vungle_key);
        editor.putString("Vungle_InterstitialPlacementID", Vungle_InterstitialPlacementID);
        editor.putString("Applovin_SDK_Key", Applovin_SDK_Key);
        editor.putString("AdcolonyAPP_ID", AdcolonyAPP_ID);
        editor.putString("AdcolonyREWARD_ZONE_ID", AdcolonyREWARD_ZONE_ID);
        editor.putString("AdcolonyINT_ZONE_ID", AdcolonyINT_ZONE_ID);
        editor.putString("IronSource_App_Key", IronSource_App_Key);
        editor.putString("pollfish_key", pollfish_key);
        editor.putString("Unity_Game_Id", Unity_Game_Id);
        editor.putString("StartaApp_app_id", StartaApp_app_id);
        editor.putString("Tapjoy_SDK_KEY", Tapjoy_SDK_KEY);
        editor.putString("Tapjoy_PLACEMENT_OFFERWALL", Tapjoy_PLACEMENT_OFFERWALL);
        editor.commit();
    }

    public String getAdmobAppId() {
        return sharedPreferences.getString("admob_app_id", "0");
    }

    public String getAdmobBannerId() {
        return sharedPreferences.getString("admob_banner_id", "0");
    }

    public String getAdmob_Interstitial_id() {
        return sharedPreferences.getString("Admob_Interstitial_id", "0");
    }

    public String getAdmobRewardedId() {
        return sharedPreferences.getString("Admob_rewarded_id", "0");
    }

    public String getFacebookAppId() {
        return sharedPreferences.getString("Facebook_app_id", "0");
    }

    public String getFacebookInterstitialId() {
        return sharedPreferences.getString("Facebook_Interstitial_id", "0");
    }

    public String getFacebookRewardedId() {
        return sharedPreferences.getString("Facebook_rewarded_id", "0");
    }

    public String getFyberAppId() {
        return sharedPreferences.getString("Fyber_app_id", "0");
    }

    public String getFyberSecurityKey() {
        return sharedPreferences.getString("Fyber_security_key", "0");
    }

    public String getVungleKey() {
        return sharedPreferences.getString("Vungle_key", "0");
    }

    public String getVungleInterstitialPlacementID() {
        return sharedPreferences.getString("Vungle_InterstitialPlacementID", "0");
    }

    public String getApplovinSDKKey() {
        return sharedPreferences.getString("Applovin_SDK_Key", "0");
    }

    public String getAdcolonyAppId() {
        return sharedPreferences.getString("AdcolonyAPP_ID", "0");
    }

    public String getAdcolonyRewardZoneId() {
        return sharedPreferences.getString("AdcolonyREWARD_ZONE_ID", "0");
    }

    public String getAdcolonyIntZoneId() {
        return sharedPreferences.getString("AdcolonyINT_ZONE_ID", "0");
    }

    public String getIronSourceAppKey() {
        return sharedPreferences.getString("IronSource_App_Key", "0");
    }

    public String getPollfishKey() {
        return sharedPreferences.getString("pollfish_key", "0");
    }

    public String getUnityGameId() {
        return sharedPreferences.getString("Unity_Game_Id", "0");
    }

    public String getStartaAppAppId() {
        return sharedPreferences.getString("StartaApp_app_id", "0");
    }

    public String getTapjoySdkKey() {
        return sharedPreferences.getString("Tapjoy_SDK_KEY", "0");
    }

    public String getTapjoyPlacementOfferwall() {
        return sharedPreferences.getString("Tapjoy_PLACEMENT_OFFERWALL", "0");
    }

}
