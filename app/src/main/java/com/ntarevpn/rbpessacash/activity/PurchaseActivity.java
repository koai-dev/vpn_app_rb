package com.ntarevpn.rbpessacash.activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.ntarevpn.rbpessacash.databinding.ActivitySubscriptionIndratechBinding;
import com.ntarevpn.rbpessacash.viewModels.PurchaseViewModel;
import com.ntarevpn.rbpessacash.adapter.SubscriptionAdapter;
import com.ntarevpn.rbpessacash.models.Subscription;
import com.ntarevpn.rbpessacash.state.SubscriptionsState;
import com.ntarevpn.rbpessacash.util.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PurchaseActivity extends AppCompatActivity implements PurchasesUpdatedListener, BillingClientStateListener {
    private final List<String> productIds = new ArrayList<>();
    private final Map<String, ProductDetails> subsWithProductDetails = new HashMap<>();
    private ActivitySubscriptionIndratechBinding binding;
    private BillingClient billingClient;
    private PurchaseViewModel viewModel;
    private SubscriptionAdapter adapter;
    private String pickedSubs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySubscriptionIndratechBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeRecyclerView();
        initializeViewModel();
        initializeClickListener();
    }

    private void initializeRecyclerView() {
        adapter = new SubscriptionAdapter();
        RecyclerView recyclerView = binding.subscriptionRV;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeViewModel() {
        viewModel = new PurchaseViewModel();
        listenToProperty();
        listenToState();

        viewModel.fetchSubscription();
    }

    private void listenToProperty() {
        viewModel.getPickedSubscription().observe(this, pickedSubscription -> {
            adapter.pickSubscription(pickedSubscription);
            pickedSubs = pickedSubscription;
        });
    }

    private void listenToState() {
        viewModel.getState().observe(this, state -> {
            if (state instanceof SubscriptionsState.Success) {
                SubscriptionsState.Success successState = (SubscriptionsState.Success) state;
                List<Subscription> subscriptions = successState.getResult();
                adapter.setData(subscriptions);
                setProductIds(subscriptions);
                initializeBillingClient();
                connectToBillingService();
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

    private void setProductIds(@NonNull List<Subscription> subscriptions) {
        productIds.clear();
        for (Subscription subscription : subscriptions) {
            productIds.add(subscription.getProductId());
        }
    }

    private void hideLoading() {
        binding.subscriptionLoading.hide();
    }

    private void showLoading() {
        binding.subscriptionLoading.show();
    }

    private void initializeClickListener() {
        adapter.setClickListener(subscription -> {
            if (Objects.equals(subscription.getId(), pickedSubs)) {
                viewModel.setPickedSubscription("");
            } else {
                viewModel.setPickedSubscription(subscription.getId());
            }
        });
        binding.subscribeButton.setOnClickListener(v -> {
            if (!pickedSubs.isEmpty()) {
                try {
                    ProductDetails productDetails = subsWithProductDetails.get(pickedSubs);
                    if (productDetails != null) {
                        purchase(productDetails);
                    } else {
                        Constant.showToastMessage(this, "Sorry, this subscription is currently unavailable");
                    }
                } catch (Exception e) {
                    Constant.showToastMessage(this, "Sorry, this subscription is currently unavailable");
                }
            } else {
                Constant.showToastMessage(this, "Pick a subscription first before purchasing");
            }
        });
        binding.exitButtonSubscription.setOnClickListener(v -> onBackPressed());
    }

    private void initializeBillingClient() {
        billingClient = BillingClient.newBuilder(this).setListener(this).enablePendingPurchases().build();
    }

    private void connectToBillingService() {
        if (!billingClient.isReady()) {
            billingClient.startConnection(this);
        }
    }

    @Override
    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
            queryProductDetailsAsync(productIds);
            Constant.queryPurchases(billingClient, this.productIds);
        }
    }

    @Override
    public void onBillingServiceDisconnected() {
        connectToBillingService();
    }

    private void queryProductDetailsAsync(List<String> idList) {
        ArrayList<QueryProductDetailsParams.Product> productList = new ArrayList<>();

        for (String productId : idList) {
            QueryProductDetailsParams.Product product = QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(productId)
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build();
            productList.add(product);
        }

        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                .setProductList(productList)
                .build();

        billingClient.queryProductDetailsAsync(params, (billingResult, productDetailsList) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

                for (ProductDetails details : productDetailsList) {
                    subsWithProductDetails.put(details.getProductId(), details);
                }
            }
        });
    }

    private void purchase(ProductDetails productDetails) {
        List<ProductDetails.SubscriptionOfferDetails> subsOfferDetailsList = productDetails.getSubscriptionOfferDetails();

        if (subsOfferDetailsList != null) {
            ProductDetails.SubscriptionOfferDetails selectedSubs = null;
            for (ProductDetails.SubscriptionOfferDetails subs : subsOfferDetailsList) {
                if (subs.getOfferTags().contains(productDetails.getProductId())) {
                    selectedSubs = subs;
                }
            }

            if (selectedSubs != null) {
                List<BillingFlowParams.ProductDetailsParams> productDetailsParamsList = new ArrayList<>();
                BillingFlowParams.ProductDetailsParams productDetailsParams = BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails)
                        .setOfferToken(selectedSubs.getOfferToken())
                        .build();

                productDetailsParamsList.add(productDetailsParams);

                BillingFlowParams params = BillingFlowParams.newBuilder().setProductDetailsParamsList(productDetailsParamsList).build();
                billingClient.launchBillingFlow(this, params);
            } else {
                Constant.showToastMessage(this, "Sorry, there's an error while purchasing");
            }
        } else {
            Constant.showToastMessage(this, "Sorry, there's an error while purchasing");
        }
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            Toast.makeText(this, "Subscribed Successfully", Toast.LENGTH_SHORT).show();

            Constant.queryPurchases(billingClient, this.productIds);

            finish();
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            QueryPurchasesParams queryParams = QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build();

            billingClient.queryPurchasesAsync(queryParams, (billingResult1, list) -> {
                if (!list.isEmpty()) {
                    Toast.makeText(this, "You are already subscribed", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(getApplicationContext(), "Purchase Canceled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Error " + billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
