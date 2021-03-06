package com.byth.lifesaver.util;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.byth.lifesaver.R;

/**
 * Created by Administrator on 2017/5/27 0027.
 * 倒计时工具
 */

public class MyCountDownTimer extends CountDownTimer {
    private static int count = 120 * 1000;//设置倒计时为60秒
    private static int countDownInterval = 1000;//每次减1秒
    private Button mGetCodeBtn;
    private LinearLayout codeShow = null;

    public MyCountDownTimer(long millisInFuture, long countDownInterval, Button button) {
        super(millisInFuture, countDownInterval);
        this.mGetCodeBtn = button;
    }

    public MyCountDownTimer(Button button) {
        super(count, countDownInterval);
        this.mGetCodeBtn = button;
    }

    public MyCountDownTimer(long millisInFuture, long countDownInterval, Button button, LinearLayout codeShow) {
        super(millisInFuture, countDownInterval);
        this.mGetCodeBtn = button;
        this.codeShow = codeShow;
    }

    public MyCountDownTimer(Button button, LinearLayout codeShow) {
        super(count, countDownInterval);
        this.mGetCodeBtn = button;
        this.codeShow = codeShow;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mGetCodeBtn.setClickable(false);
        mGetCodeBtn.setText(String.valueOf(millisUntilFinished / 1000) + "秒后重发");
        if (codeShow != null && (millisUntilFinished / 1000) == 45) {
            codeShow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFinish() {
        mGetCodeBtn.setClickable(true);
        mGetCodeBtn.setText("获取验证码");
        mGetCodeBtn.setBackgroundResource(R.drawable.shape_with_corner_auth_code);
    }
}
