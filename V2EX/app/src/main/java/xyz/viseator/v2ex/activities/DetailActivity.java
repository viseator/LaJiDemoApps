package xyz.viseator.v2ex.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.viseator.v2ex.BaseActivity;
import xyz.viseator.v2ex.R;
import xyz.viseator.v2ex.http.GetAvatarTask;
import xyz.viseator.v2ex.http.GetHTMLMainContentTask;
import xyz.viseator.v2ex.ui.DetailRecyclerViewAdapter;

/**
 * Created by viseator on 2016/11/19.
 */

public class DetailActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private ImageView avatarImageView;
    private TextView usernameTextView;
    private TextView timeTextView;
    private TextView titleTextView;
    private Toolbar toolbar;
    private static final String TAG = "wudi DetailActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity_layout);
        initView();
    }

    private void initView() {
        avatarImageView = (ImageView) findViewById(R.id.detail_avatar);
        usernameTextView = (TextView) findViewById(R.id.detail_creator_name);
        timeTextView = (TextView) findViewById(R.id.detail_time);
        titleTextView = (TextView) findViewById(R.id.detail_title_textview);
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        new GetAvatarTask(avatarImageView).execute(getIntent().getStringExtra("avatarURL"));
        usernameTextView.setText(getIntent().getStringExtra("name"));
        titleTextView.setText(getIntent().getStringExtra("title"));
//        timeTextView.setText(getIntent().getStringExtra("time"));
        recyclerView = (RecyclerView) findViewById(R.id.detail_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG, getIntent().getStringExtra("URL"));
        new GetHTMLMainContentTask(recyclerView, this).execute(getIntent().getStringExtra("URL"));

    }
}
