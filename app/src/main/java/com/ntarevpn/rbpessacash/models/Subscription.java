package com.ntarevpn.rbpessacash.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Subscription {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("product_id")
    private String productId;
    @SerializedName("price")
    private String price;
    @SerializedName("currency")
    private String currency;
    @SerializedName("description")
    private String description;
    @SerializedName("status")
    private String status;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProductId() {
        return productId;
    }

    public String getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getStatus() {
        return Objects.equals(status, "1");
    }
}
