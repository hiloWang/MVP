package com.user.hilo.activity.presenter;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface LoginPresenter {

    void validateCredentials(String username, String password);

    void onDestroy();

    void onResume();

    void onPause();
}
