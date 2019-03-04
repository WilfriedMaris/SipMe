package com.test2.wilfriedmaris.sipme2.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.test2.wilfriedmaris.sipme2.receivers.AlarmReceiver;

import java.util.Calendar;

public class AlarmHelper {
    private Context context;
    private PendingIntent mAlarmSender;

    public AlarmHelper(Context context) {
        this.context = context;
        mAlarmSender = PendingIntent.getBroadcast(
                context, 0, new Intent(context, AlarmReceiver.class), 0);
    }

    public void startAlarm(){
        Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.HOUR_OF_DAY, 20);
        calendar.add(Calendar.SECOND, 5);
        long firstTime = calendar.getTimeInMillis();
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, firstTime, mAlarmSender);
    }
}

