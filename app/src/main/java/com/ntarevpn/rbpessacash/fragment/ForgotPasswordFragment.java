package com.ntarevpn.rbpessacash.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.ntarevpn.rbpessacash.databinding.FragmentForgotBinding;
import com.ntarevpn.rbpessacash.viewModels.ResetPasswordViewModel;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.state.SendOTPState;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.Utils;
import com.google.android.material.textfield.TextInputEditText;

import ph.gemeaux.materialloadingindicator.MaterialCircularIndicator;

public class ForgotPasswordFragment extends Fragment {
    private FragmentForgotBinding binding;
    private ResetPasswordViewModel viewModel;
    private MaterialCircularIndicator progressIndicator;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentForgotBinding.inflate(inflater, container, false);

        initializeMiscellaneous();
        initializeViewModel();
        initializeClickListener();

        return binding.getRoot();
    }

    private void initializeMiscellaneous() {
        progressIndicator = new MaterialCircularIndicator(getContext());
        progressIndicator.setTrackCornerRadius(10);
        progressIndicator.setCanceleable(false);
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(getActivity()).get(ResetPasswordViewModel.class);
        listenToState();
    }

    private void listenToState() {
        viewModel.getSendOTPState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof SendOTPState.Success) {
                SendOTPState.Success success = (SendOTPState.Success) state;
                String secretOtp = success.getResult();

                viewModel.setSecretOTP(secretOtp);
                Constant.showToastMessage(getContext(), "Check your Email for OTP Code");
                Navigation.findNavController(getActivity(), R.id.fragmentNavContainer).navigate(R.id.newOtpVerificationFragment);
                progressIndicator.dismiss();
            } else if (state instanceof SendOTPState.Failure) {
                SendOTPState.Failure failure = (SendOTPState.Failure) state;
                Constant.showToastMessage(getContext(), failure.getMessage());
                progressIndicator.dismiss();
            } else if (state instanceof SendOTPState.Loading) {
                progressIndicator.show();
            }
        });
    }

    private void initializeClickListener() {
        binding.emailOtpSendButton.setOnClickListener(v -> sendOTP());
    }

    private void sendOTP() {
        if (Constant.isNetworkAvailable(getContext())) {
            String email = binding.emailInput2.getText().toString();
            String otp = String.valueOf(Utils.randomizeBetween(100000, 999999));

            if (email.length() == 0) {
                emailError(getString(R.string.account_enter_email));
            } else if (Constant.isEmailAddressNotValid(email)) {
                emailError(getString(R.string.signup_enter_valid_email));
            } else {
                Constant.hideKeyboard(getActivity());
                viewModel.sendOTP(email, otp);
                viewModel.setEmail(email);
            }
        } else {
            Constant.showInternetErrorDialog(getContext(), getString(R.string.internet_connection_of_text));
        }
    }

    private void emailError(String message) {
        TextInputEditText emailInput = binding.emailInput2;
        emailInput.setError(message);
        emailInput.requestFocus();
    }
}
