package com.ntarevpn.rbpessacash.fragment;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ntarevpn.rbpessacash.databinding.FragmentResetPasswordBinding;
import com.ntarevpn.rbpessacash.viewModels.ResetPasswordViewModel;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.state.ResetPasswordState;
import com.ntarevpn.rbpessacash.util.Constant;
import com.google.android.material.textfield.TextInputEditText;

import ph.gemeaux.materialloadingindicator.MaterialCircularIndicator;

public class ResetPasswordFragment extends Fragment {
    private FragmentResetPasswordBinding binding;

    private ResetPasswordViewModel viewModel;
    private MaterialCircularIndicator progressIndicator;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false);

        initializeMiscellaneous();
        initializeViewModel();
        initializeClickListener();
        
        return binding.getRoot();
    }

    private void initializeMiscellaneous() {
        progressIndicator = new MaterialCircularIndicator(getContext());
        progressIndicator.setTrackCornerRadius(10);
        progressIndicator.setCanceleable(false);

        binding.passwordResetInput.setTransformationMethod(new PasswordTransformationMethod());
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(getActivity()).get(ResetPasswordViewModel.class);
        listenToEmail();
        listenToState();
    }

    private void listenToEmail() {
        viewModel.getEmail().observe(getViewLifecycleOwner(), email -> binding.emailInput3.setText(email));
    }
    
    private void listenToState() {
        viewModel.getResetPasswordState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof ResetPasswordState.Success) {
                ResetPasswordState.Success success = (ResetPasswordState.Success) state;

                Constant.showToastMessage(getContext(), "Successfully reset user password");
                Constant.setString(getContext(), Constant.USER_PASSWORD, success.getResult());
                progressIndicator.dismiss();
                if (getActivity() != null) {
                    getActivity().finish();
                }
            } else if (state instanceof ResetPasswordState.Failure) {
                ResetPasswordState.Failure failure = (ResetPasswordState.Failure) state;
                Constant.showToastMessage(getContext(), failure.getMessage());
                progressIndicator.dismiss();
            } else if (state instanceof ResetPasswordState.Loading) {
                progressIndicator.show();
            }
        });
    }

    private void initializeClickListener() {
        binding.resetPassButton.setOnClickListener(v -> resetPassword());
    }

    private void resetPassword() {
        if (Constant.isNetworkAvailable(getContext())) {
            String email = binding.emailInput3.getText().toString();
            String password = binding.passwordResetInput.getText().toString();
            if (email.length() == 0) {
                emailError(getString(R.string.account_enter_email));
            } else if (Constant.isEmailAddressNotValid(email)) {
                emailError(getString(R.string.signup_enter_valid_email));
            } else if (password.length() == 0) {
                passwordError(getString(R.string.signup_enter_password));
            } else if (password.length() < 6) {
                passwordError(getString(R.string.signup_enter_valid_password));
            } else {
                Constant.hideKeyboard(getActivity());
                viewModel.resetPassword(email, password);
            }
        } else {
            Constant.showInternetErrorDialog(getContext(), getString(R.string.internet_connection_of_text));
        }
    }

    private void emailError(String message) {
        TextInputEditText emailInput = binding.emailInput3;
        textInputError(emailInput, message);
    }

    private void passwordError(String message) {
        TextInputEditText passwordInput = binding.passwordResetInput;
        textInputError(passwordInput, message);
    }

    private void textInputError(TextInputEditText input, String message) {
        input.setError(message);
        input.requestFocus();
    }
}
