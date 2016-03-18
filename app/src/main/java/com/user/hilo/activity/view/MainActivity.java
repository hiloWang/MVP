package com.user.hilo.activity.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.user.hilo.R;
import com.user.hilo.activity.presenter.MainPresenterIml;
import com.user.hilo.activity.presenter.i.MainPresenter;
import com.user.hilo.activity.view.i.MainView;
import com.user.hilo.adapter.MainAdapterItemAnimator;
import com.user.hilo.adapter.MainRecyclerAdapter;
import com.user.hilo.entitys.MainEntity;
import com.user.hilo.utils.AnimUtils;
import com.user.hilo.utils.UIUtils;
import com.user.hilo.view.FeedContextMenu;
import com.user.hilo.view.FeedContextMenuManager;
import com.user.hilo.view.pulltorefresh.PullRefreshLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class MainActivity extends AppCompatActivity
        implements MainView, NavigationView.OnNavigationItemSelectedListener, MainRecyclerAdapter.OnFeedItemClickListener, FeedContextMenu.OnFeedContextMenuClickListener {

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;
    private static final int DEL_RECYCLER_ADAPTER_ITEM_POSITION = 0;

    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    PullRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.del)
    TextView mDel;
    @Bind(R.id.add)
    TextView mAdd;
    @Bind(R.id.coordiNatorContent)
    CoordinatorLayout mCoordiNatorContent;

    private Context context;
    private MainPresenter presenter;
    private MainRecyclerAdapter adapter;
    private Animator animator;
    private int lastVisibleItem;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean pendingIntroAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 如果配置了styles里面自定义activity过度平移动画 则需要打开注释
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;

        initViews();
        initEvents();

        presenter = new MainPresenterIml(this);

        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    /**
     * 在跳转时,如果当前activity没有finish,那么释放资源要在onPause里,反之则要在onStop里
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(false);
        presenter.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.Destroy();
        super.onDestroy();
        System.gc();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_swipeback_ac_right_in, R.anim.activity_swipeback_ac_right_remain);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_settings);

        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent com.user.hilo.activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            //noinspection SimplifiableIfStatement
            case R.id.action_settings:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mSwipeRefreshLayout = (PullRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefreshing() {
                presenter.requestDataRefresh();
            }
        });
        mSwipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
    }

    private void initEvents() {
        mNavView.setNavigationItemSelectedListener(this);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    // 当滚动到最后一条时的逻辑处理
                    Toast.makeText(context, "没有数据可加载", Toast.LENGTH_SHORT).show();
                } else if (mLinearLayoutManager.findFirstVisibleItemPosition() == 0) {

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView != null && recyclerView.getChildCount() > 0)
                    lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItems(List<? extends Object> items) {
        if (adapter == null) {
            mLinearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            adapter = new MainRecyclerAdapter(this);
            adapter.setOnFeedItemClickListener(this);
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
            // false: 即使是已缓存的数据 也会有动画, 默认是true
            // scaleAdapter.setFirstOnly(false);
            // 带震动的动画
            // 因为MainAdapterItemAnimation继承了ScaleInAnimationAdapter动画,所以不再需要设置他就会有scaleAnimation动画效果
            // ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
            // scaleAdapter.setInterpolator(new OvershootInterpolator());
            mRecyclerView.setItemAnimator(new MainAdapterItemAnimator());
            mRecyclerView.setAdapter(alphaAdapter);
            adapter.updateItems((List<MainEntity>) items, true);
        } else {
            adapter.updateItems((List<MainEntity>) items, false);
        }
    }

    @Override
    public void showMessage(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addItem(Object data, int position) {
        adapter.addItem((MainEntity) data, position);
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void requestDataRefreshFinish(List<? extends Object> items) {
        mSwipeRefreshLayout.setRefreshing(false);
        adapter.updateItems((List<MainEntity>) items, true);
    }

    @Override
    public void onCommentsClick(View v, int position) {

    }

    @Override
    public void onMoreClick(View v, int position) {
        FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, position, this);
    }

    @Override
    public void onProfileClick(View v) {

    }

    @Override
    public void onItemClicked(View view, int position) {
        presenter.onItemClicked(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animator = AnimUtils.attrCreateCircularReveal(view, 1000);
        }
    }

    @Override
    public void onItemLongClicked(View view, int position) {
        if (position + 1 == 1) {
            Intent intent = new Intent(this, TestActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onReportClick(int position) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onSharePhotoClick(int position) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCopyShareUrlClick(int position) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCancelClick(int position) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    private void startIntroAnimation() {
        mFab.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
        int actionbarSize = UIUtils.dpToPx(56, getResources());
        mToolbar.setTranslationY(-actionbarSize);
        mAdd.setTranslationY(-actionbarSize);
        mDel.setTranslationY(-actionbarSize);

        mToolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);

        mAdd.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);

        mDel.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mFab.animate()
                                .translationY(0)
                                .setInterpolator(new OvershootInterpolator(1.f))
                                .setStartDelay(800)
                                .setDuration(ANIM_DURATION_FAB)
                                .start();

                        presenter.requestDataFirst();

                    }
                    // 非用户主动触发情况下要用start()
                }).start();
    }

    public void showLikedSnackbar() {
        Snackbar.make(mCoordiNatorContent, "Liked", Snackbar.LENGTH_SHORT).show();
    }

    @OnClick({R.id.del, R.id.add, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.del:
                adapter.delItem(DEL_RECYCLER_ADAPTER_ITEM_POSITION);
                break;
            case R.id.add:
                presenter.addItem();
                break;
            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }
    }
}
