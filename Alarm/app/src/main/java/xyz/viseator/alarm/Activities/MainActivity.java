package xyz.viseator.alarm.Activities;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import xyz.viseator.alarm.BaseActivity;
import xyz.viseator.alarm.DataBase.DataBaseManager;
import xyz.viseator.alarm.R;
import xyz.viseator.alarm.UI.RecyclerViewAdapter;

/**
 * Created by viseator on 2016/11/8.
 */

public class MainActivity extends BaseActivity {
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private DataBaseManager db;
    private ItemTouchHelper itemTouchHelper;
    private TextView timeNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar(MainActivity.this);
        setContentView(R.layout.main_activity_layout);
        db = new DataBaseManager(this);
        initViews();
        setListeners();
        setItemSwipeAction();//滑动删除
        new ShowTimeThread().start();//当前时间显示进程

    }


    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.all_toolbar);
        timeNow = (TextView) findViewById(R.id.time_now_text);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.main_add_button);
        recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        recyclerViewAdapter = new RecyclerViewAdapter(db, this, MainActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerViewAdapter.notifyDataSetChanged();
    }

    //增加闹钟
    private void setListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddAlarmActivity.class);
                startActivity(intent);

            }
        });
    }

    //滑动删除
    private void setItemSwipeAction() {
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                db.removeData(position);
                recyclerViewAdapter.notifyItemRemoved(position);
            }
            //透明动画
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    //进程
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            timeNow.setText(getTime("HH:mm"));
        }
    };
    //显示当前时间
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

    //获取当前时间
    private String getTime(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
