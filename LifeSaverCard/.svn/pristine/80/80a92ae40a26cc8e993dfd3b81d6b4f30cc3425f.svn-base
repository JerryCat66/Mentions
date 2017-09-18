package com.byth.lifesaver.function.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.api.AuthAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.function.auth.ForgetPswStep2Activity;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpResult;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.MyCountDownTimer;
import com.fenguo.library.util.JsonUtil;
import com.fenguo.library.util.StringUtil;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/6/16 0016.
 * 原手机可以接收验证码
 * 修改手机号码（Step1）
 */

public class ActivityModifyPhoneStepOne extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.get_auth_code)
    Button btnVerify;
    @InjectView(R.id.phone)
    TextView tvPhone;
    @InjectView(R.id.code)
    EditText edVerifyCode;
    @InjectView(R.id.ok)
    Button btnNext;
    @InjectView(R.id.hot_line)
    TextView tvServicePhone;
    private MyCountDownTimer countDownTimer;
    private String code;
    private String type;
    private String phone;
    private HttpSubscriber httpSubscriber;
    @InjectView(R.id.checkIdentity)
    TextView tvCheckIdentity;
    @InjectView(R.id.modifyPassword)
    TextView tvModifyPassword;

    @Override
    protected void receiveDataFromPreActivity() {
        Bundle bundle = getIntent().getExtras();
        phone = bundle.getString("phone");

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_modify_phone_step_one);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        tvCheckIdentity.setBackgroundResource(R.drawable.selector_login_button);
        tvCheckIdentity.setTextColor(getResources().getColor(R.color.white));
        tvModifyPassword.setBackgroundResource(R.drawable.shape_with_check_logistic);
        tvModifyPassword.setTextColor(getResources().getColor(R.color.color_main_text));
        tvModifyPassword.setText("手机号码验证");
        tvPhone.setText(phone);
    }

    @Override
    protected void initListener() {
        btnVerify.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        tvServicePhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_auth_code:
                countDownTimer = new MyCountDownTimer(btnVerify);
                countDownTimer.start();
                btnVerify.setBackgroundResource(R.drawable.btn_gray);
                getVerifyCode(tvPhone.getText().toString().trim());
                break;
            case R.id.ok:
                checkRequire();
                break;
            case R.id.hot_line:
                telPhone(getResources().getString(R.string.hot_line_of_company));
                break;
        }
    }

    private void getVerifyCode(String phoneNum) {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
        phoneNum = tvPhone.getText().toString().trim();
        type = "CM";
        httpSubscriber = new HttpSubscriber(onNextListener);
        HashMap<String, String> map = new HashMap<>();
        map.put("mobileNo", phoneNum);
        map.put("type", type);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        AuthAPI.getInstance().getRegisterCodeWithRetrofit(httpSubscriber, body);
    }

    SubscriberOnNextListener<HttpResult> onNextListener = new SubscriberOnNextListener<HttpResult>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(HttpResult result) {
            hideLoadingDialog();
        }

        @Override
        public void onApiError(ApiException e) {
            showToast(e.getMessage());
            hideLoadingDialog();
        }

        @Override
        public void onNetworkError(Throwable e) {
            showToast(e.getMessage());
            hideLoadingDialog();
        }

        @Override
        public void onOtherError(Throwable e) {
            showToast(e.getMessage());
            hideLoadingDialog();
        }

        @Override
        public void onCompleted() {
            hideLoadingDialog();
        }
    };

    /**
     * 此处应调用接口验证验证码是否正确，正确则跳到第二步
     * TODO
     */
    private void checkRequire() {
        code = edVerifyCode.getText().toString().trim();
        if (!StringUtil.isEmpty(code)) {
            /*openActivityNotClose(ActivityModifyPhoneStepTwo.class, null);*/
            toNextStep();
        } else {
            showToast("请填写验证码");
            return;
        }

    }

    /**
     * 点击下一步事件,需调用接口
     */
    private void toNextStep() {
        unSub();
        httpSubscriber = new HttpSubscriber(onCheckRandomNextListener);
        String phoneNum = tvPhone.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        type = "CM";
        map.put("mobileNo", phoneNum);
        map.put("type", type);
        map.put("randCode", edVerifyCode.getText().toString().trim());
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        AuthAPI.getInstance().checkRandomCode(httpSubscriber, body);

    }

    SubscriberOnNextListener<HttpResult> onCheckRandomNextListener = new SubscriberOnNextListener<HttpResult>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(HttpResult httpResult) {
            hideLoadingDialog();
            openActivityNotClose(ActivityModifyPhoneStepTwo.class, null);
        }

        @Override
        public void onApiError(ApiException e) {
            hideLoadingDialog();
            showToast(e.getMessage());
        }

        @Override
        public void onNetworkError(Throwable e) {
            hideLoadingDialog();
            showToast(e.getMessage());
        }

        @Override
        public void onOtherError(Throwable e) {
            hideLoadingDialog();
            showToast(e.getMessage());
        }

        @Override
        public void onCompleted() {
            hideLoadingDialog();
        }
    };

    /**
     * 释放资源
     */
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unsubscribe();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            btnVerify.setClickable(true);
            btnVerify.setText("获取验证码");
        }
    }
}
