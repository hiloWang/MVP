package com.user.hilo.model.i;

import com.user.hilo.interfaces.OnFinishedListener;

import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface IMainModel {

    void FindItems(OnFinishedListener onFinishedListener);

    void OnError();

    void onDestroy();

    Object getData();

    List<? extends Object> getItems();

}
