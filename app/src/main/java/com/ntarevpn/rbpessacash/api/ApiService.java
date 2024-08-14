package com.ntarevpn.rbpessacash.api;

import androidx.annotation.Nullable;

import com.ntarevpn.rbpessacash.models.EmptyServerResponse;
import com.ntarevpn.rbpessacash.models.ServerResponse;
import com.ntarevpn.rbpessacash.models.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @POST(ApiUrlConstant.LOGIN_API_APP)
    @FormUrlEncoded
    Call<ServerResponse<User>> login(@Field("email") String email, @Field("password") String password, @Field("get_login") String extra);

    @POST(ApiUrlConstant.REGISTER_API_APP)
    @FormUrlEncoded
    Call<ServerResponse<User>> register(@Field("name") String name, @Field("number") String number, @Field("email") String email, @Field("password") String password, @Field("referral_code") String myReferralCode, @Field("referral_with") @Nullable String referralCode, @Field("register") String extra);

    @POST(ApiUrlConstant.UPDATE_PROFILE_APP)
    @FormUrlEncoded
    Call<ServerResponse<User>> updateProfile(@Field("user_id") String userId, @Field("email") String email, @Field("number") String number, @Field("name") String name, @Field("img") String image, @Field("password") String password, @Field("update_profile") String extra);

    @POST(ApiUrlConstant.FORGOT_PASSWORD_APP)
    @FormUrlEncoded
    Call<EmptyServerResponse> forgotPassword(@Field("email") String email, @Field("otp") String secretOTP, @Field("recover") String extra);

    @POST(ApiUrlConstant.RESET_PASSWORD_APP)
    @FormUrlEncoded
    Call<EmptyServerResponse> resetPassword(@Field("email") String email, @Field("pass") String password, @Field("rest_pass") String extra);
}
