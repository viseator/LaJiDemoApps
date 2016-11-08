package xyz.viseator.alarm;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by viseator on 2016/11/8.
 */

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar(MainActivity.this);
        setContentView(R.layout.main_activity_layout);
        initViews();
    }
}
