package com.ntarevpn.rbpessacash.util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.databinding.PointsDialogIndratechBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PointsUtil {
    private PointsDialogIndratechBinding pointsDialogBinding;
    private Dialog pointsDialog;
    private final Activity activity;

    private int dailyPoints;
    private OnPointsAddedListener listener;

    public PointsUtil(Activity activity) {
        this.activity = activity;
        this.dailyPoints = 0;

    }

    public PointsUtil(Activity activity, int dailyPoints) {
        this.activity = activity;
        this.dailyPoints = dailyPoints;

    }

    public void initializePointsDialog(LayoutInflater layoutInflater) {
        pointsDialogBinding = PointsDialogIndratechBinding.inflate(layoutInflater);
        initializePointsDialogView();
    }

    public void initializePointsDialog(LayoutInflater layoutInflater, ViewGroup parent) {
        pointsDialogBinding = PointsDialogIndratechBinding.inflate(layoutInflater, parent, false);

        initializePointsDialogView();
    }

    private void initializePointsDialogView() {
        pointsDialog = new Dialog(activity);
        pointsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pointsDialog.setContentView(pointsDialogBinding.getRoot());
        pointsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pointsDialog.setCancelable(false);

        pointsDialogBinding.addBtn.setOnClickListener(view -> pointsDialog.dismiss());
    }

    public void setDailyPoints(int value) {
        dailyPoints = value;
    }

    public void setListener(OnPointsAddedListener listener) {
        this.listener = listener;
    }

    public void checkDaily() {
        if (Constant.isNetworkAvailable(activity)) {
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            String lastDate = Constant.getString(activity, Constant.LAST_DATE);

            if (lastDate.equals("")) {
                Constant.setString(activity, Constant.LAST_DATE, currentDate);
                Constant.addPoints(activity, dailyPoints, 0);
                showPointsDialog(dailyPoints);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date pastDAte = sdf.parse(lastDate);
                    Date currentDAte = sdf.parse(currentDate);
                    long diff = currentDAte.getTime() - pastDAte.getTime();
                    long differenceInDays = (diff / (1000 * 60 * 60 * 24)) % 365;

                    if (differenceInDays > 0) {
                        Constant.setString(activity, Constant.LAST_DATE, currentDate);
                        Constant.addPoints(activity, dailyPoints, 0);
                        showPointsDialog(dailyPoints);
                    } else {
                        showPointsDialog(0);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Constant.showInternetErrorDialog(activity, activity.getString(R.string.internet_connection_of_text));
        }
    }

    public void showPointsDialog(int points) {
        ImageView imageView = pointsDialogBinding.pointsImage;
        TextView titleText = pointsDialogBinding.titleTextPoints;
        TextView pointsText = pointsDialogBinding.points;
        AppCompatButton addBtn = pointsDialogBinding.addBtn;

        if (points == dailyPoints) {
            titleText.setText(activity.getString(R.string.you_win));
            pointsText.setVisibility(View.VISIBLE);
            pointsText.setText(String.valueOf(points));
            addBtn.setText(activity.getString(R.string.account_add_to_wallet));

            if (listener != null) {
                listener.onAdded(points);
            }
        } else {
            imageView.setImageResource(R.drawable.banner_gif);
            titleText.setText(activity.getString(R.string.today_checkin_over));
            pointsText.setVisibility(View.GONE);
            addBtn.setText(activity.getString(R.string.ok_text));
        }
        pointsDialog.show();
    }

    public interface OnPointsAddedListener {
        void onAdded(int points);
    }
}
