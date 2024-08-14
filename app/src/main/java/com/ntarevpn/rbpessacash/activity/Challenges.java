package com.ntarevpn.rbpessacash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.adapter.TapjoyAd;
import com.ntarevpn.rbpessacash.util.Constant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Challenges extends AppCompatActivity {

    private Button ironSource_Btn, Pollfish_Btn, Fyber_Btn, Tapjoy_Btn;
    private String uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenges_layout_indratech);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Challenges");
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        TextView pollfish_Text = findViewById(R.id.Pollfish_Text);
        ImageView pollfish_Image = findViewById(R.id.Pollfish_Image);
        TextView pollfish_Coin = findViewById(R.id.Pollfish_Coin);
        ImageView fyber_Image_section = findViewById(R.id.Fyber_Image_section);
        TextView fyber_Text = findViewById(R.id.Fyber_Text);
        TextView fyber_Coin = findViewById(R.id.Fyber_Coin);
        TextView user_points_text_view = findViewById(R.id.user_points_text_view);
        ImageView ironSource_Image = findViewById(R.id.ironSource_Image);
        TextView ironSource_Coin = findViewById(R.id.ironSource_Coin);
        TextView ironSource_Text = findViewById(R.id.ironSource_Text);
        CardView card1 = findViewById(R.id.Card1);
        CardView card2 = findViewById(R.id.Card2);
        CardView card3 = findViewById(R.id.Card3);
        Pollfish_Btn = findViewById(R.id.Pollfish_Btn);
        Fyber_Btn = findViewById(R.id.Fyber_Btn);
        ironSource_Btn = findViewById(R.id.ironSource_Btn);
        ImageView tapjoy_Image_section = findViewById(R.id.Tapjoy_Image_section);
        TextView tapjoy_Text = findViewById(R.id.Tapjoy_Text);
        TextView tapjoy_Coin = findViewById(R.id.Tapjoy_Coin);
        Tapjoy_Btn = findViewById(R.id.Tapjoy_Btn);


        pollfish_Text.setText("Pollfish");
        pollfish_Image.setImageResource(R.drawable.pollfish_ic);
        pollfish_Coin.setText("+");

        fyber_Text.setText("Fyber");
        fyber_Coin.setText("+");
        fyber_Image_section.setImageResource(R.drawable.fyber_ic);

        ironSource_Image.setImageResource(R.drawable.ironsource_ic);
        ironSource_Coin.setText("+");
        ironSource_Text.setText("ironSource");

        tapjoy_Text.setText("Tapjoy");
        tapjoy_Coin.setText("+");
        tapjoy_Image_section.setImageResource(R.drawable.tapjoy_ic);


        TextView text_view_date_activity = findViewById(R.id.text_view_date_activity);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        text_view_date_activity.setText(dateFormat.format(date));

        user_points_text_view.setText(Constant.getString(this, Constant.USER_POINTS));

        onClickView();
    }

    private void onClickView() {
        Pollfish_Btn.setOnClickListener(view -> {
            Intent i = new Intent(this, Pollfish.class);
            startActivity(i);
        });
        Fyber_Btn.setOnClickListener(view -> {
            Intent i = new Intent(this, FyberAds.class);
            startActivity(i);
        });
        ironSource_Btn.setOnClickListener(view -> {
            Intent i = new Intent(this, IronSource.class);
            startActivity(i);
        });
        Tapjoy_Btn.setOnClickListener(view -> TapjoyAd.init(this, uid));
    }

}