package com.user.hilo.model.i;

import com.user.hilo.interfaces.OnFinishedListener;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/4/17.
 */
public interface ISlideshowModel {

    void FindItems(boolean isLoadmoreData, OnFinishedListener onFinishedListener);

    void OnError();

    void onDestroy();

    Observable<Object> getData();

    Observable<List<? extends Object>> getItems();
}
