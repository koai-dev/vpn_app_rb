package com.ntarevpn.rbpessacash.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ntarevpn.rbpessacash.everydaygif.EverydayGiftAdapter;
import com.ntarevpn.rbpessacash.everydaygif.VolleySingleton;
import com.ntarevpn.rbpessacash.ApiBaseUrl;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.SomeEarnController;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import ph.gemeaux.materialloadingindicator.MaterialCircularIndicator;

public class EverydayGift extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<com.ntarevpn.rbpessacash.everydaygif.EverydayGift> movieList;
    private TextView points_textView;
    private TextView text_view_date_activity;
    private Context activity;
    SomeEarnController someEarn_controller;
    AdsController ads_controller;
    MaterialCircularIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_gift_indratech);

        progressIndicator = new MaterialCircularIndicator(this);
        progressIndicator.setTrackCornerRadius(10);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        someEarn_controller = new SomeEarnController(this);
        activity = this;
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        movieList = new ArrayList<>();
        fetchEverydayGifts();

        ACProgressFlower alertDialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please Wait...")
                .fadeColor(Color.DKGRAY).build();

        showProgressDialog();

        points_textView = findViewById(R.id.points_text_in_toolbar);
        TextView everydayGiftText = findViewById(R.id.everydayGiftText);
        text_view_date_activity = findViewById(R.id.text_view_date_activity);

        everydayGiftText.setText(Constant.getString(activity, Constant.EverydayGift_Reward_COUNT));
        setPointsText();
        DateUpdate();


        Constant.initVungle(this);
        Constant.loadAdVungle(this);


        Constant.initRewardedAdAdColony(this);
        ads_controller = new AdsController(this);


        Constant.IntUnityAds(this);

    }

    private void DateUpdate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        text_view_date_activity.setText(dateFormat.format(date));
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

    public void showProgressDialog() {
        progressIndicator.show();
    }

    public void hideProgressDialog() {
        progressIndicator.dismiss();
    }


    private void fetchEverydayGifts() {

        String url = ApiBaseUrl.Everyday_Gift_API_URL;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String title = jsonObject.getString("title");
                            String overview = jsonObject.getString("overview");
                            String poster = jsonObject.getString("poster");
                            Double rating = jsonObject.getDouble("rating");

                            com.ntarevpn.rbpessacash.everydaygif.EverydayGift movie = new com.ntarevpn.rbpessacash.everydaygif.EverydayGift(title, poster, overview, rating);
                            movieList.add(movie);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        EverydayGiftAdapter adapter = new EverydayGiftAdapter(EverydayGift.this, movieList);
                        recyclerView.setAdapter(adapter);
                        hideProgressDialog();

                    }
                }, error -> Toast.makeText(EverydayGift.this, error.getMessage(), Toast.LENGTH_SHORT).show());

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

        String typeOfAds = ads_controller.getEverydayGiftAds();
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