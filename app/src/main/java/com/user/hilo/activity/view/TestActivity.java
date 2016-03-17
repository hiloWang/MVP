package com.user.hilo.activity.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.user.hilo.R;
import com.user.hilo.activity.base.BaseToolbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/16.
 */
public class TestActivity extends BaseToolbarActivity {

    @Bind(R.id.listView)
    ListView mListView;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        toolbarTitle.setText("测试Toolbar标题");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.activity_swipeback_ac_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
