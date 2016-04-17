package com.user.hilo.interfaces;

import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface OnFinishedListener {

    void onFinished(boolean isLoadmoreData, List<? extends Object> items);

}
