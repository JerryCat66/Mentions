package com.byth.lifesaver.function.auth;

import android.os.Handler;

import com.byth.lifesaver.MainActivity;
import com.byth.lifesaver.R;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.fenguo.library.util.StringUtil;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by Administrator on 2017/5/23 0023.
 * 引导页
 */

public class SplashActivity extends FrameActivity {
    //延迟3秒
    private static final long SPLASH_DELAY_MILLLS = 3000;

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_splash);
        ImmersionBar.with(this)
                .statusBarColor(R.color.transparent)
                .statusBarDarkFont(true)
                .init();
        if (!preference.getBoolean(LifeSaverPreference.FIRST_IN)) {
            openActivity(GuideActivity.class, null);
            return;
        }
        //延迟3秒跳到MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, SPLASH_DELAY_MILLLS);
    }

    @Override
    protected void initListener() {

    }

    /**
     * 如果保存了账号密码就直接跳到MainActivity,否则就跳到登陆界面
     */
    private void toMainActivity() {
        if (StringUtil.isEmpty(preference.getString(LifeSaverPreference.ACCOUNT)) && StringUtil.isEmpty(preference.getString(LifeSaverPreference.PASSWORD))
                && StringUtil.isEmpty(preference.getString(LifeSaverPreference.SESSION_ID))) {
            openActivity(LoginActivity.class, null);
        } else {
            openActivity(MainActivity.class, null);
        }
    }
}