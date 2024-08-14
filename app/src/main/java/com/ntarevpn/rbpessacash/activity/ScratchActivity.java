package com.ntarevpn.rbpessacash.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.SomeEarnController;

public class ScratchActivity extends AppCompatActivity {

    private Context activity;
    private TextView points_textView;
    private Button Scratch_Btn1, Scratch_Btn2, Scratch_Btn3;
    AdsController ads_controller;
    SomeEarnController someEarn_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scratch_activity_indratech);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.scratch_to_win));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ads_controller = new AdsController(this);
        activity = this;
        TextView scratch_Text1 = findViewById(R.id.Scratch_Text1);
        TextView scratch_Text2 = findViewById(R.id.Scratch_Text2);
        TextView scratch_Text3 = findViewById(R.id.Scratch_Text3);
        TextView scratch_Coin1 = findViewById(R.id.Scratch_Coin1);
        TextView scratch_Coin2 = findViewById(R.id.Scratch_Coin2);
        TextView scratch_Coin3 = findViewById(R.id.Scratch_Coin3);
        Scratch_Btn1 = findViewById(R.id.Scratch_Btn1);
        Scratch_Btn2 = findViewById(R.id.Scratch_Btn2);
        Scratch_Btn3 = findViewById(R.id.Scratch_Btn3);
        ImageView scratch_Image1 = findViewById(R.id.Scratch_Image1);
        ImageView scratch_Image2 = findViewById(R.id.Scratch_Image2);
        ImageView scratch_Image3 = findViewById(R.id.Scratch_Image3);
        points_textView = findViewById(R.id.Balance_TextView);

        scratch_Text1.setText("Additional Scratch");
        scratch_Text2.setText("Extra Scratch");
        scratch_Text3.setText("Great Scratch");


        someEarn_controller = new SomeEarnController(this);
        scratch_Coin1.setText(someEarn_controller.getAdditional_Scratch_Point());
        scratch_Coin2.setText(someEarn_controller.getExtra_Scratch_Point());
        scratch_Coin3.setText(someEarn_controller.getGreat_Scratch_Point());

        scratch_Image1.setImageResource(R.drawable.scratch_ic);
        scratch_Image2.setImageResource(R.drawable.scratch_ic);
        scratch_Image3.setImageResource(R.drawable.scratch_ic);


        OnClickView();
        setPointsText();


        Constant.initVungle(this);
        Constant.loadAdVungle(this);


        Constant.initRewardedAdAdColony(this);


        Constant.initUnityAds(this);

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

    private void OnClickView() {
        Scratch_Btn1.setOnClickListener(view -> {
            Intent i = new Intent(ScratchActivity.this, Scratch.class);
            startActivity(i);
        });
        Scratch_Btn2.setOnClickListener(view -> {
            Intent i = new Intent(ScratchActivity.this, ExtraScratch.class);
            startActivity(i);
        });
        Scratch_Btn3.setOnClickListener(view -> {
            Intent i = new Intent(ScratchActivity.this, GreatScratch.class);
            startActivity(i);
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

        String typeOfAds = ads_controller.getScratchWinAds();
        switch (typeOfAds) {
            case "Admob":
                Constant.showRewardedAdmobAds(this);
                break;
            case "Facebook":
                Constant.showRewardedFacebookAds(this);
                break;
            case "Vungle":
                Constant.showRewardedVungleAds(this);
                break;

            case "UnityAds":

                Constant.ShowUnityAds(this);
                break;
            case "AdColony":
                Constant.ShowAdcolonyAds(this);

                break;
            case "Startapp":
                Constant.StartappRewardedVideo(this);

                break;
            case "applovin":
                Constant.ApplovinShowAds(this);

                break;
        }

    }

}