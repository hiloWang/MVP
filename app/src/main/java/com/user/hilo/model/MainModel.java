package com.user.hilo.model;

import com.user.hilo.R;
import com.user.hilo.bean.HomeBean;
import com.user.hilo.interfaces.OnFinishedListener;
import com.user.hilo.model.i.IMainModel;
import com.user.hilo.utils.RxUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/16.
 */
public class MainModel implements IMainModel {

    private static final int[] mipmap = {
            R.mipmap.center_6, R.mipmap.center_2, R.mipmap.center_3, R.mipmap.center_1, R.mipmap.center_5,
            R.mipmap.center_4, R.mipmap.center_7, R.mipmap.center_8
    };

    @Override
    public void FindItems(boolean isLoadmoreData, OnFinishedListener onFinishedListener) {
        if (onFinishedListener == null)
            throw new NullPointerException("must be implements OnFinishedListener");
        onFinishedListener.onFinished(isLoadmoreData, transformObservable(createArrayList()));
    }

    @Override
    public Observable<? extends Object> getData() {
        return Observable.just(new HomeBean("clicked add ", 0, false, mipmap[(int) (Math.random() * 8)]))
                .observeOn(AndroidSchedulers.mainThread()) // subscribe回调里写的代码所属线程IO， Observerable回调里面写的代码所属线程MAIN;
                .subscribeOn(Schedulers.io());
//        .compose(RxUtils.applyIOToMainThreadSchedulers()); // 等同于上面俩句
    }


    @Override
    public Observable<List<? extends Object>> getItems() {
        List<HomeBean> entities = new ArrayList<>();
        entities.addAll(Arrays.asList(
                new HomeBean("A", 0, false, mipmap[(int) (Math.random() * 8)]),
                new HomeBean("B", 0, false, mipmap[(int) (Math.random() * 8)]),
                new HomeBean("C", 0, false, mipmap[5]),
                new HomeBean("D", 0, false, mipmap[4]),
                new HomeBean("E", 0, false, mipmap[3]),
                new HomeBean("F", 0, false, mipmap[2]),
                new HomeBean("G", 0, false, mipmap[1]),
                new HomeBean("H", 0, false, mipmap[0])
        ));
        return transformObservable(entities);
    }

    private Observable<List<? extends Object>> transformObservable(List<? extends Object> dataList) {
        return Observable.just(dataList)
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public List<? extends Object> createArrayList() {
        List<HomeBean> entities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entities.add(new HomeBean("subject " + i, 0, false, mipmap[i > 7 ? (int) (Math.random() * 8) : i]));
        }
        return entities;
    }
}
