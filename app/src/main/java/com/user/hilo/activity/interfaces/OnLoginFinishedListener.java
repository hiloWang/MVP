package com.user.hilo.activity.interfaces;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface OnLoginFinishedListener {

    void onUsernameError();

    void onPasswordError();

    void validateStatus(int status);

    void onSuccess();
}
