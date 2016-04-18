package com.user.hilo.presenter;

import com.user.hilo.api.Api;
import com.user.hilo.core.mvp.BasePresenter;
import com.user.hilo.interfaces.OnFinishedListener;
import com.user.hilo.model.MainModel;
import com.user.hilo.model.i.IMainModel;
import com.user.hilo.presenter.i.IMainPresenter;
import com.user.hilo.utils.CustomDateUtils;
import com.user.hilo.utils.LogUtils;
import com.user.hilo.view.i.MainView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/3/16.
 */
public class MainPresenter extends BasePresenter<MainView> implements IMainPresenter, OnFinishedListener {

    private IMainModel model;
    private int page;

    public MainPresenter() {
        model = new MainModel();
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
        if (getMvpView() != null) {
            getMvpView().showMessage(String.format("Position %d clicked", position + 1));
        }
    }

    @Override
    public void addItem() {
        if (getMvpView() != null) {
            getMvpView().addItem(model.getData(), 0);
        }
    }

    @Override
    public void requestData(boolean isLoadmoreData) {
        model.FindItems(isLoadmoreData, this);
    }

    @Override
    public void requestDataFirst() {
        if (getMvpView() != null) {
            getMvpView().showProgress();
        }
        model.FindItems(false, this);
    }

    @Override
    public void onFinished(boolean isLoadmoreData, Observable items) {
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
                    }
                }

                @Override
                public void onNext(List<? extends Object> dataList) {
                    getMvpView().setItems(isLoadmoreData, dataList);
                    getMvpView().hideProgress();
                }
            }));
        }
    }

    public class TodayDate {

        private Calendar calendar;

        public TodayDate(Calendar canlendar) {
            this.calendar = canlendar;
        }

        public int getYear() {
            return calendar.get(Calendar.YEAR);
        }

        public int getMonth() {
            return calendar.get(Calendar.MONTH) + 1;
        }

        public int getDay() {
            return calendar.get(Calendar.DAY_OF_MONTH);
        }

        public List<TodayDate> getPastTime() {
            List<TodayDate> todays = new ArrayList<>();
            for (int i = 0; i < Api.DEFAULT_DAILY_SIZE; i++) {
            /*
             * - (page * CustomDateUtils.ONE_DAY) 翻到哪页再找 一页有DEFAULT_DAILY_SIZE这么长
             * - i * CustomDateUtils.ONE_DAY 往前一天一天 找呀找
             */
                long timeInMillis = this.calendar.getTimeInMillis()
                        - ((page - 1) * Api.DEFAULT_DAILY_SIZE * CustomDateUtils.ONE_DAY)
                        - i * CustomDateUtils.ONE_DAY;
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(timeInMillis);
                todays.add(new TodayDate(c));
            }
            return todays;
        }
    }
}
