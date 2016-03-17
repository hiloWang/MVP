package com.user.hilo.activity.model.i;

import com.user.hilo.activity.interfaces.OnLoginFinishedListener;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface LoginModel {

    void login(String username, String password, OnLoginFinishedListener onLoginFinishedListener);

    void onDestroy();
}
