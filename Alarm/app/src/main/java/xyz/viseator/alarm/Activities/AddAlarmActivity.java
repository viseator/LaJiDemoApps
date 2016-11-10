package xyz.viseator.alarm.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TimePicker;

import xyz.viseator.alarm.Alarm.SetAlarm;
import xyz.viseator.alarm.BaseActivity;
import xyz.viseator.alarm.DataBase.DataBaseHelper;
import xyz.viseator.alarm.DataBase.DataBaseManager;
import xyz.viseator.alarm.R;

/**
 * Created by viseator on 2016/11/8.
 */

public class AddAlarmActivity extends BaseActivity {

    private TimePicker timePicker;
    private FloatingActionButton floatingActionButton;
    private DataBaseManager dataBaseManager;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm_layout);
        dataBaseManager = new DataBaseManager(this);
        initViews();
        setListeners();

    }


    private void initViews() {
        timePicker = (TimePicker) findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        toolbar = (Toolbar) findViewById(R.id.add_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace);
        toolbar.setTitle(R.string.add_alarm);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.add_finish_button);
    }

    private void setListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id;
                id = dataBaseManager.createData(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                new SetAlarm().setAlarm(AddAlarmActivity.this, getApplicationContext(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(),id);
                finish();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
