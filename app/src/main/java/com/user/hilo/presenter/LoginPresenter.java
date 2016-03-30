package com.user.hilo.presenter;

import com.user.hilo.interfaces.OnLoginFinishedListener;
import com.user.hilo.model.LoginModel;
import com.user.hilo.model.i.ILoginModel;
import com.user.hilo.presenter.i.ILoginPresenter;
import com.user.hilo.view.i.LoginView;

/**
 * Created by Administrator on 2016/3/16.
 */
public class LoginPresenter implements ILoginPresenter, OnLoginFinishedListener {

    private LoginView loginView;
    private ILoginModel model;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;

        model = new LoginModel();
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
            case LoginModel.VALIDATE_SUCCESS:
                loginView.showProgress();
                break;
            case LoginModel.SERVER_ERROR:
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
