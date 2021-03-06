package com.user.hilo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.user.hilo.R;
import com.user.hilo.core.BaseToolbarActivity;
import com.user.hilo.presenter.LoginPresenter;
import com.user.hilo.service.KeepingAppAliveService;
import com.user.hilo.view.i.LoginView;
import com.user.hilo.widget.LoginButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/16.
 */
public class LoginActivity extends BaseToolbarActivity implements LoginView, LoginButton.OnSendClickListener {

    private Intent innerService;

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
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        startGrayInnerService();
        toolbar.setNavigationIcon(android.R.color.transparent);
        mActionBarHelper.setTitle(getString(R.string.login_toolbar_title));
    }

    @Override
    protected void initListeners() {
        mBtnCommit.setOnSendClickListener(this);
    }

    @Override
    protected void initData() {
        presenter = new LoginPresenter();
        presenter.attachView(this);
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
        presenter.detachView();
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
        Toast.makeText(getApplicationContext(), "服务器正在升级,请稍后再试.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSendClickListener(View v) {
        presenter.validateCredentials(mUsername.getText().toString(), mPassword.getText().toString());
    }

    private void validationError() {
        mBtnCommit.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
    }

    @Override
    public void onFailure(Throwable e) {

    }

    private void startGrayInnerService() {
        innerService = new Intent(this, KeepingAppAliveService.class);
        startService(innerService);
    }

}
