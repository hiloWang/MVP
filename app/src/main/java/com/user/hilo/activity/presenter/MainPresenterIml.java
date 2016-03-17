package com.user.hilo.activity.presenter;

import com.user.hilo.activity.interfaces.OnFinishedListener;
import com.user.hilo.activity.model.MainModelIml;
import com.user.hilo.activity.model.i.MainModel;
import com.user.hilo.activity.presenter.i.MainPresenter;
import com.user.hilo.activity.view.i.MainView;

import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public class MainPresenterIml implements MainPresenter, OnFinishedListener {

    private MainView mainView;
    private MainModel model;

    public MainPresenterIml(MainView mainView) {
        this.mainView = mainView;

        model = new MainModelIml();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void Destroy() {
        model.onDestroy();
        mainView = null;
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
    public void onFinished(List<String> items) {
        if (mainView != null) {
            mainView.setItems(items);
            mainView.hideProgress();
        }
    }
}
