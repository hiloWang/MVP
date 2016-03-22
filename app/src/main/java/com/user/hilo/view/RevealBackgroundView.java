package com.user.hilo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by Administrator on 2016/3/19.
 */
public class RevealBackgroundView extends View {

    public static final int STATE_NOT_STARTED = 0;
    public static final int STATE_FILL_STARTED = 1;
    public static final int STATE_FINISHED = 2;

    private static final Interpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();

    private int state = STATE_NOT_STARTED;
    private int startLocationX;
    private int startLocationY;
    private int currentRadius;

    private Paint fillPaint;
    private ObjectAnimator revealAnimator;

    public RevealBackgroundView(Context context) {
        super(context);
        init();
    }

    public RevealBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RevealBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RevealBackgroundView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.WHITE);
    }

    /**
     * 设置开始前颜色,以显示FILL填充效果
     *
     * @param color
     */
    public void setFillPaintColor(int color) {
        fillPaint.setColor(color);
    }

    /**
     * 开始绘制前调用
     *
     * @param tapLocationOnScreen 当前位置点击时的location
     */
    public void startFromLocation(int[] tapLocationOnScreen) {
        onStateChange(STATE_FILL_STARTED);
        startLocationX = tapLocationOnScreen[0];
        startLocationY = tapLocationOnScreen[1];
        revealAnimator = ObjectAnimator.ofInt(this, "currentRadius", 0, getWidth() + getHeight())
                .setDuration(400);
        revealAnimator.setInterpolator(ACCELERATE_INTERPOLATOR);
        revealAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onStateChange(STATE_FINISHED);
            }
        });
        revealAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (state == STATE_FINISHED) {
            canvas.drawRect(0, 0, getWidth(), getHeight(), fillPaint);
        } else {
            canvas.drawCircle(startLocationX, startLocationY, currentRadius, fillPaint);
        }
    }

    public void setCurrentRadius(int radius) {
        currentRadius = radius;
        invalidate();
    }


    public void setToFinishedFrame() {
        onStateChange(STATE_FINISHED);
        invalidate();
    }


    private void onStateChange(int state) {
        if (this.state == state) {
            return;
        }
        this.state = state;

        if (onStateChangeListener != null)
            onStateChangeListener.onStateChange(state);
    }

    private OnStateChangeListener onStateChangeListener;

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }

    public interface OnStateChangeListener {
        void onStateChange(int state);
    }
}
