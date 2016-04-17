package com.user.hilo.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/24.
 */
public class DailyBean extends Error implements Serializable {

    @SerializedName("results")
    public DailyResults results;

    @SerializedName("category")
    public ArrayList<String> category;

    public class DailyResults {

        @SerializedName("测试数据")
        public ArrayList<Basebean> testData;
    }
}
