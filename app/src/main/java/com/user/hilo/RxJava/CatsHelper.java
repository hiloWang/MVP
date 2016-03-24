package com.user.hilo.RxJava;

import android.net.Uri;

import com.user.hilo.RxJava.WebApi.ApiWrapper;
import com.user.hilo.RxJava.entity.Cat;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/3/23.
 *  所以现在我们能异步的获取猫的信息集合列表了，返回正确或错误的结果时都会通过 CatsQueryCallback回调接口。
    因为 API 改变了所以我们也不得不改变我们的CatsHelper:
 */
public class CatsHelper {

    /**
     * 9. 在目前这点上我们只能有AsyncJob<AsyncJob<Uri>>。我们需要往更深处挖吗？我们希望的是，
     * 去把AsyncJob在一个级别上的两个异步操作扁平化成一个单一的异步操作。
     * 现在我们需要的是得到能使方法返回映射成R类型也是AsyncJob<R>类型的操作。这个操作应该像map，
     * 但在最后应该flatten我们嵌套的AsyncJob。我们叫它为flatMap吧，然后就是来实现它：
     * 修复AsyncJob类
     */

  /*  ApiWrapper apiWrapper;

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
    }*/

    /**
     * 10. java8 lambdas
     */

    ApiWrapper apiWrapper;

    public AsyncJob<Uri> saveTheCutestCat(String query) {
        AsyncJob<List<Cat>> asyncListCats = apiWrapper.queryCats(query);
        AsyncJob<Cat> asyncCat = asyncListCats.map(cats -> findCutest(cats));
        AsyncJob<Uri> asyncStoreUri = asyncCat.flatMap(cat -> apiWrapper.store(cat));
        return asyncStoreUri;
    }

    private Cat findCutest(List<Cat> cats) {
        return Collections.max(cats);
    }

}
