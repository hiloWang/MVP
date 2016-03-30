package com.user.hilo.RxJava;

import android.net.Uri;

import com.user.hilo.RxJava.WebApi.ApiWrapper;
import com.user.hilo.RxJava.entity.Cat;

import java.util.Collections;
import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/3/23.
 *
 */
public class CatsHelper {

    /**
     * 10. java8 lambdas
     */

   /* ApiWrapper apiWrapper;

    public AsyncJob<Uri> saveTheCutestCat(String query) {
        AsyncJob<List<Cat>> asyncListCats = apiWrapper.queryCats(query);
        AsyncJob<Cat> asyncCat = asyncListCats.map(cats -> findCutest(cats));
        AsyncJob<Uri> asyncStoreUri = asyncCat.flatMap(cat -> apiWrapper.store(cat));
        return asyncStoreUri;
    }

    private Cat findCutest(List<Cat> cats) {
        return Collections.max(cats);
    }*/

    /**
     *  11. RxJava
     */

    ApiWrapper apiWrapper;

    public Observable<Uri> saveTheCutestCat(String query) {
//        Observable<List<Cat>> catsListObservable = apiWrapper.queryCats(query);
//        Observable<Cat> cutestCatobservable = catsListObservable.map(cats -> findCutest(cats));
//        Observable<Uri> storeUriObservable = cutestCatobservable.flatMap(cat -> apiWrapper.store(cat));
//        return storeUriObservable;
        return null;
    }

    private Cat findCutest(List<Cat> cats) {
        return Collections.max(cats);
    }

}
