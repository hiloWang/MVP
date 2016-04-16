package com.user.hilo.model;

import android.os.Handler;
import android.os.Message;

import com.user.hilo.R;
import com.user.hilo.interfaces.OnFinishedListener;
import com.user.hilo.bean.HomeBean;
import com.user.hilo.model.i.IMainModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public class MainModel implements IMainModel {

    private RequestDataHandler mHandler;
    private OnFinishedListener onFinishedListener;
    private static final int[] mipmap = {
            R.mipmap.center_6, R.mipmap.center_2, R.mipmap.center_3, R.mipmap.center_1, R.mipmap.center_5,
            R.mipmap.center_4, R.mipmap.center_7, R.mipmap.center_8
    };

    @Override
    public void FindItems(OnFinishedListener onFinishedListener) {
        if (onFinishedListener == null)
            throw new NullPointerException("must be implements OnFinishedListener");
        this.onFinishedListener = onFinishedListener;
        if (mHandler == null) {
            mHandler = new RequestDataHandler(this);
        }
        mHandler.postDelayed(requestDataRunnable, 2 * 100);
    }

    @Override
    public void OnError() {
        repleaseResource();
    }

    @Override
    public void onDestroy() {
        repleaseResource();
    }

    @Override
    public HomeBean getData() {

        return new HomeBean("clicked add ", 0, false, mipmap[(int) (Math.random() * 8)]);
    }

    @Override
    public List<HomeBean> getItems() {
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
        return entities;
    }

    private void repleaseResource() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
            requestDataRunnable = null;
        }
    }

    private List<HomeBean> createArrayList() {
        List<HomeBean> entities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entities.add(new HomeBean("subject " + i, 0, false, mipmap[i > 7 ? (int) (Math.random() * 8) : i]));
        }
        return entities;
    }

    private Runnable requestDataRunnable = new Runnable() {
        @Override
        public void run() {
            onFinishedListener.onFinished(createArrayList());
        }
    };

    private static class RequestDataHandler extends Handler {

        private WeakReference<MainModel> mWeakReference;

        public RequestDataHandler(MainModel clazz) {
            mWeakReference = new WeakReference<>(clazz);
        }

        @Override
        public void handleMessage(Message msg) {
            MainModel clazz = mWeakReference.get();
            if (clazz != null) {
                switch (msg.what) {

                }
            }
        }
    }
}
