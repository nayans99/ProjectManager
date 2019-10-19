package com.example.android.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;


class AlarmReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        //  Toast.makeText(context, "AlarmReceiver class ..", Toast.LENGTH_SHORT).show();
        String topic, employee,cond,time,desc,name;
        topic = intent.getStringExtra("Topic");
        employee = intent.getStringExtra("Employee");
        time= intent.getStringExtra("time");


        NotificationHelper notificationHelper = new NotificationHelper(context, topic,time,employee);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());

    }
}

