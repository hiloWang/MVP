package com.user.hilo.view.i;

import com.user.hilo.core.mvp.MvpView;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface LoginView extends MvpView {

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();

    void showProgress();

    void hideProgress();

    void serverError();
}
