package com.nafshadigital.smartcallassistant.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nafshadigital.smartcallassistant.R;
import com.nafshadigital.smartcallassistant.activity.Dashboard;
import com.nafshadigital.smartcallassistant.activity.ShowNotificationMessage;

public class FireBaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Send a message
        SharedPreferences sharedPreferences = this.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String userID =  sharedPreferences.getString("userID", "");

        if(remoteMessage.getNotification().getTitle().contains("You received Heart")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("message", "1");
            editor.apply();
        }

        //Intent intent = new Intent(this, ShowNotificationMessage.class);

        super.onMessageReceived(remoteMessage);
        Intent intent = new Intent(this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        if(remoteMessage.getNotification().getTitle().contains("You received Heart")) {
            intent.putExtra("title", "Heart Message");
        }
        else
        {
            Bundle bundle = new Bundle();
            bundle.putString("title",remoteMessage.getNotification().getTitle());
            bundle.putString("body",remoteMessage.getNotification().getBody());
            intent.putExtras(bundle);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.appicon)
                .setColor(NotificationCompat.COLOR_DEFAULT)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

        // hide the notification after its selected
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notificationManager.notify(0, notification);

    }
}
