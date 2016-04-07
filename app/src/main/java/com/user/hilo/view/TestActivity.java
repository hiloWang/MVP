package com.user.hilo.view;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.user.hilo.R;
import com.user.hilo.core.BaseDrawerLayoutActivity;
import com.user.hilo.utils.ToastUtils;
import com.user.hilo.utils.UIUtils;

/**
 * Created by Administrator on 2016/3/16.
 */
public class TestActivity extends BaseDrawerLayoutActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mActionBarHelper.setTitle("测试Toolbar标题");
        initAnimation();
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected NavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                return TestActivity.this.menuItemChecked(item.getItemId());
            }
        };
    }

    @Override
    protected int[] getMenuItemIds() {
        return new int[]{R.id.action_settings};
    }

    @Override
    protected void onMenuItemOnClick(MenuItem now) {
        switch (now.getItemId()) {
            case R.id.action_settings:
                ToastUtils.show(this, "action_settings" + now.getItemId(), 1);
                break;
        }
    }

    private void initAnimation() {
        int actionbarSize = UIUtils.dpToPx(56, getResources());
        toolbar.setTranslationY(-actionbarSize);
        toolbar.animate()
                .translationY(0)
                .setDuration(400)
                .setStartDelay(400);
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
