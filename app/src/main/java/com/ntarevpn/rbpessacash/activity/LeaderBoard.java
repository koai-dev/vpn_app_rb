package com.ntarevpn.rbpessacash.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyLog;
import com.ntarevpn.rbpessacash.ApiBaseUrl;
import com.ntarevpn.rbpessacash.AppsConfig;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.adapter.LeaderBoardAdapter;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.CustomVolleyJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderBoard extends AppCompatActivity {

    private RecyclerView leaderBoardRecyclerView;
    private Context activity;
    boolean LOGIN = false;
    private LeaderBoardAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    final List<com.ntarevpn.rbpessacash.models.LeaderBoard> leaderBoards = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leader_board_indratech);
        activity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Leaderboard");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        leaderBoardRecyclerView = findViewById(R.id.leaderBoardRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        leaderBoardRecyclerView.setLayoutManager(manager);
        refreshLayout = findViewById(R.id.refreshLyt);
        refreshLayout.setRefreshing(true);

        refreshLayout.setOnRefreshListener(() -> {
            try {
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                    adapter = null;
                }
                onInit();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        onInit();
        Todaydate();
        SetPoint();
    }

    private void SetPoint() {
        TextView user_points_text_view = findViewById(R.id.user_points_text_view);
        user_points_text_view.setText(Constant.getString(activity, Constant.USER_POINTS));

    }

    private void Todaydate() {
        //date
        TextView text_view_date_activity = findViewById(R.id.text_view_date_activity);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        text_view_date_activity.setText(dateFormat.format(date));


    }


    private void onInit() {
        String is_login = Constant.getString(activity, Constant.IS_LOGIN);
        if (is_login.equals("true")) {
            LOGIN = true;
        }
        if (Constant.isNetworkAvailable(activity)) {
            if (LOGIN) {
                try {
                    String tag_json_obj = "json_login_req";
                    Map<String, String> params = new HashMap<>();
                    params.put("top_users", "anything");
                    CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                            ApiBaseUrl.TOP_USER, params, response -> {
                                Log.d("TAG", response.toString());

                                try {
                                    boolean status = response.getBoolean("status");
                                    if (status) {
                                        JSONArray object = response.getJSONArray("0");
                                        leaderBoards.clear();
                                        int rank = 1;
                                        for (int i = 0; i < object.length(); i++) {
                                            JSONObject jsonObject = object.getJSONObject(i);
                                            if (!jsonObject.getString("points").equalsIgnoreCase("")) {
                                                com.ntarevpn.rbpessacash.models.LeaderBoard leaderBoard = new com.ntarevpn.rbpessacash.models.LeaderBoard();
                                                leaderBoard.setId(String.valueOf(rank));
                                                leaderBoard.setName(jsonObject.getString("name"));
                                                leaderBoard.setImage(jsonObject.getString("image"));
                                                leaderBoard.setPoints(jsonObject.getString("points"));
                                                leaderBoards.add(leaderBoard);
                                                rank++;
                                            }
                                        }
                                        if (!leaderBoards.isEmpty()) {
                                            adapter = new LeaderBoardAdapter(leaderBoards, activity);
                                            leaderBoardRecyclerView.setAdapter(adapter);
                                        } else {
                                            Constant.showToastMessage(activity, "Nothing Found...");
                                        }
                                        if (refreshLayout.isRefreshing()) {
                                            refreshLayout.setRefreshing(false);
                                        }
                                    } else {
                                        if (refreshLayout.isRefreshing()) {
                                            refreshLayout.setRefreshing(false);
                                        }
                                        Constant.showToastMessage(activity, "Nothing Found...");
                                    }
                                } catch (JSONException e) {
                                    if (refreshLayout.isRefreshing()) {
                                        refreshLayout.setRefreshing(false);
                                    }
                                    Constant.showToastMessage(activity, "Something Went Wrong...");
                                    e.printStackTrace();
                                }
                            }, error -> {
                                error.printStackTrace();
                                VolleyLog.d("TAG", "Error: " + error.getMessage());
                                if (refreshLayout.isRefreshing()) {
                                    refreshLayout.setRefreshing(false);
                                }
                                Constant.showToastMessage(activity, "Something Went Wrong...");
                                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                    Constant.showToastMessage(activity, getResources().getString(R.string.internet_connection_slow));
                                }
                            });
                    jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                            1000 * 20,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    AppsConfig.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (Constant.getString(activity, Constant.USER_BLOCKED).equals("true")) {
                    Constant.showBlockedDialog(activity, getResources().getString(R.string.app_you_are_blocked));
                    return;
                }
                Log.e("TAG", "onInit: else part of no login");
                Constant.gotoNextActivity(activity, Login.class, "");
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }

        } else {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
            }
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }
    }
}