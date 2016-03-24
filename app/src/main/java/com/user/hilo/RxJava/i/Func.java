package com.user.hilo.RxJava.i;

/**
 * Created by Administrator on 2016/3/24.
 * 相当简单，Func接口有两个类型成员，T对应于参数类型而R对应于返回类型。
 */
public interface Func<T, R> {
    R call(T t);
}
