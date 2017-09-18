package com.byth.lifesaver.function.card.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.api.GoodsAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.AddressListBean;
import com.byth.lifesaver.bean.CardActiveInfoBean;
import com.byth.lifesaver.bean.CardDetailBean;
import com.byth.lifesaver.http.ApiException;
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
 * Created by Administrator on 2017/6/9 0009.
 * 激活卡
 * Step1:卡信息确认
 * TODO
 */

public class ActivityCardInfoConfirm extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.ed_card_num)
    EditText edCardNum;
    @InjectView(R.id.card_type)
    TextView tvCardType;
    @InjectView(R.id.card_way_sell)
    TextView tvWaySell;
    @InjectView(R.id.btn_next_step)
    Button btnNextStep;
    @InjectView(R.id.hot_line)
    TextView tvHotLine;
    private boolean isUsefulCard;//是否为有效的卡
    private HttpSubscriber httpSubscriber;
    private CardActiveInfoBean.UserDto userDto;
    private CardActiveInfoBean.CardDto cardDto;


    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {
        userDto = new CardActiveInfoBean.UserDto();
        cardDto = new CardActiveInfoBean.CardDto();
    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_card_info_confirm);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
    }

    @Override
    protected void initListener() {
        btnNextStep.setOnClickListener(this);
        edCardNum.addTextChangedListener(watcher);
        tvHotLine.setOnClickListener(this);
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
            if (s.length() == 16) {
                getCardInfo();//获取卡信息，需要调用接口
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next_step:
                if (StringUtil.isEmpty(edCardNum.getText().toString().trim()) || !isUsefulCard) {
                    showToast("请填写有效卡号");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("cardCode", edCardNum.getText().toString().trim());
                    openActivityNotClose(ActivityCardUserInfo.class, bundle);
                }
                break;
            case R.id.hot_line:
                telPhone(getResources().getString(R.string.hot_line_of_company));
                break;
        }
    }

    /**
     * 获取卡信息
     */
    private void getCardInfo() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
        httpSubscriber = new HttpSubscriber(onNextListener);
        String cardCode = edCardNum.getText().toString().trim();
        userDto.setId(StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        cardDto.setCardCode(cardCode);
        Map<Object, Object> map = new HashMap<>();
        /*map.put("cardCode", cardCode);
        map.put("id", StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));*/
        map.put("userDto", userDto);
        map.put("cardDto", cardDto);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        GoodsAPI.getInstance().getCardDetail(httpSubscriber, body);
    }

    SubscriberOnNextListener<CardDetailBean> onNextListener = new SubscriberOnNextListener<CardDetailBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(CardDetailBean cardDetailBean) {
            hideLoadingDialog();
            if (cardDetailBean != null) {
                tvCardType.setText(cardDetailBean.getGoodsName());
                tvWaySell.setText(cardDetailBean.getDistribName());
                isUsefulCard = true;
            }
        }

        @Override
        public void onApiError(ApiException e) {
            isUsefulCard = false;
            hideLoadingDialog();
            showToast(e.getMessage());
        }

        @Override
        public void onNetworkError(Throwable e) {
            isUsefulCard = false;
            showToast(e.getMessage());
            hideLoadingDialog();
        }

        @Override
        public void onOtherError(Throwable e) {
            isUsefulCard = false;
            showToast(e.getMessage());
            hideLoadingDialog();
        }

        @Override
        public void onCompleted() {
            hideLoadingDialog();
        }
    };
}