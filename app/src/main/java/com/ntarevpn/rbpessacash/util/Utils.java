package com.ntarevpn.rbpessacash.util;

import android.net.Uri;

import  com.ntarevpn.rbpessacash.BuildConfig;

public class Utils {
    public static String getImgURL(int resourceId) {

        return Uri.parse("android.resource://" +  BuildConfig.APPLICATION_ID + "/" + resourceId).toString();
    }

    public static int randomizeBetween(int from, int to) {
        return from + (int) (Math.random() * ((to - from) + 1));
    }
}
