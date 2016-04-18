package com.user.hilo.presenter;

import com.user.hilo.core.mvp.BasePresenter;
import com.user.hilo.interfaces.OnLoginFinishedListener;
import com.user.hilo.model.LoginModel;
import com.user.hilo.model.i.ILoginModel;
import com.user.hilo.presenter.i.ILoginPresenter;
import com.user.hilo.view.i.LoginView;

/**
 * Created by Administrator on 2016/3/16.
 */
public class LoginPresenter extends BasePresenter<LoginView> implements ILoginPresenter, OnLoginFinishedListener {

    private ILoginModel model;

    public LoginPresenter() {
        model = new LoginModel();
    }

    @Override
    public void validateCredentials(String username, String password) {
        model.login(username, password, this);
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {
        if (getMvpView() != null) {
            getMvpView().hideProgress();
        }
    }


    @Override
    public void onUsernameError() {
        if (getMvpView() != null) {
            getMvpView().setUsernameError();
            getMvpView().hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (getMvpView() != null) {
            getMvpView().setPasswordError();
            getMvpView().hideProgress();
        }
    }

    @Override
    public void validateStatus(int status) {
        switch (status) {
            case LoginModel.VALIDATE_SUCCESS:
                getMvpView().showProgress();
                break;
            case LoginModel.SERVER_ERROR:
                getMvpView().serverError();
                break;
        }
    }

    @Override
    public void onSuccess() {
        if (getMvpView() != null) {
            getMvpView().navigateToHome();
        }
    }
}
