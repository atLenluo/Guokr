package com.xing.guokr.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2017/1/9.
 */

public class NetUtils {

    /**
     * 判断网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetAvailable(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }
}
