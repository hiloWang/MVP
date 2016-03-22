package com.user.hilo.activity.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;

import com.user.hilo.R;
import com.user.hilo.activity.base.BaseToolbarActivity;
import com.user.hilo.utils.UIUtils;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/16.
 */
public class TestActivity extends BaseToolbarActivity {

    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        initViews();
        initAnimation();
    }

    private void initAnimation() {
        int actionbarSize = UIUtils.dpToPx(56, getResources());
        toolbar.setTranslationY(-actionbarSize);
        toolbarTitle.setTranslationY(-actionbarSize);
        toolbar.animate()
                .translationY(0)
                .setDuration(400)
                .setStartDelay(400);
        toolbarTitle.animate()
                .translationY(0)
                .setDuration(400)
                .setStartDelay(500);
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
