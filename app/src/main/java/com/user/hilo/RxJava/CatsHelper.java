package com.user.hilo.RxJava;

import android.net.Uri;

import com.user.hilo.RxJava.WebApi.ApiWrapper;
import com.user.hilo.RxJava.entity.Cat;
import com.user.hilo.RxJava.i.Callback;
import com.user.hilo.RxJava.i.Func;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/3/23.
 *  所以现在我们能异步的获取猫的信息集合列表了，返回正确或错误的结果时都会通过 CatsQueryCallback回调接口。
    因为 API 改变了所以我们也不得不改变我们的CatsHelper:
 */
public class CatsHelper {

   /*  6. 简化
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
*/
    /**
     * 7. 代码量多了许多，但是更加清晰了。低层次嵌套的回调，利于理解的变量名（catsListAsyncJob、cutestCatAsyncJob、storedUriAsyncJob）。
     * 看起来好了许多，但我们要再做一些事情：
     * findCutest(result)只有一行是对我们有用（对于逻辑来说）的操作：
     * 剩下的仅仅是开启另外一个AsyncJob和传递结果与错误的样板代码。此外，这些代码并不用于特定的任务，我们可以把其移动到其它地方而不影响编写我们真正需要的业务代码。
     * 我们该怎么写呢？我们必须做下面的两件事情：
     * AsyncJob是我们转换的结果转换方法这又有另外一个问题，
     * 因为在 Java 中不能直接传递方法（函数）所以我们需要通过类（和接口）来间接实现这样的功能，然后我们就来定义这个 “方法”：
     * public interface Func<T, R> {}
     * 相当简单，Func接口有两个类型成员，T对应于参数类型而R对应于返回类型。
     * 当我们从一个AsyncJob中装换处结果后我们就需要做一些值之间的映射，这样的方法我们就叫它map。
     * 定义这个方法实例（Func 类型）最好的地方就在AsyncJob类中，所以AsyncJob代码里看起来就是这样了：
     */

    ApiWrapper apiWrapper;

    public AsyncJob<Uri> saveTheCutestCat(String query) {
        final AsyncJob<List<Cat>> asyncListCats = apiWrapper.queryCats(query);
        final AsyncJob<Cat> asyncCat = asyncListCats.map(new Func<List<Cat>, Cat>() {
            @Override
            public Cat call(List<Cat> cats) {
                return findCutest(cats);
            }
        });

        AsyncJob<Uri> asyncStoreUri = new AsyncJob<Uri>() {
            @Override
            public void start(final Callback<Uri> callback) {
                asyncCat.start(new Callback<Cat>() {
                    @Override
                    public void onResult(Cat cat) {
                        apiWrapper.store(cat).start(new Callback<Uri>() {
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
     *  8.现在好多了，创建AsyncJob<Cat> cutestCatAsyncJob只需要 6 行代码而回调也只有一个层级了。
     * 高级映射
     * 前面的那些已经很赞了，但是创建AsyncJob<Uri> storedUriAsyncJob的部分还有些不忍直视。能在这里创建映射吗？我们来试试吧：
     */
}
