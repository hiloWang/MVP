package com.user.hilo.model.i;

import com.user.hilo.bean.DailyData;
import com.user.hilo.interfaces.OnFinishedListener;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/3/24.
 */
public interface IDailyModel {
    /**
     * 查询每日数据
     *
     * @param year  year
     * @param month month
     * @param day   day
     * @return Observable<GankDaily>
     */
    Observable<DailyData> getDaily(int year, int month, int day);

    void FindItems(OnFinishedListener onFinishedListener);

    void OnError();

    void onDestroy();

    Observable<Object> getData();

    Observable<List<? extends Object>> getItems();
}
