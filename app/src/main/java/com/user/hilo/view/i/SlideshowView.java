package com.user.hilo.view.i;

import java.util.List;

/**
 * Created by Administrator on 2016/4/17.
 */
public interface SlideshowView {

    void showProgress();

    void hideProgress();

    void setItems(boolean loadingMoreData, List<? extends Object> items);

    void showMessage(String message);

    void addItem(Object obj, int position);

    void requestDataRefreshFinish(List<? extends Object> items);
}
