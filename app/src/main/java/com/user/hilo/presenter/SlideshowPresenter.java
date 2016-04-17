package com.user.hilo.presenter;

import com.user.hilo.core.mvp.BasePresenter;
import com.user.hilo.interfaces.OnFinishedListener;
import com.user.hilo.model.SlideshowModel;
import com.user.hilo.model.i.ISlideshowModel;
import com.user.hilo.presenter.i.ISlideshowPresenter;
import com.user.hilo.view.i.SlideshowView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/17.
 */
public class SlideshowPresenter extends BasePresenter<SlideshowView> implements ISlideshowPresenter, OnFinishedListener {

    private ISlideshowModel model;

    public SlideshowPresenter() {
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
    public void onFinished(boolean isLoadmoreData, List<? extends Object> items) {
        if (getMvpView() != null) {
            getMvpView().setItems(isLoadmoreData, items);
            getMvpView().hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable e) {

    }
}
