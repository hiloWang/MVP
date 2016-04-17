package com.user.hilo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.user.hilo.R;
import com.user.hilo.adapter.SlideshowAdapter;
import com.user.hilo.adapter.config.BorderDividerItemDecration;
import com.user.hilo.core.BaseDrawerLayoutActivity;
import com.user.hilo.core.BaseRecyclerViewHolder;
import com.user.hilo.utils.ToastUtils;
import com.user.hilo.utils.UIUtils;
import com.user.hilo.widget.FeedContextMenuManager;
import com.user.hilo.widget.pulltorefresh.PullRefreshLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/16.
 */
public class SlideshowActivity extends BaseDrawerLayoutActivity implements BaseRecyclerViewHolder.OnItemClickListener, BaseRecyclerViewHolder.OnItemLongClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    PullRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    private SlideshowAdapter adapter;
    private int lastVisibleItem;

    private BorderDividerItemDecration dataDecration;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean loadingMoreData;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SlideshowActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_slideshow;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mActionBarHelper.setTitle(getString(R.string.slideshow_toolbar_title));
        initAnimation();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        dataDecration = new BorderDividerItemDecration(
                getResources().getDimensionPixelOffset(R.dimen.data_border_divider_height),
                getResources().getDimensionPixelOffset(R.dimen.data_border_padding_infra_spans)
        );
        mRecyclerView.addItemDecoration(dataDecration);
        mLinearLayoutManager = /*(LinearLayoutManager) mRecyclerView.getLayoutManager();*/ new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    protected void initListeners() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(() -> {
                requestData(false);
            });
            mSwipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        }

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (loadingMoreData) return;
                // SCROLL_STATE_IDLE 滑翔状态
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    loadingMoreData = true;
                    // 当滚动到最后一条时的逻辑处理
                    mProgressBar.setVisibility(View.VISIBLE);
                    // TODO:可能会引起内容泄漏，此段代码仅供测试，实战中请用软引用SoftRefrence或者弱引用WeakRefrence;
                    new Handler().postDelayed(() -> {
                        loadingMoreData = false;
                        mProgressBar.setVisibility(View.INVISIBLE);
                        requestData(true);
                    }, 1000);
                } else if (mLinearLayoutManager.findFirstVisibleItemPosition() == 0) {

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // 弹出的ContextMenu跟随之前的窗体滚动
                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
                if (recyclerView != null && recyclerView.getChildCount() > 0)
                    lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    protected void initData() {
        if (adapter == null) {
            adapter = new SlideshowAdapter(this);
            mRecyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
            adapter.setOnItemLongClickListener(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        setRefreshing(false);
    }

    @Override
    protected void onResume() {
        setMenuChecked();
        super.onResume();
        requestData(false);
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
        return item -> SlideshowActivity.this.menuItemChecked(item.getItemId());
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

    private void requestData(boolean loadingMoreData) {
        setRefreshing(false);
        if (adapter != null) {
            List dataLists = Arrays.asList(new String("AA"),
                    new String("BB"),
                    new String("CC"),
                    new String("DD"),
                    new String("EE"),
                    new String("FF"),
                    new String("GG"),
                    new String("HH"),
                    new String("II"),
                    new String("JJ"),
                    new String("KK"));

            if (loadingMoreData) {
                int smoothScrollToPosition = adapter.getItemCount();
                adapter.addAll(dataLists);
                mRecyclerView.smoothScrollToPosition(smoothScrollToPosition);
                adapter.notifyItemChanged(adapter.getItemCount());
            } else {
                adapter.setList(dataLists);
                adapter.notifyDataSetChanged();
            }
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

    @Override
    public void onItemClick(View convertView, int position) {

    }

    @Override
    public boolean onItemLongClick(View convertView, int position) {
        return false;
    }

    public void setRefreshing(boolean refreshing) {
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(refreshing);
    }
}
