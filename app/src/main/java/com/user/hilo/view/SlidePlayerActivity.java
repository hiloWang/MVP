package com.user.hilo.view;

import android.content.res.Configuration;
import android.os.Bundle;

import com.user.hilo.R;
import com.user.hilo.core.BaseToolbarActivity;

/**
 * Created by Administrator on 2016/4/18.
 */
public class SlidePlayerActivity extends BaseToolbarActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_slide_plpayer;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mActionBarHelper.setTitle(getString(R.string.sldeplayer_toolbar_title));
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 1、不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次
     * 2、设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次
     * 3、设置Activity的android:configChanges="orientation|keyboardHidden"时，切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法
     * 这里使用的是3
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT: // 竖屏
                break;
            case Configuration.ORIENTATION_LANDSCAPE: // 横屏
                break;
        }
        super.onConfigurationChanged(newConfig);

    }
}
