package com.ntarevpn.rbpessacash;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;

import java.util.Map;


public class AppsConfig extends Application {
    private static AppsConfig mInstance;
    public static final String TAG = AppsConfig.class.getSimpleName();

    private RequestQueue mRequestQueue;
    AdsController ads_controller;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ads_controller = new AdsController(mInstance);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        AudienceNetworkAds.initialize(mInstance);
        AdSettings.addTestDevice("ad3f6f42-76a5-460a-a607-4d58eccee585");
        AdSettings.addTestDevice("8863e07b-4d10-458b-8f81-1e84c7478d9f");
        AdSettings.addTestDevice("06518f22-9f3f-40f4-97bc-f91ae11087e2");
        AdSettings.addTestDevice("0d3eb2df-0eec-4e83-a17d-b7ba15e1d395");
        AdSettings.addTestDevice("5c35e678-a81e-465f-892b-b8d59152f1e1");
        AdSettings.addTestDevice("53da7532-56ed-4a31-b6d5-2f0952efed5a");


        MobileAds.initialize(this, initializationStatus -> {
            Log.e("MOBILEADS", "onInitializationComplete: Initialize Successfully...");

            Map<String, AdapterStatus> statusMap = initializationStatus.getAdapterStatusMap();
            for (String adapterClass : statusMap.keySet()) {
                AdapterStatus status = statusMap.get(adapterClass);
                if (status != null) {
                    Log.d("MOBILEADS", String.format(
                            "Adapter name: %s, Description: %s, Latency: %d",
                            adapterClass, status.getDescription(), status.getLatency()));
                }
            }
        });

    }

    public static Context getContext() {
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public static synchronized AppsConfig getInstance() {
        return mInstance;
    }

}
