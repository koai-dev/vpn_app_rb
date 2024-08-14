package com.ntarevpn.rbpessacash.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ntarevpn.rbpessacash.api.ApiClient;
import com.ntarevpn.rbpessacash.api.ApiService;
import com.ntarevpn.rbpessacash.models.ServerResponse;
import com.ntarevpn.rbpessacash.models.User;
import com.ntarevpn.rbpessacash.state.RegisterState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {
    private final ApiService api = ApiClient.getInstance();
    private final MutableLiveData<RegisterState> state = new MutableLiveData<>(new RegisterState.Idle());

    public LiveData<RegisterState> getRegisterState() {
        return state;
    }

    public void register(String name, String number, String email, String password, String referralCode, String signUpBonus) {
        state.postValue(new RegisterState.Loading());
        String myReferralCode = name.replaceAll("\\s+", "") + number.substring(0, 4);
        Call<ServerResponse<User>> request = api.register(name, number, email, password, myReferralCode, referralCode.isEmpty() ? null : referralCode, "anythimg");
        request.enqueue(new Callback<ServerResponse<User>>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse<User>> call, @NonNull Response<ServerResponse<User>> response) {
                if (response.isSuccessful()) {
                    ServerResponse<User> body = response.body();
                    if (body != null) {
                        User user = body.getResponse();
                        String message = body.getMessage();
                        Boolean status = body.getStatus();
                        if (status) {
                            state.postValue(new RegisterState.Success(user, password));
                        } else {
                            state.postValue(new RegisterState.Failure(message));
                        }
                    } else {
                        state.postValue(new RegisterState.Failure("Unknown error has occurred. Response is null"));
                    }
                } else {
                    state.postValue(new RegisterState.Failure(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse<User>> call, @NonNull Throwable t) {

            }
        });
    }
}
