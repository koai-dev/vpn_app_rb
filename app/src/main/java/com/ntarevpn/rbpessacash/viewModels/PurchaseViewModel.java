package com.ntarevpn.rbpessacash.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ntarevpn.rbpessacash.api.ApiClient;
import com.ntarevpn.rbpessacash.api.ApiVPNService;
import com.ntarevpn.rbpessacash.models.Subscription;
import com.ntarevpn.rbpessacash.state.SubscriptionsState;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseViewModel extends ViewModel {
    private final ApiVPNService api = ApiClient.getVPNInstance();
    private final MutableLiveData<String> pickedSubscription = new MutableLiveData<>("");
    private final MutableLiveData<SubscriptionsState> state = new MutableLiveData<>(new SubscriptionsState.Idle());

    public LiveData<String> getPickedSubscription() {
        return pickedSubscription;
    }

    public void setPickedSubscription(String value) {
        pickedSubscription.postValue(value);
    }

    public LiveData<SubscriptionsState> getState() {
        return state;
    }

    public void fetchSubscription() {
        Call<List<Subscription>> request = api.getSubscriptionList();

        state.postValue(new SubscriptionsState.Loading());
        request.enqueue(new Callback<List<Subscription>>() {
            @Override
            public void onResponse(@NonNull Call<List<Subscription>> call, @NonNull Response<List<Subscription>> response) {
                if (response.isSuccessful()) {
                    final List<Subscription> body = response.body();
                    if (body != null) {
                        state.postValue(new SubscriptionsState.Success(body));
                    } else {
                        state.postValue(new SubscriptionsState.Failure("Unknown error has occurred. Response is null"));
                    }
                } else {
                    state.postValue(new SubscriptionsState.Failure(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Subscription>> call, @NonNull Throwable t) {
                state.postValue(new SubscriptionsState.Failure(t.getMessage()));
            }
        });
    }
}
