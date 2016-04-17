package com.user.hilo.view.i;

import com.user.hilo.core.mvp.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/17.
 */
public interface SlideshowView extends MvpView {

    void showProgress();

    void hideProgress();

    void setItems(boolean loadingMoreData, List<? extends Object> items);

    void showMessage(String message);

    void addItem(Object obj, int position);

}
