package com.ntarevpn.rbpessacash.viewModels;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ntarevpn.rbpessacash.api.ApiClient;
import com.ntarevpn.rbpessacash.api.ApiService;
import com.ntarevpn.rbpessacash.models.ServerResponse;
import com.ntarevpn.rbpessacash.models.User;
import com.ntarevpn.rbpessacash.state.ProfileUpdateState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    private final ApiService api = ApiClient.getInstance();

    private final MutableLiveData<ProfileUpdateState> updateProfileState = new MutableLiveData<>(new ProfileUpdateState.Idle());
    private final MutableLiveData<Uri> tempProfile = new MutableLiveData<>(Uri.EMPTY);

    public LiveData<Uri> getTempProfile() {
        return tempProfile;
    }

    public LiveData<ProfileUpdateState> getUpdateProfileState() {
        return updateProfileState;
    }

    public void resetProfileState() {
        updateProfileState.postValue(new ProfileUpdateState.Idle());
    }

    public void setTempProfile(Uri uri) {
        tempProfile.postValue(uri);
    }

    public void updateUserProfile(String id, String email, String number, String username, String image) {
        Call<ServerResponse<User>> request = api.updateProfile(id, email, number, username, image, "", "any");
        updateProfileState.postValue(new ProfileUpdateState.Loading());

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
                            updateProfileState.postValue(new ProfileUpdateState.Success(user));
                        } else {
                            updateProfileState.postValue(new ProfileUpdateState.Failure(message));
                        }
                    } else {
                        updateProfileState.postValue(new ProfileUpdateState.Failure("Unknown error has occurred. Response is null"));
                    }
                } else {
                    updateProfileState.postValue(new ProfileUpdateState.Failure(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse<User>> call, @NonNull Throwable t) {
                updateProfileState.postValue(new ProfileUpdateState.Failure(t.getMessage()));
            }
        });
    }
}
