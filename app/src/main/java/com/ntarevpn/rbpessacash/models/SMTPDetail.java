package com.ntarevpn.rbpessacash.models;

import com.google.gson.annotations.SerializedName;

public class SMTPDetail {
    @SerializedName("id")
    private String id;
    @SerializedName("smtp_type")
    private String smtpType;
    @SerializedName("smtp_host")
    private String smtpHost;
    @SerializedName("smtp_email")
    private String smtpEmail;
    @SerializedName("smtp_password")
    private String smtpPassword;
    @SerializedName("smtp_secure")
    private String smtpSecure;
    @SerializedName("port_no")
    private String portNo;
    @SerializedName("smtp_ghost")
    private String smtpGhost;
    @SerializedName("smtp_gemail")
    private String smtpGemail;
    @SerializedName("smtp_gpassword")
    private String smtpGpassword;
    @SerializedName("smtp_gsecure")
    private String smtpGsecure;
    @SerializedName("gport_no")
    private String gportNo;

    public String getId() {
        return id;
    }

    public String getSmtpType() {
        return smtpType;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public String getSmtpEmail() {
        return smtpEmail;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public String getSmtpSecure() {
        return smtpSecure;
    }

    public String getPortNo() {
        return portNo;
    }

    public String getSmtpGhost() {
        return smtpGhost;
    }

    public String getSmtpGemail() {
        return smtpGemail;
    }

    public String getSmtpGpassword() {
        return smtpGpassword;
    }

    public String getSmtpGsecure() {
        return smtpGsecure;
    }

    public String getGportNo() {
        return gportNo;
    }
}