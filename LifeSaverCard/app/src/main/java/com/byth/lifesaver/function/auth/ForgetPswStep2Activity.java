package com.byth.lifesaver.function.auth;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
import com.byth.lifesaver.util.LifeSaverPreference;
import com.fenguo.library.util.JsonUtil;
import com.fenguo.library.util.StringUtil;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/5/27 0027.
 * 重设密码
 */

public class ForgetPswStep2Activity extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.input_password)
    EditText edPassword;
    @InjectView(R.id.input_password_confirm)
    EditText edPassword_Confirm;
    @InjectView(R.id.phone_num)
    TextView tvPhoneNum;
    @InjectView(R.id.btn_ok)
    AppCompatButton btnOk;
    @InjectView(R.id.hot_line)
    TextView tvHotLine;
    private String phoneNum;
    private HttpSubscriber httpSubscriber;

    @Override
    protected void receiveDataFromPreActivity() {
        Bundle bundle = getIntent().getExtras();
        phoneNum = bundle.getString("phoneNum");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_forget_psw_step2);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.color_main)
                .init();
        tvPhoneNum.setText("手机号码:" + phoneNum);
    }

    @Override
    protected void initListener() {
        btnOk.setOnClickListener(this);
        tvHotLine.setOnClickListener(this);
        btnOk.setClickable(false);
        edPassword_Confirm.addTextChangedListener(watcher);
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
            String str = s.toString();
            if (!StringUtil.isEmpty(str)) {
                showColorWithInput();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                checkRequire();
                break;
            case R.id.hot_line://公司电话
                telPhone(getResources().getString(R.string.hot_line_of_company));
                break;
        }
    }

    private void checkRequire() {
        String password = edPassword.getText().toString().trim();
        String passwordConfirm = edPassword_Confirm.getText().toString().trim();
        if (StringUtil.isEmpty(password) || StringUtil.isEmpty(passwordConfirm)) {
            showToast("请输入密码");
        } else if (password.length() < 6 || passwordConfirm.length() < 6) {
            showToast("密码长度不能小于6位");
            return;
        } else if (!lifeSaverUtil.checkPassword(password, 6, 18) || !lifeSaverUtil.checkPassword(passwordConfirm, 6, 18)) {
            showToast("密码长度应在6-18位");
            return;
        } else if (!password.equals(passwordConfirm)) {
            showToast("两次密码输入不一致");
            return;
        } else {
            setNewPassword();
        }
    }

    /**
     * 输入完成以后改变提交按钮的颜色
     */
    private void showColorWithInput() {
        String password = edPassword.getText().toString().trim();
        String confirm_password = edPassword_Confirm.getText().toString().trim();
        if (StringUtil.isEmpty(password) || StringUtil.isEmpty(confirm_password)) {
            btnOk.setClickable(false);
            btnOk.setBackgroundResource(R.drawable.shape_register_button);
        } else {
            btnOk.setClickable(true);
            btnOk.setBackgroundResource(R.drawable.bg_login_btn_selector);
        }
    }

    /**
     * 设置新密码
     */
    private void setNewPassword() {
        unSub();
        httpSubscriber = new HttpSubscriber(onSetNewPswNextListener);
        String password = edPassword.getText().toString().trim();
        String confirmPwd = edPassword_Confirm.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        try {
            map.put("password", AES256Cipher.AES_Encode(password));
            map.put("confirmPwd", AES256Cipher.AES_Encode(confirmPwd));
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("phone", phoneNum);
        //map.put("id", StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        AuthAPI.getInstance().findBackPsw(httpSubscriber, body);

    }

    SubscriberOnNextListener<HttpResult> onSetNewPswNextListener = new SubscriberOnNextListener<HttpResult>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(HttpResult httpResult) {
            hideLoadingDialog();
            openActivity(LoginActivity.class, null);
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

    //解除订阅
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
    }
}