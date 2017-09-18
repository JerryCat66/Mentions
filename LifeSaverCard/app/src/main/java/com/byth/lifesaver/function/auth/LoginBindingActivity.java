package com.byth.lifesaver.function.auth;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.byth.lifesaver.MainActivity;
import com.byth.lifesaver.R;
import com.byth.lifesaver.api.GoodsAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.WxInfoBean;
import com.byth.lifesaver.encrypt.AES256Cipher;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.fenguo.library.util.JsonUtil;
import com.fenguo.library.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/7/21 0021.
 * 登录绑定
 */

public class LoginBindingActivity extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.input_account)
    EditText inputAccount;//账号
    @InjectView(R.id.input_password)
    EditText inputPassword;//密码
    @InjectView(R.id.login)
    AppCompatButton login;
    @InjectView(R.id.hot_line)
    TextView hotLine;

    private String Account;
    private String passWord;
    private WxInfoBean.UserDto userDto;
    private HttpSubscriber httpSubscriber;
    private String openid;


    @Override
    protected void receiveDataFromPreActivity() {
        openid = preference.getString(LifeSaverPreference.OPENID);
    }

    @Override
    protected void initData() {
        userDto = new WxInfoBean.UserDto();
    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_login_binding);
        setToolbar(toolbar);

    }

    @Override
    protected void initListener() {
        login.setClickable(false);
        login.setOnClickListener(this);
        hotLine.setOnClickListener(this);
        inputPassword.addTextChangedListener(textWatcher);
        inputAccount.addTextChangedListener(accountChangeWatcher);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                checkRequire();
                break;
            case R.id.hot_line:
                telPhone(getResources().getString(R.string.hot_line_of_company));
                break;
        }
    }

    private TextWatcher accountChangeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            inputPassword.setText(null);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            isLoginBtnClickable();
        }
    };

    private void checkRequire() {
        Account = inputAccount.getText().toString().trim();
        passWord = inputPassword.getText().toString().trim();
        if (StringUtil.isEmpty(Account) || StringUtil.isEmpty(passWord)) {
            showToast("账号或密码不能为空");
            return;
        } else if (!StringUtil.isMobile(Account) || Account.length() < 11) {
            showToast("请输入正确的手机号码");
            return;
        } else {
            loginBinding();
        }
    }

    /**
     * 改变按钮状态
     */
    private void isLoginBtnClickable() {
        Account = inputAccount.getText().toString().trim();
        passWord = inputPassword.getText().toString().trim();
        if (!StringUtil.isEmpty(Account) && !StringUtil.isEmpty(passWord)) {
            login.setBackgroundResource(R.drawable.bg_login_btn_selector);
            login.setClickable(true);
        } else {
            login.setBackgroundResource(R.drawable.shape_register_button);
            login.setClickable(false);
        }
    }

    /**
     * 绑定登录
     */
    private void loginBinding() {
        Account = inputAccount.getText().toString().trim();
        try {
            passWord = AES256Cipher.AES_Encode(inputPassword.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        userDto.setLoginName(Account);
        userDto.setPassword(passWord);
        userDto.setOpenid(openid);
        unSub();
        httpSubscriber = new HttpSubscriber(onLoginBindNextListener);
        Map<Object, Object> map = new HashMap<>();
        map.put("userDto", userDto);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        GoodsAPI.getInstance().loginBinding(httpSubscriber, body);
    }

    SubscriberOnNextListener<WxInfoBean> onLoginBindNextListener = new SubscriberOnNextListener<WxInfoBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(WxInfoBean wxInfoBean) {
            //openid = wxInfoBean.getOpenid();
            preference.putString(LifeSaverPreference.OPENID, openid);
            preference.putString(LifeSaverPreference.ID, String.valueOf(wxInfoBean.getId()));
            preference.putString(LifeSaverPreference.SESSION_ID, wxInfoBean.getSession_id());
            preference.putString(LifeSaverPreference.OPERATION_CODE, wxInfoBean.getOperation_code());
            preference.putString(LifeSaverPreference.ACCOUNT, inputAccount.getText().toString().trim());
            preference.putString(LifeSaverPreference.PASSWORD, inputPassword.getText().toString().trim());
            openActivityNotClose(MainActivity.class, null);
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
     * 释放资源
     */
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unsubscribe();
        }
    }
}
