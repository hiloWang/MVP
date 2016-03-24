package com.user.hilo.RxJava;

import com.user.hilo.RxJava.i.Callback;
import com.user.hilo.RxJava.i.Func;

/**
 * Created by Administrator on 2016/3/23.
 * 供所有的异步操作使用, 携带着回调（信息）的临时对象(例如Callback实例, 不应该让调用者使用异步的实例对象, 而是临时对象)
 */
public abstract class AsyncJob<T> {
    public abstract void start(Callback<T> callback);

    public <R> AsyncJob<R> map(final Func<T, R> func) {
        final AsyncJob<T> source = this;
        return new AsyncJob<R>() {
            @Override
            public void start(final Callback<R> callback) {
                source.start(new Callback<T>() {
                    @Override
                    public void onResult(T t) {
                        R r = func.call(t);
                        callback.onResult(r);
                    }

                    @Override
                    public void onError(Exception e) {
                        callback.onError(e);
                    }
                });
            }
        };
    }
}
