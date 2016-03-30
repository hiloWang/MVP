package com.user.hilo.RxJava.WebApi;

import android.net.Uri;

import com.user.hilo.RxJava.entity.Cat;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/3/23.
 * RxJava
 */
public class ApiWrapper {

    Api api;

    public Observable<List<Cat>> queryCats(final String query) {
        return Observable.create(new Observable.OnSubscribe<List<Cat>>() {
            @Override
            public void call(final Subscriber<? super List<Cat>> subscriber) {
                api.queryCats(query, new Api.CatsQueryCallback() {
                    @Override
                    public void onCatsListReceived(List<Cat> cats) {
                        subscriber.onNext(cats);
                    }

                    @Override
                    public void onQueryFailed(Exception e) {
                        subscriber.onError(e);
                    }
                });
            }
        });
    }

    public Observable<Uri> store(final Cat cat) {
        return Observable.create(new Observable.OnSubscribe<Uri>() {
            @Override
            public void call(final Subscriber<? super Uri> subscriber) {
                api.store(cat, new Api.StoreCallback() {
                    @Override
                    public void onCatStored(Uri uri) {
                        subscriber.onNext(uri);
                    }

                    @Override
                    public void onStoreFailed(Exception e) {
                        subscriber.onError(e);
                    }
                });
            }
        });
    }
}
