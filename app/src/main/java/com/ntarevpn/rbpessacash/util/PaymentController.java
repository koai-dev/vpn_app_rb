package com.ntarevpn.rbpessacash.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PaymentController {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public PaymentController(Context context) {
        sharedPreferences = context.getSharedPreferences("Payment_Controller", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void setDate(int date) {
        editor.putInt("date", date);
        editor.commit();
    }

    public int getDate() {
        return sharedPreferences.getInt("date", 0);
    }


    public void dataStore(String Payment_section_Image1, String Payment_section_Dollar_1, String Payment_section_CostCOint1, String Payment_section_Image2, String Payment_section_Dollar_2, String Payment_section_CostCOint2,
                          String Payment_section_Image3, String Payment_section_Dollar_3,
                          String Payment_section_CostCOint3,
                          String Payment_section_Dollar_4,
                          String Payment_section_Image4,
                          String Payment_section_CostCOint4,
                          String Payment_section_Image5,
                          String Payment_section_Dollar_5,
                          String Payment_section_CostCOint5,
                          String Payment_section_Image6,
                          String Payment_section_Dollar_6,
                          String Payment_section_CostCOint6) {
        editor.putString("Payment_section_Image1", Payment_section_Image1);
        editor.putString("Payment_section_Dollar_1", Payment_section_Dollar_1);
        editor.putString("Payment_section_CostCOint1", Payment_section_CostCOint1);
        editor.putString("Payment_section_Image2", Payment_section_Image2);
        editor.putString("Payment_section_Dollar_2", Payment_section_Dollar_2);
        editor.putString("Payment_section_CostCOint2", Payment_section_CostCOint2);
        editor.putString("Payment_section_Image3", Payment_section_Image3);
        editor.putString("Payment_section_Dollar_3", Payment_section_Dollar_3);
        editor.putString("Payment_section_CostCOint3", Payment_section_CostCOint3);
        editor.putString("Payment_section_Dollar_4", Payment_section_Dollar_4);
        editor.putString("Payment_section_Image4", Payment_section_Image4);
        editor.putString("Payment_section_CostCOint4", Payment_section_CostCOint4);
        editor.putString("Payment_section_Image5", Payment_section_Image5);
        editor.putString("Payment_section_Dollar_5", Payment_section_Dollar_5);
        editor.putString("Payment_section_CostCOint5", Payment_section_CostCOint5);
        editor.putString("Payment_section_Image6", Payment_section_Image6);
        editor.putString("Payment_section_Dollar_6", Payment_section_Dollar_6);
        editor.putString("Payment_section_CostCOint6", Payment_section_CostCOint6);

        editor.commit();
    }


    public String getPayment_section_Image1() {
        return sharedPreferences.getString("Payment_section_Image1", "0");
    }

    public String getPayment_section_Dollar_1() {
        return sharedPreferences.getString("Payment_section_Dollar_1", "0");
    }

    public String getPayment_section_CostCOint1() {
        return sharedPreferences.getString("Payment_section_CostCOint1", "0");
    }

    public String getPayment_section_Image2() {
        return sharedPreferences.getString("Payment_section_Image2", "0");
    }

    public String getPayment_section_Dollar_2() {
        return sharedPreferences.getString("Payment_section_Dollar_2", "0");
    }

    public String getPayment_section_CostCOint2() {
        return sharedPreferences.getString("Payment_section_CostCOint2", "0");
    }

    public String getPayment_section_Image3() {
        return sharedPreferences.getString("Payment_section_Image3", "0");
    }

    public String getPayment_section_Dollar_3() {
        return sharedPreferences.getString("Payment_section_Dollar_3", "0");
    }

    public String getPayment_section_CostCOint3() {
        return sharedPreferences.getString("Payment_section_CostCOint3", "0");
    }

    public String getPayment_section_Dollar_4() {
        return sharedPreferences.getString("Payment_section_Dollar_4", "0");
    }

    public String getPayment_section_Image4() {
        return sharedPreferences.getString("Payment_section_Image4", "0");
    }


    public String getPayment_section_CostCOint4() {
        return sharedPreferences.getString("Payment_section_CostCOint4", "0");
    }

    public String getPayment_section_Image5() {
        return sharedPreferences.getString("Payment_section_Image5", "0");
    }

    public String getPayment_section_Dollar_5() {
        return sharedPreferences.getString("Payment_section_Dollar_5", "0");
    }

    public String getPayment_section_CostCOint5() {
        return sharedPreferences.getString("Payment_section_CostCOint5", "0");
    }

    public String getPayment_section_Image6() {
        return sharedPreferences.getString("Payment_section_Image6", "0");
    }

    public String getPayment_section_Dollar_6() {
        return sharedPreferences.getString("Payment_section_Dollar_6", "0");
    }

    public String getPayment_section_CostCOint6() {
        return sharedPreferences.getString("Payment_section_CostCOint6", "0");
    }


}
