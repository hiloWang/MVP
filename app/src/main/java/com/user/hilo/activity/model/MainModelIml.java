package com.user.hilo.activity.model;

import android.os.Handler;
import android.os.Message;

import com.user.hilo.R;
import com.user.hilo.activity.interfaces.OnFinishedListener;
import com.user.hilo.activity.model.i.MainModel;
import com.user.hilo.entitys.MainEntity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public class MainModelIml implements MainModel {

    private RequestDataHandler mHandler;
    private OnFinishedListener onFinishedListener;
    private static final int[] mipmap = {
        R.mipmap.center_4, R.mipmap.center_2, R.mipmap.center_3, R.mipmap.center_1, R.mipmap.center_5
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
    public MainEntity getData() {

        return new MainEntity("clicked add ", 0, false, mipmap[(int) (Math.random() * 5)]);
    }

    @Override
    public List<MainEntity> getItems() {
        List<MainEntity> entities = new ArrayList<>();
        entities.addAll(Arrays.asList(
                new MainEntity("A", 0, false, mipmap[0]),
                new MainEntity("B", 0, false, mipmap[1]),
                new MainEntity("C", 0, false, mipmap[2]),
                new MainEntity("D", 0, false, mipmap[3]),
                new MainEntity("E", 0, false, mipmap[4]),
                new MainEntity("F", 0, false, mipmap[4]),
                new MainEntity("G", 0, false, mipmap[1])
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

    private List<MainEntity> createArrayList() {
        List<MainEntity> entities = new ArrayList<>();
        for (int i=0; i<10; i++) {
            entities.add(new MainEntity("subject "+i, 0, false, mipmap[i>4 ? 1 : i]));
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

        private WeakReference<MainModelIml> mWeakReference;

        public RequestDataHandler(MainModelIml clazz) {
            mWeakReference = new WeakReference<>(clazz);
        }

        @Override
        public void handleMessage(Message msg) {
            MainModelIml clazz = mWeakReference.get();
            if (clazz != null) {
                switch (msg.what) {

                }
            }
        }
    }
}
