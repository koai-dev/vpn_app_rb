package com.ntarevpn.rbpessacash.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.bumptech.glide.Glide;
import com.ntarevpn.rbpessacash.databinding.ExitDialogIndratechBinding;
import com.ntarevpn.rbpessacash.databinding.FeedbackDialogIndratechBinding;
import com.ntarevpn.rbpessacash.databinding.HeaderMainLayoutIndratechBinding;
import com.ntarevpn.rbpessacash.databinding.MainLayoutIndratechBinding;
import com.ntarevpn.rbpessacash.viewModels.MainViewModel;
import com.ntarevpn.rbpessacash.viewModels.PurchaseViewModel;
import com.ntarevpn.rbpessacash.ApiBaseUrl;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.models.Subscription;
import com.ntarevpn.rbpessacash.state.SubscriptionsState;
import com.ntarevpn.rbpessacash.util.Constant;

import java.util.ArrayList;
import java.util.List;

import ph.gemeaux.materialloadingindicator.MaterialCircularIndicator;

public class Main extends AppCompatActivity implements BillingClientStateListener {
    private MainLayoutIndratechBinding binding;
    private HeaderMainLayoutIndratechBinding headerMainBinding;
    private MainViewModel viewModel;
    private PurchaseViewModel purchaseViewModel;
    private BillingClient billingClient;
    private NavController navController;
    private MaterialCircularIndicator progressIndicator;
    private Dialog feedbackDialog;
    private Dialog exitDialog;
    private Dialog logoutDialog;
    private FeedbackDialogIndratechBinding feedbackDialogBinding;

    private final List<String> productIds = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainLayoutIndratechBinding.inflate(getLayoutInflater());
        headerMainBinding = HeaderMainLayoutIndratechBinding.bind(binding.navView.getHeaderView(0));

