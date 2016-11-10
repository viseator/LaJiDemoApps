package xyz.viseator.alarm.Alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import xyz.viseator.alarm.DataBase.DataBaseManager;

/**
 * Created by viseator on 2016/11/9.
 */

public class SetAlarm {
    private DataBaseManager db;
    private static long ONE_DAY = 24 * 60 * 60 * 1000;
    private static int allAlarm = 0;

    public void setAlarm(Activity activity, Context context, int hour, int min) {
        db = new DataBaseManager(context);
        int totalMinutes = hour * 60 + min;
        //现在时间
        Calendar timeNow = Calendar.getInstance();
        timeNow.setTime(new Date());
        int totalMinutesNow = timeNow.get(Calendar.HOUR_OF_DAY) * 60 + timeNow.get(Calendar.MINUTE);
        //发送广播的时间
        Calendar timeToSet = Calendar.getInstance();
        timeToSet.setTime(new Date());
        timeToSet.set(Calendar.HOUR_OF_DAY, hour);
        timeToSet.set(Calendar.MINUTE, min);
        timeToSet.set(Calendar.SECOND, 0);

        Intent intent = new Intent(activity, AlarmReceiver.class);
        intent.setAction("xyz.viseator.Alarm");
        PendingIntent sender = PendingIntent.getBroadcast(context, allAlarm++, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);

        if (totalMinutes >= totalMinutesNow) {
            Date date = timeToSet.getTime();
            Log.d("wudi", date.toString());
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeToSet.getTimeInMillis(), sender);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeToSet.getTimeInMillis() + ONE_DAY, sender);
        }
    }
}
