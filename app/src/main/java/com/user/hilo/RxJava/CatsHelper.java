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

  /*  ApiWrapper apiWrapper;

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
    }*/
    /**
     * 6. 哇，之前的版本更简单些啊，我们现在的优势是什么？
     * 答案就是现在我们可以给客户端返回“组合”操作的AsyncJob<Uri>。所以一个客户端（在 activity 或者 fragment 处）可以用组合起来的工作来操作。
     * 为了让我们的代码拥有之前的可读性，我们从这个事件流中强行进入到里面的操作里。但有件事需要注意，如果某些操作（方法）是异步的，
     * 然后调用它的操作（方法）又是异步的，就比如，查询猫的操作是异步的，然后寻找出最可爱的猫（即使有一个阻塞调用）也是一个异步操作（客户端希望接收的结果）。
     * 所以我们可以使用 AsyncJobs 把我们的方法分解成更小的操作：
     */

    ApiWrapper apiWrapper;

    public AsyncJob<Uri> saveTheCutestCat(String query) {
        final AsyncJob<List<Cat>> asyncListcats = apiWrapper.queryCats(query);
        final AsyncJob<Cat> asyncCat = new AsyncJob<Cat>() {
            @Override
            public void start(final Callback<Cat> callback) {
                asyncListcats.start(new Callback<List<Cat>>() {
                    @Override
                    public void onResult(List<Cat> cats) {
                        callback.onResult(findCutest(cats));
                    }

                    @Override
                    public void onError(Exception e) {
                        callback.onError(e);
                    }
                });
            }
        };

        AsyncJob<Uri> asyncStoreUri = new AsyncJob<Uri>() {
            @Override
            public void start(final Callback<Uri> callback) {
                asyncCat.start(new Callback<Cat>() {
                    @Override
                    public void onResult(Cat cat) {
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
        return asyncStoreUri;
    }

    private Cat findCutest(List<Cat> cats) {
        return Collections.max(cats);
    }

    /**
     * 7. 代码量多了许多，但是更加清晰了。低层次嵌套的回调，利于理解的变量名（catsListAsyncJob、cutestCatAsyncJob、storedUriAsyncJob）。
     * 看起来好了许多，但我们要再做一些事情：
     * findCutest(result)只有一行是对我们有用（对于逻辑来说）的操作：
     * 剩下的仅仅是开启另外一个AsyncJob和传递结果与错误的样板代码。此外，这些代码并不用于特定的任务，我们可以把其移动到其它地方而不影响编写我们真正需要的业务代码。
     * 我们该怎么写呢？我们必须做下面的两件事情：
     * AsyncJob是我们转换的结果转换方法这又有另外一个问题，
     * 因为在 Java 中不能直接传递方法（函数）所以我们需要通过类（和接口）来间接实现这样的功能，然后我们就来定义这个 “方法”：
     */
}
