package com.ntarevpn.rbpessacash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.Constant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GiftCard extends AppCompatActivity {
    private CardView cardView, cardView2, cardView3, cardView4, cardView9, cardView10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift_card_indratech);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(" Gift Card ");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        GiftCard activity = this;
        TextView user_points_textView = findViewById(R.id.user_points_textView);
        user_points_textView.setText(Constant.getString(activity, Constant.USER_POINTS));

        cardView = findViewById(R.id.cardView);
        cardView2 = findViewById(R.id.cardView2);
        cardView3 = findViewById(R.id.cardView3);
        cardView4 = findViewById(R.id.cardView4);
        cardView9 = findViewById(R.id.cardView9);
        cardView10 = findViewById(R.id.cardView10);

        SetOnClickView();

        TextView text_view_date_activity = findViewById(R.id.text_view_date_activity);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        text_view_date_activity.setText(dateFormat.format(date));

    }

    private void SetOnClickView() {
        cardView.setOnClickListener(view -> {
            Intent i = new Intent(GiftCard.this, Redeem.class);
            i.putExtra("option", getString(R.string.FF_payment_1_D));
            i.putExtra("PointOption", getString(R.string.FF_payment_1_Coin));
            i.putExtra("EnterPayment", getString(R.string.enter_ff_id));
            startActivity(i);
        });

        cardView2.setOnClickListener(view -> {
            Intent i = new Intent(GiftCard.this, Redeem.class);
            i.putExtra("option", getString(R.string.FF_payment_2_D));
            i.putExtra("PointOption", getString(R.string.FF_payment_2_Coin));
            i.putExtra("EnterPayment", getString(R.string.enter_ff_id));
            startActivity(i);
        });

        cardView3.setOnClickListener(view -> {
            Intent i = new Intent(GiftCard.this, Redeem.class);
            i.putExtra("option", getString(R.string.FF_payment_3_D));
            i.putExtra("PointOption", getString(R.string.FF_payment_3_Coin));
            i.putExtra("EnterPayment", getString(R.string.enter_ff_id));
            startActivity(i);
        });

        cardView4.setOnClickListener(view -> {
            Intent i = new Intent(GiftCard.this, Redeem.class);
            i.putExtra("option", getString(R.string.FF_payment_4_D));
            i.putExtra("PointOption", getString(R.string.FF_payment_4_Coin));
            i.putExtra("EnterPayment", getString(R.string.enter_ff_id));
            startActivity(i);
        });

        cardView9.setOnClickListener(view -> {
            Intent i = new Intent(GiftCard.this, Redeem.class);
            i.putExtra("option", getString(R.string.Pubg_payment_1_D));
            i.putExtra("PointOption", getString(R.string.Pubg_payment_1_Coin));
            i.putExtra("EnterPayment", getString(R.string.enter_Pubg_id));
            startActivity(i);
        });

        cardView10.setOnClickListener(view -> {
            Intent i = new Intent(GiftCard.this, Redeem.class);
            i.putExtra("option", getString(R.string.Pubg_payment_2_D));
            i.putExtra("PointOption", getString(R.string.Pubg_payment_2_Coin));
            i.putExtra("EnterPayment", getString(R.string.enter_Pubg_id));
            startActivity(i);
        });


    }
}