        setContentView(binding.getRoot());
        initializeMiscellaneous();
        initializeViewModel();
        initializeNavigation();
        initializeNavProfile();
        initializeOnClick();
    }

    private void initializeMiscellaneous() {
        initializeProgressIndicator();
        initializeDialog();
        initializeExitDialog();
        initializeLogoutDialog();
    }

    private void initializeProgressIndicator() {
        progressIndicator = new MaterialCircularIndicator(this);
        progressIndicator.setTrackCornerRadius(10);
        progressIndicator.setCanceleable(false);
    }

    private void initializeDialog() {
        feedbackDialogBinding = FeedbackDialogIndratechBinding.inflate(getLayoutInflater(), binding.getRoot(), false);

        feedbackDialog = new Dialog(this);
        feedbackDialog.setContentView(feedbackDialogBinding.getRoot());
        feedbackDialog.setCancelable(false);
        feedbackDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        feedbackDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        feedbackDialogBinding.feedbackDialogRating.setOnRatingBarChangeListener((ratingBar, v, b) -> {
            if (v == 0.0f) return;
            Intent i = new Intent(Main.this, Feedback.class);
            i.putExtra(Feedback.RATING_REQUEST_CODE, v);

            startActivity(i);
            feedbackDialog.hide();
            feedbackDialogBinding.feedbackDialogRating.setRating(0.0f);
        });

        feedbackDialogBinding.feedbackDialogLater.setOnClickListener(v -> {
            feedbackDialog.hide();
        });

        feedbackDialogBinding.feedbackDialogNever.setOnClickListener(v -> {
            Constant.setString(this, Constant.DEACTIVATE_FEEDBACK_REMINDER, "yes");
            feedbackDialog.hide();
        });
    }

    private void initializeExitDialog() {
        ExitDialogIndratechBinding exitDialogBinding = ExitDialogIndratechBinding.inflate(getLayoutInflater());

        exitDialog = new Dialog(this);
        exitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        exitDialog.setContentView(exitDialogBinding.getRoot());
        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitDialog.setCancelable(false);

        exitDialogBinding.noExit.setOnClickListener(view -> exitDialog.dismiss());

        exitDialogBinding.yesExit.setOnClickListener(view -> {
            exitDialog.dismiss();
            super.onBackPressed();
        });
    }

    private void initializeLogoutDialog() {
        ExitDialogIndratechBinding logoutBinding = ExitDialogIndratechBinding.inflate(getLayoutInflater());

        logoutDialog = new Dialog(this);
        logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logoutDialog.setContentView(logoutBinding.getRoot());
        logoutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logoutDialog.setCancelable(false);

        String username = Constant.getString(this, Constant.USER_NAME);
        logoutBinding.exitText.setText(getString(R.string.logout_text, username));
        logoutBinding.noExit.setOnClickListener(view -> logoutDialog.dismiss());
        logoutBinding.yesExit.setOnClickListener(view -> {
            logoutDialog.dismiss();
            Constant.logoutUser(this);
        });
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        purchaseViewModel = new ViewModelProvider(this).get(PurchaseViewModel.class);
        listenToProperties();
        listenToPurchase();

        try {
            viewModel.setPoints(Integer.parseInt(Constant.getString(this, Constant.USER_POINTS)));
        } catch (Exception e) {
            e.printStackTrace();
            Constant.showToastMessage(this, "Failed to get user's points");
        }
    }

    private void listenToProperties() {
        viewModel.getPoints().observe(this, result -> binding.userPoints.setText(String.valueOf(result)));
        viewModel.getFeedbackDialogStatus().observe(this, state -> {
            Log.d("FEEDBACK DIALOG", Boolean.toString(state));
            if (state && Constant.getString(this, Constant.DEACTIVATE_FEEDBACK_REMINDER).isEmpty()) {
                feedbackDialog.show();
                viewModel.resetFeedbackDialog();
            }
        });
    }

    private void listenToPurchase() {
        purchaseViewModel.getState().observe(this, state -> {
            if (state instanceof SubscriptionsState.Success) {
                SubscriptionsState.Success successState = (SubscriptionsState.Success) state;
                List<Subscription> subscriptions = successState.getResult();
                setProductIds(subscriptions);

                initializePurchases();
                hideLoading();
            } else if (state instanceof SubscriptionsState.Failure) {
                SubscriptionsState.Failure failure = (SubscriptionsState.Failure) state;
                Constant.showToastMessage(this, failure.getMessage());
                hideLoading();
            } else if (state instanceof SubscriptionsState.Loading) {
                showLoading();
            } else {
                hideLoading();
            }
        });
    }

    private void setProductIds(List<Subscription> subscriptions) {
        productIds.clear();

        for (Subscription subscription: subscriptions) {
            productIds.add(subscription.getProductId());
        }
    }

    private void initializePurchases() {
        billingClient = BillingClient.newBuilder(this)
                .build();
        connectToBillingService();
    }

    private void connectToBillingService() {
        if (!billingClient.isReady()) {
            billingClient.startConnection(this);
        }
    }

    private void showLoading() {
        progressIndicator.show();
    }

    private void hideLoading() {
        progressIndicator.dismiss();
    }

    private void initializeNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragmentManager);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.navView, navController);
        }
        initializeMenuItems();
    }

    private void initializeMenuItems() {
        Menu menu = binding.navView.getMenu();
        MenuItem share = menu.findItem(R.id.share);
        MenuItem logout = menu.findItem(R.id.logout);
        logout.setVisible(true);
        share.setOnMenuItemClickListener(item -> {
            shareReferCode();
            return true;
        });
        logout.setOnMenuItemClickListener(item -> {
            logoutDialog.show();
            return true;
        });
    }

    private void shareReferCode() {
        String textCode = Constant.getString(this, Constant.USER_REFFER_CODE);
        if (textCode.equals("")) {
            Constant.showToastMessage(this, "Can't Find Refer Code. Login First...");
        } else {
            Constant.referApp(this, Constant.getString(this, Constant.USER_REFFER_CODE));
        }
    }

    public void initializeNavProfile() {
        headerMainBinding.userEmail.setText(Constant.getString(this, Constant.USER_EMAIL));
        headerMainBinding.userName.setText(Constant.getString(this, Constant.USER_NAME));
        Glide.with(this)
                .load(ApiBaseUrl.LOAD_USER_IMAGE_APP + Constant.getString(this, Constant.USER_IMAGE))
                .centerCrop()
                .placeholder(R.drawable.user_ic)
                .into(headerMainBinding.userProfilePic);
        checkUserInfo();
    }

    private void checkUserInfo() {
        String referCode = Constant.getString(this, Constant.USER_REFFER_CODE);
        String username = Constant.getString(this, Constant.USER_NAME);
        String userNumber = Constant.getString(this, Constant.USER_NUMBER);
        if (referCode.equals("") || username.equals("") || userNumber.equals("")) {
            showUpdateProfileDialog();
        }
    }

    private void showUpdateProfileDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.points_dialog_indratech);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView titleText = dialog.findViewById(R.id.title_text_points);
        TextView pointsText = dialog.findViewById(R.id.points);
        AppCompatButton gotoButton = dialog.findViewById(R.id.add_btn);

        imageView.setImageResource(R.drawable.user_ic);
        titleText.setText(getResources().getString(R.string.account_incomplete_profile));
        pointsText.setVisibility(View.GONE);
        gotoButton.setText(getResources().getString(R.string.account_update_profile));

        gotoButton.setOnClickListener(view -> {
            dialog.dismiss();
            gotoProfile();
        });
        dialog.show();
    }

    private void gotoProfile() {
        if (Constant.getString(this, Constant.IS_LOGIN).equals("true")) {
            try {
                navController.navigate(R.id.newProfileFragment);
            } catch (Exception e) {
                Constant.showToastMessage(this, e.getMessage());
            }
            overridePendingTransition(R.anim.enter, R.anim.exit);
        } else {
            Intent i = new Intent(this, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Constant.showToastMessage(this, getResources().getString(R.string.account_login_first));

            startActivity(i);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
        }
    }

    private void initializeOnClick() {
        headerMainBinding.exitButtonMenu.setOnClickListener(v -> onBackPressed());
        binding.expandMenuButton.setOnClickListener(v -> binding.getRoot().openDrawer(GravityCompat.START));
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, binding.getRoot());
    }

    @Override
    public void onBackPressed() {
        if (binding.getRoot().isDrawerOpen(GravityCompat.START)) {
            binding.getRoot().closeDrawer(GravityCompat.START);
        } else {
            exitDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onBillingServiceDisconnected() {
        connectToBillingService();
    }

    @Override
    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
            Constant.queryPurchases(billingClient, this.productIds);
        }
    }
}
