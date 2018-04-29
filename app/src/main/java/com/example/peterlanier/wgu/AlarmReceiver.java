package com.example.peterlanier.wgu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private String message;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Don't panik but your time is up!!!!.",
                Toast.LENGTH_LONG).show();

        String CHANNEL_ID = "Courses";
        int notificationId = 0;

        Bundle b = intent.getExtras();
        if (b != null) {
            message = b.getString("THE_MESSAGE");

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setContentTitle("WGU Notification")
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, mBuilder.build());
            System.out.println("Notification Built?");

        }

    }

}