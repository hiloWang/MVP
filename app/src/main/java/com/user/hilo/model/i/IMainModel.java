package com.user.hilo.model.i;

import com.user.hilo.interfaces.OnFinishedListener;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface IMainModel {

    void FindItems(boolean isLoadmoreData, OnFinishedListener onFinishedListener);

    Observable<? extends Object> getData();

    Observable<List<? extends Object>> getItems();

}
