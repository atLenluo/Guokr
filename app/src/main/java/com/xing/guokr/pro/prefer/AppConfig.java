package com.xing.guokr.pro.prefer;

import android.content.Context;
import android.net.ConnectivityManager;

import com.xing.guokr.utils.SPUtils;

public class AppConfig {

    private static final String LARGE_FONT = "large_font";
    private static final String USER_TEL = "user_tel";
    private static final String USER_PASSWORD = "user_password";

    /**
     * 设置是否显示大字体
     * @param context
     * @param b
     */
    public static void setLargeFont(Context context, boolean b) {
        SPUtils.put(context, LARGE_FONT, b);
    }

    public static boolean isLargeFont(Context context) {
        return (boolean) SPUtils.get(context, LARGE_FONT, false);
    }

    public static void setUserPassword(Context context, String password) {
        SPUtils.put(context, USER_PASSWORD, password);
    }

    public static String getUserPassword(Context context) {
        return (String) SPUtils.get(context, USER_PASSWORD, null);
    }

    public static void setUserTel(Context context, String tel) {
        SPUtils.put(context, USER_TEL, tel);
    }

    public static String getUserTel(Context context) {
        return (String) SPUtils.get(context, USER_TEL, null);
    }
}
