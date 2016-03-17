package com.user.hilo.activity.presenter.i;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface MainPresenter {

    void onResume();

    void Destroy();

    void onPause();

    void onItemClicked(int position);
}
