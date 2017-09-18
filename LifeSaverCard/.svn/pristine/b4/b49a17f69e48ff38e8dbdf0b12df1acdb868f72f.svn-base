package com.byth.lifesaver.function.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.api.MineAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.CustomerInfoBean;
import com.byth.lifesaver.function.auth.LoginActivity;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.byth.lifesaver.widget.DialogPopupBottom;
import com.byth.lifesaver.widget.TipsDialog;
import com.fenguo.library.util.JsonUtil;
import com.fenguo.library.util.StringUtil;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/6/15 0015.
 * 账户管理
 */

public class ActivityAccountManager extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.account)
    TextView tvAccount;//账户
    @InjectView(R.id.password)
    TextView tvPassword;
    @InjectView(R.id.igModifyPassword)
    ImageView igModifyPassword;//修改密码
    @InjectView(R.id.phoneNum)
    TextView tvPhoneNum;
    @InjectView(R.id.igModifyPhoneNum)
    ImageView igModifyPhoneNum;//修改手机号码
    @InjectView(R.id.logout)
    Button btnLogout;//退出登录
    private HttpSubscriber httpSubscriber;
    private String userName;
    private String phone;
    private Bundle bundle = new Bundle();

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_account_manager);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        getUserInfo();
    }

    @Override
    protected void initListener() {
        igModifyPassword.setOnClickListener(this);
        igModifyPhoneNum.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.igModifyPassword:
                openActivityNotClose(ActivityModifyPasswordStepOne.class, bundle);
                break;
            case R.id.igModifyPhoneNum:
                new DialogPopupBottom(this)
                        .builder()
                        .setTitle("请选择修改方式")
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("原手机能接收验证码", DialogPopupBottom.SheetItemColor.Blue, new DialogPopupBottom.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                openActivityNotClose(ActivityModifyPhoneStepOne.class, bundle);
                            }
                        })
                        .addSheetItem("原手机不能接收验证码", DialogPopupBottom.SheetItemColor.Blue, new DialogPopupBottom.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                openActivityNotClose(ActivityModifyWithPassword.class, null);
                            }
                        }).show();
                break;
            case R.id.logout:
                TipsDialog.makeDialog(this, "提示", "是否确认退出", "是", "否", new TipsDialog.onDialogListener() {
                    @Override
                    public void onOkClick() {
                        logout();
                    }

                    @Override
                    public void onCancelClick() {

                    }
                }).show();
                break;
        }
    }

    //获取用户信息
    private void getUserInfo() {
        unSub();
        httpSubscriber = new HttpSubscriber(onGetUserInfoNext);
        Map<String, Integer> map = new HashMap<>();
        map.put("id", StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        MineAPI.getInstance().getUserInfo(httpSubscriber, body);
    }

    SubscriberOnNextListener<CustomerInfoBean> onGetUserInfoNext = new SubscriberOnNextListener<CustomerInfoBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(CustomerInfoBean customerInfoBean) {
            hideLoadingDialog();
            if (customerInfoBean != null) {
                userName = customerInfoBean.getUserName();
                phone = customerInfoBean.getPhone();
                bundle.putString("phone", phone);
            /*    if (StringUtil.isEmpty(userName)) {
                    tvAccount.setText("请完善个人信息");
                } else {
                    tvAccount.setText(userName);
                }*/
                tvAccount.setText(customerInfoBean.getNickName());
                tvPhoneNum.setText(phone);
            }
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

    //解除订阅
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
    }
}
