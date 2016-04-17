package com.user.hilo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.user.hilo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/21.
 */
public class LoadingFeedItemView extends FrameLayout {

    @Bind(R.id.vProgressBg)
    View mVProgressBg;
    @Bind(R.id.vSendingProgress)
    SendingProgressView mVSendingProgress;

    public LoadingFeedItemView(Context context) {
        super(context);
        init();
    }

    public LoadingFeedItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingFeedItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingFeedItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_feed_loader, this, true);
        ButterKnife.bind(this);
    }

    public void startLoading() {
        mVSendingProgress.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mVSendingProgress.getViewTreeObserver().removeOnPreDrawListener(this);
                // 模拟
                mVSendingProgress.simulateProgress();
                return true;
            }
        });
        mVSendingProgress.setOnLoadingFinishedListener(() -> {
            mVSendingProgress.animate().scaleX(0.f).scaleY(0.f).setDuration(200).setStartDelay(100);
            mVProgressBg.animate().alpha(0).setDuration(200).setStartDelay(100)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mVSendingProgress.setScaleX(1.f);
                            mVSendingProgress.setScaleY(1.f);
                            mVProgressBg.setAlpha(1);
                            if (onLoadingFinishedListener != null) {
                                onLoadingFinishedListener.onLoadingFinished();
                                onLoadingFinishedListener = null;
                            }
                        }
                    }).start();
        });
    }

    private OnLoadingFinishedListener onLoadingFinishedListener;

    public void setOnLoadingFinishedListener(OnLoadingFinishedListener onLoadingFinishedListener) {
        this.onLoadingFinishedListener = onLoadingFinishedListener;
    }

    public interface OnLoadingFinishedListener {
        void onLoadingFinished();
    }
}
