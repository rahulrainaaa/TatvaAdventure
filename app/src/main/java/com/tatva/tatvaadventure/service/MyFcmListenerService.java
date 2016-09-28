package com.tatva.tatvaadventure.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tatva.tatvaadventure.activity.EventsActivity;

import java.util.Map;

public class MyFcmListenerService extends FirebaseMessagingService {

    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    @Override
    public void onMessageReceived(RemoteMessage message) {
        String from = message.getFrom();
        Map data = message.getData();

        Log.d("FCM MESSAGE RECEIVED", "--------------------------------------------------------------------------------------");

        sendNotification(from,"text");
    }

    private void sendNotification(String title, String msg) {

        mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, EventsActivity.class);
        notificationIntent.putExtra("NotificationIntent", true);
        notificationIntent.setFlags(Notification.FLAG_AUTO_CANCEL);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Builder mBuilder = new Builder(this)
               // .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("" + title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg.toString());

        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(1, mBuilder.build());
    }

}