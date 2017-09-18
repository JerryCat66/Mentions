package com.byth.lifesaver.function.mine.activity;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.api.AuthAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.CustomerInfoBean;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpResult;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.LifeSaverPreference;
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
 * 修改手机号码（Step2）
 */

public class ActivityModifyPhoneStepTwo extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.get_auth_code)
    Button btnVerify;
    @InjectView(R.id.auth_code)
    EditText edCode;
    @InjectView(R.id.phone)
    EditText edPhone;
    @InjectView(R.id.ok)
    Button btnOk;
    @InjectView(R.id.hot_line)
    TextView tvServicePhone;
    private MyCountDownTimer countDownTimer;
    private String phoneNum;
    private String code;
    private String type;
    private HttpSubscriber httpSubscriber;
    @InjectView(R.id.checkIdentity)
    TextView tvCheckIdentity;
    @InjectView(R.id.modifyPassword)
    TextView tvModifyPassword;

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_modify_phone_step_two);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        btnVerify.setClickable(false);

        tvCheckIdentity.setBackgroundResource(R.drawable.shape_with_check_logistic);
        tvCheckIdentity.setTextColor(getResources().getColor(R.color.color_main_text));
        tvModifyPassword.setBackgroundResource(R.drawable.selector_login_button);
        tvModifyPassword.setTextColor(getResources().getColor(R.color.white));
        tvModifyPassword.setText("手机号码验证");
    }

    @Override
    protected void initListener() {
        btnVerify.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        tvServicePhone.setOnClickListener(this);
        edPhone.addTextChangedListener(watcher);
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 11 && StringUtil.isMobile(s.toString())) {
                btnVerify.setClickable(true);
                btnVerify.setBackgroundResource(R.drawable.shape_with_corner_full_auth_code);
            } else {
                btnVerify.setClickable(false);
                btnVerify.setBackgroundResource(R.drawable.btn_gray);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_auth_code:
                countDownTimer = new MyCountDownTimer(btnVerify);
                countDownTimer.start();
                btnVerify.setBackgroundResource(R.drawable.btn_gray);
                getVerifyCode(edPhone.getText().toString().trim());
                break;
            case R.id.ok:
                checkRequire();
                break;
            case R.id.hot_line:
                telPhone(getResources().getString(R.string.hot_line_of_company));
                break;
        }
    }

    /**
     * 获取验证码
     *
     * @param phoneNum
     */
    private void getVerifyCode(String phoneNum) {
        unSub();
        phoneNum = edPhone.getText().toString().trim();
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
     * 检查必填项
     */
    private void checkRequire() {
        phoneNum = edPhone.getText().toString().trim();
        code = edCode.getText().toString().trim();
        if (StringUtil.isEmpty(phoneNum) || !StringUtil.isMobile(phoneNum) || phoneNum.length() != 11) {
            showToast("请输入正确的手机号码");
        } else if (StringUtil.isEmpty(code)) {
            showToast("请输入验证码");
        } else {
            modifyPhoneNum(phoneNum, code);
        }
    }

    /**
     * 修改手机号码，需要调用接口
     */
    private void modifyPhoneNum(String phone, String code) {
        //openActivity(ActivityModifyPhoneSuccess.class, null);
        unSub();
        httpSubscriber = new HttpSubscriber(onModifyPhoneNextListener);
        Map<String, String> map = new HashMap<>();
        type = "CM";
        map.put("type", type);
        map.put("id", preference.getString(LifeSaverPreference.ID));
        map.put("phone", phone);
        map.put("validStr", code);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        AuthAPI.getInstance().modifyPhone(httpSubscriber, body);
    }

    SubscriberOnNextListener<CustomerInfoBean> onModifyPhoneNextListener = new SubscriberOnNextListener<CustomerInfoBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(CustomerInfoBean customerInfo) {
            hideLoadingDialog();
            openActivity(ActivityModifyPhoneSuccess.class, null);
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

    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
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
