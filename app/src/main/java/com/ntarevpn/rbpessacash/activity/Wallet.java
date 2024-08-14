package com.ntarevpn.rbpessacash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.Constant;
import com.ntarevpn.rbpessacash.util.PaymentController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Wallet extends AppCompatActivity {

    private String STSectionDollar1, STSectionDollar2, STSectionDollar3, STSectionDollar4, STSectionDollar5, STSectionDollar6, STSectionCoin1, STSectionCoin2, STSectionCoin3, STSectionCoin4, STSectionCoin5, STSectionCoin6;
    PaymentController payment_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_layout_indratech);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(" Redeem Points ");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Wallet activity = this;
        TextView user_points_textView = findViewById(R.id.user_points_textView);
        payment_controller = new PaymentController(this);
        LinearLayout sectionBtn1 = findViewById(R.id.SectionBtn1);
        LinearLayout sectionBtn2 = findViewById(R.id.SectionBtn2);
        LinearLayout sectionBtn3 = findViewById(R.id.SectionBtn3);
        LinearLayout sectionBtn4 = findViewById(R.id.SectionBtn4);
        LinearLayout sectionBtn5 = findViewById(R.id.SectionBtn5);
        LinearLayout sectionBtn6 = findViewById(R.id.SectionBtn6);

        TextView sectionImage1 = findViewById(R.id.SectionImage1);
        TextView sectionImage2 = findViewById(R.id.SectionImage2);
        TextView sectionImage3 = findViewById(R.id.SectionImage3);
        TextView sectionImage4 = findViewById(R.id.SectionImage4);
        TextView sectionImage5 = findViewById(R.id.SectionImage5);
        TextView sectionImage6 = findViewById(R.id.SectionImage6);

        //date
        TextView text_view_date_activity = findViewById(R.id.text_view_date_activity);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        text_view_date_activity.setText(dateFormat.format(date));


        sectionImage1.setText(payment_controller.getPayment_section_Image1());
        sectionImage2.setText(payment_controller.getPayment_section_Image2());
        sectionImage3.setText(payment_controller.getPayment_section_Image3());
        sectionImage4.setText(payment_controller.getPayment_section_Image4());
        sectionImage5.setText(payment_controller.getPayment_section_Image5());
        sectionImage6.setText(payment_controller.getPayment_section_Image6());

        TextView sectionDollar1 = findViewById(R.id.SectionDollar1);
        TextView sectionDollar2 = findViewById(R.id.SectionDollar2);
        TextView sectionDollar3 = findViewById(R.id.SectionDollar3);
        TextView sectionDollar4 = findViewById(R.id.SectionDollar4);
        TextView sectionDollar5 = findViewById(R.id.SectionDollar5);
        TextView sectionDollar6 = findViewById(R.id.SectionDollar6);

        sectionDollar1.setText(payment_controller.getPayment_section_Dollar_1());
        sectionDollar2.setText(payment_controller.getPayment_section_Dollar_2());
        sectionDollar3.setText(payment_controller.getPayment_section_Dollar_3());
        sectionDollar4.setText(payment_controller.getPayment_section_Dollar_4());
        sectionDollar5.setText(payment_controller.getPayment_section_Dollar_5());
        sectionDollar6.setText(payment_controller.getPayment_section_Dollar_6());

        TextView sectionCoin1 = findViewById(R.id.SectionCoin1);
        TextView sectionCoin2 = findViewById(R.id.SectionCoin2);
        TextView sectionCoin3 = findViewById(R.id.SectionCoin3);
        TextView sectionCoin4 = findViewById(R.id.SectionCoin4);
        TextView sectionCoin5 = findViewById(R.id.SectionCoin5);
        TextView sectionCoin6 = findViewById(R.id.SectionCoin6);

        sectionCoin1.setText(payment_controller.getPayment_section_CostCOint1());
        sectionCoin2.setText(payment_controller.getPayment_section_CostCOint2());
        sectionCoin3.setText(payment_controller.getPayment_section_CostCOint3());
        sectionCoin4.setText(payment_controller.getPayment_section_CostCOint4());
        sectionCoin5.setText(payment_controller.getPayment_section_CostCOint5());
        sectionCoin6.setText(payment_controller.getPayment_section_CostCOint6());

        sectionBtn1.setOnClickListener(view -> {
            String PointOP = payment_controller.getPayment_section_Dollar_1();
            String PointOption = payment_controller.getPayment_section_CostCOint1();
            String PaymentM = payment_controller.getPayment_section_Image1();

            Intent i = new Intent(Wallet.this, Redeem.class);
            i.putExtra("option", PointOP + " " + PaymentM);
            i.putExtra("PointOption", PointOption);
            i.putExtra("EnterPayment", getString(R.string.enter_account_id));
            i.putExtra("PaymentMethod", PaymentM + PaymentM);
            startActivity(i);
        });
        sectionBtn2.setOnClickListener(view -> {
            String PointOP = payment_controller.getPayment_section_Dollar_2();
            String PointOption = payment_controller.getPayment_section_CostCOint2();
            String PaymentM = payment_controller.getPayment_section_Image2();

            Intent i = new Intent(Wallet.this, Redeem.class);
            i.putExtra("option", PointOP + " " + PaymentM);
            i.putExtra("PointOption", PointOption);
            i.putExtra("EnterPayment", getString(R.string.enter_account_id));
            i.putExtra("PaymentMethod", PaymentM);
            startActivity(i);
        });

        sectionBtn3.setOnClickListener(view -> {
            String PointOP = payment_controller.getPayment_section_Dollar_3();
            String PointOption = payment_controller.getPayment_section_CostCOint3();
            String PaymentM = payment_controller.getPayment_section_Image3();

            Intent i = new Intent(Wallet.this, Redeem.class);
            i.putExtra("option", PointOP + " " + PaymentM);
            i.putExtra("PointOption", PointOption);
            i.putExtra("EnterPayment", getString(R.string.enter_account_id));
            i.putExtra("PaymentMethod", PaymentM);
            startActivity(i);
        });

        sectionBtn4.setOnClickListener(view -> {
            String PointOP = payment_controller.getPayment_section_Dollar_4();
            String PointOption = payment_controller.getPayment_section_CostCOint4();
            String PaymentM = payment_controller.getPayment_section_Image4();

            Intent i = new Intent(Wallet.this, Redeem.class);
            i.putExtra("option", PointOP + " " + PaymentM);
            i.putExtra("PointOption", PointOption);
            i.putExtra("EnterPayment", getString(R.string.enter_account_id));
            i.putExtra("PaymentMethod", PaymentM);
            startActivity(i);
        });


        CardView cardView3 = findViewById(R.id.cardView3);
        cardView3.setOnClickListener(v -> {
            String PointOP = payment_controller.getPayment_section_Dollar_5();
            String PointOption = payment_controller.getPayment_section_CostCOint5();
            String PaymentM = payment_controller.getPayment_section_Image5();

            Intent i = new Intent(Wallet.this, Redeem.class);
            i.putExtra("option", PointOP + " " + PaymentM);
            i.putExtra("PointOption", PointOption);
            i.putExtra("EnterPayment", getString(R.string.enter_account_id));
            i.putExtra("PaymentMethod", PaymentM);
            startActivity(i);
        });

        CardView cardView4 = findViewById(R.id.cardView4);
        cardView4.setOnClickListener(v -> {
            String PointOP = payment_controller.getPayment_section_Dollar_6();
            String PointOption = payment_controller.getPayment_section_CostCOint6();
            String PaymentM = payment_controller.getPayment_section_Image6();

            Intent i = new Intent(Wallet.this, Redeem.class);
            i.putExtra("option", PointOP + " " + PaymentM);
            i.putExtra("PointOption", PointOption);
            i.putExtra("EnterPayment", getString(R.string.enter_account_id));
            i.putExtra("PaymentMethod", PaymentM);
            startActivity(i);
        });


        user_points_textView.setText(Constant.getString(activity, Constant.USER_POINTS));


    }
}