package com.fenguo.library.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.ImageColumns;

import java.io.File;

public class SdCardUtil {

    public static String Sd = Environment.getExternalStorageDirectory().getPath();
    public static String catch_path = ""; // 应用的cache目录用于存放缓存
    public static String DCIM = Sd + "/DCIM/";


    /**
     * 判断是否有sd
     */
    public static boolean checkSdState() {
        String state = Environment.getExternalStorageState();
        if (state != null && state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取拓展存储Cache的绝对路径
     *
     * @param context
     */
    public static String getExternalCacheDir(Context context) {
        if (!SdCardUtil.checkSdState())
            return null;
        StringBuilder sb = new StringBuilder();
        File file = context.getExternalCacheDir();
        // In some case, even the sd card is mounted,
        // getExternalCacheDir will return null
        // may be it is nearly full.
        if (file != null) {
            sb.append(file.getAbsolutePath()).append(File.separator);
        } else {
            sb.append(Environment.getExternalStorageDirectory().getPath()).append("/Android/data/").append(context.getPackageName())
                    .append("/cache/").append(File.separator).toString();
        }
        return sb.toString();
    }

    public static String getExternalFilesDir(Context context, String type) {
        if (!SdCardUtil.checkSdState())
            return null;
        StringBuilder sb = new StringBuilder();
        File file = context.getExternalFilesDir(type);
        // In some case, even the sd card is mounted,
        // getExternalCacheDir will return null
        // may be it is nearly full.
        if (file != null) {
            sb.append(file.getAbsolutePath()).append(File.separator);
        } else {
            sb.append(Environment.getExternalStorageDirectory().getPath()).append("/Android/data/").append(context.getPackageName())
                    .append("/files/").append(File.separator).toString();
        }
        return sb.toString();
    }

    /**
     * 获取拍照路径
     *
     * @param context
     * @return
     */
    public static String getCaremaPath(Context context) {
        return getExternalCacheDir(context) + "carema.jpg";
    }


    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
