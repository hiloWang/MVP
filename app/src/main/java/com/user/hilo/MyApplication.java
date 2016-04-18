package com.user.hilo;

import android.app.Application;
import android.content.Context;

import com.anupcowkur.reservoir.Reservoir;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.leakcanary.LeakCanary;
import com.user.hilo.api.Api;
import com.user.hilo.others.Configurations;
import com.user.hilo.receiver.CrashHandler;
import com.user.hilo.utils.LogUtils;
import com.user.hilo.utils.UIUtils;

/**
 * Created by Administrator on 2016/3/17.
 */
public class MyApplication extends Application {

    public static Context mContext;
    public static Gson gson;
    //    public static Typeface font;
    public static final long ONE_KB = 1024L;
    public static final long ONE_MB = ONE_KB * 1024L;
    public static final long CACHE_DATA_MAX_SIZE = ONE_MB * 3L;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
//        Logger.init();
        initGson();
        initReservoir();

        // 检查内存泄露
        LeakCanary.install(this);
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

        /**
         * AndroidDevMetrics measures how performant are Activity lifecycle methods in your app.
         * It can give you information how much time was needed to open new Activity (or Application if it was launch screen)
         * and where are possible bottlenecks.
         */
        if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this);
        }

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


    }

    private void initGson() {
        this.gson = new GsonBuilder()
                .setDateFormat(Api.DATE_FORMAT)
                .create();
    }

    private void initReservoir() {
        try {
            Reservoir.init(this, CACHE_DATA_MAX_SIZE, this.gson);
        } catch (Exception e) {
            //failure
            e.printStackTrace();
        }
    }
}
