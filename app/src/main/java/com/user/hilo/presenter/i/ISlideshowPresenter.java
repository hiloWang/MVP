package com.user.hilo.presenter.i;

import com.user.hilo.core.mvp.MvpView;

/**
 * Created by Administrator on 2016/4/17.
 */
public interface ISlideshowPresenter extends MvpView {

    void onResume();

    void Destroy();

    void onPause();

    void onItemClicked(int position);

    void addItem();

    void requestData(boolean isLoadmoreData);

    void requestDataFirst();
}
