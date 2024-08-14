package com.ntarevpn.rbpessacash.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ntarevpn.rbpessacash.databinding.ServersLayoutIndratechBinding;
import com.ntarevpn.rbpessacash.viewModels.ServersViewModel;
import com.ntarevpn.rbpessacash.adapter.ServerPageAdapter;
import com.ntarevpn.rbpessacash.models.Server;
import com.ntarevpn.rbpessacash.util.Constant;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import top.oneconnectapi.app.api.OneConnect;

public class Servers extends AppCompatActivity {
    public static final String GET_REQUEST_CODE = "givemeserverplease";
    public static final String CHOSEN_SERVER = "alreadychosenserver";
    public static final String ONE_CONNECT_ENABLED = "isusingoneconnect";
    public static final String ONE_CONNECT_KEY = "oneconnectapikey";
    public static final ArrayList<Server> cachedServerList = new ArrayList<>();
    public static final ArrayList<Server> cachedPaidServerList = new ArrayList<>();

    private ServersLayoutIndratechBinding binding;
    private ServersViewModel viewModel;
    private OneConnect oneConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ServersLayoutIndratechBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeOneConnect();
        initializeAds();
        initializeViewModel();
        initializePager();
        initializeClickListener();
        fetchServers(false);
    }

    private void initializeOneConnect() {
        oneConnect = new OneConnect();
        if (isOneConnectEnabled() && getOneConnectKey() != null) {
            oneConnect.initialize(this, getOneConnectKey());
        }
    }

    private void initializeAds() {
        Constant.loadSmallAdBanner(binding.adsContainer);
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(ServersViewModel.class);
    }

    private void initializePager() {
        ServerPageAdapter serverPageAdapter = new ServerPageAdapter(this);
        binding.serverList.setAdapter(serverPageAdapter);

        TabLayoutMediator mediator = new TabLayoutMediator(binding.serverTabLayout, binding.serverList, (tab, pos) -> {
            if (pos == 0) {
                tab.setText("Free Servers");
            } else {
                tab.setText("VIP Servers");
            }
        });
        mediator.attach();
    }

    private void initializeClickListener() {
        binding.backButtonServers.setOnClickListener(v -> onBackPressed());
        binding.syncButtonServers.setOnClickListener(v -> fetchServers(true));
    }

    private void fetchServers(boolean forced) {
        fetchFreeServer(forced);
        fetchPaidServer(forced);
    }

    private void fetchFreeServer(boolean forced) {
        if (!forced && !cachedServerList.isEmpty()) {
            viewModel.setState(cachedServerList);
            return;
        }
        if (isOneConnectEnabled() && getOneConnectKey() != null) {
            viewModel.fetchOneConnectFreeServers(oneConnect);
        } else {
            viewModel.fetchFreeServers();
        }
    }

    private void fetchPaidServer(boolean forced) {
        if (!forced && !cachedPaidServerList.isEmpty()) {
            viewModel.setPaidState(cachedPaidServerList);
            return;
        }
        if (isOneConnectEnabled() && getOneConnectKey() != null) {
            viewModel.fetchOneConnectPaidServers(oneConnect);
        } else {
            viewModel.fetchPaidServers();
        }
    }

    private boolean isOneConnectEnabled() {
        return getIntent().getBooleanExtra(ONE_CONNECT_ENABLED, false);
    }

    private String getOneConnectKey() {
        return getIntent().getStringExtra(ONE_CONNECT_KEY);
    }
}
