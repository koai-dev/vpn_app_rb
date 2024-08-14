package com.ntarevpn.rbpessacash.models;

import com.google.gson.annotations.SerializedName;

public class ServerResponse<T> {
    @SerializedName("message")
    String message;

    @SerializedName("0")
    T response;

    @SerializedName("status")
    Boolean status;

    public String getMessage() {
        return message;
    }

    public T getResponse() {
        return response;
    }

    public Boolean getStatus() {
        return status;
    }
}
