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

    public void setAlarm(Activity activity, Context context, int hour, int min, int id) {
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
        //设置定时任务
        Intent intent = new Intent(activity, AlarmReceiver.class);
        intent.putExtra("ID", db.findIDbyTime(hour, min));
        intent.setAction("xyz.viseator.Alarm");
        PendingIntent sender = PendingIntent.getBroadcast(context, getID(hour, min), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        //判断闹钟是否开启
        if (db.getIsOn(id)) {
            //如果设置的时间早于当前时间，将时间推迟一天
            if (totalMinutes >= totalMinutesNow) {
                Date date = timeToSet.getTime();
                Log.d("wudi", date.toString());
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeToSet.getTimeInMillis(), sender);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeToSet.getTimeInMillis() + ONE_DAY, sender);
            }
        }

    }

    public void cancelAlarm(Activity activity, Context context, int hour, int min) {
        Intent intent = new Intent(activity, AlarmReceiver.class);
        intent.setAction("xyz.viseator.Alarm");
        PendingIntent sender = PendingIntent.getBroadcast(context, getID(hour, min), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        Log.d("wudi", "canceled");
    }

    //根据时间找到id
    private int getID(int hour, int min) {
        return hour * 60 + min;
    }
}
