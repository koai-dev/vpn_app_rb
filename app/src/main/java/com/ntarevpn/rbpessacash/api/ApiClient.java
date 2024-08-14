package com.ntarevpn.rbpessacash.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    private static ApiService api;
    private static ApiVPNService apiVPN;
    private static ApiIPCheckerService apiIPChecker;

    public static ApiService getInstance() {
        if (api == null) {
            api = new Retrofit.Builder().baseUrl(ApiUrlConstant.BASE_URL).client(getClient()).addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.class);
        }
        return api;
    }

    public static ApiVPNService getVPNInstance() {
        if (apiVPN == null) {
            apiVPN = new Retrofit.Builder().baseUrl(ApiUrlConstant.ADMIN_URL + "includes/").client(getClient()).addConverterFactory(GsonConverterFactory.create()).build().create(ApiVPNService.class);
        }
        return apiVPN;
    }

    public static ApiIPCheckerService getIPCheckerInstance() {
        if (apiIPChecker == null) {
            apiIPChecker = new Retrofit.Builder().baseUrl("https://checkip.amazonaws.com/").client(getClient()).addConverterFactory(ScalarsConverterFactory.create()).build().create(ApiIPCheckerService.class);
        }
        return apiIPChecker;
    }

    private static OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);

        return builder.build();
    }
}
