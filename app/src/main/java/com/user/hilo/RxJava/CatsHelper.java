package com.user.hilo.RxJava;

import android.net.Uri;
import android.util.Log;

import com.user.hilo.RxJava.WebApi.ApiWrapper;
import com.user.hilo.RxJava.entity.Cat;
import com.user.hilo.RxJava.i.Callback;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/3/23.
 *  所以现在我们能异步的获取猫的信息集合列表了，返回正确或错误的结果时都会通过 CatsQueryCallback回调接口。
    因为 API 改变了所以我们也不得不改变我们的CatsHelper:
 */
public class CatsHelper {

   /* // 3.深入改写
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

    *//**
     * 现在再来看看代码，跟之前一样优雅吗？明显不是了，这很糟糕！现在它有了更多无关代码和花括号，但是逻辑是一样的。

     那么组合在哪呢？他已经不见了！现在你不能像之前那样组合操作了。对于每一个异步操作你都必须创建出回调接口并在代码中插入它们，每一次都需要手动地加入！

     错误传递又在哪？又是一个否定！在这样的代码中错误不会自动地传递，我们需要在更深一层上通过自己手动地再让它传递下去（请看onStoreFailed和onQueryFailed方法）。

     我们很难对这样的代码进行阅读和找出潜在的 bugs。

     结束了？

     结束了又怎样？我们能拿它来干嘛？我们被困在这个没有组合回调的地狱里了吗？前方高能，请抓紧你的安全带哦，我们将努力的去把这些干掉！
     */

    // 4. 泛型回调
   /* 可以从我们的回调接口中找到共同的模式：

    都有一个分发结果的方法（onCutestCatSaved,onCatListReceived,onCatStored）
    它们中大多数（在我们的例子中是全部）有一个用于错误处理的方法（onError,onQueryFailed,onStoreFailed）

    所以我们可以创建一个泛型回调接口去替代原来所有的接口(Callback类)。但是我们不能去改变 API 的调用方法的签名，我们必须创建包装类来间接调用。

    然后我们来创建ApiWrapper来改变一下调用方法的签名(ApiWrapper api包装类)

    所以这仅仅是对于Callback的一些传递 resuts/errors 的调用转发逻辑。
    最后我们的CatsHelper：*/

    ApiWrapper apiWrapper;

    // 这样写 错误的回调接口就会自动传递了. 当queryCats()方法发生错误时, 会通过onError回调,
    // 当store()方法发生错误时,会通过调用者传递的Callback接口实现onError方式回调给调用者
    public void saveTheCutestCat(String query, final Callback<Uri> cutestCatCallback) {
        apiWrapper.queryCats(query, new Callback<List<Cat>>() {
            @Override
            public void onResult(List<Cat> result) {
                Cat cat = findCutest(result);
                apiWrapper.store(cat, cutestCatCallback);
            }

            @Override
            public void onError(Exception e) {
                Log.e("ERROR", "queryCats Error");
                cutestCatCallback.onError(e);
            }
        });
    }

    private Cat findCutest(List<Cat> cats) {
        return Collections.max(cats);
    }

    /**
     * 可以看到比之前的简明了一些。
     * 我们可以通过直接传递一个顶级的cutestCatCallback回调接口给apiWrapper.store来减少回调间的层级调用，
     * 此外作为回调方法的签名是一样的。
     * 但是我们可以做的更好！
     * 5. 你必须把它分开
     */
}
