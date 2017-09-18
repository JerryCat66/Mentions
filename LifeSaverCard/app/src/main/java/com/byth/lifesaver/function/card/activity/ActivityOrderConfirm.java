package com.byth.lifesaver.function.card.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.adapter.RadioButtonAdapter;
import com.byth.lifesaver.api.API;
import com.byth.lifesaver.api.GoodsAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.AddressListBean;
import com.byth.lifesaver.bean.GoodAddressBean;
import com.byth.lifesaver.bean.OrderSuccessBean;
import com.byth.lifesaver.bean.PurchaseDetailBean;
import com.byth.lifesaver.function.mine.activity.ActivityGoodsReceiptAddress;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpResult;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.ImageLoaderUtil;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.byth.lifesaver.widget.MyGridView;
import com.fenguo.library.util.JsonUtil;
import com.fenguo.library.util.StringUtil;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/6/2 0002.
 * 订单确认
 */

public class ActivityOrderConfirm extends FrameActivity implements View.OnClickListener, RadioButtonAdapter.IRadioNumClickListener {
    private static final String TAG = "msg";
    @InjectView(R.id.num_year)
    MyGridView numYears;//年限选择
    @InjectView(R.id.price_card)
    TextView tvPrice;//卡片价格
    @InjectView(R.id.ig_card)
    ImageView ivCard;//图片
    @InjectView(R.id.goodsName)
    TextView tvGoodsName;//商品名称
    @InjectView(R.id.trade_btn_num_reduce)
    ImageButton ibReduce;//减少按钮
    @InjectView(R.id.trade_btn_num_raise)
    ImageButton ibRaise;//增加按钮
    @InjectView(R.id.trade_edit_num_input)
    TextView tvNum;//数量
    @InjectView(R.id.total_price)
    TextView tvTotalPrice;//总额
    @InjectView(R.id.btn_pay_confirm)
    AppCompatButton btnConfirm;//确认支付
    @InjectView(R.id.info_customer)
    RelativeLayout rlContact;
    @InjectView(R.id.contact_person)
    TextView tvContact;//联系人
    @InjectView(R.id.phone_num)
    TextView tvPhoneNum;//电话
    @InjectView(R.id.address)
    TextView tvAddress;//地址
    @InjectView(R.id.isDefault)
    TextView tvIsDefault;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.notice)
    EditText edNotice;//备注

    private RadioButtonAdapter adapter;
    private double card_price;//卡片价格,年限对应单价
    private double totalPrice;//总额
    private int finalResult;
    private int goodsId;
    private String consignee;
    private String consigneeMobile;
    private String remark;//备注
    private String orderCode;//订单号
    private String orderType;//订单类型：buyer购卡、renew续卡、makeUp补办
    private String address;//完整地址
    private String defaultFlag;//是否默认地址
    public static final int REQUEST_CODE = 1;
    Bundle bundle = new Bundle();
    private HttpSubscriber httpSubscriber;
    private GoodAddressBean.GoodsDto goodsInfoList;//商品信息list
    private List<GoodAddressBean.CostDtoList> costList;//价格年限list
    private GoodAddressBean.AddressDto addressList;//地址信息list
    private PurchaseDetailBean.PurchaseGoodsInfo goodsDto;
    private PurchaseDetailBean.PurchaseOrderInfo orderDto;
    private PurchaseDetailBean.PurchaseDetailInfo detailDto;
    private AddressListBean.UserDto userDto;

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {
        costList = new ArrayList<>();
        goodsInfoList = new GoodAddressBean.GoodsDto();
        addressList = new GoodAddressBean.AddressDto();
        goodsDto = new PurchaseDetailBean.PurchaseGoodsInfo();
        orderDto = new PurchaseDetailBean.PurchaseOrderInfo();
        detailDto = new PurchaseDetailBean.PurchaseDetailInfo();
        userDto = new AddressListBean.UserDto();
    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_order_confirm);
        setToolbar(mToolbar);
        adapter = new RadioButtonAdapter(this, 0);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        numYears.setAdapter(adapter);
        tvNum.setText("0");
        orderType = "BUYCR";
        getDefaultAddress();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initListener() {
        ibRaise.setOnClickListener(this);
        ibReduce.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        rlContact.setOnClickListener(this);
        adapter.registerOnClick(this);
    }

    @Override
    public void onClick(View v) {
        String num = tvNum.getText().toString().trim();
        int result = StringUtil.toInt(num);
        bundle.putString("cardNum", num);
        switch (v.getId()) {
            case R.id.trade_btn_num_raise:
                finalResult = ++result;
                tvNum.setText(String.valueOf(finalResult));
                totalPrice = card_price * finalResult;
                tvTotalPrice.setText(String.valueOf(totalPrice));
                detailDto.setGoodsCount(finalResult);
                break;
            case R.id.trade_btn_num_reduce:
                if (result <= 1) {
                    showToast("商品数量不能小于1");
                } else {
                    finalResult = --result;
                    tvNum.setText(String.valueOf(finalResult));
                    totalPrice = card_price * finalResult;
                    tvTotalPrice.setText(String.valueOf(totalPrice));
                    detailDto.setGoodsCount(finalResult);
                }
                break;
            case R.id.btn_pay_confirm:
                checkRequire();
                break;
            case R.id.info_customer://收货地址选择
                Bundle bundle = new Bundle();
                bundle.putString("tag", "A");
                //openActivityNotClose(ActivityGoodsReceiptAddress.class, bundle);
                startForResult(bundle, REQUEST_CODE, ActivityGoodsReceiptAddress.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 10) {
            /*Bundle bundle = getIntent().getExtras();
            consignee = bundle.getString("consignee");
            consigneeMobile = bundle.getString("consigneeMobile");
            address = bundle.getString("address");
            defaultFlag = bundle.getString("defaultFlag");*/
            consignee = data.getStringExtra("consignee");
            consigneeMobile = data.getStringExtra("consigneeMobile");
            address = data.getStringExtra("address");
            defaultFlag = data.getStringExtra("defaultFlag");
            tvContact.setText("收货人:" + consignee);
            tvPhoneNum.setText("联系电话:" + consigneeMobile);
            tvAddress.setText("收货地址:" + address);
            if (defaultFlag.equals("Y")) {
                tvIsDefault.setVisibility(View.VISIBLE);
            } else {
                tvIsDefault.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 检查必填项
     */
    private void checkRequire() {
        totalPrice = StringUtil.toDouble(tvTotalPrice.getText().toString().trim());
        if (totalPrice == 0) {
            showToast("请选择购买数量和年限");
        } else if (StringUtil.isEmpty(consignee) || StringUtil.isEmpty(consigneeMobile) || StringUtil.isEmpty(address)) {
            showToast("请选择收货地址");
        } else {
            orderDto.setSum(totalPrice);
            getPurchaseInfo();//下单
        }

    }

    private void getPurchaseInfo() {
        unSub();
        httpSubscriber = new HttpSubscriber(onPurchaseOnNextListener);
        Map<Object, Object> map = new HashMap<>();
        userDto.setId(StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        orderDto.setAddr(address);
        orderDto.setConsignee(consignee);
        orderDto.setConsigneeMobile(consigneeMobile);
        map.put("userDto", userDto);
        map.put("goodsDto", goodsDto);
        map.put("orderDto", orderDto);
        map.put("detailDto", detailDto);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        GoodsAPI.getInstance().getPurchaseInfo(httpSubscriber, body);
    }

    SubscriberOnNextListener<OrderSuccessBean> onPurchaseOnNextListener = new SubscriberOnNextListener<OrderSuccessBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(OrderSuccessBean orderSuccessBean) {
            showToast("下单成功，请尽快付款");
            orderCode = orderSuccessBean.getOrderCode();
            bundle.putString("orderCode", orderCode);
            bundle.putDouble("totalPrice", totalPrice);
            openActivityNotClose(ActivityChoosePayMethod.class, bundle);
            hideLoadingDialog();
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
     * 获取默认地址
     */
    private void getDefaultAddress() {
        unSub();
        httpSubscriber = new HttpSubscriber(onNextListener);
        Map<String, Integer> map = new HashMap<>();
        map.put("id", StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        GoodsAPI.getInstance().getDefaultAddress(httpSubscriber, body);

    }

    SubscriberOnNextListener<GoodAddressBean> onNextListener = new SubscriberOnNextListener<GoodAddressBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(GoodAddressBean defaultAddressBean) {
            hideLoadingDialog();
            goodsInfoList = defaultAddressBean.getGoodsDto();
            addressList = defaultAddressBean.getAddrDto();
            goodsId = goodsInfoList.getGoodsId();
            goodsDto.setGoodsId(goodsId);
            orderDto.setRegion(510000);
            orderDto.setExpressOrder("545454545454");
            remark = edNotice.getText().toString().trim();
            orderDto.setRemarks(remark);
            orderDto.setLogisticsCompany("顺丰吧");
            orderDto.setOrderType(orderType);
            if (addressList != null) {
                consignee = addressList.getConsignee();
                consigneeMobile = addressList.getConsigneeMobile();
                address = addressList.getAddr() + addressList.getRegion_belong();
                tvContact.setText("收货人:" + consignee);
                tvPhoneNum.setText("联系电话:" + consigneeMobile);
                tvAddress.setText("收货地址:" + address);
                if (addressList.getDefaultFlag().equals("Y")) {
                    tvIsDefault.setVisibility(View.VISIBLE);
                } else {
                    tvIsDefault.setVisibility(View.GONE);
                }
            } else {
                tvContact.setText("收货人:");
                tvPhoneNum.setText("联系电话:");
                tvAddress.setText("收货地址:");
                tvIsDefault.setVisibility(View.GONE);
            }
            tvGoodsName.setText(goodsInfoList.getGoodsName());
            bundle.putString("goodsName", goodsInfoList.getGoodsName());
            bundle.putString("imageUrl", API.url + goodsInfoList.getImage());
            ImageLoaderUtil.display(ActivityOrderConfirm.this, ivCard, API.url + goodsInfoList.getImage());//加载图片
            ActivityOrderConfirm.this.costList = defaultAddressBean.getCostDtoList();
            for (int j = 0; j < costList.size(); j++) {
                tvPrice.setText(StringUtil.toString(costList.get(0).getYearPrice()) + "元");
            }
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
            hideLoadingDialog();
            Log.i(TAG, "onOtherError: " + e.getMessage());
        }

        @Override
        public void onCompleted() {
            hideLoadingDialog();
        }
    };

    /**
     * 解除订阅
     */
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
    }

    @Override
    public void onClick(RadioButtonAdapter context, String numYear) {
        for (int i = 0; i < costList.size(); i++) {
            if (StringUtil.toInt(numYear.replaceAll("年", "")) == 1) {
                card_price = costList.get(0).getYearPrice();
            } else if (StringUtil.toInt(numYear.replaceAll("年", "")) == 2) {
                card_price = costList.get(1).getYearPrice();
            } else if (StringUtil.toInt(numYear.replaceAll("年", "")) == 3) {
                card_price = costList.get(2).getYearPrice();
            } else if (StringUtil.toInt(numYear.replaceAll("年", "")) == 5) {
                card_price = costList.get(3).getYearPrice();
            }
            int num = StringUtil.toInt(tvNum.getText().toString().trim());
            totalPrice = card_price * num;
            tvTotalPrice.setText(String.valueOf(totalPrice));
            tvPrice.setText(String.valueOf(card_price) + "元");
            detailDto.setYears(StringUtil.toInt(numYear.replaceAll("年", "")));
            detailDto.setYearPrice(card_price);
        }
        bundle.putString("cardYear", numYear);
    }
}