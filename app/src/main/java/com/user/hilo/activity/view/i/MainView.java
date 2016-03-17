package com.user.hilo.activity.view.i;

import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface MainView {

    void showProgress();

    void hideProgress();

    void setItems(List<String> items);

    void showMessage(String message);

    void addItem(String data, int position);

    void requestDataRefreshFinish(List<String> items);

}