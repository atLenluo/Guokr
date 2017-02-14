package com.xing.guokr.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {

    /**
     * 隐藏输入键盘
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        View focusView = activity.getCurrentFocus();
        if (focusView != null) {
            InputMethodManager ime = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            ime.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }
    }
}
