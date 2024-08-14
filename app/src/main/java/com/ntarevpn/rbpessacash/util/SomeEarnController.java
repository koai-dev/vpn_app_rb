package com.ntarevpn.rbpessacash.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SomeEarnController {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SomeEarnController(Context context) {
        sharedPreferences = context.getSharedPreferences("SomeEarn_Controller", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void setDate(int date) {
        editor.putInt("date", date);
        editor.commit();
    }

    public int getDate() {
        return sharedPreferences.getInt("date", 0);
    }


    public void dataStore(String CollectReward, String WatchVideo, String OpenReward, String EverydayGifts_Count, String CollectRewardCount, String OpenRewardCount, String SpinCount, String TicTacCount, String TicTacReward, String daily_check, String GoldRewardPoint, String KingPotPoint, String PayEarnGift, String Pollfish_point, String Fyber_point, String ironSource_point, String Video_Ads_Available_Views, String Vungle_Rewarded, String UnityAds_Rewarded, String AppLovin_Rewarded, String AdColony_Rewarded, String Admob_Rewarded, String Startapp_Rewarded, String Facebook_Rewarded, String Slide_image1, String Slide_image2, String Slide_image3, String CompleteSurvey_reward, String Additional_Scratch_Chances, String Additional_Scratch_Point, String Extra_Scratch_Chances, String Extra_Scratch_Point, String Great_Scratch_Chances, String Great_Scratch_Point,
                          String rewarded_and_interstitial_count,
                          String Slide1_openurl,
                          String Slide1_url_control,
                          String Slide2_openurl,
                          String Slide2_url_control,
                          String Slide3_openurl,
                          String Slide3_url_control) {
        editor.putString("CollectReward", CollectReward);
        editor.putString("WatchVideo", WatchVideo);
        editor.putString("OpenReward", OpenReward);
        editor.putString("EverydayGifts_Count", EverydayGifts_Count);
        editor.putString("CollectRewardCount", CollectRewardCount);
        editor.putString("OpenRewardCount", OpenRewardCount);
        editor.putString("SpinCount", SpinCount);
        editor.putString("TicTacCount", TicTacCount);
        editor.putString("TicTacReward", TicTacReward);
        editor.putString("daily_check", daily_check);
        editor.putString("GoldRewardPoint", GoldRewardPoint);
        editor.putString("KingPotPoint", KingPotPoint);
        editor.putString("PayEarnGift", PayEarnGift);
        editor.putString("Pollfish_point", Pollfish_point);
        editor.putString("Fyber_point", Fyber_point);
        editor.putString("ironSource_point", ironSource_point);
        editor.putString("Video_Ads_Available_Views", Video_Ads_Available_Views);
        editor.putString("Vungle_Rewarded", Vungle_Rewarded);
        editor.putString("UnityAds_Rewarded", UnityAds_Rewarded);
        editor.putString("AppLovin_Rewarded", AppLovin_Rewarded);
        editor.putString("AdColony_Rewarded", AdColony_Rewarded);
        editor.putString("Admob_Rewarded", Admob_Rewarded);
        editor.putString("Startapp_Rewarded", Startapp_Rewarded);
        editor.putString("Facebook_Rewarded", Facebook_Rewarded);
        editor.putString("Slide_image1", Slide_image1);
        editor.putString("Slide_image2", Slide_image2);
        editor.putString("Slide_image3", Slide_image3);
        editor.putString("CompleteSurvey_reward", CompleteSurvey_reward);
        editor.putString("Additional_Scratch_Chances", Additional_Scratch_Chances);
        editor.putString("Additional_Scratch_Point", Additional_Scratch_Point);
        editor.putString("Extra_Scratch_Chances", Extra_Scratch_Chances);
        editor.putString("Extra_Scratch_Point", Extra_Scratch_Point);
        editor.putString("Great_Scratch_Chances", Great_Scratch_Chances);
        editor.putString("Great_Scratch_Point", Great_Scratch_Point);
        editor.putString("rewarded_and_interstitial_count", rewarded_and_interstitial_count);
        editor.putString("Slide1_openurl", Slide1_openurl);
        editor.putString("Slide1_url_control", Slide1_url_control);
        editor.putString("Slide2_openurl", Slide2_openurl);
        editor.putString("Slide2_url_control", Slide2_url_control);
        editor.putString("Slide3_openurl", Slide3_openurl);
        editor.putString("Slide3_url_control", Slide3_url_control);
        editor.commit();
    }

    public String getCollectReward() {
        return sharedPreferences.getString("CollectReward", "0");
    }

    public String getWatchVideo() {
        return sharedPreferences.getString("WatchVideo", "0");
    }

    public String getOpenReward() {
        return sharedPreferences.getString("OpenReward", "");
    }

    public String getEverydayGifts_Count() {
        return sharedPreferences.getString("EverydayGifts_Count", "");
    }

    public String getCollectRewardCount() {
        return sharedPreferences.getString("CollectRewardCount", "");
    }

    public String getOpenRewardCount() {
        return sharedPreferences.getString("OpenRewardCount", "");
    }

    public String getSpinCount() {
        return sharedPreferences.getString("SpinCount", "");
    }

    public String getTicTacCount() {
        return sharedPreferences.getString("TicTacCount", "");
    }

    public String getTicTacReward() {
        return sharedPreferences.getString("TicTacReward", "");
    }

    public String getEverydayGiftReward() {
        return sharedPreferences.getString("EverydayGiftReward", "");
    }

    public String getEverydayGiftCount() {
        return sharedPreferences.getString("EverydayGiftCount", "");
    }

    public String getDailyCheck() {
        return sharedPreferences.getString("daily_check", "");
    }

    public String getGoldRewardPoint() {
        return sharedPreferences.getString("GoldRewardPoint", "");
    }

    public String getKingPotPoint() {
        return sharedPreferences.getString("KingPotPoint", "");
    }

    public String getPayEarnGift() {
        return sharedPreferences.getString("PayEarnGift", "");
    }

    public String getPollfish_point() {
        return sharedPreferences.getString("Pollfish_point", "");
    }

    public String getFyber_point() {
        return sharedPreferences.getString("Fyber_point", "");
    }

    public String getironSource_point() {
        return sharedPreferences.getString("ironSource_point", "");
    }

    public String getVideo_Ads_Available_Views() {
        return sharedPreferences.getString("Video_Ads_Available_Views", "");
    }

    public String getVungle_Rewarded() {
        return sharedPreferences.getString("Vungle_Rewarded", "");
    }

    public String getUnityAds_Rewarded() {
        return sharedPreferences.getString("UnityAds_Rewarded", "");
    }

    public String getAppLovin_Rewarded() {
        return sharedPreferences.getString("AppLovin_Rewarded", "");
    }

    public String getAdColony_Rewarded() {
        return sharedPreferences.getString("AdColony_Rewarded", "");
    }

    public String getAdmob_Rewarded() {
        return sharedPreferences.getString("Admob_Rewarded", "");
    }

    public String getStartapp_Rewarded() {
        return sharedPreferences.getString("Startapp_Rewarded", "");
    }

    public String getFacebook_Rewarded() {
        return sharedPreferences.getString("Facebook_Rewarded", "");
    }

    public String getSlide_image1() {
        return sharedPreferences.getString("Slide_image1", "");
    }

    public String getSlide_image2() {
        return sharedPreferences.getString("Slide_image2", "");
    }

    public String getSlide_image3() {
        return sharedPreferences.getString("Slide_image3", "");
    }

    public String getCompleteSurvey_reward() {
        return sharedPreferences.getString("CompleteSurvey_reward", "");
    }

    public String getAdditional_Scratch_Chances() {
        return sharedPreferences.getString("Additional_Scratch_Chances", "");
    }

    public String getAdditional_Scratch_Point() {
        return sharedPreferences.getString("Additional_Scratch_Point", "");
    }

    public String getExtra_Scratch_Chances() {
        return sharedPreferences.getString("Extra_Scratch_Chances", "");
    }

    public String getExtra_Scratch_Point() {
        return sharedPreferences.getString("Extra_Scratch_Point", "");
    }

    public String getGreat_Scratch_Chances() {
        return sharedPreferences.getString("Great_Scratch_Chances", "");
    }

    public String getGreat_Scratch_Point() {
        return sharedPreferences.getString("Great_Scratch_Point", "");
    }

    public String getRewarded_and_interstitial_count() {
        return sharedPreferences.getString("rewarded_and_interstitial_count", "");
    }
    public String getSlide1_openurl() {
        return sharedPreferences.getString("Slide1_openurl", "");
    }
    public String getSlide1_url_control() {
        return sharedPreferences.getString("Slide1_url_control", "");
    }
    public String getSlide2_openurl() {
        return sharedPreferences.getString("Slide2_openurl", "");
    }
    public String getSlide2_url_control() {
        return sharedPreferences.getString("Slide2_url_control", "");
    }
    public String getSlide3_openurl() {
        return sharedPreferences.getString("Slide3_openurl", "");
    }
    public String getSlide3_url_control() {
        return sharedPreferences.getString("Slide3_url_control", "");
    }


}
