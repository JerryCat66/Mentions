package com.byth.lifesaver.function.card.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.api.AuthAPI;
import com.byth.lifesaver.api.GoodsAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.CardReissueBean;
import com.byth.lifesaver.function.auth.ForgetPswStep2Activity;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpResult;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.byth.lifesaver.util.MyCountDownTimer;
import com.byth.lifesaver.widget.DialogWithList;
import com.fenguo.library.util.JsonUtil;
import com.fenguo.library.util.StringUtil;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/6/14 0014.
 * 补办
 */

public class ActivityCardReissue extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.report_for_loss)
    public TextView tvReportForLoss;//挂失
    @InjectView(R.id.ed_card_type)
    TextView edCardType;//卡类型
    @InjectView(R.id.cardNumReissue)
    TextView tvCardNumReissue;//卡号
    @InjectView(R.id.idNumReissue)
    TextView tvIdNumResissue;//身份证
    @InjectView(R.id.userNameReissue)
    TextView tvUserNameReissue;//持卡人
    @InjectView(R.id.phoneNumReissue)
    TextView edPhoneNumRessiue;//电话号码
    @InjectView(R.id.editVerify)
    EditText edVerify;//验证码输入框
    @InjectView(R.id.btnVerify)
    Button btnVerify;//验证码按钮
    @InjectView(R.id.bloodType)
    TextView tvBloodType;//血型
    @InjectView(R.id.deadDate)
    TextView tvDeadDate;//失效时间
    @InjectView(R.id.btn_next_step)
    Button btnNextStep;
    @InjectView(R.id.ll_noData)
    LinearLayout llNoData;
    @InjectView(R.id.ll_card_info)
    LinearLayout llCardInfo;
    private MyCountDownTimer countDownTimer;
    private HttpSubscriber httpSubscriber;
    private String type;
    private int goodsId;
    private Bundle bundle = new Bundle();

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_card_reissue);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        btnNextStep.setClickable(false);//设置下一步为不可点击按钮
    }

    @Override
    protected void initListener() {
        edCardType.setOnClickListener(this);
        tvReportForLoss.setOnClickListener(this);
        btnNextStep.setOnClickListener(this);
        btnVerify.setOnClickListener(this);
        edVerify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String verifyCode = s.toString();
                if (!StringUtil.isEmpty(verifyCode) && verifyCode.length() == 6) {
                    btnNextStep.setBackgroundResource(R.drawable.selector_login_button);
                    btnNextStep.setClickable(true);
                } else {
                    btnNextStep.setBackgroundResource(R.drawable.shape_with_no_corner);
                    btnNextStep.setClickable(false);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ed_card_type:
                lifeSaverUtil.showDialogList(this, R.array.card_type, "请选择卡类型", new DialogWithList.OnSelectedListener() {
                    @Override
                    public void setOnSelectedListener(int position, String content) {
                        edCardType.setText(content);
                        if (content.equals("生命宝航空救援卡")) {
                            goodsId = 1;
                        }
                        bundle.putInt("goodsId", goodsId);
                        getCardReissueInfo();
                    }
                });
                break;
            case R.id.report_for_loss:
                openActivityNotClose(ActivityReportForLoss.class, null);
                break;
            case R.id.btnVerify:
                countDownTimer = new MyCountDownTimer(btnVerify);
                countDownTimer.start();
                getVerifyCode(edPhoneNumRessiue.getText().toString().trim());
                btnVerify.setBackgroundResource(R.drawable.btn_gray);
                break;
            case R.id.btn_next_step:
                toNextStep();
                break;
        }
    }

    /**
     * 获取需要补办卡的信息
     */
    private void getCardReissueInfo() {
        unSub();
        httpSubscriber = new HttpSubscriber(onCardReissueInfoNextListener);
        Map<String, Integer> map = new HashMap<>();
        map.put("id", StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        map.put("goodsId", goodsId);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        GoodsAPI.getInstance().getCardReissueInfo(httpSubscriber, body);
    }

    SubscriberOnNextListener<CardReissueBean> onCardReissueInfoNextListener = new SubscriberOnNextListener<CardReissueBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(CardReissueBean cardReissueBean) {
            hideLoadingDialog();
            if (cardReissueBean != null) {
                llCardInfo.setVisibility(View.VISIBLE);
                llNoData.setVisibility(View.GONE);
                tvUserNameReissue.setText(cardReissueBean.getCompUserGoodsCardCostDto().getNickName());
                tvCardNumReissue.setText(cardReissueBean.getCompUserGoodsCardCostDto().getCardCode());
                tvBloodType.setText("");
                tvIdNumResissue.setText(cardReissueBean.getCompUserGoodsCardCostDto().getIdCode());
                tvDeadDate.setText(cardReissueBean.getCompUserGoodsCardCostDto().getExpiryDate());
                edPhoneNumRessiue.setText(cardReissueBean.getCompUserGoodsCardCostDto().getPhone());
            }
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
     * 点击下一步事件,需调用接口
     */
    private void toNextStep() {
        unSub();
        httpSubscriber = new HttpSubscriber(onCheckRandomNextListener);
        String phoneNum = edPhoneNumRessiue.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        type = "MU";
        map.put("mobileNo", phoneNum);
        map.put("type", type);
        map.put("randCode", edVerify.getText().toString().trim());
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
            openActivityNotClose(ActivityCardReissueConfirm.class, bundle);
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
     * 获取验证码
     *
     * @param phoneNum
     */
    private void getVerifyCode(String phoneNum) {
        unSub();
        phoneNum = edPhoneNumRessiue.getText().toString().trim();
        type = "MU";
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
