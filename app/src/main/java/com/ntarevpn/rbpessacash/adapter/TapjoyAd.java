package com.ntarevpn.rbpessacash.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.ntarevpn.rbpessacash.util.AdsIDController;

import java.util.Hashtable;

public class TapjoyAd {

    public static void init(final Activity context, String uid) {
        AdsIDController ads_id_controller = new AdsIDController(context);
        ProgressDialog dialog = new ProgressDialog(context);
        dialog = ProgressDialog.show(context, "", "Please wait...", true, true);
        dialog.show();
        String TJKEY = ads_id_controller.getTapjoySdkKey();
        String TJP = ads_id_controller.getTapjoyPlacementOfferwall();
        Hashtable<String, Object> connectFlags = new Hashtable<>();

    }

    private static void uiToast(final Activity context, final String toast) {
        context.runOnUiThread(() -> Toast.makeText(context, toast, Toast.LENGTH_LONG).show());
    }

}
