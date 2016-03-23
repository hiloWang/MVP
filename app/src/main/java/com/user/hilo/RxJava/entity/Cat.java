package com.user.hilo.RxJava.entity;

import android.graphics.Bitmap;
import android.os.Build;

/**
 * Created by Administrator on 2016/3/23.
 * Cat 简单数据结果 通过compareTo返回哪只猫最可爱
 */
public class Cat implements Comparable<Cat> {

    private Bitmap image;
    private int cuteness;

    @Override
    public int compareTo(Cat another) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Integer.compare(cuteness, another.cuteness);
        }
        return new Integer(cuteness).compareTo(new Integer(another.cuteness));
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getCuteness() {
        return cuteness;
    }

    public void setCuteness(int cuteness) {
        this.cuteness = cuteness;
    }
}
