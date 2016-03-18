package com.user.hilo.activity.model.i;

import com.user.hilo.activity.interfaces.OnFinishedListener;

import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface MainModel {

    void FindItems(OnFinishedListener onFinishedListener);

    void OnError();

    void onDestroy();

    Object getData();

    List<? extends Object> getItems();

}
