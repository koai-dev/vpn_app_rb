package com.ntarevpn.rbpessacash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ntarevpn.rbpessacash.databinding.SignupLayoutIndratechBinding;
import com.ntarevpn.rbpessacash.viewModels.SignUpViewModel;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.models.User;
import com.ntarevpn.rbpessacash.state.RegisterState;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.ntarevpn.rbpessacash.util.Constant;
import com.google.android.material.textfield.TextInputEditText;

import ph.gemeaux.materialloadingindicator.MaterialCircularIndicator;

public class Signup extends AppCompatActivity {
    private SignupLayoutIndratechBinding binding;
    private SignUpViewModel viewModel;

    private MaterialCircularIndicator progressIndicator;
    private AdsController adsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignupLayoutIndratechBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeViewModel();
        initializeMiscellaneous();
        initializeIndicator();
        initializeClickListener();
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        listenToRegisterState();
    }

    private void listenToRegisterState() {
        viewModel.getRegisterState().observe(this, state -> {
            if (state instanceof RegisterState.Success) {
                RegisterState.Success successState = (RegisterState.Success) state;

                User user = successState.getResult();
                String password = successState.getPassword();

                Constant.SetUserData(this, user, password);
                hideLoading();
                showRegisterResult();
            } else if (state instanceof RegisterState.Failure) {
                String message = ((RegisterState.Failure) state).getMessage();
                hideLoading();
                Constant.showToastMessage(this, message);
            } else if (state instanceof RegisterState.Loading) {
                showLoading();
            }
        });
    }

    private void showRegisterResult() {
        Constant.setString(this, Constant.IS_LOGIN, "true");
        Constant.showToastMessage(this, getResources().getString(R.string.registration_success));
        finishRegister();
    }

    private void finishRegister() {
        Constant.gotoNextActivity(this, Main.class, "");
        overridePendingTransition(R.anim.enter, R.anim.exit);
        finish();
    }

    private void initializeMiscellaneous() {
        binding.passwordInput.setTransformationMethod(new PasswordTransformationMethod());
        adsController = new AdsController(this);
    }

    private void initializeIndicator() {
        progressIndicator = new MaterialCircularIndicator(this);
        progressIndicator.setTrackCornerRadius(10);
        progressIndicator.setCanceleable(false);
    }

    private void initializeClickListener() {
        binding.exitButtonSignUp.setOnClickListener(v -> onBackPressed());

        binding.signInNavigate.setOnClickListener(v -> {
            Intent i = new Intent(this, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        });

        initializeSubmitButton();
    }

    private void initializeSubmitButton() {
        binding.signUpButton.setOnClickListener(v -> {
            if (Constant.isNetworkAvailable(this)) {
                String name = binding.nameInput.getText().toString().trim();
                String number = binding.numberInput.getText().toString().trim();
                String email = binding.emailInput.getText().toString().trim();
                String password = binding.passwordInput.getText().toString().trim();
                String referralCode = binding.referalInput.getText().toString().trim();

                if (name.length() == 0) {
                    nameError(getString(R.string.signup_enter_name));
                } else if (number.length() == 0) {
                    numberError(getString(R.string.signup_enter_number));
                } else if (number.length() != 11) {
                    numberError(getString(R.string.enter_number_digit));
                } else if (email.length() == 0) {
                    emailError(getString(R.string.account_enter_email));
                } else if (Constant.isEmailAddressNotValid(email)) {
                    emailError(getString(R.string.signup_enter_valid_email));
                } else if (password.length() == 0) {
                    passwordError(getString(R.string.signup_enter_password));
                } else if (password.length() < 6) {
                    passwordError(getString(R.string.signup_enter_valid_password));
                } else {
                    Constant.hideKeyboard(this);
                    String signUpBonus = adsController.getSignUpBonus();
                    viewModel.register(name, number, email, password, referralCode, signUpBonus);
                }
            } else {
                Constant.showInternetErrorDialog(this, getString(R.string.internet_connection_of_text));
            }
        });
    }

    private void nameError(String message) {
        TextInputEditText nameInput = binding.nameInput;
        textInputError(nameInput, message);
    }

    private void numberError(String message) {
        TextInputEditText numberInput = binding.numberInput;
        textInputError(numberInput, message);
    }

    private void emailError(String message) {
        TextInputEditText emailInput = binding.emailInput;
        textInputError(emailInput, message);
    }

    private void passwordError(String message) {
        TextInputEditText passwordInput = binding.passwordInput;
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
}
