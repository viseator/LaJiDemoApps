package xyz.viseator.alarm.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TimePicker;

import xyz.viseator.alarm.BaseActivity;
import xyz.viseator.alarm.DataBase.DataBaseManager;
import xyz.viseator.alarm.R;

/**
 * Created by viseator on 2016/11/9.
 */

public class ChangeDataActivity extends BaseActivity {

    private TimePicker timePicker;
    private FloatingActionButton floatingActionButton;
    private DataBaseManager db;
    private int position;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm_layout);
        db = new DataBaseManager(this);
        position = getIntent().getIntExtra("position", -1);
        initViews();
        setListeners();
    }

    private void initViews() {
        timePicker = (TimePicker) findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(db.getHour(position));
        timePicker.setCurrentMinute(db.getMin(position));
        floatingActionButton = (FloatingActionButton) findViewById(R.id.add_finish_button);
        toolbar = (Toolbar) findViewById(R.id.add_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace);
    }

    private void setListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateData(position, timePicker.getCurrentHour(), timePicker.getCurrentMinute());
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
