package com.byth.lifesaver.util.upgrade;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Administrator on 2017/8/25 0025.
 * app升级操作接口
 */

public interface AppUpgrade {
    /**
     * 初始化
     * context=getApplicationContext
     *
     * @param context
     */
    void init(Context context);

    /**
     * 反初始化，在程序退出时调用
     */
    void unInit();

    /**
     * 检测升级
     *
     * @param activity
     */
    void checkLatestVersion(Activity activity);

    /**
     * 后台检测升级
     */
    void checkLatestVersionBackground();

    /**
     * 获取最新版本
     *
     * @param activity
     */
    void foundLatestVersion(Activity activity);
}
