package com.ntarevpn.rbpessacash.api;

import com.ntarevpn.rbpessacash.models.AdMob;
import com.ntarevpn.rbpessacash.models.OneConnectStatus;
import com.ntarevpn.rbpessacash.models.SMTPDetail;
import com.ntarevpn.rbpessacash.models.Server;
import com.ntarevpn.rbpessacash.models.Subscription;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiVPNService {
    @GET("oneConnectfrsrv")
    Call<List<Server>> getFreeServers();

    @GET("oneConnectsrv")
    Call<List<Server>> getPaidServers();

    @GET("api.php?oneConnect")
    Call<OneConnectStatus> getOneConnectStatus();

    @GET("api.php?adsapp")
    Call<List<AdMob>> getAdMob();

    @GET("api.php?get_subscription")
    Call<List<Subscription>> getSubscriptionList();

    @GET("api.php?smtp")
    Call<List<SMTPDetail>> getSMTPDetailList();
}
