package com.user.hilo;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.user.hilo.others.Configurations;
import com.user.hilo.receiver.CrashHandler;
import com.user.hilo.utils.LogUtils;
import com.user.hilo.utils.UIUtils;

/**
 * Created by Administrator on 2016/3/17.
 */
public class MyApplication extends Application {

    public static Context mContext;
//    public static Typeface font;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Configurations cfg = Configurations.getConfig();
        Configurations.saveConfig();
        cfg.deviceType = 1;

//        if (getResources().getBoolean(R.bool.isTablet))
        if (UIUtils.isTablet(getResources()))
            cfg.deviceType = 2;
        cfg.deviceName = android.os.Build.BRAND + "_" + android.os.Build.PRODUCT;
        LogUtils.I("deviceName: " + cfg.deviceName);

//        AssetManager mgr = this.getAssets();
//        font = Typeface.createFromAsset(mgr, "fonts/PingFang Medium.ttf");

        // 检查内存泄露
        LeakCanary.install(this);
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }
}
