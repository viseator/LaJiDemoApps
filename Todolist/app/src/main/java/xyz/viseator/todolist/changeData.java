package xyz.viseator.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChangeData extends AppCompatActivity implements OnDateSetListener{
    private Toolbar toolbar;
    private EditText textTitle;
    private EditText textContent;
    private FloatingActionButton floatingActionButton;
    private Intent intent;
    private OperateData db;
    private Button button;
    private PopupMenu popupMenu;
    private int pos;
    private int primer;
    private String endTime;
    private Button buttonSetDate;
    TimePickerDialog timePickerDialog;
    private long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        setStatusBar(ChangeData.this);

        toolbar = (Toolbar) findViewById(R.id.addToolbar);
        textTitle = (EditText) findViewById(R.id.addTitle);
        textContent = (EditText) findViewById(R.id.addContent);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.doneButton);
        button = (Button) findViewById(R.id.setprimer);
        buttonSetDate = (Button) findViewById(R.id.setdate);

        intent = getIntent();
        pos = intent.getIntExtra("pos",0);
        try {
            init();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        endTime = db.getEndTime(pos);

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
                SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
                String creTime = format.format(new java.util.Date());

                OperateData db = new OperateData(ChangeData.this);
                db.updateData(pos,"'"+title+"'","'"+content+"'","'"+endTime+"'",primer);
                finish();
            }
        });



        try {
            timePickerDialog = new TimePickerDialog.Builder()
                    .setCallBack(this)
                    .setCancelStringId("取消")
                    .setSureStringId("确定")
                    .setTitleStringId("截止时间")
                    .setYearText("年")
                    .setMonthText("月")
                    .setDayText("日")
                    .setHourText("时")
                    .setMinuteText("分")
                    .setCyclic(false)
                    .setMinMillseconds(System.currentTimeMillis())
                    .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                    .setCurrentMillseconds(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").parse(db.getEndTime(pos)).getTime())
                    .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                    .setType(Type.ALL)
                    .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                    .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                    .setWheelItemTextSize(18)
                    .build();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        popupMenu = new PopupMenu(this, button);
        popupMenu.inflate(R.menu.primer_menu);
        primer = db.getPrimerNum(pos);
        button.setText(db.getPrimer(pos));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
        buttonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show(getSupportFragmentManager(), "all");
            }
        });
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.holy:
                        primer = 4;
                        button.setText("Holy");
                        break;
                    case R.id.important:
                        primer = 3;
                        button.setText("Important");
                        break;
                    case R.id.normal:
                        primer = 2;
                        button.setText("Normal");
                        break;
                    case R.id.shit:
                        primer = 1;
                        button.setText("Shit");
                        break;
                }
                return true;
            }
        });


    }




    private void init() throws ParseException {
        db = new OperateData(ChangeData.this);
        textTitle.setText(db.getTitle(pos));
        textContent.setText(db.getContext(pos));
        textTitle.setSelection(db.getTitle(pos).length());
        buttonSetDate.setText(db.getEndTime(pos));
    }

    private void setStatusBar(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        endTime = format.format(millseconds);
        buttonSetDate.setText(endTime);
    }
}
