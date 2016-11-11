package xyz.viseator.alarm.Alarm;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.WindowManager;

import xyz.viseator.alarm.Activities.ShowAlarmActivity;
import xyz.viseator.alarm.DataBase.DataBaseHelper;
import xyz.viseator.alarm.DataBase.DataBaseManager;
import xyz.viseator.alarm.R;
import xyz.viseator.alarm.Service.AlarmRingService;

/**
 * Created by viseator on 2016/11/9.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private Context context;
    private NotificationCompat.Builder builder;
    private NotificationManager manager;
    private int id;
    private DataBaseManager db;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("wudi", "GOT!");
        id = intent.getIntExtra("ID", -1);
        Log.d("wudi id", String.valueOf(id));
        this.context = context;
        db = new DataBaseManager(context);
        showNotification();
        startService();
        wakePhone();

    }

    private void wakePhone() {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "wudi");
        mWakelock.acquire();//唤醒屏幕
    }


    private void showNotification() {
        Intent intent1 = new Intent(context, ShowAlarmActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);

        builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_action_alarm);
        builder.setContentTitle(context.getResources().getString(R.string.notification_title)).setContentText(context.getResources().getString(R.string.notification_content))
                .setContentIntent(pi).setVisibility(Notification.VISIBILITY_PUBLIC);

        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

    private void startService() {
        Intent service = new Intent(context, AlarmRingService.class);
        service.putExtra("Path", db.getPath(id));
        context.startService(service);
    }
}
