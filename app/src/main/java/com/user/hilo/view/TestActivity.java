package com.user.hilo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;

import com.user.hilo.R;
import com.user.hilo.core.BaseDrawerLayoutActivity;
import com.user.hilo.utils.ToastUtils;
import com.user.hilo.utils.UIUtils;

/**
 * Created by Administrator on 2016/3/16.
 */
public class TestActivity extends BaseDrawerLayoutActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TestActivity.class);
        context.startActivity(intent);
    }

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
    protected void onResume() {
        setMenuChecked();
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent == null || intent.getAction() == null) return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected NavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener() {
        return item -> TestActivity.this.menuItemChecked(item.getItemId());
    }

    @Override
    protected int[] getMenuItemIds() {
        return new int[]{R.id.nav_home, R.id.nav_slideshow, R.id.nav_share, R.id.nav_send};
    }

    @Override
    protected void onMenuItemOnClick(MenuItem now) {
        switch (now.getItemId()) {
            case R.id.nav_home:
                setOnDrawerClosedCallback(() -> MainActivity.startActivity(this));
                break;
            case R.id.nav_slideshow:

                break;
            case R.id.nav_share:
                ToastUtils.show(this, "nav_share" + now.getItemId(), 1);
                break;
            case R.id.nav_send:
                ToastUtils.show(this, "nav_send" + now.getItemId(), 1);
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
        overridePendingTransition(true);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    private void setMenuChecked() {
        mNavigationView.getMenu().findItem(R.id.nav_slideshow).setChecked(true);
    }
}
