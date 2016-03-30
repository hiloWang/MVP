package com.user.hilo.core;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.user.hilo.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/16.
 */
public class BaseToolbarActivity extends AppCompatActivity {

    private ToolbarHelper toolBarHelper;
    protected Toolbar toolbar;
    protected TextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        toolBarHelper = new ToolbarHelper(this, layoutResID);
        toolbar = toolBarHelper.getToolbar();
        toolbarTitle = toolBarHelper.getToolbarTitle();
        setContentView(toolBarHelper.getContentView());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.activity_swipeback_ac_right_out);
            }
        });
    }
}
