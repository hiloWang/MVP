package com.user.hilo.model;

import com.user.hilo.api.Api;
import com.user.hilo.interfaces.OnFinishedListener;
import com.user.hilo.model.i.ISlidelistModel;
import com.user.hilo.utils.RxUtils;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/4/17.
 */
public class SlidelistModel implements ISlidelistModel {

    @Override
    public void FindItems(boolean isLoadmoreData, OnFinishedListener onFinishedListener) {
        if (onFinishedListener == null)
            throw new NullPointerException("must be implements OnFinishedListener");
        onFinishedListener.onFinished(isLoadmoreData,
                requestApi()
                        .compose(RxUtils.applyIOToMainThreadSchedulers()));
    }

    private Observable<? extends Object> requestApi() {
        return Api.createApiService().getApiService().getDailyData("zh");
    }

    @Override
    public Observable<? extends Object> getData() {
        return requestApi();
    }

    @Override
    public void onDestroy() {
        // 释放资源
    }
}
