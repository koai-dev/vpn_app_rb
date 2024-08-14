package com.ntarevpn.rbpessacash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ntarevpn.rbpessacash.databinding.LoginActivityIndratechBinding;
import com.ntarevpn.rbpessacash.viewModels.LoginViewModel;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.models.User;
import com.ntarevpn.rbpessacash.state.LoginState;
import com.ntarevpn.rbpessacash.util.Constant;
import com.google.android.material.textfield.TextInputEditText;

import ph.gemeaux.materialloadingindicator.MaterialCircularIndicator;

public class Login extends AppCompatActivity {
    private LoginActivityIndratechBinding binding;
    private LoginViewModel viewModel;

    private MaterialCircularIndicator progressIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginActivityIndratechBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeViewModel();
        initializeMiscellaneous();
        initializeIndicator();
        initializeClickListener();
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        listenToLoginState();
    }

    private void listenToLoginState() {
        viewModel.getLoginState().observe(this, state -> {
            if (state instanceof LoginState.Success) {
                LoginState.Success successState = (LoginState.Success) state;
                User user = successState.getResult();
                String password = successState.getPassword();

                Constant.SetUserData(this, user, password);
                hideLoading();
                showLoginResult();
            } else if (state instanceof LoginState.Failure) {
                hideLoading();
                String message = ((LoginState.Failure) state).getMessage();
                Constant.showToastMessage(this, message);
            } else if (state instanceof LoginState.Loading) {
                showLoading();
            }
        });
    }

    private void showLoginResult() {
        if (Constant.getString(this, Constant.USER_BLOCKED).equals("0")) {
            Constant.showBlockedDialog(this, getResources().getString(R.string.app_you_are_blocked));
        } else {
            Constant.setString(this, Constant.IS_LOGIN, "true");
            Constant.showToastMessage(this, getResources().getString(R.string.app_login_successfully));
            finishLogin();
        }
    }

    private void finishLogin() {
        Constant.gotoNextActivity(this, Main.class, "");
        overridePendingTransition(R.anim.enter, R.anim.exit);
        finish();
    }

    private void initializeMiscellaneous() {
        binding.passwordInput2.setTransformationMethod(new PasswordTransformationMethod());
    }

    private void initializeIndicator() {
        progressIndicator = new MaterialCircularIndicator(this);
        progressIndicator.setTrackCornerRadius(10);
        progressIndicator.setCanceleable(false);
    }

    private void initializeClickListener() {
        binding.forgotPassword.setOnClickListener(v -> {
            Intent i = new Intent(this, ForgotPassword.class);
            startActivity(i);
        });

        binding.signUpNavigate.setOnClickListener(v -> {
            Intent i = new Intent(this, Signup.class);
            startActivity(i);
        });

        initializeSubmitListener();
    }

    private void initializeSubmitListener() {
        binding.signInButton.setOnClickListener(v -> {
            if (Constant.isNetworkAvailable(this)) {
                String email = binding.emailPhoneInput.getText().toString().trim();
                String password = binding.passwordInput2.getText().toString().trim();

                if (email.length() == 0) {
                    emailError(getString(R.string.account_enter_email));
                } else if (Constant.isEmailAddressNotValid(email)) {
                    emailError(getString(R.string.signup_enter_valid_email));
                } else if (password.length() == 0) {
                    passwordError(getString(R.string.signup_enter_password));
                } else if (password.length() < 6) {
                    passwordError(getString(R.string.signup_enter_valid_password));
                } else {
                    Constant.hideKeyboard(this);
                    viewModel.login(email, password);
                }
            } else {
                Constant.showInternetErrorDialog(this, getString(R.string.internet_connection_of_text));
            }
        });
    }

    private void emailError(String message) {
        TextInputEditText emailInput = binding.emailPhoneInput;
        textInputError(emailInput, message);
    }

    private void passwordError(String message) {
        TextInputEditText passwordInput = binding.passwordInput2;
        textInputError(passwordInput, message);
    }

    private void textInputError(TextInputEditText input, String message) {
        input.setError(message);
        input.requestFocus();
    }

    private void showLoading() {
        progressIndicator.show();
    }

    private void hideLoading() {
        progressIndicator.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
