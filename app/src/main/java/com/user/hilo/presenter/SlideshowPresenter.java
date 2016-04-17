package com.user.hilo.presenter;

import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.google.gson.reflect.TypeToken;
import com.user.hilo.bean.DailyBean;
import com.user.hilo.core.mvp.BasePresenter;
import com.user.hilo.interfaces.OnFinishedListener;
import com.user.hilo.model.SlideshowModel;
import com.user.hilo.model.i.ISlideshowModel;
import com.user.hilo.others.Api;
import com.user.hilo.presenter.i.ISlideshowPresenter;
import com.user.hilo.utils.LogUtils;
import com.user.hilo.utils.ReservoirUtils;
import com.user.hilo.view.i.SlideshowView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/17.
 */
public class SlideshowPresenter extends BasePresenter<SlideshowView> implements ISlideshowPresenter, OnFinishedListener {

    private ISlideshowModel model;
    private ReservoirUtils reservoirUtils;

    public SlideshowPresenter() {
        reservoirUtils = new ReservoirUtils();
        model = new SlideshowModel();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void Destroy() {
        model.onDestroy();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void addItem() {

    }

    @Override
    public void requestData(boolean isLoadmoreData) {
        model.FindItems(isLoadmoreData, this);
    }

    @Override
    public void requestDataFirst() {
        if (getMvpView() != null)
            getMvpView().showProgress();
    }

    @Override
    public void onFinished(boolean isLoadmoreData, Observable<List<? extends Object>> items) {
        if (getMvpView() != null) {
            mCompositeSubscription.add(items.subscribe(new Subscriber<List<? extends Object>>() {
                @Override
                public void onCompleted() {
                    if (mCompositeSubscription != null)
                        mCompositeSubscription.remove(this);
                }

                @Override
                public void onError(Throwable e) {
                    try {
                        LogUtils.E(e.getMessage());
                    } catch (Throwable e1) {
                        e1.getMessage();
                    } finally {
                        Type resultType = new TypeToken<ArrayList<DailyBean>>() {
                        }.getType();
                        reservoirUtils.get(Api.daily + "", resultType, new ReservoirGetCallback<ArrayList<DailyBean>>() {
                            @Override
                            public void onSuccess(ArrayList<DailyBean> dataList) {
                                // 有缓存显示缓存数据
                                onSuccessCallback(isLoadmoreData, dataList);
                            }

                            @Override
                            public void onFailure(Exception e) {
                                if (getMvpView() != null)
                                    getMvpView().onFailure(e);
                            }
                        });
                    }
                }

                @Override
                public void onNext(List<? extends Object> dataList) {
                    reservoirUtils.refresh(Api.daily + "", dataList);
                    onSuccessCallback(isLoadmoreData, dataList);
                }
            }));

        }
    }

    public void onSuccessCallback(boolean isLoadmoreData, List<? extends Object> dataList) {
        if (getMvpView() != null) {
            getMvpView().setItems(isLoadmoreData, dataList);
            getMvpView().hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable e) {

    }
}
