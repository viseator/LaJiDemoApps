package xyz.viseator.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private LinearLayoutManager layoutManager;
    private OperateData db;
    private ItemTouchHelper itemtouchheler;
    private ItemTouchHelper.Callback callback;
    private Toolbar toolbar;

    private FloatingActionButton floatingActionButton;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBar(MainActivity.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.toolbarmenu);

        db = new OperateData(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(this);
        adapter = new MyAdapter(db,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view,int pos) {
                showMyDialog(pos);

            }
        });


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuId = item.getItemId();
                switch (menuId) {
                    case R.id.sortbytime:
                        try {
                            db.sortDataByEndTime(adapter);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.sortbyprimer:
                        try {
                            db.sortDataByPrimer(adapter);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                return true;
            }
        });


        floatingActionButton = (FloatingActionButton) findViewById(R.id.addButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddData.class);
                startActivity(intent);
            }
        });


        callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                db.swapId(from, to);
                adapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                db.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder.itemView.setAlpha((float) 0.5);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setAlpha((float) 1.0);
            }
        };

        itemtouchheler = new ItemTouchHelper(callback);
        itemtouchheler.attachToRecyclerView(recyclerView);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.closedb();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    private void setStatusBar(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
    }

    private void showMyDialog(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(db.getTitle(pos));
        View dialog = getLayoutInflater().inflate(R.layout.message, (ViewGroup) findViewById(R.id.showMessage));
        TextView message = (TextView) dialog.findViewById(R.id.message);
        TextView messageEndtime = (TextView) dialog.findViewById(R.id.message_endTime);
        TextView messageCretime = (TextView) dialog.findViewById(R.id.message_creTime);
        TextView messagePrimer = (TextView) dialog.findViewById(R.id.message_primer);

        message.setText(db.getContext(pos));
        messageEndtime.setText(db.getEndTime(pos));
        messageCretime.setText(db.getCreTime(pos));
        messagePrimer.setText(db.getPrimer(pos));
        builder.setView(dialog);
        builder.setPositiveButton("确定", null);
        builder.setNeutralButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, ChangeData.class);
                intent.putExtra("pos", pos);
                startActivity(intent);
            }
        });

        switch (db.getPrimerNum(pos)) {
            case 1:
                messagePrimer.setTextColor(Color.parseColor("#A9A9A9"));
                break;
            case 2:
                messagePrimer.setTextColor(Color.BLACK);
                break;
            case 3:
                messagePrimer.setTextColor(Color.parseColor("#FFD700"));
                break;
            case 4:
                messagePrimer.setTextColor(Color.parseColor("#FF0000"));
                break;
        }
        builder.show();

    }
}
