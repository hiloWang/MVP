package com.user.hilo.model;

import com.user.hilo.bean.DailyBean;
import com.user.hilo.interfaces.OnFinishedListener;
import com.user.hilo.model.i.ISlideshowModel;
import com.user.hilo.utils.RxUtils;

import java.util.Arrays;
import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/4/17.
 */
public class SlideshowModel implements ISlideshowModel {

    @Override
    public void FindItems(boolean isLoadmoreData, OnFinishedListener onFinishedListener) {
        if (onFinishedListener == null)
            throw new NullPointerException("must be implements OnFinishedListener");
        onFinishedListener.onFinished(isLoadmoreData, createArrayList());
    }

    private Observable<List<? extends Object>> createArrayList() {
        List<DailyBean> dataLists = Arrays.asList(new DailyBean("A"),
                new DailyBean("B"),
                new DailyBean("C"),
                new DailyBean("D"),
                new DailyBean("E"),
                new DailyBean("F"),
                new DailyBean("G"),
                new DailyBean("H"),
                new DailyBean("I"),
                new DailyBean("J"),
                new DailyBean("K"));
        return Observable.just(dataLists)
                .compose(RxUtils.applyIOToMainThreadSchedulers());
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
