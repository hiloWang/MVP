package com.user.hilo.presenter;

import com.user.hilo.interfaces.OnFinishedListener;
import com.user.hilo.model.MainModel;
import com.user.hilo.model.i.IMainModel;
import com.user.hilo.others.Api;
import com.user.hilo.presenter.i.IMainPresenter;
import com.user.hilo.utils.CustomDateUtils;
import com.user.hilo.view.i.MainView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public class MainPresenter implements IMainPresenter, OnFinishedListener {

    private IMainModel model;
    private MainView mainView;
    private int page;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
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
        if (mainView != null) {
            mainView.hideProgress();
        }
    }

    @Override
    public void onItemClicked(int position) {
        if (mainView != null) {
            mainView.showMessage(String.format("Position %d clicked", position + 1));
        }
    }

    @Override
    public void addItem() {
        if (mainView != null) {
            mainView.addItem(model.getData(), 0);
        }
    }

    @Override
    public void requestDataRefresh() {
        if (mainView != null) {
            mainView.requestDataRefreshFinish(model.getItems());
        }
    }

    @Override
    public void requestDataFirst() {
        if (mainView != null) {
            mainView.showProgress();
        }
        model.FindItems(this);
    }

    @Override
    public void onFinished(List<? extends Object> items) {
        if (mainView != null) {
            mainView.setItems(items);
            mainView.hideProgress();
        }
    }

    public void getDaily(final boolean refresh, final int oldPage) {
        
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
