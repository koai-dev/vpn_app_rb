package com.ntarevpn.rbpessacash.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsController;
import com.google.android.material.textfield.TextInputEditText;

public class Contact extends AppCompatActivity {

    private TextInputEditText subject, message;
    private AppCompatButton btn;
    private ImageView back;
    AdsController ads_controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us_indratech);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Contact Us");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);
        btn = findViewById(R.id.send_btn);
        back = findViewById(R.id.back_img_contact);
        intView();
        ads_controller = new AdsController(this);

    }

    private void intView() {
        btn.setOnClickListener(v -> {
            if (subject.getText().toString().length() == 0) {
                subject.setError("Enter Subject");
                subject.requestFocus();
            } else if (message.getText().toString().length() == 0) {
                message.setError("Please Type Message");
                message.requestFocus();
            } else {
                if (Contact.this == null) {
                    return;
                }
                String[] Email = {ads_controller.getContactUsEmail()};
                String Subject = subject.getText().toString();
                String Message = message.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, Email);
                intent.putExtra(Intent.EXTRA_SUBJECT, Subject);
                intent.putExtra(Intent.EXTRA_TEXT, Message);
                Contact.this.startActivity(Intent.createChooser(intent, "Send Via"));
            }
        });
        back.setOnClickListener(view -> {
            if (Contact.this == null) {
                return;
            }
            Contact.this.onBackPressed();
        });

    }
}