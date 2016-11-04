package xyz.viseator.todolist;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChangeData extends AppCompatActivity {
    private TimePicker timePicker;
    private DatePicker datePicker;
    private Toolbar toolbar;
    private EditText textTitle;
    private EditText textContent;
    private FloatingActionButton floatingActionButton;
    private Intent intent;
    private OperateData db;
    private int month;
    private int day;
    private int hour;
    private int min;
    private int pos;

    private void init() throws ParseException {
        db = new OperateData(ChangeData.this);
        textTitle.setText(db.getTitle(pos));
        textContent.setText(db.getContext(pos));
        textTitle.setSelection(db.getTitle(pos).length());

        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyyMMdd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(simpleDateFormat.parse(db.getEndTime(pos)).getTime());
        datePicker.init(calendar.get(calendar.YEAR),calendar.get(calendar.MONTH),calendar.get(calendar.DAY_OF_MONTH),null);
            timePicker.setCurrentHour(calendar.get(calendar.HOUR));
            timePicker.setCurrentMinute(calendar.get(calendar.MINUTE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        toolbar = (Toolbar) findViewById(R.id.addToolbar);
        textTitle = (EditText) findViewById(R.id.addTitle);
        textContent = (EditText) findViewById(R.id.addContent);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.doneButton);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        datePicker = (DatePicker) findViewById(R.id.datepicker);

        intent = getIntent();
        pos = intent.getIntExtra("pos",0);
        try {
            init();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        timePicker.setIs24HourView(true);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = textTitle.getText().toString();
                String content = textContent.getText().toString();
                String done = "0";
                SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd HH:mm");
                String creTime = format.format(new java.util.Date());

                Calendar calendar = Calendar.getInstance();
                calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),timePicker.getCurrentHour(),timePicker.getCurrentMinute());
                SimpleDateFormat format1=new SimpleDateFormat("yyyyMMdd HH:mm");
                String endTime = format1.format(calendar.getTime());
                OperateData db = new OperateData(ChangeData.this);
                db.updateData(pos,"'"+title+"'","'"+content+"'","'"+endTime+"'");
                finish();
            }
        });
    }


}
