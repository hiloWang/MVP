package com.user.hilo.activity.view.i;

import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface MainView {

    void showProgress();

    void hideProgress();

    void setItems(List<? extends Object> items);

    void showMessage(String message);

    void addItem(Object obj, int position);

    void requestDataRefreshFinish(List<? extends Object> items);

}
