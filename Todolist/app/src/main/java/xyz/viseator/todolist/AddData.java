package xyz.viseator.todolist;

import android.app.Activity;
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
import android.widget.Switch;
import android.widget.TimePicker;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddData extends AppCompatActivity implements OnDateSetListener {
    private Toolbar toolbar;
    private EditText textTitle;
    private EditText textContent;
    private FloatingActionButton floatingActionButton;
    private PopupMenu popupMenu;
    private Button button;
    private int primer = 2;
    private TimePickerDialog timePickerDialog;
    private long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
    private String endTime;
    private Button buttonSetDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        setStatusBar(AddData.this);

        button = (Button) findViewById(R.id.setprimer);
        toolbar = (Toolbar) findViewById(R.id.addToolbar);
        textTitle = (EditText) findViewById(R.id.addTitle);
        textContent = (EditText) findViewById(R.id.addContent);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.doneButton);
        buttonSetDate = (Button) findViewById(R.id.setdate);

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
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(18)
                .build();

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button.setText("设置优先级");

        buttonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show(getSupportFragmentManager(), "all");
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

                OperateData db = new OperateData(AddData.this);
                db.setData("'"+title+"'","'"+content+"'","'"+creTime+"'","'"+endTime+"'",primer,"'"+done+"'");
                finish();
            }
        });

        popupMenu = new PopupMenu(this, button);
        popupMenu.inflate(R.menu.primer_menu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
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


    private void setStatusBar(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd HH:mm");
        endTime = format.format(millseconds);
        buttonSetDate.setText(endTime);
    }
}
