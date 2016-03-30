package com.user.hilo.presenter.i;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface IMainPresenter {

    void onResume();

    void Destroy();

    void onPause();

    void onItemClicked(int position);

    void addItem();

    void requestDataRefresh();

    void requestDataFirst();
}
