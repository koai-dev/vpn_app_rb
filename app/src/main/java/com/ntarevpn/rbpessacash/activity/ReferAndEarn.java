package com.ntarevpn.rbpessacash.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;

import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.ntarevpn.rbpessacash.util.Constant;

public class ReferAndEarn extends AppCompatActivity {
    TextView txtrefercoin, txtcode, txtcopy, txtinvite;
    Toolbar toolbar;
    String preText = "";
    private TextView points_textView;
    private Context mContext;
    AdsController ads_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refer_and_earn_indratech);

        mContext = this;
        toolbar = findViewById(R.id.toolbar);
        txtcode = findViewById(R.id.txtcode);
        txtcopy = findViewById(R.id.txtcopy);
        txtinvite = findViewById(R.id.txtinvite);
        txtrefercoin = findViewById(R.id.txtrefercoin);
        ads_controller = new AdsController(this);

        preText = ads_controller.getReferPoint();
        try {
            ((AppCompatActivity) ReferAndEarn.this).setSupportActionBar(toolbar);
            ((AppCompatActivity) ReferAndEarn.this).getSupportActionBar().setTitle("Invite Friends");
            ((AppCompatActivity) ReferAndEarn.this).getSupportActionBar().setDisplayShowTitleEnabled(true);
            ((AppCompatActivity) ReferAndEarn.this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            TextView titleText = toolbar.findViewById(R.id.toolbarText);
            titleText.setText("Invite Friends");
            points_textView = toolbar.findViewById(R.id.points_text_in_toolbar);
            setPointsText();
            toolbar.setNavigationOnClickListener(v -> ReferAndEarn.this.onBackPressed());
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtrefercoin.setText("Refer a friend and earn " + preText + " coins when they redeem their points for the first time. Your friend also get " + ads_controller.getSignUpBonus() + " additional points as a Sign up bonus.");

        txtinvite.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(mContext, R.drawable.ic_share), null, null, null);
        txtcode.setText(Constant.getString(mContext, Constant.USER_REFFER_CODE));
        txtcopy.setOnClickListener(v -> {

            ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", txtcode.getText());
            clipboard.setPrimaryClip(clip);
            Constant.showToastMessage(mContext, "Refer Code Copied");
        });
        txtinvite.setOnClickListener(v -> {
            if (txtcode.equals("")) {
                Constant.showToastMessage(mContext, "Can't Find Refer Code Login First...");
            } else {
                Constant.referApp(mContext, Constant.getString(mContext, Constant.USER_REFFER_CODE));
            }
        });

    }

    private void setPointsText() {
        if (points_textView != null) {
            String userPoints = Constant.getString(mContext, Constant.USER_POINTS);
            if (userPoints.equalsIgnoreCase("")) {
                userPoints = "0";
            }
            points_textView.setText(userPoints);
        }
    }

}