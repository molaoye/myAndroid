package com.android.storage;

import java.io.File;                                                                                                

import android.os.Environment;    
import android.os.StatFs;    
    
public class StorageUtil {

    private static final int ERROR = -1;

    /**
     * ����Ƿ�ɴ�
     */
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * ��ȡ�ֻ��ڲ�ʣ��洢�ռ�
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
     * ��ȡ�ֻ��ڲ��ܵĴ洢�ռ�
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
     * ��ȡ���ʣ��洢�ռ�
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
     * ��ȡ����ܵĴ洢�ռ�
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
     * ��ȡ�ⲿ�洢Ŀ¼
     * @return
     */
    public static String getExternalStorageDirectory(){
    	File path = Environment.getExternalStorageDirectory();
    	return path.getPath();
    }
    
    /**
     * ��ȡAndroid ����Ŀ¼/�ֻ��ڲ��洢Ŀ¼
     * @return
     */
    public static String getDataDirectory(){
    	File path = Environment.getDataDirectory();
    	return path.getPath();
    }
    
    /**
     * ��ȡAndroid ����/��������Ŀ¼
     * @return
     */
    public static String getDownloadCacheDirectory(){
    	File path = Environment.getDownloadCacheDirectory();
    	return path.getPath();
    }
    
    /**
     * ��ȡAndroid �ĸ�Ŀ¼
     * @return
     */
    public static String getRootDirectory(){
    	File path = Environment.getRootDirectory();
    	return path.getPath();
    }
    
    /**
     * ����Ƿ��Ƴ�
     * @return
     */
    public static boolean isExternalStorageRemovable(){
    	return Environment.isExternalStorageRemovable();
    }
    
}

