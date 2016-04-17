package com.user.hilo.view.i;

import com.user.hilo.core.mvp.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface MainView extends MvpView {

    void showProgress();

    void hideProgress();

    void setItems(boolean isLoadmoreData, List<? extends Object> items);

    void showMessage(String message);

    void addItem(Object obj, int position);

}
