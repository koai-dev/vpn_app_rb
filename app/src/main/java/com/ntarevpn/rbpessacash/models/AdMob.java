package com.ntarevpn.rbpessacash.models;

import com.google.gson.annotations.SerializedName;

public class AdMob {
    @SerializedName("admobID")
    private String adMobID;
    @SerializedName("bannerID")
    private String bannerID;
    @SerializedName("interstitialID")
    private String interstitialID;
    @SerializedName("nativeID")
    private String nativeID;
    @SerializedName("rewardID")
    private String rewardID;
    @SerializedName("adType")
    private String adType;

    public String getAdMobID() {
        return adMobID;
    }

    public String getBannerID() {
        return bannerID;
    }

    public String getInterstitialID() {
        return interstitialID;
    }

    public String getNativeID() {
        return nativeID;
    }

    public String getRewardID() {
        return rewardID;
    }

    public String getAdType() {
        return adType;
    }
}
