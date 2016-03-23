package com.user.hilo.RxJava.i;

/**
 * Created by Administrator on 2016/3/23.
 * 泛型回调接口(提取inteface共性)
 * T 形参
 */
public interface Callback<T> {
    void onResult(T result);
    void onError(Exception e);
}
