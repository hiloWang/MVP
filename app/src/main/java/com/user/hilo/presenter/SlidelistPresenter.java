package com.user.hilo.presenter;

import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.google.gson.reflect.TypeToken;
import com.user.hilo.api.Api;
import com.user.hilo.bean.DailyBean;
import com.user.hilo.core.mvp.BasePresenter;
import com.user.hilo.interfaces.OnFinishedListener;
import com.user.hilo.model.SlidelistModel;
import com.user.hilo.model.i.ISlidelistModel;
import com.user.hilo.presenter.i.ISlidelistPresenter;
import com.user.hilo.utils.LogUtils;
import com.user.hilo.utils.ReservoirUtils;
import com.user.hilo.view.i.SlidelistView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/17.
 */
public class SlidelistPresenter extends BasePresenter<SlidelistView> implements ISlidelistPresenter, OnFinishedListener {

    private ISlidelistModel model;
    private ReservoirUtils reservoirUtils;

    public SlidelistPresenter() {
        reservoirUtils = new ReservoirUtils();
        model = new SlidelistModel();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        model.onDestroy();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void requestData(boolean isLoadmoreData) {
        model.FindItems(isLoadmoreData, this);
    }

    @Override
    public void requestDataFirst() {
        if (getMvpView() != null)
            getMvpView().showProgress();
        model.FindItems(false, this);
    }


    public void onSuccessCallback(boolean isLoadmoreData, List<? extends Object> dataList) {
        if (getMvpView() != null) {
            getMvpView().setItems(isLoadmoreData, dataList);
            getMvpView().hideProgress();
        }
    }

    @Override
    public void onFinished(boolean isLoadmoreData, Observable items) {
        if (getMvpView() != null) {
            mCompositeSubscription.add(items.subscribe(new Subscriber<DailyBean>() {
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
                public void onNext(DailyBean dailyBean) {
                    reservoirUtils.refresh(Api.daily + "", dailyBean.getMenus().getContent());
                    onSuccessCallback(isLoadmoreData, dailyBean.getMenus().getContent());
                }
            }));
        }
    }
}
