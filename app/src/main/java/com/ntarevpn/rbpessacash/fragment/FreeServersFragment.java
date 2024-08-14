package com.ntarevpn.rbpessacash.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ntarevpn.rbpessacash.databinding.FragmentServersBinding;
import com.ntarevpn.rbpessacash.viewModels.ServersViewModel;
import com.ntarevpn.rbpessacash.activity.Servers;
import com.ntarevpn.rbpessacash.adapter.ServersAdapter;
import com.ntarevpn.rbpessacash.models.Server;
import com.ntarevpn.rbpessacash.state.ServersState;
import com.ntarevpn.rbpessacash.util.Constant;

import java.util.List;

public class FreeServersFragment extends Fragment {
    private FragmentServersBinding binding;
    private ServersViewModel viewModel;
    private ServersAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentServersBinding.inflate(getLayoutInflater());

        initializeMiscellaneous();
        initializeViewModel();
        initializeAdapter();

        return binding.getRoot();
    }

    private void initializeMiscellaneous() {
        binding.serverLoading.hide();
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(getActivity()).get(ServersViewModel.class);
        listenToFreeState();
    }

    private void listenToFreeState() {
        viewModel.getState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof ServersState.Success) {
                ServersState.Success successState = (ServersState.Success) state;
                List<Server> serverList = successState.getResult();
                adapter.setData(serverList, getChosenServer());
                Servers.cachedServerList.clear();
                Servers.cachedServerList.addAll(serverList);
                hideLoading();
            } else if (state instanceof ServersState.Failure) {
                ServersState.Failure failure = (ServersState.Failure) state;
                Constant.showToastMessage(getContext(), failure.getMessage());
                hideLoading();
            } else if (state instanceof ServersState.Loading) {
                showLoading();
            }
        });
    }

    private String getChosenServer() {
        return getActivity().getIntent().getStringExtra(Servers.CHOSEN_SERVER);
    }

    private void initializeAdapter() {
        adapter = new ServersAdapter();
        RecyclerView rv = binding.serverRV;
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setServerListener(server -> {
            Intent i = new Intent();
            i.putExtra(Servers.GET_REQUEST_CODE, server);
            getActivity().setResult(HomeFragment.SERVER_RESULT_CODE, i);
            getActivity().finish();
        });
    }

    private void showLoading() {
        binding.serverLoading.show();
    }

    private void hideLoading() {
        binding.serverLoading.hide();
    }
}
