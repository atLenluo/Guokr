package com.xing.guokr.utils;

import android.content.Context;
import android.media.audiofx.BassBoost;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.xing.guokr.R;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificationUtils {

    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 验证是否是用户名
     * @param context
     * @param name
     * @return
     */
    public static boolean isNickName(Context context, String name) {
        if(TextUtils.isEmpty(name)) {
            ToastUtils.show(context, R.string.error_nickname_empty);
            return false;
        }
        if(name.length() < 2) {
            ToastUtils.show(context, "昵称不能少于2个字符");
            return false;
        }
        if(name.length() > 16) {
            ToastUtils.show(context, "昵称不能大于16个字符");
            return false;
        }
        String regEx = "[^`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]{1,}";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(name);
        if(!m.matches()) {
            ToastUtils.show(context, R.string.error_nickname_illegal_character);
            return false;
        }
        return true;
    }

    /**
     * 验证密码
     * @param context
     * @param passwordStr
     * @param passwordConfirmStr
     * @return
     */
    public static boolean veryPassword(Context context, String passwordStr, String passwordConfirmStr) {
        if(TextUtils.isEmpty(passwordStr)) {
            ToastUtils.show(context, R.string.error_password_empty);
            return false;
        }
        if(!passwordStr.equalsIgnoreCase(passwordConfirmStr)) {
            ToastUtils.show(context, R.string.error_password_unequal);
            return false;
        }
        return true;
    }

    public static boolean checkUserName(Context context, String userName) {
        if(TextUtils.isEmpty(userName)) {
            ToastUtils.show(context, R.string.error_username_empty);
            return false;
        }
        if(!isEmail(userName) && !isMobile(userName)) {
            ToastUtils.show(context, R.string.error_illegal_username);
            return false;
        }
        return true;
    }


    public static boolean isEmail(String email) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher("134654");
        boolean isMatched = matcher.matches();
        return isMatched;
    }


    /** 对字符串进行MD5加密 */
    public static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                // 创建具有指定算法名称的信息摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md.digest(originString.getBytes());
                // 将得到的字节数组变成字符串返回
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
