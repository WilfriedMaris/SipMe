package com.test2.wilfriedmaris.sipme2.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.test2.wilfriedmaris.sipme2.R;
import com.test2.wilfriedmaris.sipme2.activities.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager
                = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                context.getString(R.string.channel_id))
                .setSmallIcon(R.drawable.cocktail_icon)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .setVibrate(new long[] { 1000, 1000 })
                .setAutoCancel(true);

        notificationManager.notify(0, builder.build());
    }
}
