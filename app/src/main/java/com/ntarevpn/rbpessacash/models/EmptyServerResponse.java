package com.ntarevpn.rbpessacash.models;

import com.google.gson.annotations.SerializedName;

public class EmptyServerResponse {
    @SerializedName("message")
    String message;

    @SerializedName("status")
    Boolean status;

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }
}
