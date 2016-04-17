package com.user.hilo.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/24.
 */
public class Basebean implements Serializable {

    @SerializedName("titleDesc")
    public String titleDesc;

    @SerializedName("photoUrl")
    public String photoUrl;

    @SerializedName("createAt")
    public Date createAt;
}
