package com.byth.lifesaver.util.upgrade;

import android.content.Context;
import android.content.Intent;

import com.byth.lifesaver.R;
import com.byth.lifesaver.base.FrameActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/8/28 0028.
 * 设置一个透明activity
 * 防止用户在升级弹框出现的时候进行其他操作时app奔溃
 */

public class UpgradeActivity extends FrameActivity {
    public static void startInstance(Context context) {
        Intent intent = new Intent(context, UpgradeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_upgrade);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            AppUpgradeManager.getInstance().foundLatestVersion(this);
        }
    }

    @Override
    protected void initListener() {

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void handleUpgradeEvent(final EventMessage msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (msg.getMessage().equals("dialog missing")) {
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
