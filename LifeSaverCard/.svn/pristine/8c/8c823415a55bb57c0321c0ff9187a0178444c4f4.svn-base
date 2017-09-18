package com.byth.lifesaver.function.card.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.byth.lifesaver.MainActivity;
import com.byth.lifesaver.R;
import com.byth.lifesaver.api.OrderAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.PayResultBean;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.fenguo.library.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/8/22 0022.
 * 支付结果
 */

public class ActivityPayResult extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.payPrice)
    TextView tvPayPrice;//总额
    @InjectView(R.id.tvTradeObject)
    TextView tvTradeObject;//交易对象
    @InjectView(R.id.tvTradeProduct)
    TextView tvTradeProduct;//商品描述
    @InjectView(R.id.tvTradeTime)
    TextView tvTradeTime;//交易时间
    @InjectView(R.id.tvTradeNum)
    TextView tvTradeNum;//交易单号
    @InjectView(R.id.tvTradeRemark)
    TextView tvTradeRemark;//备注
    @InjectView(R.id.btnBack)
    AppCompatButton btnBack;
    private HttpSubscriber httpSubscriber;
    private String orderCode;

    @Override
    protected void receiveDataFromPreActivity() {
        Bundle bundle = getIntent().getExtras();
        orderCode = bundle.getString("orderCode");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_pay_result);
        setToolbar(mToolbar);
        getPayResult();
    }

    /**
     * 获取支付接口
     */
    private void getPayResult() {
        unSub();
        httpSubscriber = new HttpSubscriber(onPayResultNextListener);
        Map<String, String> map = new HashMap<>();
        map.put("orderCode", orderCode);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        OrderAPI.getInstance().getPayResultInfo(httpSubscriber, body);
    }

    SubscriberOnNextListener<PayResultBean> onPayResultNextListener = new SubscriberOnNextListener<PayResultBean>() {
        @Override
        public void onStart() {
            hideLoadingDialog();
        }

        @Override
        public void onNext(PayResultBean payResultBean) {
            hideLoadingDialog();
            tvPayPrice.setText("￥" + String.valueOf(payResultBean.getSum()) + "元");
            tvTradeNum.setText(payResultBean.getOrderCode());
            tvTradeObject.setText(payResultBean.getPayee());
            tvTradeProduct.setText(payResultBean.getGoodsName());
            tvTradeTime.setText(payResultBean.getTradeTime());
            tvTradeRemark.setText(payResultBean.getRemarks());
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

    @Override
    protected void initListener() {
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                openActivity(MainActivity.class, null);
                break;
        }
    }

    //解除订阅
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
    }
}
