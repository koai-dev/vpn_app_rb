package com.ntarevpn.rbpessacash.util;

import static com.ntarevpn.rbpessacash.util.Utils.getImgURL;

import android.content.Context;
import android.content.SharedPreferences;

import com.ntarevpn.rbpessacash.models.Server;
import  com.ntarevpn.rbpessacash.R;

public class SharedPreference {

    private static final String APP_PREFS_NAME = "VPNPreference";

    private final SharedPreferences mPreference;
    private final SharedPreferences.Editor mPrefEditor;

    private static final String SERVER_ID = "server_id";
    private static final String SERVER_NAME = "server_name";
    private static final String SERVER_COUNTRY = "server_country";
    private static final String SERVER_FLAG = "server_flag";
    private static final String SERVER_OVPN = "server_ovpn";
    private static final String SERVER_OVPN_USER = "server_ovpn_user";
    private static final String SERVER_OVPN_PASSWORD = "server_ovpn_password";
    private static final String SERVER_IS_FREE = "server_ovpn_password";
    public SharedPreference(Context context) {
        this.mPreference = context.getSharedPreferences(APP_PREFS_NAME, Context.MODE_PRIVATE);
        this.mPrefEditor = mPreference.edit();
    }


    public void saveServer(Server server){
        mPrefEditor.putString(SERVER_ID, server.getId());
        mPrefEditor.putString(SERVER_NAME, server.getServerName());
        mPrefEditor.putString(SERVER_COUNTRY, server.getCountry());
        mPrefEditor.putString(SERVER_FLAG, server.getFlagUrl());
        mPrefEditor.putString(SERVER_OVPN, server.getVpnConfiguration());
        mPrefEditor.putString(SERVER_OVPN_USER, server.getVpnUserName());
        mPrefEditor.putString(SERVER_OVPN_PASSWORD, server.getVpnUserPassword());
        mPrefEditor.putString(SERVER_IS_FREE, server.getIsFree() ? "1" : "0");
        mPrefEditor.commit();
    }

    public Server getServer() {
        return new Server(
                mPreference.getString(SERVER_ID,"-1"),
                mPreference.getString(SERVER_NAME,""),
                mPreference.getString(SERVER_COUNTRY,""),
                mPreference.getString(SERVER_FLAG,getImgURL(R.drawable.ic_baseline_language_24)),
                mPreference.getString(SERVER_OVPN,"japan.ovpn"),
                mPreference.getString(SERVER_OVPN_USER,"vpn"),
                mPreference.getString(SERVER_OVPN_PASSWORD,"vpn"),
                mPreference.getString(SERVER_IS_FREE,"1")
        );
    }

    public void resetServer(){
        mPrefEditor.putString(SERVER_ID,"-1");
        mPrefEditor.putString(SERVER_NAME,"");
        mPrefEditor.putString(SERVER_COUNTRY,"");
        mPrefEditor.putString(SERVER_FLAG,getImgURL(R.drawable.ic_baseline_language_24));
        mPrefEditor.putString(SERVER_OVPN,"japan.ovpn");
        mPrefEditor.putString(SERVER_OVPN_USER,"vpn");
        mPrefEditor.putString(SERVER_OVPN_PASSWORD,"vpn");
        mPrefEditor.putString(SERVER_IS_FREE,"1");
        mPrefEditor.commit();
    }
}
