package com.user.hilo.activity.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.user.hilo.MyApplication;
import com.user.hilo.R;
import com.user.hilo.activity.base.BaseToolbarActivity;
import com.user.hilo.activity.presenter.LoginPresenterIml;
import com.user.hilo.activity.presenter.i.LoginPresenter;
import com.user.hilo.activity.view.i.LoginView;
import com.user.hilo.interfaces.OnNoDoubleClickListener;
import com.user.hilo.view.LoginButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/16.
 */
public class LoginActivity extends BaseToolbarActivity implements LoginView, LoginButton.OnSendClickListener {

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private Context context;

    @Bind(R.id.username)
    EditText mUsername;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.btnCommit)
    LoginButton mBtnCommit;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context = this;

        initViews();
        initEvents();

        presenter = new LoginPresenterIml(this);
    }

    private void initViews() {
        toolbar.setNavigationIcon(android.R.color.transparent);
        toolbarTitle.setText("登录界面");
    }

    private void initEvents() {
        mBtnCommit.setOnSendClickListener(this);
        toolbar.setNavigationOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClickListener(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        presenter.onDestroy();
        super.onDestroy();
        System.gc();

    }

    @OnClick({R.id.username, R.id.password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.username:
                break;
            case R.id.password:
                break;
        }
    }

    @Override
    public void setUsernameError() {
        mUsername.setError(getString(R.string.username_error));
        validationError();
    }

    @Override
    public void setPasswordError() {
        mPassword.setError(getString(R.string.password_error));
        validationError();
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showProgress() {
        mBtnCommit.startCustomAnimation();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void serverError() {
        mBtnCommit.endCustomAnimation();
        hideProgress();
        Toast.makeText(MyApplication.mContext, "服务器正在升级,请稍后再试.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSendClickListener(View v) {
        presenter.validateCredentials(mUsername.getText().toString(), mPassword.getText().toString());
    }

    private void validationError() {
        mBtnCommit.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
    }

    private void startIntroAnimation() {
//        int actionbarSize = UIUtils.dpToPx(R.dimen.status_bar_height, getResources());
        int actionbarSize = 0;
        toolbar.setTranslationY(-actionbarSize);
        toolbarTitle.setTranslationY(-actionbarSize);
        toolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        toolbarTitle.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);

        final float translateX = mUsername.getTranslationX();
        mUsername.setTranslationX(-actionbarSize);
        mPassword.setTranslationX(-actionbarSize);
        mBtnCommit.setTranslationX(-actionbarSize);
        mUsername.animate()
                .translationX(translateX)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .start();
        mPassword.animate()
                .translationX(translateX)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(600)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mBtnCommit.animate()
                                .translationX(translateX)
                                .setInterpolator(new OvershootInterpolator(1.f))
                                .setStartDelay(300)
                                .setDuration(ANIM_DURATION_TOOLBAR)
                                .start();
                    }
                })
                .start();

    }
}