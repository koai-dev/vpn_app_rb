package com.ntarevpn.rbpessacash.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("number")
    private String number;
    @SerializedName("email")
    private String email;
    @SerializedName("image")
    private String image;
    @SerializedName("points")
    private String points;
    @SerializedName("status")
    private String isBLocked;
    @SerializedName("referraled_with")
    private String referCode;
    @SerializedName("referral_code")
    @Expose
    private String userReferCode;

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }
    public String getEmail() {
        return email;
    }
    public String getImage() {
        return image;
    }
    public String getPoints() {
        return points;
    }
    public String getIsBLocked() {
        return isBLocked;
    }
    public String getReferCode() {
        return referCode;
    }
    public String getUserReferCode() {
        return userReferCode;
    }

}
