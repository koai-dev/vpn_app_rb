package com.ntarevpn.rbpessacash.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.ntarevpn.rbpessacash.services.PointsService;
import com.ntarevpn.rbpessacash.AppsConfig;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.ntarevpn.rbpessacash.util.AdsIDController;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.SomeEarnController;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.startapp.sdk.adsbase.StartAppAd;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TictactoeMain extends AppCompatActivity {

    private static final String PLACEMENT_ID_SKIPPABLE_VIDEO = "video";
    private static final String PLACEMENT_ID_REWARDED_VIDEO = "rewardedVideo";
    private String mGameId;
    private Dialog UnityDialog, dialogAdcolony, dialogStartAppVideoAds, ApplovinDialog;




    android.widget.Toolbar myToolbar;
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6, cardView7, cardView8, cardView9;
    ImageView cardView1_o, cardView1_x, cardView1_free,
            cardView2_o, cardView2_x, cardView2_free,
            cardView3_o, cardView3_x, cardView3_free,
            cardView4_o, cardView4_x, cardView4_free,
            cardView5_o, cardView5_x, cardView5_free,
            cardView6_o, cardView6_x, cardView6_free,
            cardView7_o, cardView7_x, cardView7_free,
            cardView8_o, cardView8_x, cardView8_free,
            cardView9_o, cardView9_x, cardView9_free;
    ImageView cardView1_orow, cardView1_xrow, cardView1_ocol, cardView1_xcol, cardView1_omajor, cardView1_xmajor,
            cardView2_orow, cardView2_xrow, cardView2_ocol, cardView2_xcol,
            cardView3_orow, cardView3_xrow, cardView3_ocol, cardView3_xcol, cardView3_odiagonal, cardView3_xdiagonal,
            cardView4_orow, cardView4_xrow, cardView4_ocol, cardView4_xcol,
            cardView5_orow, cardView5_ocol, cardView5_omajor, cardView5_odiagonal, cardView5_xrow, cardView5_xcol, cardView5_xmajor, cardView5_xdiagonal,
            cardView6_orow, cardView6_ocol, cardView6_xrow, cardView6_xcol,
            cardView7_orow, cardView7_ocol, cardView7_odiagonal, cardView7_xrow, cardView7_xcol, cardView7_xdiagonal,
            cardView8_orow, cardView8_ocol, cardView8_xrow, cardView8_xcol,
            cardView9_orow, cardView9_ocol, cardView9_omajor, cardView9_xrow, cardView9_xcol, cardView9_xmajor;
    TextView showOWin, showXWin, showDraw;
    CardView play_again;
    TextView owins, draws, xwins;
    int count = 0;
    final int[] arr = new int[]{-10, -10, -10, -10, -10, -10, -10, -10, -10, -10};
    int draw = 0, xwin = 0, owin = 0;




    TextView tictac_count_textView, user_points_text_view;
    TictactoeMain activity;
    TextView points_text;
    boolean first_time = true;

    private int tictiac_count = 1;
    private final String TAG = TictactoeMain.class.getSimpleName();
    private String random_points;
    public StartAppAd startAppAd;
    public int poiints = 0;
    public boolean rewardShow = true, interstitialShow = true;
    SomeEarnController someEarn_controller;
    AdsController ads_controller;
    private LinearLayout adLayout;
    AdsIDController ads_id_controller;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tictactoe_layout_indratech);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(" Tic Tac Toe");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        activity = this;
        tictac_count_textView = findViewById(R.id.tictac_count_textView);
        someEarn_controller = new SomeEarnController(this);
        points_text = findViewById(R.id.textView_points_show);
        user_points_text_view = findViewById(R.id.user_points_text_view);
        ads_controller = new AdsController(this);
        ads_id_controller = new AdsIDController(this);

        Constant.loadAdVungle(this);


        Constant.initRewardedAdAdColony(this);
        Constant.initVungle(this);

        onInit();
        adLayout = findViewById(R.id.banner_container);

        if (ads_controller.getBannerAdsControl().equals("Admob")) {
            loadBannerAdmob();
        } else if (ads_controller.getBannerAdsControl().equals("Facebook")) {
            loadBannerFacebook();
        }

        Constant.IntUnityAds(activity);

        if (Constant.isNetworkAvailable(activity)) {

            String typeOfAds = ads_controller.getTicTacToeInterstitialAds();
            switch (typeOfAds) {
                case "Admob":
                    Constant.LoadAdmobInterstitialAd(this);
                    break;
                case "Facebook":
                    Constant.LoadFacebookInterstitialAd();
                    break;
                case "Vungle":
                    Constant.initVungleInterstitial();
                    Constant.loadVungleAdInterstitial();
                    break;

                case "AdColony":
                    Constant.AdcolonyInitInterstitialAd();
                    break;
                case "Startapp":
                    Constant.LoadStartAppInterstitial(this);
                    break;
                case "IronSource":
                    Constant.IronSourceInterstitialInt(this);
                    break;

            }

        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }

        DateUpdate();


        cardView1 = findViewById(R.id.card1);
        cardView2 = findViewById(R.id.card2);
        cardView3 = findViewById(R.id.card3);
        cardView4 = findViewById(R.id.card4);
        cardView5 = findViewById(R.id.card5);
        cardView6 = findViewById(R.id.card6);
        cardView7 = findViewById(R.id.card7);
        cardView8 = findViewById(R.id.card8);
        cardView9 = findViewById(R.id.card9);

        cardView1_orow = findViewById(R.id.card1_oh);
        cardView1_xrow = findViewById(R.id.card1_xh);
        cardView1_ocol = findViewById(R.id.card1_ov);
        cardView1_xcol = findViewById(R.id.card1_xv);
        cardView1_omajor = findViewById(R.id.card1_om);
        cardView1_xmajor = findViewById(R.id.card1_xm);
        cardView2_orow = findViewById(R.id.card2_oh);
        cardView2_xrow = findViewById(R.id.card2_xh);
        cardView2_ocol = findViewById(R.id.card2_ov);
        cardView2_xcol = findViewById(R.id.card2_xv);
        cardView3_orow = findViewById(R.id.card3_oh);
        cardView3_xrow = findViewById(R.id.card3_xh);
        cardView3_ocol = findViewById(R.id.card3_ov);
        cardView3_xcol = findViewById(R.id.card3_xv);
        cardView3_odiagonal = findViewById(R.id.card3_od);
        cardView3_xdiagonal = findViewById(R.id.card3_xd);
        cardView4_orow = findViewById(R.id.card4_oh);
        cardView4_xrow = findViewById(R.id.card4_xh);
        cardView4_ocol = findViewById(R.id.card4_ov);
        cardView4_xcol = findViewById(R.id.card4_xv);
        cardView5_orow = findViewById(R.id.card5_oh);
        cardView5_xrow = findViewById(R.id.card5_xh);
        cardView5_ocol = findViewById(R.id.card5_ov);
        cardView5_xcol = findViewById(R.id.card5_xv);
        cardView5_omajor = findViewById(R.id.card5_om);
        cardView5_xmajor = findViewById(R.id.card5_xm);
        cardView5_odiagonal = findViewById(R.id.card5_od);
        cardView5_xdiagonal = findViewById(R.id.card5_xd);
        cardView6_orow = findViewById(R.id.card6_oh);
        cardView6_xrow = findViewById(R.id.card6_xh);
        cardView6_ocol = findViewById(R.id.card6_ov);
        cardView6_xcol = findViewById(R.id.card6_xv);
        cardView7_orow = findViewById(R.id.card7_oh);
        cardView7_xrow = findViewById(R.id.card7_xh);
        cardView7_ocol = findViewById(R.id.card7_ov);
        cardView7_xcol = findViewById(R.id.card7_xv);
        cardView7_odiagonal = findViewById(R.id.card7_od);
        cardView7_xdiagonal = findViewById(R.id.card7_xd);
        cardView8_orow = findViewById(R.id.card8_oh);
        cardView8_xrow = findViewById(R.id.card8_xh);
        cardView8_ocol = findViewById(R.id.card8_ov);
        cardView8_xcol = findViewById(R.id.card8_xv);
        cardView9_orow = findViewById(R.id.card9_oh);
        cardView9_xrow = findViewById(R.id.card9_xh);
        cardView9_ocol = findViewById(R.id.card9_ov);
        cardView9_xcol = findViewById(R.id.card9_xv);
        cardView9_omajor = findViewById(R.id.card9_om);
        cardView9_xmajor = findViewById(R.id.card9_xm);

        cardView1_x = findViewById(R.id.card1_x);
        cardView1_o = findViewById(R.id.card1_o);
        cardView1_free = findViewById(R.id.card1_free);
        cardView2_x = findViewById(R.id.card2_x);
        cardView2_o = findViewById(R.id.card2_o);
        cardView2_free = findViewById(R.id.card2_free);
        cardView3_x = findViewById(R.id.card3_x);
        cardView3_o = findViewById(R.id.card3_o);
        cardView3_free = findViewById(R.id.card3_free);
        cardView4_x = findViewById(R.id.card4_x);
        cardView4_o = findViewById(R.id.card4_o);
        cardView4_free = findViewById(R.id.card4_free);
        cardView5_x = findViewById(R.id.card5_x);
        cardView5_o = findViewById(R.id.card5_o);
        cardView5_free = findViewById(R.id.card5_free);
        cardView6_x = findViewById(R.id.card6_x);
        cardView6_o = findViewById(R.id.card6_o);
        cardView6_free = findViewById(R.id.card6_free);
        cardView7_x = findViewById(R.id.card7_x);
        cardView7_o = findViewById(R.id.card7_o);
        cardView7_free = findViewById(R.id.card7_free);
        cardView8_x = findViewById(R.id.card8_x);
        cardView8_o = findViewById(R.id.card8_o);
        cardView8_free = findViewById(R.id.card8_free);
        cardView9_x = findViewById(R.id.card9_x);
        cardView9_o = findViewById(R.id.card9_o);
        cardView9_free = findViewById(R.id.card9_free);

        showOWin = findViewById(R.id.showowin);
        showXWin = findViewById(R.id.showxwin);
        showDraw = findViewById(R.id.showdraw);
        play_again = findViewById(R.id.playagain);
        owins = findViewById(R.id.owins);
        draws = findViewById(R.id.draw);
        xwins = findViewById(R.id.xwins);


        cardView1.setOnClickListener(v -> {

            if (count % 2 == 0 && count < 9) {
                if (arr[1] == -10) {
                    cardView1_o.setVisibility(View.VISIBLE);
                    cardView1_x.setVisibility(View.GONE);
                    cardView1_free.setVisibility(View.GONE);
                    arr[1] = 2;
                    count++;

                    if (check() == 2) {
                        owin++;
                        showOWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;



                        if (first_time) {
                            first_time = false;
                            Log.e("onTicTac", "Complete");
                            Log.e("onTicTac", "Complete" + random_points);
                            int counter = Integer.parseInt(tictac_count_textView.getText().toString());
                            if (counter == 0) {
                                showDialogPoints(0, "0", counter);
                            } else {
                                showDialogPoints(1, points_text.getText().toString(), counter);
                            }
                        }
                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            } else if (count % 2 != 0 && count < 9) {
                if (arr[1] == -10) {
                    cardView1_x.setVisibility(View.VISIBLE);
                    cardView1_o.setVisibility(View.GONE);
                    cardView1_free.setVisibility(View.GONE);
                    arr[1] = 1;
                    count++;

                    if (check() == 1) {
                        xwin++;
                        showXWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                    }


                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }

                }
            }

            updateScore();

        });

        cardView2.setOnClickListener(v -> {
            if (count % 2 == 0 && count < 9) {
                if (arr[2] == -10) {
                    cardView2_o.setVisibility(View.VISIBLE);
                    cardView2_x.setVisibility(View.GONE);
                    cardView2_free.setVisibility(View.GONE);
                    arr[2] = 2;
                    count++;

                    if (check() == 2) {
                        owin++;
                        showOWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                        GameScoreUpdate();

                    }


                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            } else if (count % 2 != 0 && count < 9) {
                if (arr[2] == -10) {
                    cardView2_x.setVisibility(View.VISIBLE);
                    cardView2_o.setVisibility(View.GONE);
                    cardView2_free.setVisibility(View.GONE);
                    arr[2] = 1;
                    count++;

                    if (check() == 1) {

                        xwin++;
                        showXWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                        GameScoreUpdate();
                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            }
            updateScore();

        });

        cardView3.setOnClickListener(v -> {
            if (count % 2 == 0 && count < 9) {
                if (arr[3] == -10) {
                    cardView3_o.setVisibility(View.VISIBLE);
                    cardView3_x.setVisibility(View.GONE);
                    cardView3_free.setVisibility(View.GONE);
                    arr[3] = 2;
                    count++;

                    if (check() == 2) {

                        owin++;
                        showOWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                        GameScoreUpdate();
                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            } else if (count % 2 != 0 && count < 9) {
                if (arr[3] == -10) {
                    cardView3_x.setVisibility(View.VISIBLE);
                    cardView3_o.setVisibility(View.GONE);
                    cardView3_free.setVisibility(View.GONE);
                    arr[3] = 1;
                    count++;

                    if (check() == 1) {
                        //X WIN
                        xwin++;
                        showXWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                        GameScoreUpdate();
                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            }

            updateScore();

        });

        cardView4.setOnClickListener(v -> {
            if (count % 2 == 0 && count < 9) {
                if (arr[4] == -10) {
                    cardView4_o.setVisibility(View.VISIBLE);
                    cardView4_x.setVisibility(View.GONE);
                    cardView4_free.setVisibility(View.GONE);
                    arr[4] = 2;
                    count++;

                    if (check() == 2) {
                        //O WIN
                        owin++;
                        showOWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                        GameScoreUpdate();
                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            } else if (count % 2 != 0 && count < 9) {
                if (arr[4] == -10) {
                    cardView4_x.setVisibility(View.VISIBLE);
                    cardView4_o.setVisibility(View.GONE);
                    cardView4_free.setVisibility(View.GONE);
                    arr[4] = 1;
                    count++;
                    if (check() == 1) {
                        //X WIN
                        xwin++;
                        showXWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                        GameScoreUpdate();

                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            }


            updateScore();

        });

        cardView5.setOnClickListener(v -> {
            if (count % 2 == 0 && count < 9) {
                if (arr[5] == -10) {
                    cardView5_o.setVisibility(View.VISIBLE);
                    cardView5_x.setVisibility(View.GONE);
                    cardView5_free.setVisibility(View.GONE);
                    arr[5] = 2;
                    count++;

                    if (check() == 2) {
                        //O WIN
                        owin++;
                        showOWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                        GameScoreUpdate();

                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            } else if (count % 2 != 0 && count < 9) {
                if (arr[5] == -10) {
                    cardView5_x.setVisibility(View.VISIBLE);
                    cardView5_o.setVisibility(View.GONE);
                    cardView5_free.setVisibility(View.GONE);
                    arr[5] = 1;
                    count++;

                    if (check() == 1) {
                        //X WIN
                        xwin++;
                        showXWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                        GameScoreUpdate();

                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            }
            updateScore();

        });

        cardView6.setOnClickListener(v -> {
            if (count % 2 == 0 && count < 9) {
                if (arr[6] == -10) {
                    cardView6_o.setVisibility(View.VISIBLE);
                    cardView6_x.setVisibility(View.GONE);
                    cardView6_free.setVisibility(View.GONE);
                    arr[6] = 2;
                    count++;

                    if (check() == 2) {
                        //O WIN
                        owin++;
                        showOWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                        GameScoreUpdate();

                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            } else if (count % 2 != 0 && count < 9) {
                if (arr[6] == -10) {
                    cardView6_x.setVisibility(View.VISIBLE);
                    cardView6_o.setVisibility(View.GONE);
                    cardView6_free.setVisibility(View.GONE);
                    arr[6] = 1;
                    count++;

                    if (check() == 1) {
                        //X WIN
                        xwin++;
                        showXWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                        GameScoreUpdate();

                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            }

            updateScore();

        });

        cardView7.setOnClickListener(v -> {
            if (count % 2 == 0 && count < 9) {
                if (arr[7] == -10) {
                    cardView7_o.setVisibility(View.VISIBLE);
                    cardView7_x.setVisibility(View.GONE);
                    cardView7_free.setVisibility(View.GONE);
                    arr[7] = 2;
                    count++;

                    if (check() == 2) {
                        //O WIN
                        owin++;
                        showOWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                        GameScoreUpdate();

                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            } else if (count % 2 != 0 && count < 9) {
                if (arr[7] == -10) {
                    cardView7_x.setVisibility(View.VISIBLE);
                    cardView7_o.setVisibility(View.GONE);
                    cardView7_free.setVisibility(View.GONE);
                    arr[7] = 1;
                    count++;

                    if (check() == 1) {
                        //X WIN
                        xwin++;
                        showXWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                        GameScoreUpdate();


                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            }

            updateScore();

        });

        cardView8.setOnClickListener(v -> {
            if (count % 2 == 0 && count < 9) {
                if (arr[8] == -10) {
                    cardView8_o.setVisibility(View.VISIBLE);
                    cardView8_x.setVisibility(View.GONE);
                    cardView8_free.setVisibility(View.GONE);
                    arr[8] = 2;
                    count++;
                    if (check() == 2) {
                        //O WIN
                        owin++;
                        showOWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                        //
                        GameScoreUpdate();

                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            } else if (count % 2 != 0 && count < 9) {
                if (arr[8] == -10) {
                    cardView8_x.setVisibility(View.VISIBLE);
                    cardView8_o.setVisibility(View.GONE);
                    cardView8_free.setVisibility(View.GONE);
                    arr[8] = 1;
                    count++;

                    if (check() == 1) {
                        //X WIN
                        xwin++;
                        showXWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                        GameScoreUpdate();
                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            }
            updateScore();

        });

        cardView9.setOnClickListener(v -> {
            if (count % 2 == 0 && count < 9) {
                if (arr[9] == -10) {
                    cardView9_o.setVisibility(View.VISIBLE);
                    cardView9_x.setVisibility(View.GONE);
                    cardView9_free.setVisibility(View.GONE);
                    arr[9] = 2;
                    count++;
                    if (check() == 2) {
                        //O WIN
                        owin++;
                        showOWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                        GameScoreUpdate();
                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            } else if (count % 2 != 0 && count < 9) {
                if (arr[9] == -10) {
                    cardView9_x.setVisibility(View.VISIBLE);
                    cardView9_o.setVisibility(View.GONE);
                    cardView9_free.setVisibility(View.GONE);
                    arr[9] = 1;
                    count++;
                    if (check() == 1) {
                        //X WIN
                        xwin++;
                        showXWin.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;

                        GameScoreUpdate();
                    }

                    if (check() == 11) {
                        draw++;
                        showDraw.setVisibility(View.VISIBLE);
                        play_again.setVisibility(View.VISIBLE);
                        count = 10;
                    }
                }
            }

            updateScore();

        });

        play_again.setOnClickListener(v -> {
            count = 0;
            arr[1] = -10;
            arr[2] = -10;
            arr[3] = -10;
            arr[4] = -10;
            arr[5] = -10;
            arr[6] = -10;
            arr[7] = -10;
            arr[8] = -10;
            arr[9] = -10;


            play_again.setVisibility(View.GONE);
            showOWin.setVisibility(View.GONE);
            showXWin.setVisibility(View.GONE);
            showDraw.setVisibility(View.GONE);

            cardView1_o.setVisibility(View.GONE);
            cardView1_x.setVisibility(View.GONE);
            cardView1_orow.setVisibility(View.GONE);
            cardView1_xrow.setVisibility(View.GONE);
            cardView1_ocol.setVisibility(View.GONE);
            cardView1_xcol.setVisibility(View.GONE);
            cardView1_xmajor.setVisibility(View.GONE);
            cardView1_omajor.setVisibility(View.GONE);

            cardView2_o.setVisibility(View.GONE);
            cardView2_x.setVisibility(View.GONE);
            cardView2_orow.setVisibility(View.GONE);
            cardView2_xrow.setVisibility(View.GONE);
            cardView2_ocol.setVisibility(View.GONE);
            cardView2_xcol.setVisibility(View.GONE);

            cardView3_o.setVisibility(View.GONE);
            cardView3_x.setVisibility(View.GONE);
            cardView3_orow.setVisibility(View.GONE);
            cardView3_xrow.setVisibility(View.GONE);
            cardView3_ocol.setVisibility(View.GONE);
            cardView3_xcol.setVisibility(View.GONE);
            cardView3_odiagonal.setVisibility(View.GONE);
            cardView3_xdiagonal.setVisibility(View.GONE);

            cardView4_o.setVisibility(View.GONE);
            cardView4_x.setVisibility(View.GONE);
            cardView4_orow.setVisibility(View.GONE);
            cardView4_xrow.setVisibility(View.GONE);
            cardView4_ocol.setVisibility(View.GONE);
            cardView4_xcol.setVisibility(View.GONE);

            cardView5_o.setVisibility(View.GONE);
            cardView5_x.setVisibility(View.GONE);
            cardView5_orow.setVisibility(View.GONE);
            cardView5_xrow.setVisibility(View.GONE);
            cardView5_ocol.setVisibility(View.GONE);
            cardView5_xcol.setVisibility(View.GONE);
            cardView5_odiagonal.setVisibility(View.GONE);
            cardView5_xdiagonal.setVisibility(View.GONE);
            cardView5_omajor.setVisibility(View.GONE);
            cardView5_xmajor.setVisibility(View.GONE);

            cardView6_o.setVisibility(View.GONE);
            cardView6_x.setVisibility(View.GONE);
            cardView6_orow.setVisibility(View.GONE);
            cardView6_xrow.setVisibility(View.GONE);
            cardView6_ocol.setVisibility(View.GONE);
            cardView6_xcol.setVisibility(View.GONE);

            cardView7_o.setVisibility(View.GONE);
            cardView7_x.setVisibility(View.GONE);
            cardView7_orow.setVisibility(View.GONE);
            cardView7_xrow.setVisibility(View.GONE);
            cardView7_ocol.setVisibility(View.GONE);
            cardView7_xcol.setVisibility(View.GONE);
            cardView7_odiagonal.setVisibility(View.GONE);
            cardView7_xdiagonal.setVisibility(View.GONE);

            cardView8_o.setVisibility(View.GONE);
            cardView8_x.setVisibility(View.GONE);
            cardView8_orow.setVisibility(View.GONE);
            cardView8_xrow.setVisibility(View.GONE);
            cardView8_ocol.setVisibility(View.GONE);
            cardView8_xcol.setVisibility(View.GONE);

            cardView9_o.setVisibility(View.GONE);
            cardView9_x.setVisibility(View.GONE);
            cardView9_orow.setVisibility(View.GONE);
            cardView9_xrow.setVisibility(View.GONE);
            cardView9_ocol.setVisibility(View.GONE);
            cardView9_xcol.setVisibility(View.GONE);
            cardView9_omajor.setVisibility(View.GONE);
            cardView9_xmajor.setVisibility(View.GONE);


            //Show free grids

            cardView1_free.setVisibility(View.VISIBLE);
            cardView2_free.setVisibility(View.VISIBLE);
            cardView3_free.setVisibility(View.VISIBLE);
            cardView4_free.setVisibility(View.VISIBLE);
            cardView5_free.setVisibility(View.VISIBLE);
            cardView6_free.setVisibility(View.VISIBLE);
            cardView7_free.setVisibility(View.VISIBLE);
            cardView8_free.setVisibility(View.VISIBLE);
            cardView9_free.setVisibility(View.VISIBLE);


        });

    }

    private void DateUpdate() {
        //date
        TextView text_view_date_activity = findViewById(R.id.text_view_date_activity);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        text_view_date_activity.setText(dateFormat.format(date));

    }

    //game

    int check() {
        int row1 = arr[1] + arr[2] + arr[3];
        int row2 = arr[4] + arr[5] + arr[6];
        int row3 = arr[7] + arr[8] + arr[9];
        int col1 = arr[1] + arr[4] + arr[7];
        int col2 = arr[2] + arr[5] + arr[8];
        int col3 = arr[3] + arr[6] + arr[9];
        int major_diagonal = arr[1] + arr[5] + arr[9];
        int diagonal = arr[3] + arr[5] + arr[7];

        if (row1 == 6 || row2 == 6 || row3 == 6 || col1 == 6 || col2 == 6 || col3 == 6 || major_diagonal == 6 || diagonal == 6) {
            if (row1 == 6) {
                cardView1_orow.setVisibility(View.VISIBLE);
                cardView2_orow.setVisibility(View.VISIBLE);
                cardView3_orow.setVisibility(View.VISIBLE);

                cardView1_o.setVisibility(View.GONE);
                cardView2_o.setVisibility(View.GONE);
                cardView3_o.setVisibility(View.GONE);


            } else if (row2 == 6) {
                cardView4_orow.setVisibility(View.VISIBLE);
                cardView5_orow.setVisibility(View.VISIBLE);
                cardView6_orow.setVisibility(View.VISIBLE);

                cardView4_o.setVisibility(View.GONE);
                cardView5_o.setVisibility(View.GONE);
                cardView6_o.setVisibility(View.GONE);


            } else if (row3 == 6) {
                cardView7_orow.setVisibility(View.VISIBLE);
                cardView8_orow.setVisibility(View.VISIBLE);
                cardView9_orow.setVisibility(View.VISIBLE);

                cardView7_o.setVisibility(View.GONE);
                cardView8_o.setVisibility(View.GONE);
                cardView9_o.setVisibility(View.GONE);

            } else if (col1 == 6) {
                cardView1_ocol.setVisibility(View.VISIBLE);
                cardView4_ocol.setVisibility(View.VISIBLE);
                cardView7_ocol.setVisibility(View.VISIBLE);

                cardView1_o.setVisibility(View.GONE);
                cardView4_o.setVisibility(View.GONE);
                cardView7_o.setVisibility(View.GONE);

            } else if (col2 == 6) {
                cardView2_ocol.setVisibility(View.VISIBLE);
                cardView5_ocol.setVisibility(View.VISIBLE);
                cardView8_ocol.setVisibility(View.VISIBLE);

                cardView2_o.setVisibility(View.GONE);
                cardView5_o.setVisibility(View.GONE);
                cardView8_o.setVisibility(View.GONE);

            } else if (col3 == 6) {
                cardView3_ocol.setVisibility(View.VISIBLE);
                cardView6_ocol.setVisibility(View.VISIBLE);
                cardView9_ocol.setVisibility(View.VISIBLE);

                cardView3_o.setVisibility(View.GONE);
                cardView6_o.setVisibility(View.GONE);
                cardView9_o.setVisibility(View.GONE);

            } else if (major_diagonal == 6) {
                cardView1_omajor.setVisibility(View.VISIBLE);
                cardView5_omajor.setVisibility(View.VISIBLE);
                cardView9_omajor.setVisibility(View.VISIBLE);

                cardView1_o.setVisibility(View.GONE);
                cardView5_o.setVisibility(View.GONE);
                cardView9_o.setVisibility(View.GONE);
            } else {
                cardView3_odiagonal.setVisibility(View.VISIBLE);
                cardView5_odiagonal.setVisibility(View.VISIBLE);
                cardView7_odiagonal.setVisibility(View.VISIBLE);

                cardView3_o.setVisibility(View.GONE);
                cardView5_o.setVisibility(View.GONE);
                cardView7_o.setVisibility(View.GONE);
            }
            return 2;
        } else if (row1 == 3 || row2 == 3 || row3 == 3 || col1 == 3 || col2 == 3 || col3 == 3 || major_diagonal == 3 || diagonal == 3) {
            if (row1 == 3) {
                cardView1_xrow.setVisibility(View.VISIBLE);
                cardView2_xrow.setVisibility(View.VISIBLE);
                cardView3_xrow.setVisibility(View.VISIBLE);

                cardView1_x.setVisibility(View.GONE);
                cardView2_x.setVisibility(View.GONE);
                cardView3_x.setVisibility(View.GONE);
            } else if (row2 == 3) {
                cardView4_xrow.setVisibility(View.VISIBLE);
                cardView5_xrow.setVisibility(View.VISIBLE);
                cardView6_xrow.setVisibility(View.VISIBLE);

                cardView4_x.setVisibility(View.GONE);
                cardView5_x.setVisibility(View.GONE);
                cardView6_x.setVisibility(View.GONE);
            } else if (row3 == 3) {
                cardView7_xrow.setVisibility(View.VISIBLE);
                cardView8_xrow.setVisibility(View.VISIBLE);
                cardView9_xrow.setVisibility(View.VISIBLE);

                cardView7_x.setVisibility(View.GONE);
                cardView8_x.setVisibility(View.GONE);
                cardView9_x.setVisibility(View.GONE);
            } else if (col1 == 3) {
                cardView1_xcol.setVisibility(View.VISIBLE);
                cardView4_xcol.setVisibility(View.VISIBLE);
                cardView7_xcol.setVisibility(View.VISIBLE);

                cardView1_x.setVisibility(View.GONE);
                cardView4_x.setVisibility(View.GONE);
                cardView7_x.setVisibility(View.GONE);
            } else if (col2 == 3) {
                cardView2_xcol.setVisibility(View.VISIBLE);
                cardView5_xcol.setVisibility(View.VISIBLE);
                cardView8_xcol.setVisibility(View.VISIBLE);

                cardView2_x.setVisibility(View.GONE);
                cardView5_x.setVisibility(View.GONE);
                cardView8_x.setVisibility(View.GONE);
            } else if (col3 == 3) {
                cardView3_xcol.setVisibility(View.VISIBLE);
                cardView6_xcol.setVisibility(View.VISIBLE);
                cardView9_xcol.setVisibility(View.VISIBLE);

                cardView3_x.setVisibility(View.GONE);
                cardView6_x.setVisibility(View.GONE);
                cardView9_x.setVisibility(View.GONE);

            } else if (major_diagonal == 3) {
                cardView1_xmajor.setVisibility(View.VISIBLE);
                cardView5_xmajor.setVisibility(View.VISIBLE);
                cardView9_xmajor.setVisibility(View.VISIBLE);

                cardView1_x.setVisibility(View.GONE);
                cardView5_x.setVisibility(View.GONE);
                cardView9_x.setVisibility(View.GONE);
            } else {
                cardView3_xdiagonal.setVisibility(View.VISIBLE);
                cardView5_xdiagonal.setVisibility(View.VISIBLE);
                cardView7_xdiagonal.setVisibility(View.VISIBLE);

                cardView3_x.setVisibility(View.GONE);
                cardView5_x.setVisibility(View.GONE);
                cardView7_x.setVisibility(View.GONE);
            }
            return 1;
        } else if (count >= 9) {
            count = 10;
            return 11;
        } else return -1;
    }

    void updateScore() {
        String o, d, x;
        o = Integer.toString(owin);
        x = Integer.toString(xwin);
        d = Integer.toString(draw);

        owins.setText(o);
        xwins.setText(x);
        draws.setText(d);


    }

    private void GameScoreUpdate() {

        Log.e("onTicTac", "onTicTAcStarted");
        if (Constant.isNetworkAvailable(activity)) {
            random_points = String.valueOf(someEarn_controller.getTicTacReward());
            points_text.setText(random_points);
        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }

        if (first_time) {
            first_time = false;
            Log.e("onTicTac", "Complete");
            Log.e("onTicTac", "Complete" + random_points);
            int counter = Integer.parseInt(tictac_count_textView.getText().toString());
            if (counter == 0) {
                showDialogPoints(0, "0", counter);
            } else {
                showDialogPoints(1, points_text.getText().toString(), counter);
            }
        }

    }

    ;

    private void LoadInterstitial() {
        if (startAppAd == null) {
            startAppAd = new StartAppAd(AppsConfig.getContext());
            startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC);
        } else {
            startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC);
        }
    }

    private void ShowInterstital() {
        if (startAppAd != null && startAppAd.isReady()) {
            startAppAd.showAd();
        }
    }

    private void onInit() {
        String user_points = Constant.getString(activity, Constant.USER_POINTS);
        if (user_points.equals("")) {
            user_points_text_view.setText("0");
        } else {
            user_points_text_view.setText(Constant.getString(activity, Constant.USER_POINTS));
        }
        String tictacCount = Constant.getString(activity, Constant.TICTAC_COUNT);
        if (tictacCount.equals("0")) {
            tictacCount = "";
            Log.e("TAG", "onInit: TicTac card 0");
        }
        if (tictacCount.equals("")) {
            Log.e("TAG", "onInit: TicTac card empty vala part");
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(activity, Constant.LAST_DATE_TICTAC);
            Log.e("TAG", "Lat date" + last_date);
            if (last_date.equals("")) {
                Log.e("TAG", "onInit: last date empty part");
                tictac_count_textView.setText(someEarn_controller.getTicTacCount());
                Constant.setString(activity, Constant.TICTAC_COUNT, someEarn_controller.getTicTacCount());
                Constant.setString(activity, Constant.LAST_DATE_TICTAC, currentDate);
            } else {
                Log.e("TAG", "onInit: last date not empty part");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date current_date = sdf.parse(currentDate);
                    Date lastDate = sdf.parse(last_date);
                    long diff = current_date.getTime() - lastDate.getTime();
                    long difference_In_Days = (diff / (1000 * 60 * 60 * 24)) % 365;
                    Log.e("TAG", "onClick: Days Difference" + difference_In_Days);
                    if (difference_In_Days > 0) {
                        Constant.setString(activity, Constant.LAST_DATE_TICTAC, currentDate);
                        Constant.setString(activity, Constant.TICTAC_COUNT, someEarn_controller.getTicTacCount());
                        tictac_count_textView.setText(Constant.getString(activity, Constant.TICTAC_COUNT));
                        Log.e("TAG", "onClick: today date added to preference" + currentDate);
                    } else {
                        tictac_count_textView.setText("0");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("TAG", "onInit: scracth card in preference part");
            tictac_count_textView.setText(tictacCount);
        }
    }


    private void showDialogPoints(final int addOrNoAddValue, final String points, final int counter) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.points_dialog_indratech);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);
        if (Constant.isNetworkAvailable(activity)) {
            if (addOrNoAddValue == 1) {
                if (points.equals("0")) {
                    Log.e("TAG", "showDialogPoints: 0 points");

                    title_text.setText(getResources().getString(R.string.better_luck_next_time));
                    points_text.setVisibility(View.VISIBLE);
                    points_text.setText(points);
                    add_btn.setText(getResources().getString(R.string.ok_text));
                } else {
                    Log.e("TAG", "showDialogPoints: points");

                    title_text.setText(getResources().getString(R.string.you_win));
                    points_text.setVisibility(View.VISIBLE);
                    points_text.setText(points);
                    add_btn.setText(getResources().getString(R.string.account_add_to_wallet));
                }
            } else {
                Log.e("TAG", "showDialogPoints: chance over");

                title_text.setText(getResources().getString(R.string.today_work_is_over));
                points_text.setVisibility(View.GONE);
                add_btn.setText(getResources().getString(R.string.ok_text));
            }
            add_btn.setOnClickListener(view -> {


                String typeOfAds = ads_controller.getTicTacToeInterstitialAds();
                switch (typeOfAds) {
                    case "Admob":
                        Constant.ShowAdmobInterstitialAds(TictactoeMain.this);
                        break;
                    case "Facebook":
                        Constant.ShowFacebookInterstitialAds();
                        break;
                    case "Vungle":
                        Constant.playVungleAdInterstitial();
                        break;
                    case "AdColony":
                        Constant.AdcolonyDisplayInterstitialAd();
                        break;
                    case "Startapp":
                        Constant.startAppInterstitialShow();
                        break;
                    case "IronSource":
                        Constant.IronSourceInterstitialShow();
                        break;

                }


                first_time = true;
                poiints = 0;
                if (addOrNoAddValue == 1) {
                    if (points.equals("0")) {
                        String current_counter = String.valueOf(counter - 1);
                        Constant.setString(activity, Constant.TICTAC_COUNT, current_counter);
                        tictac_count_textView.setText(Constant.getString(activity, Constant.TICTAC_COUNT));
                        dialog.dismiss();
                    } else {
                        String current_counter = String.valueOf(counter - 1);
                        Constant.setString(activity, Constant.TICTAC_COUNT, current_counter);
                        tictac_count_textView.setText(Constant.getString(activity, Constant.TICTAC_COUNT));
                        try {
                            int finalPoint;
                            if (points.equals("")) {
                                finalPoint = 0;
                            } else {
                                finalPoint = Integer.parseInt(points);
                            }
                            poiints = finalPoint;
                            Constant.addPoints(activity, finalPoint, 0);
                            user_points_text_view.setText(Constant.getString(activity, Constant.USER_POINTS));
                        } catch (NumberFormatException ex) {
                            Log.e("TAG", "onTicTacComplete: " + ex.getMessage());
                        }
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }
                if (tictiac_count == Integer.parseInt(someEarn_controller.getRewarded_and_interstitial_count())) {
                    if (rewardShow) {
                        Log.e(TAG, "onReachTarget: rewaded ads showing method");
                        if (ads_controller.getIntAdsApp().equals("startapp")) {
                            ShowInterstital();
                        } else {
                            showDailog();
                        }
                        rewardShow = false;
                        interstitialShow = true;
                        tictiac_count = 1;
                    } else if (interstitialShow) {
                        Log.e(TAG, "onReachTarget: rewaded ads showing method");


                        String typeOfAdsTimer = ads_controller.getTicTacToeInterstitialAds();
                        switch (typeOfAdsTimer) {
                            case "Admob":
                                Constant.ShowAdmobInterstitialAds(TictactoeMain.this);
                                break;
                            case "Facebook":
                                Constant.ShowFacebookInterstitialAds();
                                break;
                            case "Vungle":
                                Constant.playVungleAdInterstitial();
                                break;
                            case "AdColony":
                                Constant.AdcolonyDisplayInterstitialAd();
                                break;
                            case "Startapp":
                                Constant.startAppInterstitialShow();
                                break;
                            case "IronSource":
                                Constant.IronSourceInterstitialShow();
                                break;

                        }

                        rewardShow = true;
                        interstitialShow = false;
                        tictiac_count = 1;
                    }
                } else {
                    tictiac_count += 1;
                }
            });
        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.internet_connection_of_text));
        }
        dialog.show();
    }

    public void showDailog() {

        CardView CardDialogVideoAds = findViewById(R.id.CardDialogVideoAds);
        CardDialogVideoAds.setVisibility(View.VISIBLE);

        AppCompatButton add_btn_video_ads = findViewById(R.id.add_btn_video_ads);
        add_btn_video_ads.setOnClickListener(view -> {

            String typeOfAds = ads_controller.getTicTacToeAds();
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
                    //Unity
                    Constant.ShowUnityAds(activity);
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

            CardView CardDialogVideoAds12 = findViewById(R.id.CardDialogVideoAds);
            CardDialogVideoAds12.setVisibility(View.GONE);
        });

        AppCompatButton cancel_btn_video_ads = findViewById(R.id.cancel_btn_video_ads);
        cancel_btn_video_ads.setOnClickListener(view -> {

            CardView CardDialogVideoAds1 = findViewById(R.id.CardDialogVideoAds);
            CardDialogVideoAds1.setVisibility(View.GONE);

            if (poiints != 0) {

                String po = Constant.getString(activity, Constant.USER_POINTS);
                if (po.equals("")) {
                    po = "0";
                }
                int current_Points = Integer.parseInt(po);
                int finalPoints = current_Points - poiints;
                Constant.setString(activity, Constant.USER_POINTS, String.valueOf(finalPoints));
                Intent serviceIntent = new Intent(activity, PointsService.class);
                serviceIntent.putExtra("points", Constant.getString(activity, Constant.USER_POINTS));
                startService(serviceIntent);
                user_points_text_view.setText(Constant.getString(activity, Constant.USER_POINTS));
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


        String typeOfAds = ads_controller.getTicTacToeAds();
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
                Constant.ShowUnityAds(activity);
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

        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        finish();
    }

    private void loadBannerAdmob() {

        final AdView adView = new AdView(activity);
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adView.setAdUnitId(ads_id_controller.getAdmobBannerId());
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adLayout.addView(adView);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.v("admob", "error" + loadAdError.getMessage());
            }
        });
        adView.loadAd(new AdRequest.Builder().build());

    }

    private void loadBannerFacebook() {
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(activity, getResources().getString(R.string.facebook_banner_ads_id), com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250);
        adView.loadAd();
        adLayout.addView(adView);

    }

}