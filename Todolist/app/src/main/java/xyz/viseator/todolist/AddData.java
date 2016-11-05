package xyz.viseator.todolist;

import android.app.Activity;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddData extends AppCompatActivity {
    private TimePicker timePicker;
    private DatePicker datePicker;
    private Toolbar toolbar;
    private EditText textTitle;
    private EditText textContent;
    private FloatingActionButton floatingActionButton;
    private int month;
    private int day;
    private int hour;
    private int min;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        setStatusBar(AddData.this);

        toolbar = (Toolbar) findViewById(R.id.addToolbar);
        textTitle = (EditText) findViewById(R.id.addTitle);
        textContent = (EditText) findViewById(R.id.addContent);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.doneButton);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        datePicker = (DatePicker) findViewById(R.id.datepicker);


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
                Log.d("wudi endTime",endTime);
                OperateData db = new OperateData(AddData.this);
                db.setData("'"+title+"'","'"+content+"'","'"+creTime+"'","'"+endTime+"'","'"+done+"'");
                finish();
            }
        });
    }
    private void setStatusBar(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
    }
}
