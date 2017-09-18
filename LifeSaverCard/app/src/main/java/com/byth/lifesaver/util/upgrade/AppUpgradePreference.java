package com.byth.lifesaver.util.upgrade;

import android.content.Context;

import com.byth.lifesaver.bean.VersionInfoBean;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.fenguo.library.util.Preference;

/**
 * Created by Administrator on 2017/8/25 0025.
 * 版本更新持久化数据
 * TODO
 */

public class AppUpgradePreference {
    private Preference preference = Preference.getInstance();

    public AppUpgradePreference() {

    }

    /**
     * 保存数据
     *
     * @param version
     */
    public void saveVersionInfo(VersionInfoBean version) {
        if (version == null) {
            return;
        }
        preference.putInt(LifeSaverPreference.VERSION_CODE, version.getVersionCode());
    }

    /**
     * 获取数据
     *
     * @return
     */
    public VersionInfoBean getVersionInfo() {
        String versionCode = preference.getString(LifeSaverPreference.VERSION_CODE);
        return new VersionInfoBean();
    }

    /**
     * 保存当前下载任务id
     *
     * @param downloadTaskId
     */
    public void saveDownloadTaskId(long downloadTaskId) {
        preference.putLong(LifeSaverPreference.DOWNLOAD_TASK_ID, downloadTaskId);
    }

    /**
     * 获取保存的当前下载任务id
     */
    public long getDownloadTaskId() {
        long downloadTaskId = preference.getLong(LifeSaverPreference.DOWNLOAD_TASK_ID, -12306L);
        return downloadTaskId;
    }

    /**
     * 移除保存的下载任务id
     */
    public void removeDownloadTaskId() {
        preference.remove(LifeSaverPreference.DOWNLOAD_TASK_ID);
    }
}
