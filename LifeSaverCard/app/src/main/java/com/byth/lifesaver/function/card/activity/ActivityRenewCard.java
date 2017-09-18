package com.byth.lifesaver.function.card.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.api.API;
import com.byth.lifesaver.api.GoodsAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.CardRenewBean;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.ImageLoaderUtil;
import com.byth.lifesaver.util.LifeSaverPreference;
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
 * 续费
 */

public class ActivityRenewCard extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.ed_card_type)
    TextView edCardType;//卡类型
    @InjectView(R.id.tv_card_renew_num)
    TextView tvRenewNum;//卡号
    @InjectView(R.id.userName)
    TextView tvUserName;//持卡人
    @InjectView(R.id.bindPhone)
    TextView tvBindPhone;//绑定手机
    @InjectView(R.id.idNum)
    TextView tvIdNum;//身份证
    @InjectView(R.id.cardPrice)
    TextView tvCardPrice;//卡费
    @InjectView(R.id.deadTime)
    TextView tvDeadTime;//到期时间
    @InjectView(R.id.cardImage)
    ImageView igCard;//卡图片
    @InjectView(R.id.llRenewCardInfo)
    LinearLayout llRenewCardInfo;
    @InjectView(R.id.btn_next_step)
    Button btnNextStep;//下一步按钮
    private boolean canNextStep = false;//是否可以点击下一步标识
    private Integer goodsId;
    private HttpSubscriber httpSubscriber;
    private Bundle bundle = new Bundle();

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_renew_card);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
    }

    @Override
    protected void initListener() {
        edCardType.setOnClickListener(this);
        btnNextStep.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ed_card_type:
                lifeSaverUtil.showDialogList(this, R.array.card_type, "请选择卡类型", new DialogWithList.OnSelectedListener() {
                    @Override
                    public void setOnSelectedListener(int position, String content) {
                        edCardType.setText(content);
                        bundle.putString("goodsName", content);
                        if (content.equals("生命宝航空救援卡")) {
                            goodsId = 1;
                        }
                        bundle.putInt("goodsId", goodsId);
                        getCardRenewInfo();
                    }
                });
                break;
            case R.id.btn_next_step:
                if (canNextStep) {
                    openActivityNotClose(ActivityRenewCardConfirm.class, bundle);
                } else {
                    showToast("请选择需要激活的卡片");
                }
                break;
        }
    }

    /**
     * 获取续费卡信息
     */
    private void getCardRenewInfo() {
        unSub();
        httpSubscriber = new HttpSubscriber(onCardRenewNextListener);
        Map<String, Integer> map = new HashMap<>();
        map.put("id", StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        map.put("goodsId", goodsId);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        GoodsAPI.getInstance().getCardRenewInfo(httpSubscriber, body);
    }

    SubscriberOnNextListener<CardRenewBean> onCardRenewNextListener = new SubscriberOnNextListener<CardRenewBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(CardRenewBean cardRenewBean) {
            hideLoadingDialog();
            if (cardRenewBean != null) {
                llRenewCardInfo.setVisibility(View.VISIBLE);
                tvUserName.setText("使用人:" + cardRenewBean.getNickName());
                tvBindPhone.setText("绑定手机:" + cardRenewBean.getPhone());
                tvDeadTime.setText("失效时间:" + cardRenewBean.getExpiryDate());
                tvIdNum.setText("身份证:" + cardRenewBean.getIdCode());
                tvCardPrice.setText(String.valueOf(cardRenewBean.getYearPrice()));
                tvRenewNum.setText(cardRenewBean.getCardCode());
                ImageLoaderUtil.display(ActivityRenewCard.this, igCard, API.url + cardRenewBean.getImage());
                bundle.putString("cardNum", cardRenewBean.getCardCode());
                bundle.putString("imageUrl", API.url + cardRenewBean.getImage());
                canNextStep = true;
            }
        }

        @Override
        public void onApiError(ApiException e) {
            hideLoadingDialog();
            showToast(e.getMessage());
            llRenewCardInfo.setVisibility(View.GONE);
            canNextStep = false;
        }

        @Override
        public void onNetworkError(Throwable e) {
            hideLoadingDialog();
            showToast(e.getMessage());
            llRenewCardInfo.setVisibility(View.GONE);
            canNextStep = false;
        }

        @Override
        public void onOtherError(Throwable e) {
            hideLoadingDialog();
            showToast(e.getMessage());
            llRenewCardInfo.setVisibility(View.GONE);
            canNextStep = false;
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