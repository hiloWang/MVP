package com.user.hilo.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/24.
 */
public class DailyBean extends Error implements Serializable {

    public DailyBean(String testName) {
        this.testName = testName;
    }

    @SerializedName("testName")
    public String testName;
}
