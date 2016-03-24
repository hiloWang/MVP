package com.user.hilo.RxJava;

import android.net.Uri;

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

//    4. 泛型回调
    /*ApiWrapper apiWrapper;

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
    }*/

    /**
     * 可以看到比之前的简明了一些。
     * 我们可以通过直接传递一个顶级的cutestCatCallback回调接口给apiWrapper.store来减少回调间的层级调用，
     * 此外作为回调方法的签名是一样的。
     * 但是我们可以做的更好！
     * 5. 你必须把它分开
     *  让我们来看看我们的异步操作（queryCats，queryCats，还有saveTheCutestCat），它们都遵循了相同的模式。
     *  调用它们的方法有一些参数（query、cat）也包括一个回调对象。再次说明：任何异步操作需要携带所需的常规参数和一个回调实例对象。
     *  如果我们试图去分开这几个阶段，每个异步操作仅仅将会携带一个参数对象，然后返回一些携带着回调（信息）的临时对象。
        我们来应用下这样的模式，看看是否对我们有所帮助。
        如果在异步操作中返回一些临时对象，我们需要定义一个出来。这样的一个对象需要包括常见的行为（以回调为单一参数），
        我们将定义这样的类给所有的异步操作使用，这个类就叫它AsyncJob。
        P.S. 称之为AsyncTask更合适一点，但是我不希望你混淆了异步操作跟另外一个存在的抽象概念之间的关系（这是不好的一点）。
     */


    ApiWrapper apiWrapper;

    public AsyncJob<Uri> saveTheCutestCat(final String query) {
        return new AsyncJob<Uri>() {
            @Override
            public void start(final Callback<Uri> callback) {
                apiWrapper.queryCats(query)
                        .start(new Callback<List<Cat>>() {
                            @Override
                            public void onResult(List<Cat> cats) {
                                Cat cat = findCutest(cats);
                                apiWrapper.store(cat)
                                        .start(new Callback<Uri>() {
                                            @Override
                                            public void onResult(Uri uri) {
                                                callback.onResult(uri);
                                            }

                                            @Override
                                            public void onError(Exception e) {
                                                callback.onError(e);
                                            }
                                        });
                            }

                            @Override
                            public void onError(Exception e) {
                                callback.onError(e);
                            }
                        });
            }
        };
    }

    private Cat findCutest(List<Cat> cats) {
        return Collections.max(cats);
    }
    /**
     * 哇，之前的版本更简单些啊，我们现在的优势是什么？
     * 答案就是现在我们可以给客户端返回“组合”操作的AsyncJob<Uri>。所以一个客户端（在 activity 或者 fragment 处）可以用组合起来的工作来操作。
     * 为了让我们的代码拥有之前的可读性，我们从这个事件流中强行进入到里面的操作里。但有件事需要注意，如果某些操作（方法）是异步的，
     * 然后调用它的操作（方法）又是异步的，就比如，查询猫的操作是异步的，然后寻找出最可爱的猫（即使有一个阻塞调用）也是一个异步操作（客户端希望接收的结果）。
     * 所以我们可以使用 AsyncJobs 把我们的方法分解成更小的操作：
     */

}
