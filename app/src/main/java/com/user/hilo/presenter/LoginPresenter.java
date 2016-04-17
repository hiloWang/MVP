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

    private LoginView mainView;
    private ILoginModel model;

    public LoginPresenter(LoginView mainView) {
        this.mainView = mainView;

        model = new LoginModel();
    }

    @Override
    public void validateCredentials(String username, String password) {
        if (mainView != null) {
            model.login(username, password, this);
        }
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
        mainView = null;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {
        if (mainView != null) {
            mainView.hideProgress();
        }
    }


    @Override
    public void onUsernameError() {
        if (mainView != null) {
            mainView.setUsernameError();
            mainView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (mainView != null) {
            mainView.setPasswordError();
            mainView.hideProgress();
        }
    }

    @Override
    public void validateStatus(int status) {
        switch (status) {
            case LoginModel.VALIDATE_SUCCESS:
                mainView.showProgress();
                break;
            case LoginModel.SERVER_ERROR:
                mainView.serverError();
                break;
        }
    }

    @Override
    public void onSuccess() {
        if (mainView != null) {
            mainView.navigateToHome();
        }
    }
}
