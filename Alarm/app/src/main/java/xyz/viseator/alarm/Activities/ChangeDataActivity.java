package xyz.viseator.alarm.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
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
    private TextView textView;
    private CardView cardView;
    private String filePath;
    private static int PICK_CONTENT_CODE = 1;


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
        toolbar.setTitle(R.string.change_alarm);
        textView = (TextView) findViewById(R.id.custom_ring_text);
        cardView = (CardView) findViewById(R.id.set_custom_ring);
        filePath = db.getPath(position);
        for (int i = filePath.length() - 1; i >= 0; i--) {
            if (filePath.charAt(i) == '/') {
                textView.setText(filePath.substring(i + 1));
                break;
            }
        }
    }

    private void setListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateData(position, timePicker.getCurrentHour(), timePicker.getCurrentMinute(), filePath);
                finish();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, PICK_CONTENT_CODE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            filePath = uri.getPath();
            for (int i = filePath.length() - 1; i >= 0; i--) {
                if (filePath.charAt(i) == '/') {
                    textView.setText(filePath.substring(i + 1));
                    break;
                }
            }
        }
    }
}
