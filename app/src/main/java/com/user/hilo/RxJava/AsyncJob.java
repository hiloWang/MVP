package com.user.hilo.RxJava;

import com.user.hilo.RxJava.i.Callback;

/**
 * Created by Administrator on 2016/3/23.
 * 供所有的异步操作使用, 携带着回调（信息）的临时对象(例如Callback实例, 不应该让调用者使用异步的实例对象, 而是临时对象)
 */
public abstract class AsyncJob<T> {
    public abstract void start(Callback<T> callback);
}
