package com.user.hilo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.user.hilo.R;

/**
 * Created by Administrator on 2016/3/21.
 */
public class SendingProgressView extends View {

    private static final int SIMULATE_ANIMATOR_DURATION = 2000;
    private static final int DONE_ANIMATOR_DURATION = 300;

    private static final float PROGRESS_STROKE_SIZE = 10;
    private static final int INNER_CIRCLE_PADDING = 30;
    private static final int MAX_DONE_BG_OFFSET = 800;
    private static final int MAX_DONE_IMG_OFFSET = 400;

    private static final byte STATE_NOT_STARTED = 0;
    private static final byte STATE_PROGRESS_STARTED = 1;
    private static final byte STATE_DONE_STARTED = 2;
    private static final byte STATE_FINISH = 3;
    private byte state = STATE_NOT_STARTED;

    private float currentDoneBgOffset = MAX_DONE_BG_OFFSET;
    private float currentCheckmarkOffset = MAX_DONE_IMG_OFFSET;

    private Paint progressPaint;
    private Paint doneBgPaint;
    private Paint checkmarkPaint;
    private Paint maskPaint;


    private int checkmarkXPosition = 0;
    private int checkmarkYPosition = 0;
    private Bitmap checkmarkBitmap;
    private Bitmap innerCircleMaskBitmap;
    private Bitmap tempBitmap;

    private ObjectAnimator simulateAnimator, doneBgAnimator, checkmarkAnimator;
    private float currentProgress = 0;
    private Canvas tempCanvas;
    // RectF这个类包含一个矩形的四个单精度浮点坐标
    private RectF progressBounds;

    public SendingProgressView(Context context) {
        super(context);
        init();
    }

