package com.byth.lifesaver.function.mine.activity;

import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.function.auth.LoginActivity;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/7/18 0018.
 * 修改手机号码成功
 */

public class ActivityModifyPhoneSuccess extends FrameActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tvHint)
    TextView tvHint;
    @InjectView(R.id.loginAgain)
    Button loginAgain;
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
        setContentView(R.layout.activity_modify_psw_success);
        setToolbar(toolbar);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        tvHint.setText("手机号码绑定成功");
        loginAgain.setText("返回个人中心");
        //三秒内如果不点击重新登陆，则自动跳转到登录界面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logout();
            }
        }, SPLASH_DELAY_MILLLS);
    }

    @Override
    protected void initListener() {
        loginAgain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginAgain:
                logout();
                break;
        }
    }

    /**
     * 退出当前账户，需要调用接口，也需要清空部分持久化数据
     */
    private void logout() {
        boolean firstIn = preference.getBoolean(LifeSaverPreference.FIRST_IN);
        //String account=preference.getString(LifeSaverPreference.ACCOUNT);
        preference.clearData();
        preference.putBoolean(LifeSaverPreference.FIRST_IN, firstIn);
        openActivity(LoginActivity.class, null);
        close();
    }
}
