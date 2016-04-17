package com.user.hilo.presenter.i;

/**
 * Created by Administrator on 2016/4/17.
 */
public interface ISlideshowPresenter {

    void onResume();

    void Destroy();

    void onPause();

    void onItemClicked(int position);

    void addItem();

    void requestData(boolean isLoadmoreData);

    void requestDataFirst();
}
