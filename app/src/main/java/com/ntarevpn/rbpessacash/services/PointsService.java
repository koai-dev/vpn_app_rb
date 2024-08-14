package com.ntarevpn.rbpessacash.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.ntarevpn.rbpessacash.ApiBaseUrl;
import com.ntarevpn.rbpessacash.AppsConfig;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.CustomVolleyJsonRequest;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class PointsService extends Service {
    Thread thread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String points = intent.getStringExtra("points");

        addDataToFirebase(points);

        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "onCreate: service created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onCreate: service destroy");
    }

    private void addDataToFirebase(final String points) {

        thread = new Thread() {
            @Override
            public void run() {
                Constant.setString(AppsConfig.getContext(), Constant.LAST_TIME_ADD_TO_SERVER, "");
                String tag_json_obj = "json_login_req";
                Map<String, String> params = new HashMap<>();
                params.put("update_point", "any");
                params.put("new_point", points);
                params.put("user_id", Constant.getString(AppsConfig.getContext(), Constant.USER_ID));
                Log.e("TAG", "run: " + params);
                CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                        ApiBaseUrl.UPDATE_POINTS_APP, params, response -> {
                            Log.d("TAG", response.toString());
                            try {
                                boolean status = response.getBoolean("status");
                                if (status) {
                                    Constant.setString(AppsConfig.getContext(), Constant.LAST_TIME_ADD_TO_SERVER, "add");
                                } else {
                                    Constant.setString(AppsConfig.getContext(), Constant.LAST_TIME_ADD_TO_SERVER, "");
                                }
                                stopSelf();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                stopSelf();
                            }
                        }, error -> {
                            error.printStackTrace();
                            VolleyLog.d("TAG", "Error: " + error.getMessage());
                            stopSelf();
                        });
                jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                        1000 * 30,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                AppsConfig.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
            }
        };
        thread.start();
    }
}
