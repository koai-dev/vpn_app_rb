package com.ntarevpn.rbpessacash.viewModels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ntarevpn.rbpessacash.api.ApiClient;
import com.ntarevpn.rbpessacash.api.ApiVPNService;
import com.ntarevpn.rbpessacash.models.Server;
import com.ntarevpn.rbpessacash.state.ServersState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.oneconnectapi.app.api.OneConnect;

public class ServersViewModel extends ViewModel {
    private final ApiVPNService api = ApiClient.getVPNInstance();
    private final MutableLiveData<ServersState> state = new MutableLiveData<>(new ServersState.Idle());
    private final MutableLiveData<ServersState> paidState = new MutableLiveData<>(new ServersState.Idle());

    public LiveData<ServersState> getState() {
        return state;
    }

    public LiveData<ServersState> getPaidState() {
        return paidState;
    }

    public void fetchFreeServers() {
        Call<List<Server>> freeServers = api.getFreeServers();
        state.postValue(new ServersState.Loading());

        freeServers.enqueue(new Callback<List<Server>>() {
            @Override
            public void onResponse(@NonNull Call<List<Server>> call, @NonNull Response<List<Server>> response) {
                if (response.isSuccessful()) {
                    List<Server> body = response.body();
                    if (body != null) {
                        state.postValue(new ServersState.Success(body));
                    } else {
                        state.postValue(new ServersState.Failure("Unknown error has occurred. Response is null"));
                    }
                } else {
                    state.postValue(new ServersState.Failure(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Server>> call, @NonNull Throwable t) {
                state.postValue(new ServersState.Failure(t.getMessage()));
            }
        });
    }

    public void fetchOneConnectFreeServers(OneConnect oneConnect) {
        Log.d("FREE SERVER", "LOADING");
        state.postValue(new ServersState.Loading());
        new Thread(() -> {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Server>>() {
                }.getType();

                String response = oneConnect.fetch(false);
                try {
                    List<Server> servers = gson.fromJson(response, type);
                    state.postValue(new ServersState.Success(servers));
                } catch (Exception e) {
                    try {
                        Server server = gson.fromJson(response, Server.class);
                        List<Server> servers = new ArrayList<>();
                        servers.add(server);
                        state.postValue(new ServersState.Success(servers));
                    } catch (Exception e2) {
                        Log.d("FREE SERVER", "FAILURE");
                        state.postValue(new ServersState.Failure(e2.getMessage()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                state.postValue(new ServersState.Failure(e.getMessage()));
                Log.d("FREE SERVER", "FAILURE");
            }
        }).start();
    }

    public void fetchOneConnectPaidServers(OneConnect oneConnect) {
        paidState.postValue(new ServersState.Loading());
        new Thread(() -> {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Server>>() {
                }.getType();

                String response = oneConnect.fetch(true);
                try {
                    List<Server> servers = gson.fromJson(response, type);
                    paidState.postValue(new ServersState.Success(servers));
                } catch (Exception e) {
                    try {
                        Server server = gson.fromJson(response, Server.class);
                        List<Server> servers = new ArrayList<>();
                        servers.add(server);
                        paidState.postValue(new ServersState.Success(servers));
                    } catch (Exception e2) {
                        paidState.postValue(new ServersState.Failure(e2.getMessage()));
                    }
                }
            } catch (Exception e) {
                paidState.postValue(new ServersState.Failure(e.getMessage()));
            }
        }).start();
    }

    public void setState(List<Server> serverList) {
        state.postValue(new ServersState.Success(serverList));
    }

    public void fetchPaidServers() {
        Call<List<Server>> paidServers = api.getPaidServers();
        paidState.postValue(new ServersState.Loading());
        Log.d("PAID SERVER", "GET");

        paidServers.enqueue(new Callback<List<Server>>() {
            @Override
            public void onResponse(@NonNull Call<List<Server>> call, @NonNull Response<List<Server>> response) {
                if (response.isSuccessful()) {
                    List<Server> body = response.body();
                    if (body != null) {
                        paidState.postValue(new ServersState.Success(body));
                    } else {
                        paidState.postValue(new ServersState.Failure("Unknown error has occurred. Response is null"));
                    }
                } else {
                    paidState.postValue(new ServersState.Failure(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Server>> call, @NonNull Throwable t) {
                paidState.postValue(new ServersState.Failure(t.getMessage()));
            }
        });
    }

    public void setPaidState(List<Server> serverList) {
        paidState.postValue(new ServersState.Success(serverList));
    }
}
