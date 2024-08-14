package com.ntarevpn.rbpessacash.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ntarevpn.rbpessacash.AppsConfig;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsIDController;
import com.pollfish.Pollfish;
import com.pollfish.builder.Params;
import com.pollfish.callback.PollfishClosedListener;
import com.pollfish.callback.PollfishOpenedListener;
import com.pollfish.callback.PollfishSurveyCompletedListener;
import com.pollfish.callback.PollfishSurveyNotAvailableListener;
import com.pollfish.callback.PollfishSurveyReceivedListener;
import com.pollfish.callback.PollfishUserNotEligibleListener;
import com.pollfish.callback.PollfishUserRejectedSurveyListener;
import com.pollfish.callback.SurveyInfo;

public class PollfishAdapter implements
        PollfishSurveyCompletedListener,
        PollfishOpenedListener,
        PollfishClosedListener,
        PollfishSurveyReceivedListener,
        PollfishSurveyNotAvailableListener,
        PollfishUserNotEligibleListener,
        PollfishUserRejectedSurveyListener {

    private static Dialog dialog;

    public static void initPollfish(Activity activity) {
        AdsIDController ads_id_controller = new AdsIDController(AppsConfig.getContext());
        Params params = new Params.Builder(ads_id_controller.getPollfishKey())
                .rewardMode(true)
                .offerwallMode(true)
                .build();

        Pollfish.initWith(activity, params);
    }


    @Override
    public void onUserRejectedSurvey() {

    }

    @Override
    public void onPollfishClosed() {

    }

    @Override
    public void onPollfishOpened() {

    }

    @Override
    public void onPollfishSurveyCompleted(@NonNull SurveyInfo surveyInfo) {

    }

    @Override
    public void onPollfishSurveyNotAvailable() {
        Toast.makeText(AppsConfig.getContext(), "Pollfish Survey Not Available", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPollfishSurveyReceived(@Nullable SurveyInfo surveyInfo) {
        dialog.dismiss();
        Pollfish.show();

        Toast.makeText(AppsConfig.getContext(), AppsConfig.getContext().getString(R.string.survey_received), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUserNotEligible() {

    }
}
