package com.user.hilo.activity.model;

import com.user.hilo.activity.interfaces.OnFinishedListener;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface MainModel {

    void FindItems(OnFinishedListener onFinishedListener);

    void OnError();

    void onDestroy();
}
