package com.user.hilo.RxJava.WebApi;

import android.net.Uri;

import com.user.hilo.RxJava.AsyncJob;
import com.user.hilo.RxJava.entity.Cat;
import com.user.hilo.RxJava.i.Callback;

import java.util.List;

/**
 * Created by Administrator on 2016/3/23.
 */
public class ApiWrapper {

    Api api;

    public AsyncJob<List<Cat>> queryCats(final String query) {
        return new AsyncJob<List<Cat>>() {
            @Override
            public void start(final Callback<List<Cat>> callback) {
                api.queryCats(query, new Api.CatsQueryCallback() {
                    @Override
                    public void onCatsListReceived(List<Cat> cats) {
                        callback.onResult(cats);
                    }

                    @Override
                    public void onQueryFailed(Exception e) {
                        callback.onError(e);
                    }
                });
            }
        };
    }

    public AsyncJob<Uri> store(final Cat cat) {
        return new AsyncJob<Uri>() {
            @Override
            public void start(final Callback<Uri> callback) {
                api.store(cat, new Api.StoreCallback() {
                    @Override
                    public void onCatStored(Uri uri) {
                        callback.onResult(uri);
                    }

                    @Override
                    public void onStoreFailed(Exception e) {
                        callback.onError(e);
                    }
                });
            }
        };
    }
}
