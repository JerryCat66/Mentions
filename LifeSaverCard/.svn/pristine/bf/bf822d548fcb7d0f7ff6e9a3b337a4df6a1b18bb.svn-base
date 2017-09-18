package com.fenguo.library.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

    // share数据保存
    /**
     * 聊天声音
     */
    public static  String VOICE="voice";
    /**
     * 聊天振动
     */
    public static  String RATING="rating";

    /**
     * 保存当前位置信息
     */
    public static final String CURRENT_POSITION = "Current_position";

    private static Preference catche;
    private SharedPreferences spf;

    public static Preference instance(Context context) {
        if (catche == null) {
            catche = new Preference(context.getApplicationContext());
        }
        return catche;
    }

    public static Preference getInstance() {
        return catche;
    }

    public Preference(Context context) {
        if (spf == null) {
            spf = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        }
    }

    public void putBoolean(String key, boolean value) {
        spf.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return spf.getBoolean(key, false);
    }

    public void putString(String key, String value) {
        spf.edit().putString(key, value).commit();
    }

    public String getString(String key) {
        return spf.getString(key, "");
    }

    public void putInt(String key, int value) {
        spf.edit().putInt(key, value).commit();
    }

    public void putLong(String key, long value) {
        spf.edit().putLong(key, value).commit();
    }

    public void putFloat(String key, float value) {
        spf.edit().putFloat(key, value).commit();
    }

    public float getFolat(String key) {
        return spf.getFloat(key, -1);
    }

    public float getFolat(String key, float defaultValue) {
        return spf.getFloat(key, defaultValue);
    }

    public int getInt(String key) {
        return spf.getInt(key, -1);
    }

    public int getInt(String key, int defaultValue) {
        return spf.getInt(key, defaultValue);
    }

    public long getLong(String key) {
        return spf.getLong(key, -1);
    }

    public long getLong(String key, long def) {
        return spf.getLong(key, def);
    }

    public void clearData() {
        spf.edit().clear().commit();
    }

    public void remove(String key) {
        spf.edit().remove(key).commit();
    }

    public void commit() {
        spf.edit().commit();
    }

    public String getCurrentPosition() {
        String pos = getString(CURRENT_POSITION);
        if (pos != null && !pos.equals("")) {
            return pos;
        } else {
            return "暂无位置信息";
        }
    }
}
