package com.user.hilo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import com.user.hilo.adapter.MainRecyclerAdapter;
import com.user.hilo.adapter.config.MainAdapterItemAnimator;
import com.user.hilo.bean.HomeBean;
import com.user.hilo.core.BaseDrawerLayoutActivity;
import com.user.hilo.presenter.MainPresenter;
import com.user.hilo.utils.AnimUtils;
import com.user.hilo.utils.ToastUtils;
import com.user.hilo.utils.UIUtils;
import com.user.hilo.view.i.MainView;
import com.user.hilo.widget.FeedContextMenu;
import com.user.hilo.widget.FeedContextMenuManager;
import com.user.hilo.widget.LoadingFeedItemView;
import com.user.hilo.widget.pulltorefresh.PullRefreshLayout;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;

public class MainActivity extends BaseDrawerLayoutActivity
        implements MainView, MainRecyclerAdapter.OnFeedItemClickListener, FeedContextMenu.OnFeedContextMenuClickListener, LoadingFeedItemView.OnLoadingFinishedListener {

    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";
    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;
    private static final int DEL_RECYCLER_ADAPTER_ITEM_POSITION = 0;

    @Bind(R.id.takePhoto)
    FloatingActionButton mTakePhoto;
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
    private LinearLayoutManager mLinearLayoutManager;
    private boolean pendingIntroAnimation;
    private boolean loadingMoreData;

    private DelayHandler delayHandler;
    private DelayRunnable delayRunnable;
    private static final int PHOTO_INTO_VIEW_DELAY = 0x110;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        context = this;
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(() -> presenter.requestData(false));
            mSwipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        }

        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        }
    }

    @Override
    protected void initListeners() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean moveToDown = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (loadingMoreData) return;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) { // SCROLL_STATE_IDLE 滑翔状态
                        if (moveToDown && manager.findLastCompletelyVisibleItemPosition() == (manager.getItemCount() - 1)) {
                            loadingMoreData = true;
                            // 当滚动到最后一条时的逻辑处理
                            mProgressBar.setVisibility(View.VISIBLE);
                            // TODO:可能会引起内容泄漏，此段代码仅供测试，实战中请用软引用SoftRefrence或者弱引用WeakRefrence;
                            new Handler().postDelayed(() -> {
                                loadingMoreData = false;
                                mProgressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(context, "没有数据可加载", Toast.LENGTH_SHORT).show();
                            }, 1000);
                        } else if (mLinearLayoutManager.findFirstVisibleItemPosition() == 0) {

                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                /*
                 * dy 表示y轴滑动方向
                 * dx 表示x轴滑动方向
                 */
                if (dy > 0) {
                    // 正在向下滑动
                    this.moveToDown = true;
                } else {
                    // 停止滑动或者向上滑动
                    this.moveToDown = false;
                }

                // 弹出的ContextMenu跟随之前的窗体滚动
                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new MainPresenter();
        presenter.attachView(this);
    }

    /**
     * action bar menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    @Override
    protected NavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener() {
        return item -> MainActivity.this.menuItemChecked(item.getItemId());
    }

    /**
     * nav menu
     *
     * @return
     */
    @Override
    protected int[] getMenuItemIds() {
        return new int[]{R.id.nav_home, R.id.nav_slidelist, R.id.nav_share, R.id.nav_send};
    }

    @Override
    protected void onMenuItemOnClick(MenuItem now) {
        switch (now.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_slidelist:
                setOnDrawerClosedCallback(() -> SlideListActivity.startActivity(this));
                break;
            case R.id.nav_share:
                ToastUtils.show(this, "nav_share" + now.getItemId(), 1);
                break;
            case R.id.nav_send:
                ToastUtils.show(this, "nav_send" + now.getItemId(), 1);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent == null || intent.getAction() == null) return;
        if (intent.getAction().equals(ACTION_SHOW_LOADING_ITEM)) {
            mTakePhoto.setEnabled(false);
            showPhotoIntoImageViewDelayed();
            adapter.setPhotoUri(intent.getData());
        }
    }

    private void showPhotoIntoImageViewDelayed() {
        if (delayHandler == null)
            delayHandler = new DelayHandler(this);
        if (delayRunnable == null)
            delayRunnable = new DelayRunnable();
        delayHandler.postDelayed(delayRunnable, 500);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        setMenuChecked();
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
        hideProgress();
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
        presenter.detachView();
        setAllRepleaseResourceFieldsNull();
        super.onDestroy();
        System.gc();
    }

    private void setAllRepleaseResourceFieldsNull() {
        if (delayHandler != null) {
            delayHandler.removeCallbacksAndMessages(0);
            delayRunnable = null;
            delayHandler = null;
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(false);
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
    public void setItems(boolean isLoadmoreData, List<? extends Object> items) {
        if (adapter == null) {
            mLinearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
                // 设置更多的预留空间(在RecyclerView 的元素比较高，一屏只能显示一个元素的时候，第一次滑动到第二个元素会卡顿。)
                @Override
                protected int getExtraLayoutSpace(RecyclerView.State state) {
                    return 300;
                }
            };
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            adapter = new MainRecyclerAdapter(this);
            adapter.setOnLoadingFinishedCallback(this);
            adapter.setOnFeedItemClickListener(this);
            // false: 即使是已缓存的数据 也会有动画, 默认是true
            // scaleAdapter.setFirstOnly(false);
            // 带震动的动画
            // 因为MainAdapterItemAnimation继承了ScaleInAnimationAdapter动画,所以不再需要设置他就会有scaleAnimation动画效果
            // ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
            // scaleAdapter.setInterpolator(new OvershootInterpolator());
            mRecyclerView.setAdapter(/*new AlphaInAnimationAdapter(*/adapter);
            mRecyclerView.setItemAnimator(new MainAdapterItemAnimator());
            adapter.updateItems((List<HomeBean>) items, true);
        } else {
            if (mSwipeRefreshLayout != null)
                mSwipeRefreshLayout.setRefreshing(false);
            adapter.updateItems((List<HomeBean>) items, false);
        }
    }

    @Override
    public void showMessage(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addItem(Object data, int position) {
        if (adapter != null) {
            adapter.addItem((HomeBean) data, position);
            mRecyclerView.scrollToPosition(0);
        }
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
        if (UIUtils.isAndroid5()) {
            animator = AnimUtils.attrCreateCircularReveal(view, 1000);
        }
    }

    @Override
    public void onItemLongClicked(View view, int position) {
        if (position + 1 == 1) {
            Intent intent = new Intent(this, SlideListActivity.class);
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
        mTakePhoto.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
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
                        mTakePhoto.animate()
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

    @OnClick({R.id.del, R.id.add, R.id.takePhoto})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.del:
                if (adapter != null)
                    adapter.delItem(DEL_RECYCLER_ADAPTER_ITEM_POSITION);
                break;
            case R.id.add:
                presenter.addItem();
                break;
            case R.id.takePhoto:
                int[] startingLocation = new int[2];
                view.getLocationOnScreen(startingLocation);
                startingLocation[0] += mTakePhoto.getWidth() / 2;
                TakePhotoActivity.startCameraFromLocation(startingLocation, this);
                overridePendingTransition(0, 0);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(true);
    }

    private void setMenuChecked() {
        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    @Override
    public void onLoadingFinished() {
        mTakePhoto.setEnabled(true);
    }

    @Override
    public void onFailure(Throwable e) {

    }

    static class DelayHandler extends Handler {
        private WeakReference<MainActivity> mWeakReference;

        DelayHandler(MainActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case MainActivity.PHOTO_INTO_VIEW_DELAY:
                        activity.delayHandler.removeCallbacks(activity.delayRunnable);
                        break;
                }
            }
        }
    }

    class DelayRunnable implements Runnable {

        @Override
        public void run() {
            // 因为是SINGLE_TOP | FLAG_ACTIVITY_CLEAR_TOP启动模式,所以会重走newIntent()方法
            mRecyclerView.smoothScrollToPosition(0);
            adapter.showLoadingView();
        }
    }
}
