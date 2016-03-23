package com.user.hilo.RxJava;

import android.net.Uri;

import com.user.hilo.RxJava.entity.Cat;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/3/23.
 *  所以现在我们能异步的获取猫的信息集合列表了，返回正确或错误的结果时都会通过 CatsQueryCallback回调接口。
    因为 API 改变了所以我们也不得不改变我们的CatsHelper:
 */
public class CatsHelper {

    /* 2.异步
    Api api;

    interface CutestCatCallback {
        void onCutestCatSaved(Uri uri);
        void onQueryFailed(Exception e);
    }

    *//**
     * ps: 因为异步的原因, 取消了返回值Uri, 提供了返回Uri的接口.
     * 现在我们已经不能使用阻塞的 API 了，
     * 我们也不能把我们的客户端上写成阻塞式的调用（实际上是可以的，但需要明确的在线程中使用synchronized、CountdownLatch、等等，也需要在下层中使用异步处理）。
     * 所以我们不能在 saveTheCutestCat方法中直接返回一个值，我们需要对它进行异步回调处理。让我们再深入一点，
     * 如果我们 API 的两个方法调用都是异步的，举个例子，我们正在使用非阻塞IO去写进一个文件等。
     * @param query
     * @param cutestCatCallback
     *//*
    public void saveTheCutestCat(String query, final CutestCatCallback cutestCatCallback) {
        api.queryCats(query, new Api.CatsQueryCallback() {
            @Override
            public void onCatsListReceived(List<Cat> cats) {
                Cat cat = findCutest(cats);
                Uri saveUri = api.store(cat);
                cutestCatCallback.onCutestCatSaved(saveUri);
            }

            @Override
            public void onError(Exception e) {
                cutestCatCallback.onQueryFailed(e);
            }
        });
    }

    private Cat findCutest(List<Cat> cats) {
        return Collections.max(cats);
    }*/

    // 3.深入改写
    Api api;

    interface CutestCatCallback {
        void onCutestCatSaved(Uri uri);
        void onError(Exception e);
    }

    public void saveTheCutestCat(String query, final CutestCatCallback cutestCatCallback) {
        api.queryCats(query, new Api.CatsQueryCallback() {
            @Override
            public void onCatsListReceived(List<Cat> cats) {
                Cat cat = findCutest(cats);
                api.store(cat, new Api.StoreCallback() {
                    @Override
                    public void onCatStored(Uri uri) {
                        cutestCatCallback.onCutestCatSaved(uri);
                    }

                    @Override
                    public void onStoreFailed(Exception e) {
                        cutestCatCallback.onError(e);
                    }
                });
            }

            @Override
            public void onQueryFailed(Exception e) {
                cutestCatCallback.onError(e);
            }
        });
    }
    private Cat findCutest(List<Cat> cats) {
        return Collections.max(cats);
    }

    /**
     * 现在再来看看代码，跟之前一样优雅吗？明显不是了，这很糟糕！现在它有了更多无关代码和花括号，但是逻辑是一样的。

     那么组合在哪呢？他已经不见了！现在你不能像之前那样组合操作了。对于每一个异步操作你都必须创建出回调接口并在代码中插入它们，每一次都需要手动地加入！

     错误传递又在哪？又是一个否定！在这样的代码中错误不会自动地传递，我们需要在更深一层上通过自己手动地再让它传递下去（请看onStoreFailed和onQueryFailed方法）。

     我们很难对这样的代码进行阅读和找出潜在的 bugs。

     结束了？

     结束了又怎样？我们能拿它来干嘛？我们被困在这个没有组合回调的地狱里了吗？前方高能，请抓紧你的安全带哦，我们将努力的去把这些干掉！
     */
}
