package com.user.hilo.activity.view;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface LoginView {

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();

    void showProgress();

    void hideProgress();

    void serverError();
}
