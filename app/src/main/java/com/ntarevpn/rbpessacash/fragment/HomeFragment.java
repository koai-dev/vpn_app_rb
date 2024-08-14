package com.ntarevpn.rbpessacash.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.VpnService;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ntarevpn.rbpessacash.databinding.FragmentHomeBinding;
import com.ntarevpn.rbpessacash.databinding.MainBottomSheetNavBinding;
import com.ntarevpn.rbpessacash.viewModels.HomeViewModel;
import com.ntarevpn.rbpessacash.viewModels.MainViewModel;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.activity.ReferAndEarn;
import com.ntarevpn.rbpessacash.activity.Servers;
import com.ntarevpn.rbpessacash.activity.SpinToWin;
import com.ntarevpn.rbpessacash.adapter.MenuAdapter;
import com.ntarevpn.rbpessacash.models.AdMob;
import com.ntarevpn.rbpessacash.models.OneConnectStatus;
import com.ntarevpn.rbpessacash.models.Server;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.ntarevpn.rbpessacash.util.CheckInternetConnection;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.PointsUtil;
import com.ntarevpn.rbpessacash.util.SomeEarnController;
import com.ntarevpn.rbpessacash.util.SwipeUpGestureDetector;
import com.ntarevpn.rbpessacash.util.WebAPI;
import com.ntarevpn.rbpessacash.views.ConnectionSwitchButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;

import top.oneconnectapi.app.OpenVpnApi;
import top.oneconnectapi.app.core.OpenVPNService;
import top.oneconnectapi.app.core.OpenVPNThread;

public class HomeFragment extends Fragment implements SwipeUpGestureDetector.OnSwipeUpListener {
    public static final int SERVER_RESULT_CODE = 453;
    private static final int MAX_RECONNECT_ATTEMPT = 4;
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private MainViewModel mainViewModel;
    private Server server;
    private BottomSheetDialog bottomMenu;
    private CheckInternetConnection connection;
    private ActivityResultLauncher<Intent> resultLauncher;

    private Boolean useOneConnect;
    private String oneConnectKey;
    private Boolean vpnStart = false;
    private Integer dailyPoints = 0;
    private Integer reconnectAttempts = 0;
    private PointsUtil pointsUtil;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        initializeViewModel();
        initializeMiscellaneous();
        initializeResultLauncher();
        initializeVPN();
        setServerPickerView();
        initializeClickListener();
        initializeAds();
        initializeBottomMenu(inflater);

