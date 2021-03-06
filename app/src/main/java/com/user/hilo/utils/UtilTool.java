package com.user.hilo.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;


public class UtilTool {

    public static void appLogout(Context context) {
    }

    public static void setVariablesNull() {

    }

    /**
     * 判断当前应用是否处于debug状态
     *
     * @param context
     * @return
     */
    public static boolean isApkDebugable(Context context) {
        boolean flag = false;
        try {
            if (null != context) {
                ApplicationInfo info = context.getApplicationInfo();
                flag = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
