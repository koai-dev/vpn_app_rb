package com.ntarevpn.rbpessacash.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class AdsController {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public AdsController(Context context) {
        sharedPreferences = context.getSharedPreferences("Ads_Controller", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void setDate(int date) {
        editor.putInt("date", date);
        editor.commit();
    }

    public int getDate() {
        return sharedPreferences.getInt("date", 0);
    }

    public void dataStore(String collect_reward_ads, String Open_Reward_ads, String Everyday_gift_ads,
                          String Scratch_win_ads1,
                          String Scratch_win_ads2,
                          String Scratch_win_ads3,
                          String TicTAcToe_Ads,
                          String gold_reward_ads,
                          String king_pot_ads,
                          String pay_earn_gift_ads,
                          String daily_check_reward_ads,
                          String spin_reward_ads,
                          String Banner_Ads,
                          String Int_Ads,
                          String signup_bonus,
                          String Refer_Point,
                          String Contact_us_email,
                          String spin_Interstitial_ads,
                          String collect_Interstitial_ads,
                          String Open_Interstitial_ads,
                          String Everyday_gift_Interstitial_ads,
                          String Scratch_win_ads1_Interstitial,
                          String Scratch_win_ads2_Interstitial,
                          String Scratch_win_ads3_Interstitial,
                          String TicTAcToe_Interstitial_Ads,
                          String BannerAdsControl,
                          String privacypolicyurl

    ) {
        editor.putString("collect_reward_ads", collect_reward_ads);
        editor.putString("Open_Reward_ads", Open_Reward_ads);
        editor.putString("Everyday_gift_ads", Everyday_gift_ads);
        editor.putString("Scratch_win_ads1", Scratch_win_ads1);
        editor.putString("Scratch_win_ads2", Scratch_win_ads2);
        editor.putString("Scratch_win_ads3", Scratch_win_ads3);
        editor.putString("TicTAcToe_Ads", TicTAcToe_Ads);
        editor.putString("gold_reward_ads", gold_reward_ads);
        editor.putString("king_pot_ads", king_pot_ads);
        editor.putString("pay_earn_gift_ads", pay_earn_gift_ads);
        editor.putString("daily_check_reward_ads", daily_check_reward_ads);
        editor.putString("spin_reward_ads", spin_reward_ads);
        editor.putString("Banner_Ads", Banner_Ads);
        editor.putString("Int_Ads", Int_Ads);
        editor.putString("signup_bonus", signup_bonus);
        editor.putString("Refer_Point", Refer_Point);
        editor.putString("Contact_us_email", Contact_us_email);
        editor.putString("spin_Interstitial_ads", spin_Interstitial_ads);
        editor.putString("collect_Interstitial_ads", collect_Interstitial_ads);
        editor.putString("Open_Interstitial_ads", Open_Interstitial_ads);
        editor.putString("Everyday_gift_Interstitial_ads", Everyday_gift_Interstitial_ads);
        editor.putString("Scratch_win_ads1_Interstitial", Scratch_win_ads1_Interstitial);
        editor.putString("Scratch_win_ads2_Interstitial", Scratch_win_ads2_Interstitial);
        editor.putString("Scratch_win_ads3_Interstitial", Scratch_win_ads3_Interstitial);
        editor.putString("TicTAcToe_Interstitial_Ads", TicTAcToe_Interstitial_Ads);
        editor.putString("BannerAdsControl", BannerAdsControl);
        editor.putString("privacypolicyurl", privacypolicyurl);

        editor.commit();
    }

    public String getCollectRewardAds() {
        return sharedPreferences.getString("collect_reward_ads", "");
    }

    public String getOpenRewardsAds() {
        return sharedPreferences.getString("Open_Reward_ads", "");
    }

    public String getEverydayGiftAds() {
        return sharedPreferences.getString("Everyday_gift_ads", "");
    }

    public String getScratchWinAds() {
        return sharedPreferences.getString("Scratch_win_ads1", "");
    }

    public String getScratchWinAds2() {
        return sharedPreferences.getString("Scratch_win_ads2", "");
    }

    public String getScratchWinAds3() {
        return sharedPreferences.getString("Scratch_win_ads3", "");
    }

    public String getTicTacToeAds() {
        return sharedPreferences.getString("TicTAcToe_Ads", "");
    }

    public String getGoldRewardAds() {
        return sharedPreferences.getString("gold_reward_ads", "");
    }

    public String getKingPotAds() {
        return sharedPreferences.getString("king_pot_ads", "");
    }

    public String getPayEarnGiftAds() {
        return sharedPreferences.getString("pay_earn_gift_ads", "");
    }

    public String getDailyCheckRewardAds() {
        return sharedPreferences.getString("daily_check_reward_ads", "");
    }

    public String getSpinRewardAds() {
        return sharedPreferences.getString("spin_reward_ads", "");
    }

    public String getBannerAds() {
        return sharedPreferences.getString("Banner_Ads", "");
    }

    public String getIntAdsApp() {
        return sharedPreferences.getString("Int_Ads", "");
    }

    public String getSignUpBonus() {
        return sharedPreferences.getString("signup_bonus", "");
    }

    public String getReferPoint() {
        return sharedPreferences.getString("Refer_Point", "");
    }

    public String getContactUsEmail() {
        return sharedPreferences.getString("Contact_us_email", "");
    }

    public String getSpinInterstitialAds() {
        return sharedPreferences.getString("spin_Interstitial_ads", "");
    }

    public String getCollectInterstitialAds() {
        return sharedPreferences.getString("collect_Interstitial_ads", "");
    }

    public String getOpenInterstitialAds() {
        return sharedPreferences.getString("Open_Interstitial_ads", "");
    }

    public String getEverydayGiftInterstitialAds() {
        return sharedPreferences.getString("Everyday_gift_Interstitial_ads", "");
    }

    public String getScratchWinAds1Interstitial() {
        return sharedPreferences.getString("Scratch_win_ads1_Interstitial", "");
    }

    public String getScratchWinAds2Interstitial() {
        return sharedPreferences.getString("Scratch_win_ads2_Interstitial", "");
    }

    public String getScratchWinAds3Interstitial() {
        return sharedPreferences.getString("Scratch_win_ads3_Interstitial", "");
    }

    public String getTicTacToeInterstitialAds() {
        return sharedPreferences.getString("TicTAcToe_Interstitial_Ads", "");
    }

    public String getBannerAdsControl() {
        return sharedPreferences.getString("BannerAdsControl", "");
    }

    public String getPrivacyPolicyUrl() {
        return sharedPreferences.getString("privacypolicyurl", "");
    }


}
