package com.ntarevpn.rbpessacash.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import  com.ntarevpn.rbpessacash.R;
import  com.ntarevpn.rbpessacash.databinding.ForgotLayoutIndratechBinding;

public class ForgotPassword extends AppCompatActivity {
    private ForgotLayoutIndratechBinding binding;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ForgotLayoutIndratechBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        initializeNavigation();
        initializeClickListener();
    }

    private void initializeNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentNavContainer);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }

    private void initializeClickListener() {
        binding.exitButton.setOnClickListener(v -> onSupportNavigateUp());
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (navController.getPreviousBackStackEntry() == null) {
            onBackPressed();
            return true;
        } else {
            return navController.navigateUp();
        }
    }
}
