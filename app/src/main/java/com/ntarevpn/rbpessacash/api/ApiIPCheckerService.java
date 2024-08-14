package com.ntarevpn.rbpessacash.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiIPCheckerService {
    @GET("/")
    Call<String> checkIP();
}
