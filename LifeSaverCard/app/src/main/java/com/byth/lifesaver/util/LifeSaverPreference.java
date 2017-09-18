package com.byth.lifesaver.util;

/**
 * Created by Administrator on 2017/5/23 0023.
 * 生命宝若干数据持久化
 */

public class LifeSaverPreference {
    public static final String SESSION_ID = "session_id";//用户唯一标识
    public static final String ID = "id";
    /**
     * 第一次进入
     */
    public static final String FIRST_IN = "first_in";
    /**
     * 账号
     */
    public static final String ACCOUNT = "account";
    /**
     * 密码
     */
    public static final String PASSWORD = "password";
    public static final String OPERATION_CODE = "operation_code";//用户是否可以操作
    /**
     * 复审判断
     */
    public static final String MINE_PROFILE_IS_STATE = "mine_profile_is_state";
    public static final String ISOPERATINGCARD = "isOperatingCard";
    public static final String OPENID = "openid";

    /*==========================检查更新持久化数据================*/
    public static final String DOWNLOAD_TASK_ID = "download_task_id";//下载id
    public static final String VERSION_CODE = "version_code";//版本号
    public static final String VERSION_NAME = "version_name";//版本名
    public static final String VERSION_URL = "url";//下载地址
    public static final String IS_MUST_UPGRADE = "isMustUpgrade";//强制升级
}
