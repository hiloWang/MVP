package com.user.hilo.model.i;

import com.user.hilo.interfaces.OnLoginFinishedListener;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface ILoginModel {

    void login(String username, String password, OnLoginFinishedListener onLoginFinishedListener);

    void onDestroy();
}