    public SendingProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SendingProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SendingProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setupProgressPaint();
        setupDonePaint();
        setupSimulateProgressAnimator();
        setupDoneAnimators();
    }

    private void setupSimulateProgressAnimator() {
        simulateAnimator = ObjectAnimator.ofFloat(this, "currentProgress", 0, 100).setDuration(SIMULATE_ANIMATOR_DURATION);
        simulateAnimator.setInterpolator(new AccelerateInterpolator());
        simulateAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                changeState(STATE_DONE_STARTED);
            }
        });
    }

    private void setupDoneAnimators() {
        doneBgAnimator = ObjectAnimator.ofFloat(this, "currentDoneBgOffset", MAX_DONE_BG_OFFSET, 0).setDuration(DONE_ANIMATOR_DURATION);
        doneBgAnimator.setInterpolator(new DecelerateInterpolator());

        checkmarkAnimator = ObjectAnimator.ofFloat(this, "currentCheckmarkOffset", MAX_DONE_IMG_OFFSET, 0).setDuration(DONE_ANIMATOR_DURATION);
        checkmarkAnimator.setInterpolator(new OvershootInterpolator());
        checkmarkAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                changeState(STATE_FINISH);
            }
        });
    }

    private void setupDonePaint() {
        doneBgPaint = new Paint();
        doneBgPaint.setAntiAlias(true);
        doneBgPaint.setStyle(Paint.Style.FILL);
        doneBgPaint.setColor(0xff39cb72);

        checkmarkPaint = new Paint();
        maskPaint = new Paint();
        // PorterDuff.Mode.DST_IN 取两层绘制交集, 显示上层。
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    private void setupProgressPaint() {
        progressPaint = new Paint();
        // 图像边缘相对清晰一点,锯齿痕迹不那么明显
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setColor(0xffffffff);
        progressPaint.setStrokeWidth(PROGRESS_STROKE_SIZE);
    }

    private void changeState(byte state) {
        if (this.state == state) {
            return;
        }

        tempBitmap.recycle();
        resetTempCanvas();

        this.state = state;
        switch (state) {
            case STATE_PROGRESS_STARTED:
                setCurrentProgress(0);
                simulateAnimator.start();
                break;
            case STATE_DONE_STARTED:
                setCurrentDoneBgOffset(MAX_DONE_BG_OFFSET);
                setCurrentCheckmarkOffset(MAX_DONE_IMG_OFFSET);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playSequentially(doneBgAnimator, checkmarkAnimator);
                animatorSet.start();
                break;
            case STATE_FINISH:
                if (onLoadingFinishedListener != null)
                    onLoadingFinishedListener.onLoadingFinished();
                break;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateProgressBounds();
        setupCheckmarkBitmap();
        setupDoneMaskBitmap();
        resetTempCanvas();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (state) {
            case STATE_PROGRESS_STARTED:
                drawArcforCurrentProgress();
                postInvalidate();
                break;
            case STATE_DONE_STARTED:
                drawFrameForDoneAnimation();
                drawFinishedState();
                break;
            case STATE_FINISH:
                drawFinishedState();
                break;
        }
        canvas.drawBitmap(tempBitmap, 0, 0, null);
    }

    private void updateProgressBounds() {
        progressBounds = new RectF(
                PROGRESS_STROKE_SIZE, PROGRESS_STROKE_SIZE,
                getWidth() - PROGRESS_STROKE_SIZE, getWidth() - PROGRESS_STROKE_SIZE);
    }

    private void setupCheckmarkBitmap() {
        checkmarkBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_done_white_48dp);
        checkmarkXPosition = getWidth() / 2 - checkmarkBitmap.getWidth() / 2;
        checkmarkYPosition = getWidth() / 2 - checkmarkBitmap.getHeight() / 2;
    }

    private void setupDoneMaskBitmap() {
        innerCircleMaskBitmap = Bitmap.createBitmap(getWidth(), getWidth(), Bitmap.Config.ARGB_8888);
        Canvas srcCanvas = new Canvas(innerCircleMaskBitmap);
        srcCanvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - INNER_CIRCLE_PADDING, new Paint());
    }

    private void resetTempCanvas() {
        tempBitmap = Bitmap.createBitmap(getWidth(), getWidth(), Bitmap.Config.ARGB_8888);
        tempCanvas = new Canvas(tempBitmap);
    }

    /**
     * 绘制一段弧线,它表示由一对坐标、宽度和高度指定的椭圆部分
     */
    private void drawArcforCurrentProgress() {
        tempCanvas.drawArc(progressBounds, -90f, 360 * currentProgress / 100, false, progressPaint);
    }

    private void drawFrameForDoneAnimation() {
        // 绿圈 Paint.Style.FILL
        tempCanvas.drawCircle(getWidth() / 2, getWidth() / 2 + currentDoneBgOffset, getWidth() / 2 - INNER_CIRCLE_PADDING, doneBgPaint);
        // '对勾'
        tempCanvas.drawBitmap(checkmarkBitmap, checkmarkXPosition, checkmarkYPosition + currentCheckmarkOffset, checkmarkPaint);
        // 内圈 且浮在上层
        tempCanvas.drawBitmap(innerCircleMaskBitmap, 0, 0, maskPaint);
        tempCanvas.drawArc(progressBounds, 0, 360f, false, progressPaint);
    }

    private void drawFinishedState() {
        tempCanvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - INNER_CIRCLE_PADDING, doneBgPaint);
        tempCanvas.drawBitmap(checkmarkBitmap, checkmarkXPosition, checkmarkYPosition, checkmarkPaint);
        tempCanvas.drawArc(progressBounds, 0, 360f, false, progressPaint);
    }

    public void simulateProgress() {
        changeState(STATE_PROGRESS_STARTED);
    }

    public void setCurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress;
        postInvalidate();
    }

    public void setCurrentDoneBgOffset(float currentDoneBgOffset) {
        this.currentDoneBgOffset = currentDoneBgOffset;
        postInvalidate();
    }

    public void setCurrentCheckmarkOffset(float currentCheckmarkOffset) {
        this.currentCheckmarkOffset = currentCheckmarkOffset;
        postInvalidate();
    }

    private OnLoadingFinishedListener onLoadingFinishedListener;
    public void setOnLoadingFinishedListener(OnLoadingFinishedListener onLoadingFinishedListener) {
        this.onLoadingFinishedListener = onLoadingFinishedListener;
    }

    public interface OnLoadingFinishedListener {
        void onLoadingFinished();
    }
}
