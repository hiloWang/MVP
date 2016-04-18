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
import com.user.hilo.adapter.SlidelistAdapter;
import com.user.hilo.adapter.config.BorderDividerItemDecration;
import com.user.hilo.core.BaseDrawerLayoutActivity;
import com.user.hilo.core.BaseRecyclerViewHolder;
import com.user.hilo.presenter.SlidelistPresenter;
import com.user.hilo.utils.LogUtils;
import com.user.hilo.utils.ToastUtils;
import com.user.hilo.utils.UIUtils;
import com.user.hilo.view.i.SlidelistView;
import com.user.hilo.widget.pulltorefresh.PullRefreshLayout;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/16.
 */
public class SlideListActivity extends BaseDrawerLayoutActivity implements SlidelistView, BaseRecyclerViewHolder.OnItemClickListener, BaseRecyclerViewHolder.OnItemLongClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    PullRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    private SlidelistAdapter adapter;

    private BorderDividerItemDecration dataDecration;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean loadingMoreData;
    private SlidelistPresenter presenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SlideListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_slidelist;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mActionBarHelper.setTitle(getString(R.string.slidelist_toolbar_title));
        initAnimation();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        dataDecration = new BorderDividerItemDecration(
                getResources().getDimensionPixelOffset(R.dimen.data_border_divider_height),
                getResources().getDimensionPixelOffset(R.dimen.data_border_padding_infra_spans)
        );
        mRecyclerView.addItemDecoration(dataDecration);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    protected void initListeners() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(() -> {
                presenter.requestData(false);
            });
            mSwipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        }

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean moveToDown = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (loadingMoreData) return;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (moveToDown && manager.findLastCompletelyVisibleItemPosition() == (manager.getItemCount() - 1)) {
                            loadingMoreData = true;
                            // 当滚动到最后一条时的逻辑处理
                            mProgressBar.setVisibility(View.VISIBLE);
                            // TODO:可能会引起内容泄漏，此段代码仅供测试，实战中请用软引用SoftRefrence或者弱引用WeakRefrence;
                            new Handler().postDelayed(() -> {
                                loadingMoreData = false;
                                mProgressBar.setVisibility(View.INVISIBLE);
                                presenter.requestData(true);
                            }, 1000);
                        } else if (mLinearLayoutManager.findFirstVisibleItemPosition() == 0) {

                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    // 正在向下滑动
                    this.moveToDown = true;
                } else {
                    // 停止滑动或者向上滑动
                    this.moveToDown = false;
                }
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new SlidelistPresenter();
        presenter.attachView(this);
        if (adapter == null) {
            adapter = new SlidelistAdapter(this);
            mRecyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
            adapter.setOnItemLongClickListener(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        setRefreshing(false);
        hideProgress();
    }

    @Override
    protected void onResume() {
        setMenuChecked();
        super.onResume();
        presenter.requestData(false);
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
        return item -> SlideListActivity.this.menuItemChecked(item.getItemId());
    }

    @Override
    protected int[] getMenuItemIds() {
        return new int[]{R.id.nav_home, R.id.nav_slidelist, R.id.nav_share, R.id.nav_send};
    }

    @Override
    protected void onMenuItemOnClick(MenuItem now) {
        switch (now.getItemId()) {
            case R.id.nav_home:
                setOnDrawerClosedCallback(() -> MainActivity.startActivity(this));
                break;
            case R.id.nav_slidelist:

                break;
            case R.id.nav_share:
                ToastUtils.show(this, "nav_share" + now.getItemId(), 1);
                break;
            case R.id.nav_send:
                ToastUtils.show(this, "nav_send" + now.getItemId(), 1);
                break;
        }
    }

    private void feedAdapter(boolean loadingMoreData, List<? extends Object> items) {
        setRefreshing(false);
        if (adapter != null) {
            if (loadingMoreData) {
                int smoothScrollToPosition = adapter.getItemCount();
                adapter.addAll(items);
                mRecyclerView.scrollToPosition(smoothScrollToPosition);
                adapter.notifyItemChanged(adapter.getItemCount());
            } else {
                adapter.setList(items);
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
        presenter.detachView();
        super.onDestroy();
        System.gc();
    }

    private void setMenuChecked() {
        mNavigationView.getMenu().findItem(R.id.nav_slidelist).setChecked(true);
    }

    @Override
    public void onItemClick(View convertView, int position) {
        ToastUtils.showCenter(this, position + "");
    }

    @Override
    public void onItemLongClick(View convertView, int position) {

    }

    public void setRefreshing(boolean refreshing) {
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setItems(boolean loadingMoreData, List<? extends Object> items) {
        feedAdapter(loadingMoreData, items);
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.showCenter(this, message);
    }

    @Override
    public void onFailure(Throwable e) {
        LogUtils.E(e + "");
    }
}
