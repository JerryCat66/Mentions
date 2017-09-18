package com.byth.lifesaver.function.mine.activity;

import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
 * Created by Administrator on 2017/7/18 0018.
 * 修改密码第二步
 * 输入两次相同的密码
 */

public class ActivityModifyPasswordStepTwo extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.psw)
    EditText edPsw;//密码
    @InjectView(R.id.pswConfirm)
    EditText edPswConfirm;//确认密码
    @InjectView(R.id.ok)
    Button ok;
    @InjectView(R.id.hot_line)
    TextView servicePhone;
    @InjectView(R.id.cbPswVisible)
    CheckBox cbPswVisible;
    @InjectView(R.id.cbPswConfirmVisible)
    CheckBox cbPswConfirmVisible;
    private HttpSubscriber httpSubscriber;

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_modify_psw_step2);
        setToolbar(toolbar);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
    }

    @Override
    protected void initListener() {
        ok.setOnClickListener(this);
        servicePhone.setOnClickListener(this);
        cbPswVisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //显示密码
                    edPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //隐藏密码
                    edPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                edPsw.setSelection(edPsw.length());//光标始终在输入内容后面
            }
        });
        cbPswConfirmVisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edPswConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edPswConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                edPswConfirm.setSelection(edPswConfirm.length());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                //openActivityNotClose(ActivityModifyPasswordSuccess.class, null);
                checkParameter();//校验传入参数
                break;
            case R.id.hot_line:
                telPhone(getResources().getString(R.string.hot_line_of_company));
                break;
        }
    }

    private void checkParameter() {
        String psw = edPsw.getText().toString().trim();
        String pswConfirm = edPswConfirm.getText().toString().trim();
        if (StringUtil.isEmpty(psw) || StringUtil.isEmpty(pswConfirm)) {
            showToast("请输入您要修改的密码");
            return;
        } else if (!psw.equals(pswConfirm)) {
            showToast("输入的两次密码不一致");
            return;
        } else if (psw.length() < 6 || pswConfirm.length() < 6) {
            showToast("密码长度必须大于6位数");
            return;
        } else {
            modifyPassword();
        }
    }

    //修改密码
    private void modifyPassword() {
        unSub();
        httpSubscriber = new HttpSubscriber(onModifyPswNextListener);
        Map<Object, Object> map = new HashMap<>();
        map.put("id", StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        try {
            map.put("password", AES256Cipher.AES_Encode(edPsw.getText().toString().trim()));
            map.put("confirmPwd", AES256Cipher.AES_Encode(edPswConfirm.getText().toString().trim()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        AuthAPI.getInstance().modifyPassword(httpSubscriber, body);
    }

    SubscriberOnNextListener<HttpResult> onModifyPswNextListener = new SubscriberOnNextListener<HttpResult>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(HttpResult httpResult) {
            hideLoadingDialog();
            openActivity(ActivityModifyPasswordSuccess.class, null);
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
