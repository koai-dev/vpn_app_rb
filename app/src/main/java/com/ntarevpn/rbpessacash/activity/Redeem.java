package com.ntarevpn.rbpessacash.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyLog;
import com.ntarevpn.rbpessacash.ApiBaseUrl;
import com.ntarevpn.rbpessacash.AppsConfig;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.CustomVolleyJsonRequest;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import ph.gemeaux.materialloadingindicator.MaterialCircularIndicator;

public class Redeem extends AppCompatActivity {

    private Redeem activity;
    private TextView user_points_textView;
    private EditText redeem_editText, points_editText;
    private AppCompatButton redeemBtn;
    private ProgressDialog progressDialog;
    private RadioGroup payment_group;
    MaterialCircularIndicator progressIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redeem_activity_indratech);
        progressIndicator = new MaterialCircularIndicator(this);
        progressIndicator.setTrackCornerRadius(10);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("  ");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        activity = this;
        onClick();

        Bundle bundle = getIntent().getExtras();

        final String PaymentOption = bundle.getString("option");
        final String PointOption = bundle.getString("PointOption");
        final String EnterPayment = bundle.getString("EnterPayment");

        user_points_textView = findViewById(R.id.user_points_text_view_redeem);
        redeem_editText = findViewById(R.id.redeem_edit_text);
        redeem_editText.setHint(EnterPayment);
        points_editText = findViewById(R.id.points_edit_text);
        redeemBtn = findViewById(R.id.redeem_btn);
        String user_points = Constant.getString(activity, Constant.USER_POINTS);
        if (user_points.equals("")) {
            user_points_textView.setText("0");
        } else {
            user_points_textView.setText(Constant.getString(activity, Constant.USER_POINTS));
        }
        redeemBtn.setOnClickListener(view -> {
            if (Constant.isNetworkAvailable(activity)) {
                String numberOrUpiId = redeem_editText.getText().toString();
                String points = PointOption;
                if (numberOrUpiId.length() == 0) {

                    redeem_editText.requestFocus();
                } else if (points.length() == 0) {
                    points_editText.setError(getResources().getString(R.string.enter_points));
                    points_editText.requestFocus();
                } else if (points.equals("0")) {
                    points_editText.setError(getResources().getString(R.string.enter_correct_points));
                    points_editText.requestFocus();
                } else {

                    if (Integer.parseInt(Constant.getString(activity, Constant.USER_POINTS)) < Integer.parseInt(points)) {
                        Constant.showToastMessage(activity, "You Have Not Enough Coins");
                        return;
                    }



                    Constant.hideKeyboard(activity);
                    RedeemPointsDialog(numberOrUpiId, points, PaymentOption);
                }
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
            }
        });

    }


    private void onClick() {

    }

    private void RedeemPointsDialog(final String numberOrUpiId, final String points, final String type) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.wallet_dialog_indratech);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton confirm_btn = dialog.findViewById(R.id.add_btn);
        AppCompatButton cancel_btn = dialog.findViewById(R.id.cancel_btn);

        title_text.setVisibility(View.VISIBLE);
        points_text.setVisibility(View.VISIBLE);
        confirm_btn.setVisibility(View.VISIBLE);
        cancel_btn.setVisibility(View.VISIBLE);

        title_text.setText(getResources().getString(R.string.redeem_tag_line_1));

        points_text.setText("Your payment ID -" + " " + numberOrUpiId + " " + "\n Your redeem Points -" + " " + points + " " + "\n Your redeem Method  -" + " " + type);
        confirm_btn.setText(getResources().getString(R.string.yes));
        cancel_btn.setText(getResources().getString(R.string.cancel_text));

        confirm_btn.setOnClickListener(view -> {
            dialog.dismiss();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setTitle(getResources().getString(R.string.in_progress));
            progressDialog.setMessage(getResources().getString(R.string.please_wait));
            progressDialog.setCancelable(false);
            redeemBtn.setEnabled(false);
            if (Constant.getString(activity, Constant.IS_LOGIN).equalsIgnoreCase("true")) {
                showProgressDialog();
                makeRedeemRequest(numberOrUpiId, points, type, Constant.getString(activity, Constant.REFER_CODE));
            } else {
                Constant.showToastMessage(activity, "Login First");
            }
        });
        cancel_btn.setOnClickListener(view -> {
            redeemBtn.setEnabled(true);
            dialog.dismiss();
        });
        dialog.show();
    }

    public void showProgressDialog() {
            progressIndicator.show();
    }

    public void hideProgressDialog() {
            progressIndicator.dismiss();
    }

    private void makeRedeemRequest(String numberOrUpiId, final String points, String type, String refer_by) {
        redeemBtn.setEnabled(true);
        final String user_previous_points = Constant.getString(activity, Constant.USER_POINTS);
        final int current_points = Integer.parseInt(user_previous_points) - Integer.parseInt(points);
        Constant.setString(activity, Constant.USER_POINTS, String.valueOf(current_points));
        user_points_textView.setText(String.valueOf(current_points));
        String tag_json_obj = "json_login_req";
        Map<String, String> params = new HashMap<>();
        params.put("redeem_point", "redeem");
        if (!refer_by.equalsIgnoreCase("")) {
            params.put("referraled_with", refer_by);
        }
        params.put("user_id", Constant.getString(activity, Constant.USER_ID));
        params.put("new_point", String.valueOf(current_points));
        params.put("redeemed_point", points);
        params.put("payment_mode", type);
        params.put("payment_info", numberOrUpiId);
        Log.e("TAG", "signupNewUser: " + params);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                ApiBaseUrl.UPDATE_POINTS_APP, params, response -> {
                    Log.d("TAG", response.toString());

                    try {
                        hideProgressDialog();
                        boolean status = response.getBoolean("status");
                        if (status) {
                            Constant.showToastMessage(activity, getResources().getString(R.string.redeem_successfully));

                            Constant.addPoints(activity, Integer.parseInt(Constant.getString(activity, Constant.USER_POINTS)), 1);
                        } else {
                            Constant.showToastMessage(activity, response.getString("message"));
                            user_points_textView.setText(String.valueOf(user_previous_points));
                            Constant.setString(activity, Constant.USER_POINTS, String.valueOf(user_previous_points));
                            Constant.addPoints(activity, Integer.parseInt(Constant.getString(activity, Constant.USER_POINTS)), 1);
                        }
                    } catch (JSONException e) {
                        hideProgressDialog();
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                    VolleyLog.d("TAG", "Error: " + error.getMessage());
                    hideProgressDialog();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Constant.showToastMessage(activity, getResources().getString(R.string.internet_connection_slow));
                    }
                    user_points_textView.setText(String.valueOf(user_previous_points));
                    Constant.setString(activity, Constant.USER_POINTS, String.valueOf(user_previous_points));
                    Constant.addPoints(activity, Integer.parseInt(Constant.getString(activity, Constant.USER_POINTS)), 1);
                });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                1000 * 20,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppsConfig.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
}