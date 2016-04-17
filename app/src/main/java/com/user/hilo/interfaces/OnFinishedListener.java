package com.user.hilo.interfaces;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface OnFinishedListener {

    void onFinished(boolean isLoadmoreData, Observable<List<? extends Object>> items);

}
