package com.user.hilo.RxJava;

import android.net.Uri;

import com.user.hilo.RxJava.entity.Cat;

import java.util.List;

/**
 * Created by Administrator on 2016/3/23.
 * 我们有个 Web API，能根据给定的查询请求搜索到整个互联网上猫的图片。
 * 每个图片包含可爱指数的参数（描述图片可爱度的整型值）。
 * 我们的任务将会下载到一个猫列表的集合，选择最可爱的那个，然后把它保存到本地。
 */
public interface Api {
    // 传统风格的api,他被打包进cat-sdk.jar中
    List<Cat> queryCats(String query);
    Uri store(Cat cat);
}
