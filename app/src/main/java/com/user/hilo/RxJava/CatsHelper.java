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

    Api api;

   /* 传统:
   public Uri saveTheCutestCat(String query) {
        try {
            List<Cat> cats = api.queryCats(query);
            Cat cat = findCutest(cats);
            Uri saveUri = api.store(cat);
            return saveUri;
        } catch (Exception e) {
            e.printStackTrace();
            return Uri.parse("");
        }
    }

    private Cat findCutest(List<Cat> cats) {
        return Collections.max(cats);
    }*/


    interface CutestCatCallback {
        void onCutestCatSaved(Uri uri);
        void onQueryFailed(Exception e);
    }



    /**
     * ps: 因为异步的原因, 取消了返回值Uri, 提供了返回Uri的接口.
     * 现在我们已经不能使用阻塞的 API 了，
     * 我们也不能把我们的客户端上写成阻塞式的调用（实际上是可以的，但需要明确的在线程中使用synchronized、CountdownLatch、等等，也需要在下层中使用异步处理）。
     * 所以我们不能在 saveTheCutestCat方法中直接返回一个值，我们需要对它进行异步回调处理。让我们再深入一点，
     * 如果我们 API 的两个方法调用都是异步的，举个例子，我们正在使用非阻塞IO去写进一个文件等。
     * @param query
     * @param cutestCatCallback
     */
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
    }
}
