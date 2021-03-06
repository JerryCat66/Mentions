package com.byth.lifesaver.function.card.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.api.OrderAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.WeChatRechargeBean;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.Contants;
import com.byth.lifesaver.util.ImageLoaderUtil;
import com.byth.lifesaver.util.OnSingleClickListener;
import com.byth.lifesaver.widget.TipsDialog;
import com.fenguo.library.util.JsonUtil;
import com.fenguo.library.util.StringUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/6/6 0006.
 * 选择支付方式
 */

public class ActivityChoosePayMethod extends FrameActivity {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.ig_card)
    ImageView igCard;//商品图片
    @InjectView(R.id.goodsName)
    TextView tvGoodsName;//商品名称
    @InjectView(R.id.cardYear)
    TextView tvCardYear;//年限
    @InjectView(R.id.cardNum)
    TextView tvCardNum;//卡数量
    @InjectView(R.id.total_price)
    TextView tvTotalPrice;//总额
    @InjectView(R.id.radioGroup_payType)
    RadioGroup rgPayType;
    @InjectView(R.id.wx_radioBtn)
    RadioButton rbWeixin;//微信支付
    @InjectView(R.id.alipay_radioBtn)
    RadioButton rbAlipay;//支付宝支付
    @InjectView(R.id.submitPayBtn)
    Button btnSubmit;
    public static final int ORDER_PAY_WITH_WEICHAT = 1;//微信支付为1，支付宝支付为2，默认微信支付
    public static final int ORDER_PAY_WITH_ALIPAY = 2;
    private IWXAPI api;
    private IntentFilter intentFilter;
    private HttpSubscriber httpSubscriber;
    private Bundle bundle = new Bundle();

    private int payType = ORDER_PAY_WITH_WEICHAT;
    private double totalPrice;
    private String total;
    private String cardNum;
    private String cardYear;
    private String imageUrl;
    private String goodsName;
    private String orderCode;//订单号
    private String tradeType;//交易终端，固定APP

    @Override
    protected void receiveDataFromPreActivity() {
        Bundle bundle = getIntent().getExtras();
        totalPrice = bundle.getDouble("totalPrice");
        cardNum = bundle.getString("cardNum");
        cardYear = bundle.getString("cardYear");
        imageUrl = bundle.getString("imageUrl");
        goodsName = bundle.getString("goodsName");
        orderCode = bundle.getString("orderCode");
    }

    @Override
    protected void initData() {
        total = String.valueOf(totalPrice);
    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.acitivity_choose_payment_method);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        tvTotalPrice.setText(total);
        if (StringUtil.isEmpty(cardNum)) {
            tvCardNum.setVisibility(View.GONE);
        } else {
            tvCardNum.setVisibility(View.VISIBLE);
            tvCardNum.setText(cardNum + "张");
        }
        tvCardYear.setText("使用年限" + cardYear);
        tvGoodsName.setText(goodsName);
        ImageLoaderUtil.display(this, igCard, imageUrl);
        intentFilter = new IntentFilter("com.byth.lifesaver.wxapi.pay");
        registerReceiver(receiver, intentFilter);
        api = WXAPIFactory.createWXAPI(this, Contants.APP_ID);
        api.registerApp(Contants.APP_ID);
        rbAlipay.setVisibility(View.GONE);//TODO 暂时没有对接支付宝支付，设置为不可见2017.9.11
    }

    @Override
    protected void initListener() {
        btnSubmit.setOnClickListener(new OnSingleClickListener(1000) {
            @Override
            protected void onSingleClick(View view) {
                if (!rbWeixin.isChecked() && !rbAlipay.isChecked()) {
                    showToast("请选择支付方式");
                } else if (payType == ORDER_PAY_WITH_WEICHAT) {
                    if (!api.isWXAppInstalled()) {
                        showToast("未安装微信，请先安装微信客户端才能支付");
                    } else if (!api.isWXAppSupportAPI()) {
                        showToast("当前微信版本过低，请先升级微信客户端");
                    } else {
                        getWeChatRecharge();//获取微信支付参数
                    }
                } else {
                    showToast("选择的是支付宝支付");
                }
            }
        });
        rgPayType.setOnCheckedChangeListener(checkedChangeListener);//radioButton添加适配器
    }

    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.wx_radioBtn:
                    payType = ORDER_PAY_WITH_WEICHAT;
                    break;
                case R.id.alipay_radioBtn:
                    payType = ORDER_PAY_WITH_ALIPAY;
                    break;
            }
        }
    };


    //获取微信支付参数
    private void getWeChatRecharge() {
        unSub();
        httpSubscriber = new HttpSubscriber(onGetWeChatPayInfoListener);
        tradeType = "APP";
        Map<String, String> map = new HashMap<>();
        map.put("trade_type", tradeType);
        map.put("out_trade_no", orderCode);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        OrderAPI.getInstance().getWeChatPayInfo(httpSubscriber, body);
    }

    SubscriberOnNextListener<WeChatRechargeBean> onGetWeChatPayInfoListener = new SubscriberOnNextListener<WeChatRechargeBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(WeChatRechargeBean weChatRechargeBean) {
            hideLoadingDialog();
            reqWeChatRecharge(weChatRechargeBean);
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

    //调起微信支付
    private void reqWeChatRecharge(WeChatRechargeBean weChatRechargeBean) {
        PayReq req = new PayReq();//请求参数
        req.appId = Contants.APP_ID;
        req.partnerId = weChatRechargeBean.getPartnerid();
        req.prepayId = weChatRechargeBean.getPrepayid();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = weChatRechargeBean.getNoncestr();
        req.timeStamp = weChatRechargeBean.getTimestamp();
        req.sign = weChatRechargeBean.getSign();
        api.sendReq(req);
    }

    //接收微信支付回调
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra("payResult");
            String notice = "";
            if (result.equals("0")) {
                bundle.putString("orderCode", orderCode);
                openActivityNotClose(ActivityPayResult.class, bundle);
            } else if (result.equals("-1")) {
                notice = "微信支付提示:支付失败";
                showTipsDialog(notice);
            } else {
                notice = "微信支付提示:用户取消";
                showTipsDialog(notice);
            }

        }
    };

    //支付状态提示
    private void showTipsDialog(String notice) {
        TipsDialog.makeDialog(ActivityChoosePayMethod.this, "提示", notice, "确定", new TipsDialog.onDialogListener() {
            @Override
            public void onOkClick() {

            }

            @Override
            public void onCancelClick() {

            }
        }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    //解除订阅
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
    }
}
