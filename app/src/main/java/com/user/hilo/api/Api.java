package com.user.hilo.api;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.user.hilo.MyApplication;
import com.user.hilo.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Administrator on 2016/4/18.
 */
public class Api {

    public static final String BASE_URL = "http://m.1332255.com";
    // /mlottery/core/footBallMatch.queryMatchInfos.do?lang=zh&thirdId=310478

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String DAILY_DATE_FORMAT = "yyyy.MM.dd";

    public static final int DEFAULT_DAILY_SIZE = 15;

    public static final int home = 22061;
    public static final int daily = 22062;

    private static Api instance;
    private ApiService apiService;

    public static Api createApiService() {
        if (instance == null) {
            synchronized (Api.class) {
                if (instance == null)
                    instance = new Api();
            }
        }
        return instance;
    }

    private Api() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(7676, TimeUnit.MILLISECONDS);

        /**
         * 检查网络请求情况
         */
        if (LogUtils.DEBUG) {
            okHttpClient.interceptors().add(chain -> {
                Response response = chain.proceed(chain.request());
                LogUtils.I(chain.request().urlString());
                return response;
            });
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(MyApplication.gson))
                .client(okHttpClient)
                .build();
        this.apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
