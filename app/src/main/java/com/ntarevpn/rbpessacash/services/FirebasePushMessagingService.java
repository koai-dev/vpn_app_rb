package com.ntarevpn.rbpessacash.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.activity.Splash;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebasePushMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> params = remoteMessage.getData();
        if (params.size() > 0) {
            sendNotification(params.get("title"), params.get("message"));
            broadcastNewNotification();
        } else {
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

    }

    private void sendNotification(String title, String messageBody) {

        Intent intent = new Intent(this, Splash.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Push Notification", title);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(getResources().getString(R.string.app_name),
                    getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableLights(true);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder;
        notificationBuilder = new NotificationCompat.Builder(this, getResources().getString(R.string.app_name))
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setColor(getResources().getColor(R.color.black))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.mipmap.ic_launcher_round))
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setChannelId(getResources().getString(R.string.app_name))
                .setFullScreenIntent(pendingIntent, true);
        notificationManager.notify(1, notificationBuilder.build());
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }

    private void broadcastNewNotification() {
        Intent intent = new Intent("new_notification");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
