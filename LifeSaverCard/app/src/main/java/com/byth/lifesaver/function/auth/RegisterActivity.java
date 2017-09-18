package com.byth.lifesaver.function.auth;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.api.AuthAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.encrypt.AES256Cipher;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpResult;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.MyCountDownTimer;
import com.fenguo.library.util.JsonUtil;
import com.fenguo.library.util.StringUtil;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;

import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/5/23 0023.
 * 注册
 */

public class RegisterActivity extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.input_account)
    EditText edAccount;//账户
    @InjectView(R.id.input_password)
    EditText edPassword;//密码
    @InjectView(R.id.input_password_confirm)
    EditText edPassword_Confirm;//确认密码
    @InjectView(R.id.editVerify)
    EditText edVerify;//验证码
    @InjectView(R.id.btnVerify)
    Button btnVerify;//验证码按钮
    @InjectView(R.id.btn_signUp)
    AppCompatButton btnSignUp;//注册按钮
    @InjectView(R.id.link_login)
    TextView tvLogin;//底部登录跳转
    @InjectView(R.id.chkBoxAgree)
    CheckBox cbAgree;//同意协议
    @InjectView(R.id.tvProfile)
    TextView tvProfile;//协议
    @InjectView(R.id.hot_line)
    TextView tvHotLine;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    private MyCountDownTimer downTimer;
    private String password;
    private String confirmPwd;
    private String phone;
    private String verifyCode;
    private String type;//获取验证码类型

    private HttpSubscriber httpSubscriber;

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_register);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.color_main)
                .init();
        getTextChangedListener();
    }

    @Override
    protected void initListener() {
        btnSignUp.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        btnVerify.setOnClickListener(this);
        tvHotLine.setOnClickListener(this);
        btnVerify.setClickable(false);
        btnSignUp.setClickable(false);
        edPassword_Confirm.addTextChangedListener(textWatcher);
        tvProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signUp:
                checkRequire();
                break;
            case R.id.btnVerify:
                if (StringUtil.isEmpty(edAccount.getText().toString().trim())) {
                    showToast("手机号码不能为空");
                    return;
                }
                downTimer = new MyCountDownTimer(btnVerify);
                downTimer.start();
                btnVerify.setBackgroundResource(R.drawable.btn_gray);
                getVerifyCode(edAccount.getText().toString().trim());
                break;
            case R.id.link_login:
                openActivity(LoginActivity.class, null);
                break;
            case R.id.hot_line://公司电话
                telPhone(getResources().getString(R.string.hot_line_of_company));
                break;
            case R.id.tvProfile://协议
                break;
        }
    }

    /**
     * 检查必填项
     */
    private void checkRequire() {
        phone = edAccount.getText().toString().trim();
        verifyCode = edVerify.getText().toString().trim();
        password = edPassword.getText().toString().trim();
        confirmPwd = edPassword_Confirm.getText().toString().trim();
        if (StringUtil.isEmpty(phone)) {
            showToast("请输入手机号码");
            return;
        } else if (phone.length() < 11) {
            showToast("请输入正确的手机号码");
            return;
        } else if (StringUtil.isEmpty(verifyCode)) {
            showToast("请输入验证码");
            return;
        } else if (password.length() < 6 || confirmPwd.length() < 6) {
            showToast("密码长度不能小于6位");
            return;
        } else if (!lifeSaverUtil.checkPassword(password, 6, 18) || !lifeSaverUtil.checkPassword(confirmPwd, 6, 18)) {
            showToast("密码长度应在6-18位");
            return;
        } else if (!password.equals(confirmPwd)) {
            showToast("两次密码不一致");
            return;
        } else if (!cbAgree.isChecked()) {
            showToast("请勾选服务条框");
            return;
        } else {
            register();
        }
    }

    SubscriberOnNextListener<HttpResult> onGetVerifyCodeNextListener = new SubscriberOnNextListener<HttpResult>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(HttpResult result) {
            showToast("获取验证码成功");
            hideLoadingDialog();
        }

        @Override
        public void onApiError(ApiException e) {
            hideLoadingDialog();
            showToast(e.getMessage());
        }

        @Override
        public void onNetworkError(Throwable e) {
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
    SubscriberOnNextListener<HttpResult> onRegisterNextListener = new SubscriberOnNextListener<HttpResult>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(HttpResult result) {
            hideLoadingDialog();
            showToast("注册成功");
            openActivityNotClose(LoginActivity.class, null);
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
     * 获取验证码
     */
    private void getVerifyCode(String phone) {
        unSub();
        httpSubscriber = new HttpSubscriber<>(onGetVerifyCodeNextListener);
        phone = edAccount.getText().toString().trim();
        type = "RG";
        HashMap<String, String> map = new HashMap<>();
        map.put("mobileNo", phone);
        map.put("type", type);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        AuthAPI.getInstance().getRegisterCodeWithRetrofit(httpSubscriber, body);
    }

    /**
     * 释放资源
     */
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unsubscribe();
        }
    }

    /**
     * 注册操作,应调用接口
     */
    private void register() {
        unSub();
        httpSubscriber = new HttpSubscriber<>(onRegisterNextListener);
        password = edPassword.getText().toString().trim();
        confirmPwd = edPassword_Confirm.getText().toString().trim();
        phone = edAccount.getText().toString().trim();
        type = "RG";
        verifyCode = edVerify.getText().toString().trim();
        HashMap<String, String> map = new HashMap<>();
        try {
            map.put("password", AES256Cipher.AES_Encode(password));
            map.put("confirmPwd", AES256Cipher.AES_Encode(confirmPwd));
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("type", type);
        map.put("phone", phone);
        map.put("validStr", verifyCode);
        String strData = JsonUtil.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), strData);
        AuthAPI.getInstance().register(httpSubscriber, body);
    }

    private void getTextChangedListener() {
        edAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phoneStr = s.toString();
                if (phoneStr.length() == 11) {
                    checkMobile(phoneStr);
                }
            }
        });
    }

    /**
     * 输入监听
     */
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            showColorWithInput();
        }
    };

    /**
     * 此处输入手机号码应该验证手机号码是否已经注册,是否有效,调用接口
     *
     * @param phone
     */
    private void checkMobile(String phone) {
        if (StringUtil.isMobile(phone)) {
            btnVerify.setClickable(true);
            btnVerify.setBackgroundResource(R.drawable.shape_with_corner_auth_code);
        } else {
            showToast("请输入正确的手机号码");
            btnVerify.setClickable(false);
            btnVerify.setBackgroundResource(R.drawable.btn_gray);
        }
    }

    /**
     * 根据输入框的值显示不同的颜色
     */
    private void showColorWithInput() {
        String regPhone = edAccount.getText().toString().trim();
        String regVerifyCode = edVerify.getText().toString().trim();
        String regPassword = edPassword.getText().toString().trim();
        String regPasswordConfirm = edPassword_Confirm.getText().toString().trim();
        if (StringUtil.isEmpty(regPhone) || StringUtil.isEmpty(regVerifyCode) || StringUtil.isEmpty(regPassword)
                || StringUtil.isEmpty(regPasswordConfirm)) {
            btnSignUp.setClickable(false);
            btnSignUp.setBackgroundResource(R.drawable.shape_register_button);
        } else {
            btnSignUp.setClickable(true);
            btnSignUp.setBackgroundResource(R.drawable.bg_login_btn_selector);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (downTimer != null) {
            downTimer.cancel();
            btnVerify.setClickable(true);
            btnVerify.setText("获取验证码");
        }
    }
}