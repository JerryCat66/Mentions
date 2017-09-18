package com.byth.lifesaver.util;

/**
 * 数据加载类型，加载更多时，用AddAll；下来刷新时用ReplaceALL
 *
 * Created by Lee on 2015/7/7.
 */
public enum LoadType {

    AddAll(0, ""), ReplaceALL(1, "");
    private int key;
    private String type;


    private LoadType(int key, String type) {
        this.key = key;
        this.type = type;
    }

    public int getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public static LoadType valueOf(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new IndexOutOfBoundsException("Invalid ordinal");
        }
        return values()[ordinal];
    }

}
