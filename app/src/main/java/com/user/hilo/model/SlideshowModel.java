package com.user.hilo.model;

import android.os.Handler;
import android.os.Message;

import com.user.hilo.interfaces.OnFinishedListener;
import com.user.hilo.model.i.ISlideshowModel;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/4/17.
 */
public class SlideshowModel implements ISlideshowModel {

    private RequestDataHandler mHandler;
    private OnFinishedListener onFinishedListener;
    private boolean isLoadmoreData;

    @Override
    public void FindItems(boolean isLoadmoreData, OnFinishedListener onFinishedListener) {
        if (onFinishedListener == null)
            throw new NullPointerException("must be implements OnFinishedListener");
        this.isLoadmoreData = isLoadmoreData;
        this.onFinishedListener = onFinishedListener;
        if (mHandler == null) {
            mHandler = new RequestDataHandler(this);
        }
        mHandler.postDelayed(requestDataRunnable, 2 * 100);
    }

    private void repleaseResource() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
            requestDataRunnable = null;
        }
    }

    private List<String> createArrayList() {
        return Arrays.asList(new String("AA"),
                new String("BB"),
                new String("CC"),
                new String("DD"),
                new String("EE"),
                new String("FF"),
                new String("GG"),
                new String("HH"),
                new String("II"),
                new String("JJ"),
                new String("KK"));
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
    public Observable<Object> getData() {
        return null;
    }

    @Override
    public Observable<List<? extends Object>> getItems() {
        return null;
    }

    private Runnable requestDataRunnable = new Runnable() {
        @Override
        public void run() {
            onFinishedListener.onFinished(isLoadmoreData, createArrayList());
        }
    };

    private static class RequestDataHandler extends Handler {

        private WeakReference<SlideshowModel> mWeakReference;

        public RequestDataHandler(SlideshowModel clazz) {
            mWeakReference = new WeakReference<>(clazz);
        }

        @Override
        public void handleMessage(Message msg) {
            SlideshowModel clazz = mWeakReference.get();
            if (clazz != null) {
                switch (msg.what) {

                }
            }
        }
    }
}
