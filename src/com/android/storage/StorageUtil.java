package com.android.storage;

import java.io.File;                                                                                                

import android.os.Environment;    
import android.os.StatFs;    
    
public class StorageUtil {

    private static final int ERROR = -1;

    /**
     * 外存是否可存
     */
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机内部剩余存储空间
     * @return
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取手机内部总的存储空间
     * @return
     */
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 获取外存剩余存储空间
     * @return
     */
    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return ERROR;
        }
    }

    /**
     * 获取外存总的存储空间
     * @return
     */
    public static long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return ERROR;
        }
    }
    
    /**
     * 获取外部存储目录
     * @return
     */
    public static String getExternalStorageDirectory(){
    	File path = Environment.getExternalStorageDirectory();
    	return path.getPath();
    }
    
    /**
     * 获取Android 数据目录/手机内部存储目录
     * @return
     */
    public static String getDataDirectory(){
    	File path = Environment.getDataDirectory();
    	return path.getPath();
    }
    
    /**
     * 获取Android 下载/缓存内容目录
     * @return
     */
    public static String getDownloadCacheDirectory(){
    	File path = Environment.getDownloadCacheDirectory();
    	return path.getPath();
    }
    
    /**
     * 获取Android 的根目录
     * @return
     */
    public static String getRootDirectory(){
    	File path = Environment.getRootDirectory();
    	return path.getPath();
    }
    
    /**
     * 外存是否移除
     * @return
     */
    public static boolean isExternalStorageRemovable(){
    	return Environment.isExternalStorageRemovable();
    }
    
}

