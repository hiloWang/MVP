package com.user.hilo.model;

import com.user.hilo.bean.DailyData;
import com.user.hilo.interfaces.OnFinishedListener;
import com.user.hilo.model.i.IDailyModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/3/24.
 */
public class DailyModel implements IDailyModel {

    private static final DailyModel instance = new DailyModel();
    private DailyModel(){}
    public static DailyModel getInstance() {return instance;}

    @Override
    public Observable<DailyData> getDaily(int year, int month, int day) {
        return null;

    }

    @Override
    public void FindItems(OnFinishedListener onFinishedListener) {

    }

    @Override
    public void OnError() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<Object> getData() {
        return null;
    }

    @Override
    public Observable<List<? extends Object>> getItems() {
        return null;
    }
}
