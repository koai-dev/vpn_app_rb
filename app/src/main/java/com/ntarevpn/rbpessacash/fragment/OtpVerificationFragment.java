package com.ntarevpn.rbpessacash.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.ntarevpn.rbpessacash.databinding.FragmentOtpVerifyBinding;
import com.ntarevpn.rbpessacash.viewModels.ResetPasswordViewModel;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.Constant;

import java.util.Objects;

public class OtpVerificationFragment extends Fragment {
    private FragmentOtpVerifyBinding binding;
    private ResetPasswordViewModel viewModel;
    private String otp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOtpVerifyBinding.inflate(inflater, container, false);

        initializeViewModel();
        initializeClickListener();

        return binding.getRoot();
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(getActivity()).get(ResetPasswordViewModel.class);
        listenToOTP();
    }

    private void listenToOTP() {
        viewModel.getSecretOTP().observe(getViewLifecycleOwner(), secretOtp -> {
            otp = secretOtp;
            Log.d("OTP", otp);
        });
    }

    private void initializeClickListener() {
        binding.otpVerifyButton.setOnClickListener(v -> verifyOTP());
    }

    private void verifyOTP() {
        String otpInput = binding.otpVerifyInput.getOTP();
        if (otpInput.length() == 0) {
            Constant.showToastMessage(getContext(), "Please input OTP Code");
        } else if (!Objects.equals(otpInput, otp)) {
            Constant.showToastMessage(getContext(), "Not a valid OTP Code");
        } else {
            Constant.hideKeyboard(getActivity());
            Navigation.findNavController(getActivity(), R.id.fragmentNavContainer).navigate(R.id.newResetPasswordFragment);
        }
    }
}
