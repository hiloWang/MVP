package com.user.hilo.activity.presenter;

import com.user.hilo.activity.interfaces.OnLoginFinishedListener;
import com.user.hilo.activity.model.LoginModelIml;
import com.user.hilo.activity.model.i.LoginModel;
import com.user.hilo.activity.presenter.i.LoginPresenter;
import com.user.hilo.activity.view.i.LoginView;

/**
 * Created by Administrator on 2016/3/16.
 */
public class LoginPresenterIml implements LoginPresenter, OnLoginFinishedListener {

    private LoginView loginView;
    private LoginModel model;

    public LoginPresenterIml(LoginView loginView) {
        this.loginView = loginView;

        model = new LoginModelIml();
    }

    @Override
    public void validateCredentials(String username, String password) {
        if (loginView != null) {
            model.login(username, password, this);
        }
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
        loginView = null;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {
        if (loginView != null) {
            loginView.hideProgress();
        }
    }


    @Override
    public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override
    public void validateStatus(int status) {
        switch (status) {
            case LoginModelIml.VALIDATE_SUCCESS:
                loginView.showProgress();
                break;
            case LoginModelIml.SERVER_ERROR:
                loginView.serverError();
                break;
        }
    }

    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.navigateToHome();
        }
    }
}
