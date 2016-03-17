package com.user.hilo.activity.model;

import android.os.Handler;
import android.os.Message;

import com.user.hilo.activity.interfaces.OnFinishedListener;
import com.user.hilo.activity.model.i.MainModel;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public class MainModelIml implements MainModel {

    private RequestDataHandler mHandler;
    private OnFinishedListener onFinishedListener;

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

    private void repleaseResource() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
            requestDataRunnable = null;
        }
    }

    private List<String> createArrayList() {
        return Arrays.asList(
                "Item 1",
                "Item 2",
                "Item 3",
                "Item 4",
                "Item 5",
                "Item 6",
                "Item 7",
                "Item 8",
                "Item 9",
                "Item 10"
        );
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
