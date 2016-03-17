package com.user.hilo.activity.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.user.hilo.R;

/**
 * Created by Administrator on 2016/3/16.
 */
class ToolbarHelper {

    private Context mContext;
    private FrameLayout baseContentView;
    private View constomView;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private LayoutInflater mLayoutInflater;

    private static int[] ATTRS = {
        R.attr.windowActionBarOverlay,
        R.attr.actionBarSize
    };

    ToolbarHelper(Context context, int layoutResID) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

        initContentView();
        initConstomView(layoutResID);
        initToolbar();
    }

    private void initContentView() {
        baseContentView = new FrameLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        baseContentView.setLayoutParams(params);
    }


    private void initConstomView(int layoutResID) {
        constomView = mLayoutInflater.inflate(layoutResID, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray ta = mContext.getTheme().obtainStyledAttributes(ATTRS);
        // 获取toolbar悬浮标志
        boolean overly = ta.getBoolean(0, false);
        // 获取toolbar高度
        int toolbarHeight = (int) ta.getDimension(1, mContext.getResources().getDimension(R.dimen.status_bar_height));
        ta.recycle();
        params.topMargin = overly ? 0 : toolbarHeight;
        baseContentView.addView(constomView, params);
    }

    private void initToolbar() {
        View toolbarView = mLayoutInflater.inflate(R.layout.toolbar, baseContentView);
        toolbar = (Toolbar) toolbarView.findViewById(R.id.toolbar);
        toolbarTitle = (TextView) toolbarView.findViewById(R.id.toolbarTitle);
        toolbar.setTitle("");
    }

    Toolbar getToolbar() {
        return toolbar;
    }

    TextView getToolbarTitle() {
        return toolbarTitle;
    }

    FrameLayout getContentView() {
        return baseContentView;
    }
}
