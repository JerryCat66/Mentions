package com.byth.lifesaver.function.auth;

import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.byth.lifesaver.MainActivity;
import com.byth.lifesaver.R;
import com.byth.lifesaver.api.AuthAPI;
import com.byth.lifesaver.base.BaseApplication;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.UserInfoBean;
import com.byth.lifesaver.encrypt.AES256Cipher;
import com.byth.lifesaver.encrypt.DESUtils;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.fenguo.library.util.JsonUtil;
import com.fenguo.library.util.StringUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/5/23 0023.
 * 登陆
 */

public class LoginActivity extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.btn_login)
    AppCompatButton btn_login;//登录
    @InjectView(R.id.tv_signUp)
    TextView tv_signUp;//注册
    @InjectView(R.id.input_account)
    EditText edAccount;
    @InjectView(R.id.input_password)
    EditText edPassword;
    @InjectView(R.id.sv_root)
    ScrollView svRoot;
    @InjectView(R.id.tv_forgetPsw)
    TextView tv_ForgetPsw;//忘记密码
    @InjectView(R.id.hot_line)
    TextView tvHotLine;
    @InjectView(R.id.login_with_wechat)
    TextView tvLoginWithWechat;//微信登录
    private HttpSubscriber httpSubscriber;

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_login);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.transparent)
                .init();
    }

    @Override
    protected void initListener() {
        btn_login.setOnClickListener(this);
        tv_signUp.setOnClickListener(this);
        tv_ForgetPsw.setOnClickListener(this);
        tvHotLine.setOnClickListener(this);
        if (!StringUtil.isEmpty(preference.getString(LifeSaverPreference.ACCOUNT))) {
            edAccount.setText(preference.getString(LifeSaverPreference.ACCOUNT));
        }
        if (!StringUtil.isEmpty(preference.getString(LifeSaverPreference.PASSWORD))) {
            edPassword.setText(preference.getString(LifeSaverPreference.PASSWORD));
        }
        tvLoginWithWechat.setOnClickListener(this);
        edAccount.addTextChangedListener(loginTextChangeWatcher);
    }

    //用户名输入框改变监听，清空密码。
    private TextWatcher loginTextChangeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            edPassword.setText(null);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://登录
                checkRequire();
                break;
            case R.id.tv_signUp://注册
                openActivityNotClose(RegisterActivity.class, null);
                break;
            case R.id.tv_forgetPsw://忘记密码
                openActivityNotClose(ForgetPswStep1Activity.class, null);
                break;
            case R.id.login_with_wechat://微信授权登录
                loginWithWx();
                break;
            case R.id.hot_line://公司电话
                telPhone(getResources().getString(R.string.hot_line_of_company));
                break;
        }
    }

    /**
     * 检查必填项
     */
    private void checkRequire() {
        String account = edAccount.getText().toString();
        String password = edPassword.getText().toString();
        if (StringUtil.isEmpty(account)) {
            showToast("账号不能为空");
        } else if (StringUtil.isEmpty(password)) {
            showToast("密码不能为空");
        } else {
            login(account, password);
        }
    }

    /**
     * 微信登录
     */
    private void loginWithWx() {
        if (!BaseApplication.iwxapi.isWXAppInstalled()) {
            showToast("请安装微信客户端");
        } else {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";//应用授权作用域
            req.state = "wechat_sdk_demo";
            BaseApplication.iwxapi.sendReq(req);
        }
    }

    /**
     * 登录
     *
     * @param account
     * @param password
     */
    private void login(String account, String password) {
        unSub();
        httpSubscriber = new HttpSubscriber(onLoginNextListener);
        account = edAccount.getText().toString().trim();
        password = edPassword.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        map.put("loginName", account);
        try {
            map.put("password", AES256Cipher.AES_Encode(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        AuthAPI.getInstance().login(httpSubscriber, body);
    }

    SubscriberOnNextListener<UserInfoBean> onLoginNextListener = new SubscriberOnNextListener<UserInfoBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(UserInfoBean userInfoBean) {
            preference.putString(LifeSaverPreference.SESSION_ID, userInfoBean.getSession_id());
            preference.putString(LifeSaverPreference.ID, String.valueOf(userInfoBean.getId()));
            preference.putString(LifeSaverPreference.ACCOUNT, edAccount.getText().toString().trim());
            preference.putString(LifeSaverPreference.PASSWORD, edPassword.getText().toString().trim());
            preference.putString(LifeSaverPreference.OPERATION_CODE, userInfoBean.getOperation_code());
            openActivity(MainActivity.class, null);
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

    //解除订阅
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
    }
}
