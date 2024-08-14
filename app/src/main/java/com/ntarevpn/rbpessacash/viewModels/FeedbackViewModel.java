package com.ntarevpn.rbpessacash.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ntarevpn.rbpessacash.api.ApiClient;
import com.ntarevpn.rbpessacash.api.ApiVPNService;
import com.ntarevpn.rbpessacash.models.SMTPDetail;
import com.ntarevpn.rbpessacash.state.SMTPState;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackViewModel extends ViewModel {
    private final ApiVPNService api = ApiClient.getVPNInstance();
    private final MutableLiveData<SMTPState> state = new MutableLiveData<>(new SMTPState.Idle());

    public LiveData<SMTPState> getState() {
        return state;
    }

    public void fetchSMTPDetailList() {
        Call<List<SMTPDetail>> request = api.getSMTPDetailList();
        state.postValue(new SMTPState.Loading());

        request.enqueue(new Callback<List<SMTPDetail>>() {
            @Override
            public void onResponse(@NonNull Call<List<SMTPDetail>> call, @NonNull Response<List<SMTPDetail>> response) {
                if (response.isSuccessful()) {
                    List<SMTPDetail> body = response.body();
                    if (body != null) {
                        state.postValue(new SMTPState.Success(body));
                    } else {
                        state.postValue(new SMTPState.Failure("Unknown error has occurred. Response is null"));
                    }
                } else {
                    state.postValue(new SMTPState.Failure(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SMTPDetail>> call, @NonNull Throwable t) {
                state.postValue(new SMTPState.Failure(t.getMessage()));
            }
        });
    }
}
