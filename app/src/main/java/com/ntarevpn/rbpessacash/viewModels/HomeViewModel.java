package com.ntarevpn.rbpessacash.viewModels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ntarevpn.rbpessacash.api.ApiClient;
import com.ntarevpn.rbpessacash.api.ApiIPCheckerService;
import com.ntarevpn.rbpessacash.api.ApiVPNService;
import com.ntarevpn.rbpessacash.models.AdMob;
import com.ntarevpn.rbpessacash.models.OneConnectStatus;
import com.ntarevpn.rbpessacash.models.Server;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private final ApiVPNService api = ApiClient.getVPNInstance();
    private final ApiIPCheckerService apiIPChecker = ApiClient.getIPCheckerInstance();
    private final MutableLiveData<String> myIP = new MutableLiveData<>("");
    private final MutableLiveData<Server> server = new MutableLiveData<>(null);
    private final MutableLiveData<Boolean> vpnActivated = new MutableLiveData<>(false);
    private final MutableLiveData<OneConnectStatus> oneConnectStatus = new MutableLiveData<>(null);
    private final MutableLiveData<List<AdMob>> adMobList = new MutableLiveData<>(null);

    public LiveData<String> getMyIP() {
        return myIP;
    }

    public void resetMyIP() {
        myIP.postValue("");
    }

    public void fetchMyIP() {
        Call<String> request = apiIPChecker.checkIP();
        Log.d("IP", "GET IP");
        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    String body = response.body();
                    if (body != null) {
                        myIP.postValue(body);
                        Log.d("IP", "SUCCESS");
                    } else {
                        myIP.postValue("");
                        Log.d("IP", "FAILED NULL");
                    }
                } else {
                    myIP.postValue("");
                    Log.d("IP", "FAILED");
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                myIP.postValue("");
                t.printStackTrace();
                Log.d("IP", "ERROR ???");
            }
        });
    }

    public LiveData<Server> getServer() {
        return server;
    }

    public LiveData<Boolean> getVpnActivated() {
        return vpnActivated;
    }

    public void setServer(Server value) {
        server.postValue(value);
    }

    public void setVpnActivated(Boolean value) {
        vpnActivated.postValue(value);
    }

    public LiveData<OneConnectStatus> getOneConnectStatus() {
        return oneConnectStatus;
    }

    public void fetchOneConnectStatus() {
        Call<OneConnectStatus> request = api.getOneConnectStatus();

        request.enqueue(new Callback<OneConnectStatus>() {
            @Override
            public void onResponse(@NonNull Call<OneConnectStatus> call, @NonNull Response<OneConnectStatus> response) {
                if (response.isSuccessful()) {
                    oneConnectStatus.postValue(response.body());
                } else {
                    Log.e("OneConnectStatus", "Failed to fetch data");
                }
            }

            @Override
            public void onFailure(@NonNull Call<OneConnectStatus> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("OneConnectStatus", "Failed to fetch data");
            }
        });
    }

    public LiveData<List<AdMob>> getAdMobList() {
        return adMobList;
    }

    public void fetchAdMob() {
        Call<List<AdMob>> request = api.getAdMob();

        request.enqueue(new Callback<List<AdMob>>() {
            @Override
            public void onResponse(@NonNull Call<List<AdMob>> call, @NonNull Response<List<AdMob>> response) {
                if (response.isSuccessful()) {
                    adMobList.postValue(response.body());
                } else {
                    Log.e("AdMob", "Failed to fetch data");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AdMob>> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("AdMob", "Failed to fetch data");
            }
        });
    }
}
