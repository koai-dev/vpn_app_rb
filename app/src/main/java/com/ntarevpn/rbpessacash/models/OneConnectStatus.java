package com.ntarevpn.rbpessacash.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class OneConnectStatus {
    @SerializedName("one_connect")
    private String isEnabled;

    @SerializedName("one_connect_key")
    private String key;

    public Boolean getIsEnabled() {
        return Objects.equals(isEnabled, "1");
    }

    public String getKey() {
        return key;
    }
}
