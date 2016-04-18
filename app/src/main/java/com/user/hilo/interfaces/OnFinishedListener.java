package com.user.hilo.interfaces;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface OnFinishedListener<T> {

    void onFinished(boolean isLoadmoreData, Observable<T> items);

}