        return binding.getRoot();
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);

        listenToProperties();
        listenToOneConnectStatus();
        listenToAdMob();
    }

    private void listenToProperties() {
        viewModel.getMyIP().observe(getViewLifecycleOwner(), result -> {
            if (!result.isEmpty() && vpnStart) {
                binding.serverIP.setText(result);
                binding.serverIP.setVisibility(View.VISIBLE);
            } else {
                binding.serverIP.setText("Not Connected");
                binding.serverIP.setVisibility(View.GONE);
            }
        });

        viewModel.getServer().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                server = result;
                setServerPickerView();
            }
        });

        viewModel.getVpnActivated().observe(getViewLifecycleOwner(), result -> {
            vpnStart = result;
            if (!result) {
                resetVPNInfoView();
            }
        });

        mainViewModel.getDailyPoints().observe(getViewLifecycleOwner(), result -> {
            dailyPoints = result;
            pointsUtil.setDailyPoints(dailyPoints);
        });
    }

    private void resetVPNInfoView() {
        binding.serverConnectingTimer.setText("00:00:00");
        binding.downloadValue.setText("0kb");
        binding.uploadValue.setText("0kb");
        viewModel.resetMyIP();
    }

    private void listenToOneConnectStatus() {
        viewModel.getOneConnectStatus().observe(getViewLifecycleOwner(), this::initializeOneConnectVPN);
    }

    private void initializeOneConnectVPN(OneConnectStatus result) {
        if (result != null) {
            if (result.getIsEnabled()) {
                Log.d("OneConnect", "Enabled");
                useOneConnect = true;
                oneConnectKey = result.getKey();
                viewModel.setServer(server);
            } else {
                useOneConnect = false;
                oneConnectKey = null;
            }
        }
    }

    private void listenToAdMob() {
        viewModel.getAdMobList().observe(getViewLifecycleOwner(), result -> {
            if (result != null && !result.isEmpty()) {
                AdMob admob = result.get(0);
                WebAPI.ADMOB_ID = admob.getAdMobID();
                WebAPI.ADMOB_BANNER = admob.getBannerID();
                WebAPI.ADMOB_INTERSTITIAL = admob.getInterstitialID();
                WebAPI.ADMOB_NATIVE = admob.getNativeID();
                WebAPI.ADMOB_REWARD_ID = admob.getRewardID();
                WebAPI.ADS_TYPE = admob.getAdType();

                try {
                    if (getActivity() != null) {
                        ApplicationInfo applicationInfo = getActivity().getPackageManager().getApplicationInfo(getActivity().getPackageName(), PackageManager.GET_META_DATA);
                        Bundle bundle = applicationInfo.metaData;
                        applicationInfo.metaData.putString("com.google.android.gms.ads.APPLICATION_ID", WebAPI.ADMOB_ID);
                        String apiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");
                        Log.d("AppID", "The saved id is " + WebAPI.ADMOB_ID);
                        Log.d("AppID", "The saved id is " + apiKey);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initializeResultLauncher() {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Log.d("Result Launcher", result.toString());
            if (result.getResultCode() == SERVER_RESULT_CODE) {
                Intent data = result.getData();

                if (data != null) {
                    Server newServer = data.getParcelableExtra(Servers.GET_REQUEST_CODE);

                    if (newServer != null)
                        viewModel.setServer(newServer);
                }
            }
        });
    }

    private void initializeVPN() {
        viewModel.fetchOneConnectStatus();
        initializeVPNStateReceiver();
        setStatus(OpenVPNService.getStatus());
    }

    private void initializeVPNStateReceiver() {
        BroadcastReceiver vpnStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String state = intent.getStringExtra("state");
                if (state != null) {
                    setStatus(state);
                } else {
                    String duration = intent.getStringExtra("duration");
                    String lastPacketReceive = intent.getStringExtra("lastPacketReceive");
                    String byteIn = intent.getStringExtra("byteIn");
                    String byteOut = intent.getStringExtra("byteOut");

                    if (duration == null) duration = "00:00:00";
                    if (lastPacketReceive == null) lastPacketReceive = "0";
                    if (byteIn == null) byteIn = "Wait";
                    if (byteOut == null) byteOut = "Wait";
                    updateConnectionStatus(duration, lastPacketReceive, byteIn, byteOut);
                }
            }
        };

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(vpnStateReceiver, new IntentFilter("connectionState"));
    }

    private void setStatus(String connectionState) {
        if (connectionState != null)
            switch (connectionState) {
                case "DISCONNECTED":
                    viewModel.setVpnActivated(false);
                    OpenVPNService.setDefaultStatus();
                    setReceivedStatus("Not Connected");
                    setSwitchState(ConnectionSwitchButton.MODE_OFF);
                    break;
                case "CONNECTED":
                    viewModel.setVpnActivated(true);
                    viewModel.fetchMyIP();
                    setReceivedStatus("Connection Time");
                    setSwitchState(ConnectionSwitchButton.MODE_ON);
                    break;
                case "WAIT":
                    setSwitchState(ConnectionSwitchButton.MODE_LOADING);
                    setReceivedStatus("Connecting...");
                    break;
                case "AUTH":
                    setReceivedStatus("Authenticating...");
                    break;
                case "GET_CONFIG":
                    setReceivedStatus("Getting Configurations...");
                    break;
                case "AUTH_FAILED":
                    setReceivedStatus("Authentication Failed!");
                    break;
                case "RECONNECTING":
                    reconnectAttempts += 1;
                    if (reconnectAttempts >= MAX_RECONNECT_ATTEMPT) {
                        stopVPN();
                        Constant.showToastMessage(getContext(), "Failed to connect to VPN. Try using other available server.");
                        break;
                    }
                    setReceivedStatus("Reconnecting...");
                    break;
                case "NONETWORK":
                    setReceivedStatus("No Network Connection!");
                    break;
            }

    }

    private void setSwitchState(int state) {
        binding.vpnSwitch.setState(state);
    }

    private void setReceivedStatus(String status) {
        binding.serverConnectingInfo.setText(status);
    }

    private void updateConnectionStatus(String duration, String lastPacketReceive, String byteIn, String byteOut) {
        binding.serverConnectingTimer.setText(duration);
        binding.downloadValue.setText(byteIn.substring(1));
        binding.uploadValue.setText(byteOut.substring(1));
        Log.d("Last Packet Receive", lastPacketReceive);
    }

    private void initializeMiscellaneous() {
        connection = new CheckInternetConnection();
        pointsUtil = new PointsUtil(getActivity(), dailyPoints);
        pointsUtil.initializePointsDialog(getLayoutInflater(), binding.getRoot());
        AdsController adsController = new AdsController(getContext());

        pointsUtil.setListener(points -> {
            try {
                mainViewModel.setPoints(Integer.parseInt(Constant.getString(getContext(), Constant.USER_POINTS)));
            } catch (Exception e) {
                Constant.showToastMessage(getContext(), "Failed to get user's points");
            }

            String typeOfAds = adsController.getDailyCheckRewardAds();
            Constant.showAds(getActivity(), typeOfAds);
        });

        SwipeUpGestureDetector gestureDetector = new SwipeUpGestureDetector(getContext(), this);
        binding.swipe.setOnTouchListener(gestureDetector);
    }

    private void initializeBottomMenu(LayoutInflater inflater) {
        MainBottomSheetNavBinding bottomMenuBinding = MainBottomSheetNavBinding.inflate(inflater, binding.getRoot(), false);

        bottomMenu = new BottomSheetDialog(getContext(), R.style.BottomSheetNavMenu);
        bottomMenu.setContentView(bottomMenuBinding.getRoot());
        bottomMenu.setCancelable(true);

        MenuAdapter adapter = new MenuAdapter(getContext());
        RecyclerView recyclerView = bottomMenuBinding.bottomSheetNavRV;
        adapter.addMenu(R.string.daily_checking, R.string.click_to_check_daily_coin, R.drawable.event, v -> pointsUtil.checkDaily());
        adapter.initializeNavMenu();
        adapter.addMenu(R.string.refer, R.string.refer_your_friends, R.drawable.affiliate_marketing, v -> startActivity(new Intent(getContext(), ReferAndEarn.class)));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setServerPickerView() {
        if (getContext() != null) {
            if (server != null && !server.getServerName().isEmpty()) {
                binding.serverCountry.setText(server.getServerName());
                Glide.with(getContext())
                        .load(server.getFlagUrl())
                        .into(binding.bannerImage);
            } else {
                binding.serverCountry.setText(getString(R.string.choose_server));
                Glide.with(getContext())
                        .load(R.drawable.location_flag)
                        .into(binding.bannerImage);
            }
        }
    }

    private void initializeClickListener() {
        ConnectionSwitchButton vpnSwitch = binding.vpnSwitch;
        vpnSwitch.setOnClickListener(v -> toggleVPN());
        binding.serverPick.setOnClickListener(v -> pickVPNServer());
        binding.bottomMenuHint.setOnClickListener(v -> bottomMenu.show());
        binding.dailyCheckInButton.setOnClickListener(v -> pointsUtil.checkDaily());
        binding.luckyWheelButton.setOnClickListener(v -> startActivity(new Intent(getContext(), SpinToWin.class)));
        binding.referEarnButton.setOnClickListener(v -> startActivity(new Intent(getContext(), ReferAndEarn.class)));
    }

    private void toggleVPN() {
        if (useOneConnect == null || (server != null && Objects.equals(server.getId(), "-1"))) {
            Constant.showToastMessage(getActivity(), "Please select a server first");
        } else {
            if (!vpnStart) {
                prepareVpn();
            } else {
                stopVPN();
                mainViewModel.showFeedbackDialog();
            }
        }
    }

    private void prepareVpn() {
        if (!vpnStart) {
            if (getInternetStatus()) {
                Intent intent = VpnService.prepare(getActivity());

                if (intent != null) {
                    Log.d("INTENT IS HERE", "IS HERE");
                    resultLauncher.launch(intent);
                } else startVPN();

            } else {
                Constant.showToastMessage(getContext(), "Please wait or check your connection");
            }
        } else if (stopVPN()) {
            Constant.showToastMessage(getContext(), "Disconnect Successfully");
        }
    }

    private boolean getInternetStatus() {
        if (!WebAPI.ADMOB_ID.equals("") && getContext() != null)
            return connection.netCheck(getContext());
        else
            return false;
    }

    private void startVPN() {
        try {
            stopVPN();
            if (getActivity() != null) {
                setSwitchState(ConnectionSwitchButton.MODE_LOADING);
                setReceivedStatus("Connecting...");
                OpenVpnApi.startVpn(getActivity(), server.getVpnConfiguration(), server.getServerName(), server.getVpnUserName(), server.getVpnUserPassword());
                viewModel.setVpnActivated(true);
            }
        } catch (RemoteException e) {
            setSwitchState(ConnectionSwitchButton.MODE_OFF);
            e.printStackTrace();
            Log.e("OpenVpnError", e.toString());
        }
    }

    private boolean stopVPN() {
        reconnectAttempts = 0;
        try {
            setSwitchState(ConnectionSwitchButton.MODE_OFF);
            setReceivedStatus("Not Connected");
            OpenVPNThread.stop();
            viewModel.setVpnActivated(false);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void pickVPNServer() {
        if (!vpnStart) {
            if (getActivity() != null) {
                if (useOneConnect == null) {
                    Constant.showToastMessage(getContext(), "Please wait until the servers are fully loaded.");
                    return;
                }
                Intent i = new Intent(getActivity(), Servers.class);
                if (server != null) {
                    i.putExtra(Servers.CHOSEN_SERVER, server.getId() != null ? server.getId() : "");
                }

                if (Boolean.TRUE.equals(useOneConnect) && oneConnectKey != null) {
                    i.putExtra(Servers.ONE_CONNECT_ENABLED, true);
                    i.putExtra(Servers.ONE_CONNECT_KEY, oneConnectKey);
                }
                resultLauncher.launch(i);
            }
        } else {
            Constant.showToastMessage(getContext(), "Stop the VPN first before changing server");
        }
    }

    private void initializeAds() {
        if (getActivity() != null) {
            Constant.loadSmallAdBanner(binding.adsContainer);
            SomeEarnController someEarnController = new SomeEarnController(getActivity());
            viewModel.fetchAdMob();
            mainViewModel.setDailyPoints(Integer.valueOf(someEarnController.getDailyCheck()));
            Constant.initVungle(getActivity());
            Constant.loadAdVungle(getActivity());
            Constant.initRewardedAdAdColony(getActivity());
        }
    }

    @Override
    public Boolean onSwipeUp() {
        bottomMenu.show();
        return true;
    }
}
