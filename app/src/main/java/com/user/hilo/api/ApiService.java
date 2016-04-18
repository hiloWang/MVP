package com.user.hilo.api;

import com.user.hilo.bean.DailyBean;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/18.
 */
public interface ApiService {

    /**
     * http://m.1332255.com/mlottery/core/mainPage.findAndroidMainRsts.do?lang=zh
     *
     * @param language
     * @return
     */
    @GET("mlottery/core/mainPage.findAndroidMainRsts.do")
    Observable<DailyBean> getDailyData(@Query("lang") String language);

}
