package xyz.viseator.alarm.Activities;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import xyz.viseator.alarm.BaseActivity;
import xyz.viseator.alarm.R;
import xyz.viseator.alarm.Service.AlarmRingService;

/**
 * Created by viseator on 2016/11/10.
 */

public class ShowAlarmActivity extends BaseActivity {

    private TextView showTimeTextView;
    private Button button;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getBaseContext();
        setContentView(R.layout.show_alarm_layout);
        initView();
        new ShowTimeThread().start();

    }

    private void initView() {
        showTimeTextView = (TextView) findViewById(R.id.show_alarm_time_now_text);
        button = (Button) findViewById(R.id.stop_ring);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent service = new Intent(context, AlarmRingService.class);
                context.stopService(service);
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(0);
                finish();
            }
        });
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showTimeTextView.setText(getTime("HH:mm"));
        }
    };

    public class ShowTimeThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                handler.sendMessage(msg);
            }
        }
    }

    private String getTime(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }



}
