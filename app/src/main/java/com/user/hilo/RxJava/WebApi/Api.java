package com.user.hilo.RxJava.WebApi;

import android.net.Uri;

import com.user.hilo.RxJava.entity.Cat;

import java.util.List;

/**
 * Created by Administrator on 2016/3/23.
 * 我们有个 Web API，能根据给定的查询请求搜索到整个互联网上猫的图片。
 * 每个图片包含可爱指数的参数（描述图片可爱度的整型值）。
 * 我们的任务将会下载到一个猫列表的集合，选择最可爱的那个，然后把它保存到本地。
 *
 * 2.走向异步
     要知道我们身在一个对等待很敏感的世界里，我们也知道不可能只有阻塞式的调用。在 Android 中我们也总需要处理异步代码。
     拿 Android 的 OnClickListener 举个例子，当你需要处理一个控件的点击事件时，你必须提供一个监听器（回调）以供在用户点击控件时被调用。这没有理由使用阻塞的方式去接受点击事件的回调，所以对点击来说总是异步的。现在，让我们也使用异步编程吧。
     异步的网络调用
     开始想象下使用没有阻塞的 HTTP client（例如Ion），还有就是我们的cats-sdk.jar已经更新。它的 API 也换成了异步的方式调用。
     新 API 的接口：
 */
public interface Api {

    //4. 泛型接口
    interface CatsQueryCallback {
        void onCatsListReceived(List<Cat> cats);
        void onQueryFailed(Exception e);
    }

    interface StoreCallback {
        void onCatStored(Uri uri);
        void onStoreFailed(Exception e);
    }

    void queryCats(String query, CatsQueryCallback catsQueryCallback);

    void store(Cat cat, StoreCallback storeCallback);
}
