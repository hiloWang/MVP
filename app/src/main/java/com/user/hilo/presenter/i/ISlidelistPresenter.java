package com.user.hilo.presenter.i;

/**
 * Created by Administrator on 2016/4/17.
 */
public interface ISlidelistPresenter {

    void onResume();

    void onDestroy();

    void onPause();

    void requestDataFirst();

    void requestData(boolean isLoadmoreData);

}
