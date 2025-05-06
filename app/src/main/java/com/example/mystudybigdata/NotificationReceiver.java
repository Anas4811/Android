package com.example.mystudybigdata;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String description = intent.getStringExtra("description");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "homework_channel";
            CharSequence name = "Homework Notifications";
            String descriptionText = "Channel for homework due notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(descriptionText);
            notificationManager.createNotificationChannel(channel);
        }

        // Build and send notification
        Notification notification = new NotificationCompat.Builder(context, "homework_channel")
                .setSmallIcon(R.drawable.notification_icon) // Ensure this exists
                .setContentTitle("Homework Due")
                .setContentText(description)
                .setAutoCancel(true)
                .build();

        int notificationId = (int) System.currentTimeMillis();
        notificationManager.notify(notificationId, notification);
    }
}


