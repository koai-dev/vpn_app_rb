package com.ntarevpn.rbpessacash.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Server implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("serverName")
    private String serverName;
    @SerializedName("country")
    private String country;
    @SerializedName("flag_url")
    private String flagUrl;
    @SerializedName("ovpnConfiguration")
    private String vpnConfiguration;
    @SerializedName("vpnUserName")
    private String vpnUserName;
    @SerializedName("vpnPassword")
    private String vpnUserPassword;
    @SerializedName("isFree")
    private String isFree;

    public Server() {}

    public Server(String id, String serverName, String country, String flagUrl, String vpnConfiguration, String vpnUserName, String vpnUserPassword, String isFree) {
        this.id = id;
        this.serverName = serverName;
        this.country = country;
        this.flagUrl = flagUrl;
        this.vpnConfiguration = vpnConfiguration;
        this.vpnUserName = vpnUserName;
        this.vpnUserPassword = vpnUserPassword;
        this.isFree = isFree;
    }

    @Nullable
    public String getId() {
        return id;
    }

    public String getServerName() {
        return serverName;
    }

    public String getCountry() {
        return country;
    }
    public String getFlagUrl() {
        return flagUrl;
    }

    public String getVpnConfiguration() {
        return vpnConfiguration;
    }

    public String getVpnUserName() {
        return vpnUserName;
    }

    public String getVpnUserPassword() {
        return vpnUserPassword;
    }

    public Boolean getIsFree() {
        return Objects.equals(isFree, "1");
    }

    public static final Creator<Server> CREATOR
            = new Creator<Server>() {
        public Server createFromParcel(Parcel in) {
            return new Server(in);
        }

        public Server[] newArray(int size) {
            return new Server[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(serverName);
        dest.writeString(country);
        dest.writeString(flagUrl);
        dest.writeString(vpnConfiguration);
        dest.writeString(vpnUserName);
        dest.writeString(vpnUserPassword);
        dest.writeString(isFree);
    }

    private Server(Parcel in) {
        id = in.readString();
        serverName = in.readString();
        country = in.readString();
        flagUrl = in.readString();
        vpnConfiguration = in.readString();
        vpnUserName = in.readString();
        vpnUserPassword = in.readString();
        isFree = in.readString();
    }

    @NonNull
    @Override
    public String toString() {
        return "Id: " + id + "\nServer name: " + serverName + "\nCountry: " + country + "\nUsername: " + vpnUserName + "\nPassword: " + vpnUserPassword + "\nIs Free: " + isFree + "\nConfig: " + vpnConfiguration;
    }
}
