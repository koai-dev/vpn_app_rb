package com.ntarevpn.rbpessacash.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.fyber.requesters.OfferWallRequester;
import com.ntarevpn.rbpessacash.databinding.FragmentOfferwallBinding;

public class OfferWallFragment extends FyberFragment {
    private static final String TAG = OfferWallFragment.class.getSimpleName();
    private Dialog dialogLoading;

    private FragmentOfferwallBinding binding;

    public OfferWallFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOfferwallBinding.inflate(inflater, container, false);

        setButtonToSuccessState();
        binding.offerWallButton.setOnClickListener(view1 -> requestOrShowAd());

        return binding.getRoot();
    }

    @Override
    protected void performRequest() {
        OfferWallRequester
                .create(this)
                .request(getActivity());
    }

    @Override
    protected int getRequestCode() {
        return OFFERWALL_REQUEST_CODE;
    }
}
