package com.byth.lifesaver.function.card.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.api.API;
import com.byth.lifesaver.api.GoodsAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.CardReissueConfirmBean;
import com.byth.lifesaver.bean.OrderSuccessBean;
import com.byth.lifesaver.bean.PurchaseDetailBean;
import com.byth.lifesaver.function.mine.activity.ActivityGoodsReceiptAddress;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.ImageLoaderUtil;
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
 * Created by Administrator on 2017/9/13 0013.
 * 补办确认
 */

public class ActivityCardReissueConfirm extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.ig_card)
    ImageView igCard;//卡片
    @InjectView(R.id.goodsName)
    TextView tvGoodsName;//名称
    @InjectView(R.id.price_card)
    TextView tvPriceCard;//卡价格
    @InjectView(R.id.cardType)
    TextView tvCardType;//卡类型
    @InjectView(R.id.cardNum)
    TextView tvCardNum;//卡号
    @InjectView(R.id.total_price)
    TextView tvTotalPrice;//总价
    @InjectView(R.id.contact_person)
    TextView tvContactPerson;//收货人
    @InjectView(R.id.phone_num)
    TextView tvPhoneNum;//电话号码
    @InjectView(R.id.address)
    TextView tvAddress;//地址
    @InjectView(R.id.isDefault)
    TextView isDefault;//是否默认
    @InjectView(R.id.info_customer)
    RelativeLayout rlInfoCustomer;//收货地址relativeLayout
    @InjectView(R.id.btn_pay_confirm)
    AppCompatButton btnPayConfirm;//确认支付按钮

    public static final int REQUEST_CODE = 1;
    private String consignee;
    private String consigneeMobile;
    private String orderCode;//订单号
    //    private String orderType;//订单类型：buyer购卡、renew续卡、makeUp补办
    private String address;//完整地址
    private String defaultFlag;//是否默认地址
    private int goodsId;
    private HttpSubscriber httpSubscriber;
    private CardReissueConfirmBean.CompGoodsAddrCostListDto compGoodsAddrCostListDto;
    private CardReissueConfirmBean.CompGoodsAddrCostListDto.GoodsDto goodsDto;
    private CardReissueConfirmBean.CompGoodsAddrCostListDto.AddressDto addressDto;

    private PurchaseDetailBean.PurchaseOrderInfo orderDto;
    private PurchaseDetailBean.PurchaseDetailInfo detailDto;
    private Bundle bundlePay = new Bundle();

    @Override
    protected void receiveDataFromPreActivity() {
        Bundle bundle = getIntent().getExtras();
        goodsId = bundle.getInt("goodsId");
    }

    @Override
    protected void initData() {
        compGoodsAddrCostListDto = new CardReissueConfirmBean.CompGoodsAddrCostListDto();
        goodsDto = new CardReissueConfirmBean.CompGoodsAddrCostListDto.GoodsDto();
        addressDto = new CardReissueConfirmBean.CompGoodsAddrCostListDto.AddressDto();
        orderDto = new PurchaseDetailBean.PurchaseOrderInfo();
        detailDto = new PurchaseDetailBean.PurchaseDetailInfo();
    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_card_reissue_confirm);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        getCardReissueConfirmInfo();
    }

    @Override
    protected void initListener() {
        rlInfoCustomer.setOnClickListener(this);
        btnPayConfirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay_confirm:
                takeReissueCardOrder();
                break;
            case R.id.info_customer:
                Bundle bundle = new Bundle();
                bundle.putString("tag", "A");
                startForResult(bundle, REQUEST_CODE, ActivityGoodsReceiptAddress.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 10) {
            consignee = data.getStringExtra("consignee");
            consigneeMobile = data.getStringExtra("consigneeMobile");
            address = data.getStringExtra("address");
            defaultFlag = data.getStringExtra("defaultFlag");
            tvContactPerson.setText("收货人:" + consignee);
            tvPhoneNum.setText("联系电话:" + consigneeMobile);
            tvAddress.setText("收货地址:" + address);
            if (defaultFlag.equals("Y")) {
                isDefault.setVisibility(View.VISIBLE);
            } else {
                isDefault.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 获取确认补卡所需信息
     */
    private void getCardReissueConfirmInfo() {
        unSub();
        httpSubscriber = new HttpSubscriber(onCardReissueConfirmInfoNextListener);
        Map<String, Integer> map = new HashMap<>();
        map.put("id", StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        map.put("goodsId", goodsId);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        GoodsAPI.getInstance().getCardReissueInfo(httpSubscriber, body);
    }

    SubscriberOnNextListener<CardReissueConfirmBean> onCardReissueConfirmInfoNextListener = new SubscriberOnNextListener<CardReissueConfirmBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(CardReissueConfirmBean cardReissueConfirmBean) {
            hideLoadingDialog();
            compGoodsAddrCostListDto = cardReissueConfirmBean.getCompGoodsAddrCostListDto();
            addressDto = compGoodsAddrCostListDto.getAddressDto();
            goodsDto = compGoodsAddrCostListDto.getGoodsDto();
            tvContactPerson.setText("收货人:" + addressDto.getConsignee());
            tvPhoneNum.setText("联系电话:" + addressDto.getConsigneeMobile());
            tvAddress.setText("收货地址:" + addressDto.getAddr());
            if (addressDto.getDefaultFlag().equals("Y")) {
                isDefault.setVisibility(View.VISIBLE);
            } else {
                isDefault.setVisibility(View.GONE);
            }
            ImageLoaderUtil.display(ActivityCardReissueConfirm.this, igCard, API.url + goodsDto.getImage());
            bundlePay.putString("imageUrl", API.url + goodsDto.getImage());
            bundlePay.putString("goodsName", goodsDto.getGoodsName());
            bundlePay.putString("cardNum", goodsDto.getGoodsCode());
            tvGoodsName.setText(goodsDto.getGoodsName());
            tvPriceCard.setText(String.valueOf(goodsDto.getMakeUpCost()) + "元");
            double totalPrice = goodsDto.getMakeUpCost() + goodsDto.getExpressCost();
            tvTotalPrice.setText(String.valueOf(totalPrice));
            tvCardNum.setText(goodsDto.getGoodsCode());
            switch (goodsId) {
                case 1:
                    tvCardType.setText("生命宝航空救援卡");
                    break;
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
     * 补办下单
     */
    private void takeReissueCardOrder() {
        unSub();
        httpSubscriber = new HttpSubscriber(onTakeOrderNextListener);
        orderDto.setUserId(StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        orderDto.setSum(StringUtil.toDouble(tvTotalPrice.getText().toString().trim()));
        orderDto.setConsignee(tvContactPerson.getText().toString().trim());
        orderDto.setConsigneeMobile(tvPhoneNum.getText().toString().trim());
        orderDto.setAddr(tvAddress.getText().toString().trim());
        detailDto.setGoodsCount(1);
        detailDto.setGoodsId(goodsId);
        Map<Object, Object> map = new HashMap<>();
        map.put("orderDto", orderDto);
        map.put("detailDto", detailDto);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        GoodsAPI.getInstance().reissueCardOrder(httpSubscriber, body);

    }

    SubscriberOnNextListener<OrderSuccessBean> onTakeOrderNextListener = new SubscriberOnNextListener<OrderSuccessBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(OrderSuccessBean orderSuccessBean) {
            hideLoadingDialog();
            orderCode = orderSuccessBean.getOrderCode();
            bundlePay.putString("orderCode", orderCode);
            bundlePay.putDouble("totalPrice", StringUtil.toDouble(tvTotalPrice.getText().toString().trim()));
            bundlePay.putString("cardYear", "1");
            openActivityNotClose(ActivityChoosePayMethod.class, bundlePay);
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

    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
    }
}
