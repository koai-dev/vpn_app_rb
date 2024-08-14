package com.ntarevpn.rbpessacash.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.ntarevpn.rbpessacash.util.AdsIDController;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.PointsUtil;
import com.ntarevpn.rbpessacash.util.SomeEarnController;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class KingPot extends AppCompatActivity {

    CardView getMyCoin;
    private Context activity;
    private TextView points_textView;
    private SomeEarnController someEarnController;
    private AdsController adsController;

    private LinearLayout adLayout;
    private AdsIDController adsIDController;
    private PointsUtil pointsUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.king_pot_indratech);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        adsController = new AdsController(this);
        activity = this;
        getMyCoin = findViewById(R.id.GetMyCoin);
        TextView coinViewText = findViewById(R.id.coinViewText);
        points_textView = findViewById(R.id.points_text_in_toolbar);

        adsIDController = new AdsIDController(this);

        getMyCoin.setOnClickListener(view -> pointsUtil.checkDaily());
        someEarnController = new SomeEarnController(this);
        coinViewText.setText(someEarnController.getKingPotPoint());
        setPointsText();

        pointsUtil = new PointsUtil(this);
        pointsUtil.initializePointsDialog(getLayoutInflater());
        pointsUtil.setListener(points -> {
            setPointsText();
        });

        try {
            int points = Integer.parseInt(Constant.getString(this, Constant.USER_POINTS));
            pointsUtil.setDailyPoints(points);
            setPointsText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getMyCoin.setOnClickListener(view -> pointsUtil.checkDaily());


        Constant.initVungle(this);
        Constant.loadAdVungle(this);
        Constant.initRewardedAdAdColony(this);

        adLayout = findViewById(R.id.banner_container);

        Constant.initUnityAds(this);
    }

    private void loadBannerAdmob() {

        final AdView adView = new AdView(activity);
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adView.setAdUnitId(adsIDController.getAdmobBannerId());
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adLayout.addView(adView);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.v("admob", "error" + loadAdError.getMessage());
            }
        });
        adView.loadAd(new AdRequest.Builder().build());

    }

    private void loadBannerFacebook() {
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(activity, getResources().getString(R.string.facebook_banner_ads_id), com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250);
        adView.loadAd();
        adLayout.addView(adView);

    }

    private void setPointsText() {
        if (points_textView != null) {
            String userPoints = Constant.getString(activity, Constant.USER_POINTS);
            if (userPoints.equalsIgnoreCase("")) {
                userPoints = "0";
            }
            points_textView.setText(userPoints);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

        String typeOfAds = adsController.getKingPotAds();
        Constant.showAds(this, typeOfAds);

    }
}