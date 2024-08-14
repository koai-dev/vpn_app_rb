package com.ntarevpn.rbpessacash.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.ntarevpn.rbpessacash.util.AdsIDController;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.SomeEarnController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PayEarnGift extends AppCompatActivity {


    private static final String PLACEMENT_ID_SKIPPABLE_VIDEO = "video";
    private static final String PLACEMENT_ID_REWARDED_VIDEO = "rewardedVideo";
    private String mGameId;
    private Dialog UnityDialog, dialogAdcolony, dialogStartAppVideoAds, ApplovinDialog;

    CardView GetMyCoin;
    private Context activity;
    private TextView Pay_Earn_Gift_TextView, points_textView;
    SomeEarnController someEarn_controller;
    AdsController ads_controller;
    AdsIDController ads_id_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_earn_gift_indratech);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ads_controller = new AdsController(this);
        activity = this;
        GetMyCoin = findViewById(R.id.GetMyCoin);
        Pay_Earn_Gift_TextView = findViewById(R.id.Pay_Earn_Gift_TextView);
        points_textView = findViewById(R.id.points_text_in_toolbar);
        ads_id_controller = new AdsIDController(this);

        GetMyCoin.setOnClickListener(view -> checkDailyCoin());
        someEarn_controller = new SomeEarnController(this);
        Pay_Earn_Gift_TextView.setText(someEarn_controller.getPayEarnGift());
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

    private void checkDailyCoin() {
        if (Constant.isNetworkAvailable(activity)) {

            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String gold_reward_date = Constant.getString(activity, Constant.Pay_Earn_Gift_DATE);
            Log.e("TAG", "onClick: Pay_Earn_Gift_Date Date" + gold_reward_date);
            if (gold_reward_date.equals("")) {
                Constant.setString(activity, Constant.Pay_Earn_Gift_DATE, currentDate);
                Constant.addPoints(activity, Integer.parseInt(Pay_Earn_Gift_TextView.getText().toString()), 0);
                showDialogOfPoints(Integer.parseInt(Pay_Earn_Gift_TextView.getText().toString()));
                if (getApplicationContext() == null) {
                    return;
                }
                setPointsText();

            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date pastDAte = sdf.parse(gold_reward_date);
                    Date currentDAte = sdf.parse(currentDate);
                    long diff = currentDAte.getTime() - pastDAte.getTime();
                    long difference_In_Days = (diff / (1000 * 60 * 60 * 24)) % 365;
                    Log.e("TAG", "onClick: Days Diffrernce" + difference_In_Days);
                    if (difference_In_Days > 0) {
                        Constant.setString(activity, Constant.Pay_Earn_Gift_DATE, currentDate);
                        Constant.addPoints(activity, Integer.parseInt(Pay_Earn_Gift_TextView.getText().toString()), 0);
                        showDialogOfPoints(Integer.parseInt(Pay_Earn_Gift_TextView.getText().toString()));
                        if (getApplicationContext() == null) {
                            return;
                        }
                        PayEarnGift.this.setPointsText();
                    } else {
                        showDialogOfPoints(0);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }
    }

    public void showDialogOfPoints(int points) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.points_dialog_indratech);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);

        if (points == Integer.parseInt(Pay_Earn_Gift_TextView.getText().toString())) {
            title_text.setText(getResources().getString(R.string.you_win));
            points_text.setVisibility(View.VISIBLE);
            points_text.setText(String.valueOf(points));
            add_btn.setText(getResources().getString(R.string.account_add_to_wallet));

        } else {
            title_text.setText(getResources().getString(R.string.today_checkin_over));
            points_text.setVisibility(View.GONE);
            add_btn.setText(getResources().getString(R.string.ok_text));
        }
        add_btn.setOnClickListener(view -> {
            dialog.dismiss();

            String typeOfAds = ads_controller.getPayEarnGiftAds();
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

        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

        String typeOfAds = ads_controller.getPayEarnGiftAds();
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