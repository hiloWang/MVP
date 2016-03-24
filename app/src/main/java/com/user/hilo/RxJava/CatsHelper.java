package com.user.hilo.RxJava;

import android.net.Uri;

import com.user.hilo.RxJava.WebApi.ApiWrapper;
import com.user.hilo.RxJava.entity.Cat;
import com.user.hilo.RxJava.i.Func;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/3/23.
 *  所以现在我们能异步的获取猫的信息集合列表了，返回正确或错误的结果时都会通过 CatsQueryCallback回调接口。
    因为 API 改变了所以我们也不得不改变我们的CatsHelper:
 */
public class CatsHelper {

    /**
     *  8.现在好多了，创建AsyncJob<Cat> cutestCatAsyncJob只需要 6 行代码而回调也只有一个层级了。
     * 高级映射
     * 前面的那些已经很赞了，但是创建AsyncJob<Uri> storedUriAsyncJob的部分还有些不忍直视。能在这里创建映射吗？我们来试试吧：
     */

    /*ApiWrapper apiWrapper;

    public AsyncJob<AsyncJob<Uri>> saveTheCutestCat(String query) {
        AsyncJob<List<Cat>> asyncListCats = apiWrapper.queryCats(query);
        final AsyncJob<Cat> asyncCat = asyncListCats.map(new Func<List<Cat>, Cat>() {
            @Override
            public Cat call(List<Cat> cats) {
                return findCutest(cats);
            }
        });

        AsyncJob<AsyncJob<Uri>> asyncStoreUri = asyncCat.map(new Func<Cat, AsyncJob<Uri>>() {
            @Override
            public AsyncJob<Uri> call(Cat cat) {
                return apiWrapper.store(cat);
            }
        });
        return asyncStoreUri;
    }

    private Cat findCutest(List<Cat> cats) {
        return Collections.max(cats);
    }*/

    /**
     * 9. 在目前这点上我们只能有AsyncJob<AsyncJob<Uri>>。我们需要往更深处挖吗？我们希望的是，
     * 去把AsyncJob在一个级别上的两个异步操作扁平化成一个单一的异步操作。
     * 现在我们需要的是得到能使方法返回映射成R类型也是AsyncJob<R>类型的操作。这个操作应该像map，
     * 但在最后应该flatten我们嵌套的AsyncJob。我们叫它为flatMap吧，然后就是来实现它：
     * 修复AsyncJob类
     */

    ApiWrapper apiWrapper;

    public AsyncJob<Uri> saveTheCutestCat(String query) {
        AsyncJob<List<Cat>> asyncListCats = apiWrapper.queryCats(query);
        AsyncJob<Cat> asyncCat = asyncListCats.map(new Func<List<Cat>, Cat>() {
            @Override
            public Cat call(List<Cat> cats) {
                return findCutest(cats);
            }
        });

        AsyncJob<Uri> asyncStoreUri = asyncCat.flatMap(new Func<Cat, AsyncJob<Uri>>() {
            @Override
            public AsyncJob<Uri> call(Cat cat) {
                return apiWrapper.store(cat);
            }
        });
        return asyncStoreUri;
    }

    private Cat findCutest(List<Cat> cats) {
        return Collections.max(cats);
    }
}
