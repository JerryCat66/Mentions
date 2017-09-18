package com.byth.lifesaver.util.upgrade;

import android.app.Activity;

import com.byth.lifesaver.bean.VersionInfoBean;
import com.byth.lifesaver.widget.TipsDialog;
import com.fenguo.library.util.StringUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/8/25 0025.
 * 发现新版本dialog
 */

public class FoundNewVersionDialog {
    private Activity activity;
    private VersionInfoBean versionInfoBean;
    private IVersionInfoDialog iVersionInfoDialog;

    public FoundNewVersionDialog(Activity activity, VersionInfoBean versionInfoBean, IVersionInfoDialog iVersionInfoDialog) {
        this.activity = activity;
        this.versionInfoBean = versionInfoBean;
        this.iVersionInfoDialog = iVersionInfoDialog;
    }

    //展示升级框
    public void show() {
        TipsDialog.makeDialog(activity, "检测到新版本：" + versionInfoBean.getVersionCode(), !StringUtil.isEmpty(versionInfoBean.getVersionDesc()) ? versionInfoBean.getVersionDesc() : "", "立即更新"
                , "稍后提醒我", new TipsDialog.onDialogListener() {
                    @Override
                    public void onOkClick() {
                        if (iVersionInfoDialog != null) {
                            EventBus.getDefault().post("dialog missing");
                            iVersionInfoDialog.doUpdate();
                        }
                    }

                    @Override
                    public void onCancelClick() {
                        if (iVersionInfoDialog != null) {
                            EventBus.getDefault().post("dialog missing");
                            iVersionInfoDialog.remindMeLater();
                        }
                    }
                }).show();
    }
}
