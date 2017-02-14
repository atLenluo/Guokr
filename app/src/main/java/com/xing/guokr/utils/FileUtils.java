package com.xing.guokr.utils;

import java.io.File;

public class FileUtils {

    /**
     * 获取文件的大小
     * @param file
     * @return
     */
    public static long getFileSize(File file) {
        if (file == null || !file.exists()) {
            return 0;
        }
        long size = 0;
        if (file.isDirectory()) {
            // 目录
            File[] files = file.listFiles();
            for (File f : files) {
                size = size + getFileSize(f);
            }
        } else if(file.isFile()) {
            // 文件
            size = file.length();
        }
        return size;
    }

    public static boolean deleteFile(File file) {
        if(file == null || !file.exists()) {
            return false;
        }

        if(file.isFile()) {
            return file.delete();
        } else if(file.isDirectory()) {
            boolean b = false;
            File[] files = file.listFiles();
            for (File f : files) {
                b = deleteFile(f);
            }
            return b;
        }
        return false;
    }
}
