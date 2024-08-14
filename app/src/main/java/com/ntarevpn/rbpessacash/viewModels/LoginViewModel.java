package com.ntarevpn.rbpessacash.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ntarevpn.rbpessacash.api.ApiClient;
import com.ntarevpn.rbpessacash.api.ApiService;
import com.ntarevpn.rbpessacash.models.ServerResponse;
import com.ntarevpn.rbpessacash.models.User;
import com.ntarevpn.rbpessacash.state.LoginState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private final ApiService api = ApiClient.getInstance();
    private final MutableLiveData<LoginState> state = new MutableLiveData<>(new LoginState.Idle());

    public LiveData<LoginState> getLoginState() {
        return state;
    }

    public void login(String email, String password) {
        state.postValue(new LoginState.Loading());
        Call<ServerResponse<User>> request = api.login(email, password, "any");
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
                            state.postValue(new LoginState.Success(user, password));
                        } else {
                            state.postValue(new LoginState.Failure(message));
                        }
                    } else {
                        state.postValue(new LoginState.Failure("Unknown error has occurred. Response is null"));
                    }
                } else {
                    state.postValue(new LoginState.Failure(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse<User>> call, @NonNull Throwable t) {
                state.postValue(new LoginState.Failure(t.getMessage()));
            }
        });
    }
}
