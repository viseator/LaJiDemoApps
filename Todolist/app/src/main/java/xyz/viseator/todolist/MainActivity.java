package xyz.viseator.todolist;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private LinearLayoutManager layoutManager;
    private OperateData db;
    private ItemTouchHelper itemtouchheler;
    private ItemTouchHelper.Callback callback;

    private FloatingActionButton floatingActionButton;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new OperateData(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(this);
        adapter = new MyAdapter(db,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Toast.makeText(MainActivity.this, "Hello!", Toast.LENGTH_SHORT).show();
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


        callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                db.remove(position);
                adapter.notifyItemRemoved(position);
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
}

