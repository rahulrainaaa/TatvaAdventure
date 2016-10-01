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
import com.tatva.tatvaadventure.R;
import com.tatva.tatvaadventure.activity.PushActivity;
import com.tatva.tatvaadventure.model.EventDetail;
import com.tatva.tatvaadventure.utils.Constants;

import java.util.Map;

public class MyFcmListenerService extends FirebaseMessagingService {

    private NotificationManager mNotificationManager;

    @Override
    public void onMessageReceived(RemoteMessage message) {

        Log.d("TATVA", "New Notification Received");
        String from = message.getFrom();
        Map data = message.getData();
        String title = (String) data.get("title");
        Integer id = Integer.parseInt((String) data.get("id"));
        String place = (String) data.get("place");
        String time = (String) data.get("time");
        EventDetail eventDetail = new EventDetail();
        eventDetail.setId(id);
        eventDetail.setTitle(title.trim());
        eventDetail.setPlace(place);
        eventDetail.setTime(time);
        Constants.pushDetail = eventDetail;
        sendNotification("Tatva Adventure", eventDetail.getTitle() + "  On  " + eventDetail.getTime().trim());
    }

    private void sendNotification(String title, String msg) {

        mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, PushActivity.class);
        notificationIntent.putExtra("NotificationIntent", true);
        notificationIntent.setFlags(Notification.FLAG_AUTO_CANCEL);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Builder mBuilder = new Builder(this)
                .setSmallIcon(R.mipmap.push)
                .setContentTitle("" + title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg.toString());

        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(1, mBuilder.build());
    }

}