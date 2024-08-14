package com.ntarevpn.rbpessacash.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ntarevpn.rbpessacash.api.ApiClient;
import com.ntarevpn.rbpessacash.api.ApiService;
import com.ntarevpn.rbpessacash.models.EmptyServerResponse;
import com.ntarevpn.rbpessacash.state.ResetPasswordState;
import com.ntarevpn.rbpessacash.state.SendOTPState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordViewModel extends ViewModel {
    private final ApiService api = ApiClient.getInstance();

    private final MutableLiveData<String> secretOTP = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<SendOTPState> sendOTPState = new MutableLiveData<>();
    private final MutableLiveData<ResetPasswordState> resetPasswordState = new MutableLiveData<>();

    public LiveData<String> getSecretOTP() {
        return secretOTP;
    }

    public void setSecretOTP(String value) {
        secretOTP.postValue(value);
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String value) {
        email.postValue(value);
    }

    public LiveData<SendOTPState> getSendOTPState() {
        return sendOTPState;
    }

    public void sendOTP(String email, String secretOTP) {
        Call<EmptyServerResponse> request = api.forgotPassword(email, secretOTP, "anything");
        sendOTPState.postValue(new SendOTPState.Loading());

        request.enqueue(new Callback<EmptyServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<EmptyServerResponse> call, @NonNull Response<EmptyServerResponse> response) {
                if (response.isSuccessful()) {
                    EmptyServerResponse body = response.body();
                    if (body != null) {
                        String message = body.getMessage();
                        Boolean status = body.getStatus();
                        if (status) {
                            sendOTPState.postValue(new SendOTPState.Success(secretOTP));
                            sendOTPState.setValue(new SendOTPState.Idle());
                        } else {
                            sendOTPState.postValue(new SendOTPState.Failure(message));
                        }
                    } else {
                        sendOTPState.postValue(new SendOTPState.Failure("Unknown error has occurred. Response is null"));
                    }
                } else {
                    sendOTPState.postValue(new SendOTPState.Failure(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<EmptyServerResponse> call, @NonNull Throwable t) {
                sendOTPState.postValue(new SendOTPState.Failure(t.getMessage()));
            }
        });
    }

    public LiveData<ResetPasswordState> getResetPasswordState() {
        return resetPasswordState;
    }

    public void resetPassword(String email, String password) {
        Call<EmptyServerResponse> request = api.resetPassword(email, password, "any");
        resetPasswordState.postValue(new ResetPasswordState.Loading());

        request.enqueue(new Callback<EmptyServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<EmptyServerResponse> call, @NonNull Response<EmptyServerResponse> response) {
                if (response.isSuccessful()) {
                    EmptyServerResponse body = response.body();
                    if (body != null) {
                        String message = body.getMessage();
                        Boolean status = body.getStatus();
                        if (status) {
                            resetPasswordState.postValue(new ResetPasswordState.Success(password));
                            resetPasswordState.setValue(new ResetPasswordState.Idle());
                        } else {
                            resetPasswordState.postValue(new ResetPasswordState.Failure(message));
                        }
                    } else {
                        resetPasswordState.postValue(new ResetPasswordState.Failure("Unknown error has occurred. Response is null"));
                    }
                } else {
                    resetPasswordState.postValue(new ResetPasswordState.Failure(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<EmptyServerResponse> call, @NonNull Throwable t) {
                resetPasswordState.postValue(new ResetPasswordState.Failure(t.getMessage()));
            }
        });
    }
}
