package com.xing.guokr.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static Toast mToast;

    public static void show(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }

    public static void show(Context context, int resId) {
        show(context, context.getString(resId));
    }
}
