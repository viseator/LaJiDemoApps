package xyz.viseator.todolist;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;

public class AddData extends AppCompatActivity {
    private TimePicker timePicker;
    private DatePicker datePicker;
    private Toolbar toolbar;
    private EditText textTitle;
    private EditText textContent;
    private FloatingActionButton floatingActionButton;


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


        timePicker.setIs24HourView(true);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String title = textTitle.getText().toString();
                String content = textContent.getText().toString();
                String month;
                String day;
                if(datePicker.getMonth()+1 <= 9)
                    month = "0" + String.valueOf(datePicker.getMonth() + 1);
                else month = String.valueOf(datePicker.getMonth() + 1);

                if(datePicker.getDayOfMonth() <= 9)
                    day = "0" + String.valueOf(datePicker.getMonth());
                else day = String.valueOf(datePicker.getMonth());
                String endTime = String.valueOf(datePicker.getYear()) + month + day;
//                String endTime = String.valueOf(datePicker.getYear()) + month + day+" "+String.valueOf(timePicker.getHour())+":"+String.valueOf(timePicker.getMinute());
                String done = "0";
                SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd HH:mm");
                String creTime = format.format(new java.util.Date());

                OperateData db = new OperateData(AddData.this);
                db.setData("'"+title+"'","'"+content+"'","'"+creTime+"'","'"+endTime+"'","'"+done+"'");
            }
        });
    }
}
