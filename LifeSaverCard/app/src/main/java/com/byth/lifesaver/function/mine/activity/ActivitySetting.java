package com.byth.lifesaver.function.mine.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.util.upgrade.AppUpgrade;
import com.byth.lifesaver.util.upgrade.AppUpgradeManager;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/7/17 0017.
 * 设置
 */

public class ActivitySetting extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.cbLocation)
    CheckBox cbLocation;//定位
    @InjectView(R.id.cbNotice)
    CheckBox cbNotice;//消息推送
    @InjectView(R.id.tvCheckNewVersion)
    TextView tvCheckNewVersion;//检查版本
    @InjectView(R.id.tvAboutUs)
    TextView tvAboutUs;//关于我们
    @InjectView(R.id.tvUserProtocol)
    TextView tvUserProtocol;//用户协议
    @InjectView(R.id.tvUserGuide)
    TextView tvUserGuide;//用户指南
    private AppUpgrade appUpgrade;

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_setting);
        setToolbar(toolbar);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        appUpgrade = AppUpgradeManager.getInstance();
        appUpgrade.init(getApplicationContext());
    }

    @Override
    protected void initListener() {
        tvAboutUs.setOnClickListener(this);
        tvCheckNewVersion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAboutUs:
                openActivityNotClose(ActivityAboutUs.class, null);
                break;
            case R.id.tvCheckNewVersion:
                appUpgrade.checkLatestVersion(ActivitySetting.this);
                break;
        }
    }
}