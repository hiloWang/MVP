package com.user.hilo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ViewSwitcher;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.commonsware.cwac.camera.CameraView;
import com.commonsware.cwac.camera.PictureTransaction;
import com.commonsware.cwac.camera.SimpleCameraHost;
import com.user.hilo.R;
import com.user.hilo.adapter.PhotoFiltersAdapter;
import com.user.hilo.utils.UIUtils;
import com.user.hilo.widget.RevealBackgroundView;
import com.user.hilo.widget.SquaredFrameLayout;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/19.
 */
public class TakePhotoActivity extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener, CameraHostProvider {

    private static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private static final int STATE_TAKE_PHOTO = 0;
    private static final int STATE_SETUP_PHOTO = 1;

    private static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final Interpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();

    @Bind(R.id.btnBack)
    ImageButton mBtnBack;
    @Bind(R.id.btnAccept)
    ImageButton mBtnAccept;
    @Bind(R.id.vUpperPanel)
    ViewSwitcher mVUpperPanel;
    @Bind(R.id.cameraView)
    CameraView mCameraView;
    @Bind(R.id.vShutter)
    View mVShutter;
    @Bind(R.id.vPhotoRoot)
    SquaredFrameLayout mVPhotoRoot;
    @Bind(R.id.btnTakePhoto)
    Button mBtnTakePhoto;
    @Bind(R.id.rvFilters)
    RecyclerView mRvFilters;
    @Bind(R.id.vLowerPanel)
    ViewSwitcher mVLowerPanel;
    @Bind(R.id.vRevealBackground)
    RevealBackgroundView mVRevealBackground;
    @Bind(R.id.ivTakenPhoto)
    ImageButton mIvTakenPhoto;

    private int currentState;
    private File photoPath;
    private boolean pendingInfo;

    public static void startCameraFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, TakePhotoActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        ButterKnife.bind(this);
        updateStatusBarColor();
        updateState(STATE_TAKE_PHOTO);
        setupRevealBackground(savedInstanceState);
        setPhotoFilters();

        mVUpperPanel.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mVUpperPanel.getViewTreeObserver().removeOnPreDrawListener(this);
                pendingInfo = true;
                mVUpperPanel.setTranslationY(-mVUpperPanel.getHeight());
                mVLowerPanel.setTranslationY(mVLowerPanel.getHeight());
                return true;
            }
        });

    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        mVRevealBackground.setFillPaintColor(0xFF16181a);
        mVRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            mVRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    mVRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    mVRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            mVRevealBackground.setToFinishedFrame();
        }
    }


    private void setPhotoFilters() {
        PhotoFiltersAdapter photoFiltersAdapter = new PhotoFiltersAdapter();
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRvFilters.setHasFixedSize(true);
        mRvFilters.setAdapter(photoFiltersAdapter);
        mRvFilters.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void updateState(int state) {
        currentState = state;
        if (currentState == STATE_TAKE_PHOTO) {
            mVUpperPanel.setInAnimation(this, R.anim.slide_in_from_right);
            mVLowerPanel.setInAnimation(this, R.anim.slide_in_from_right);
            mVUpperPanel.setOutAnimation(this, R.anim.slide_out_to_right);
            mVLowerPanel.setOutAnimation(this, R.anim.slide_out_to_right);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIvTakenPhoto.setVisibility(View.GONE);
                }
            }, 400);
        } else if (currentState == STATE_SETUP_PHOTO) {
            mVUpperPanel.setInAnimation(this, R.anim.slide_in_from_right);
            mVLowerPanel.setInAnimation(this, R.anim.slide_in_from_right);
            mVUpperPanel.setOutAnimation(this, R.anim.slide_out_to_right);
            mVLowerPanel.setOutAnimation(this, R.anim.slide_out_to_right);
            mIvTakenPhoto.setVisibility(View.VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void updateStatusBarColor() {
        if (UIUtils.isAndroid5()) {
            getWindow().setStatusBarColor(0xff111111);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCameraView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStateChange(int state) {
        if (state == RevealBackgroundView.STATE_FINISHED) {
            mVPhotoRoot.setVisibility(View.VISIBLE);
            if (pendingInfo) {
                startIntroAnimation();
            }
        } else {
            mVPhotoRoot.setVisibility(View.INVISIBLE);
        }
    }

    private void startIntroAnimation() {
        mVUpperPanel.animate()
                .translationY(0)
                .setDuration(400)
                .setInterpolator(DECELERATE_INTERPOLATOR);
        mVLowerPanel.animate()
                .translationY(0)
                .setDuration(400)
                .setInterpolator(DECELERATE_INTERPOLATOR)
                .start();
    }

    @OnClick(R.id.btnTakePhoto)
    public void onTakePhotoClick() {
        mBtnTakePhoto.setEnabled(false);
        mCameraView.takePicture(true, true);
        animateShutter();
    }

    @OnClick(R.id.btnAccept)
    public void onAcceptClick() {
        PublishActivity.openWithPhotoUri(this, Uri.fromFile(photoPath));
    }

    /**
     * 触发快门闪烁效果
     */
    private void animateShutter() {
        mVShutter.setVisibility(View.VISIBLE);
        mVShutter.setAlpha(0.f);

        ObjectAnimator alphaInAnimator = ObjectAnimator.ofFloat(mVShutter, "alpha", 0.f, 0.8f);
        alphaInAnimator.setDuration(100);
        alphaInAnimator.setStartDelay(100);
        alphaInAnimator.setInterpolator(ACCELERATE_INTERPOLATOR);

        ObjectAnimator alphaOutAnimator = ObjectAnimator.ofFloat(mVShutter, "alpha", 0.8f, 0.f);
        alphaOutAnimator.setDuration(200);
        alphaOutAnimator.setInterpolator(DECELERATE_INTERPOLATOR);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(alphaInAnimator, alphaOutAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mVShutter.setVisibility(View.GONE);
            }
        });
        animatorSet.start();
    }

    @Override
    public CameraHost getCameraHost() {
        return new MyCameraHost(this);
    }

    class MyCameraHost extends SimpleCameraHost {

        private Camera.Size previewSize;

        public MyCameraHost(Context ctxt) {
            super(ctxt);
        }

        @Override
        public boolean useFullBleedPreview() {
            return true;
        }

        @Override
        public Camera.Size getPictureSize(PictureTransaction xact, Camera.Parameters parameters) {
            return previewSize;
        }

        @Override
        public Camera.Parameters adjustPreviewParameters(Camera.Parameters parameters) {
            Camera.Parameters parameters1 = super.adjustPreviewParameters(parameters);
            previewSize = parameters1.getPreviewSize();
            return parameters1;
        }

        @Override
        public void saveImage(PictureTransaction xact, final Bitmap bitmap) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showTakenPicture(bitmap);
                }
            });
        }

        @Override
        public void saveImage(PictureTransaction xact, byte[] image) {
            super.saveImage(xact, image);
            photoPath = getPhotoPath();
        }
    }

    private void showTakenPicture(Bitmap bitmap) {
        mVUpperPanel.showNext();
        mVLowerPanel.showNext();
        mIvTakenPhoto.setImageBitmap(bitmap);
        updateState(STATE_SETUP_PHOTO);
    }

    @OnClick({R.id.ibBack, R.id.btnBack})
    public void backActivity() {
        finish();
        overridePendingTransition(0, R.anim.activity_swipeback_slide_right_out);
    }
}
