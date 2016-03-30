package com.user.hilo.presenter.i;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface ILoginPresenter {

    void validateCredentials(String username, String password);

    void onDestroy();

    void onResume();

    void onPause();
}
