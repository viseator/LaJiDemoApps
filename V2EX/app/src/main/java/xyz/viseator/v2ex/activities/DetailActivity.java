package xyz.viseator.v2ex.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import xyz.viseator.v2ex.BaseActivity;
import xyz.viseator.v2ex.R;
import xyz.viseator.v2ex.ui.DetailRecyclerViewAdapter;

/**
 * Created by viseator on 2016/11/19.
 */

public class DetailActivity extends BaseActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity_layout);
        recyclerView = (RecyclerView) findViewById(R.id.detail_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DetailRecyclerViewAdapter(this));
    }
}
