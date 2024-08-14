package com.ntarevpn.rbpessacash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.multidex.BuildConfig;

import com.ntarevpn.rbpessacash.fragment.OfferWallFragment;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsIDController;
import com.fyber.Fyber;
import com.fyber.utils.FyberLogger;

public class FyberAds extends AppCompatActivity {

    private static final int DURATION_MILLIS = 300;
    private static final int DEGREES_360 = 360;
    private static final int DEGREES_0 = 0;
    private static final float PIVOT_X_VALUE = 0.5f;
    private static final float PIVOT_Y_VALUE = 0.5f;
    private static final String TAG = "FyberMainActivity";

    Fragment fragment;
    AdsIDController ads_id_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fyber_ads);

        ads_id_controller = new AdsIDController(this);


        FyberLogger.enableLogging(BuildConfig.DEBUG);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.offer_wall_fragment_layout);
        if (fragment == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new OfferWallFragment();
            fragmentTransaction.add(android.R.id.content, fragment);
            fragmentTransaction.commit();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Fyber.Settings fyberSettings = Fyber
                    .with(ads_id_controller.getFyberAppId(), this)
                    .withSecurityToken(ads_id_controller.getFyberSecurityKey())
                    .start();

            customiseFyberSettings(fyberSettings);

        } catch (IllegalArgumentException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
    }


    private void customiseFyberSettings(Fyber.Settings fyberSettings) {
        fyberSettings.notifyUserOnReward(false)
                .closeOfferWallOnRedirect(true)
                .addParameter("myCustomParamKey", "myCustomParamValue")
                .setCustomUIString(Fyber.Settings.UIStringIdentifier.GENERIC_ERROR, "my custom generic error msg");
    }



    public static Animation getClockwiseAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotateAnimation = new RotateAnimation(DEGREES_0, DEGREES_360, Animation.RELATIVE_TO_SELF, PIVOT_X_VALUE, Animation.RELATIVE_TO_SELF, PIVOT_Y_VALUE);
        rotateAnimation.setDuration(DURATION_MILLIS);
        animationSet.addAnimation(rotateAnimation);

        return animationSet;
    }

    public static Animation getCounterclockwiseAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotateAnimation = new RotateAnimation(DEGREES_360, DEGREES_0, Animation.RELATIVE_TO_SELF, PIVOT_X_VALUE, Animation.RELATIVE_TO_SELF, PIVOT_Y_VALUE);
        rotateAnimation.setDuration(DURATION_MILLIS);
        animationSet.addAnimation(rotateAnimation);

        return animationSet;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(FyberAds.this, Main.class);
        startActivity(i);
        finish();
    }
}
