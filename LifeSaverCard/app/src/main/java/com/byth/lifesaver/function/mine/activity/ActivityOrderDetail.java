package com.byth.lifesaver.function.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.api.API;
import com.byth.lifesaver.api.OrderAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.OrderDetailBean;
import com.byth.lifesaver.function.card.activity.ActivityChoosePayMethod;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.ImageLoaderUtil;
import com.byth.lifesaver.widget.TipsDialog;
import com.fenguo.library.util.JsonUtil;
import com.fenguo.library.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/7/17 0017.
 * 订单详情
 * auth:Lee
 */

public class ActivityOrderDetail extends FrameActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.payStatus)
    TextView tvPayStatus;//什么类型的订单?续卡：购卡etc
    @InjectView(R.id.tvLogisticInfo)
    TextView tvLogisticInfo;//物流信息
    @InjectView(R.id.orderNumLabel)
    TextView orderNumLabel;//订单号
    @InjectView(R.id.orderState)
    TextView orderState;//订单状态
    @InjectView(R.id.image_card)
    ImageView imageCard;//商品图片
    @InjectView(R.id.card_name)
    TextView cardName;//卡名
    @InjectView(R.id.card_price)
    TextView cardPrice;//卡价格
    @InjectView(R.id.count)
    TextView count;//总计
    @InjectView(R.id.year)
    TextView year;//年限
    @InjectView(R.id.payLabel)
    TextView payLabel;//付款金额
    @InjectView(R.id.contact_person)
    TextView contactPerson;//联系人
    @InjectView(R.id.phone_num)
    TextView phoneNum;//电话号码
    @InjectView(R.id.address)
    TextView address;//地址
    @InjectView(R.id.isDefault)
    TextView isDefault;//是否默认
    @InjectView(R.id.info_customer)
    RelativeLayout infoCustomer;
    @InjectView(R.id.notice)
    TextView notice;//备注
    @InjectView(R.id.checkLogistic)
    Button checkLogistic;//查看物流信息
    @InjectView(R.id.receiptConfirm)
    Button receiptConfirm;
    private HttpSubscriber httpSubscriber;
    private Integer orderId;//订单id
    private String orderStatus;//订单状态
    private OrderDetailBean.GoodsDto goodsDto;
    private OrderDetailBean.OrderDto orderDto;
    private OrderDetailBean.DetailDto detailDto;
    private Bundle bundle = new Bundle();
    private double totalPrice;
    private String cardNum;
    private String cardYear;
    private String imageUrl;
    private String goodsName;
    private String orderCode;//订单号

    @Override
    protected void receiveDataFromPreActivity() {
        Bundle bundle = getIntent().getExtras();
        orderId = bundle.getInt("id");
        orderStatus = bundle.getString("status");
        totalPrice = bundle.getDouble("totalPrice");
        cardNum = bundle.getString("cardNum");
        cardYear = bundle.getString("cardYear");
        imageUrl = bundle.getString("imageUrl");
        goodsName = bundle.getString("goodsName");
        orderCode = bundle.getString("orderCode");
    }

    @Override
    protected void initData() {
        goodsDto = new OrderDetailBean.GoodsDto();
        orderDto = new OrderDetailBean.OrderDto();
        detailDto = new OrderDetailBean.DetailDto();
    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_order_detail);
        setToolbar(toolbar);
        showButtonWithStatus();
        getOrderDetail();
    }

    @Override
    protected void initListener() {

    }

    /**
     * 根据传入的不同状态的订单，详情按钮显示和点击事件也不一样
     */
    private void showButtonWithStatus() {
        if (String.valueOf(orderStatus).equals("UNPAY")) {
            tvLogisticInfo.setVisibility(View.GONE);
            checkLogistic.setText("取消订单");
            receiptConfirm.setText("确认支付");
            checkLogistic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("取消订单");
                }
            });
            receiptConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundle.putDouble("totalPrice", totalPrice);
                    bundle.putString("imageUrl", imageUrl);
                    bundle.putString("cardYear", cardYear);
                    bundle.putString("goodsName", goodsName);
                    bundle.putString("orderCode", orderCode);
                    openActivityNotClose(ActivityChoosePayMethod.class, bundle);
                }
            });
        } else if (String.valueOf(orderStatus).equals("UNREV")) {
            checkLogistic.setText("查看物流");
            receiptConfirm.setText("确认收货");
            tvLogisticInfo.setVisibility(View.VISIBLE);
            tvLogisticInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("我是物流信息");
                }
            });
            checkLogistic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("查看物流");
                }
            });
            receiptConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TipsDialog.makeDialog(ActivityOrderDetail.this, "提示", "是否确认收货？", "确认", "取消", new TipsDialog.onDialogListener() {
                        @Override
                        public void onOkClick() {
                            showToast("确认收货");
                        }

                        @Override
                        public void onCancelClick() {

                        }
                    }).show();
                }
            });
        } else if (String.valueOf(orderStatus).equals("CMPLD")) {
            checkLogistic.setText("删除订单");
            receiptConfirm.setText("再次购买");
            tvLogisticInfo.setVisibility(View.GONE);
            checkLogistic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TipsDialog.makeDialog(ActivityOrderDetail.this, "提示", "确认删除订单？", "确认", "取消", new TipsDialog.onDialogListener() {
                        @Override
                        public void onOkClick() {
                            showToast("点击了删除");
                        }

                        @Override
                        public void onCancelClick() {

                        }
                    }).show();
                }
            });
            receiptConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("再次购买");
                }
            });
        }
    }

    /**
     * 获取订单详情
     */
    private void getOrderDetail() {
        unSub();
        httpSubscriber = new HttpSubscriber(onOrderDetailNextListener);
        Map<String, Integer> map = new HashMap<>();
        map.put("id", orderId);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        OrderAPI.getInstance().getOrderDetail(httpSubscriber, body);
    }

    SubscriberOnNextListener<OrderDetailBean> onOrderDetailNextListener = new SubscriberOnNextListener<OrderDetailBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(OrderDetailBean orderDetailBean) {
            goodsDto = orderDetailBean.getGoodsDto();
            orderDto = orderDetailBean.getOrderDto();
            detailDto = orderDetailBean.getDetailDto();
            orderNumLabel.setText(orderDto.getOrderCode());
            switch (String.valueOf(orderDto.getOrderStatus())) {
                case "CMPLD":
                    orderState.setText("已完成");
                    break;
                case "UNREV":
                    orderState.setText("待收货");
                    break;
                case "UNPAY":
                    orderState.setText("待付款");
                    break;
            }
            ImageLoaderUtil.display(getApplicationContext(), imageCard, API.url + goodsDto.getImage());
            cardName.setText(goodsDto.getGoodsName());
            cardPrice.setText(String.valueOf(detailDto.getYearPrice()) + "元");
            count.setText(String.valueOf(detailDto.getGoodsCount()));
            year.setText(String.valueOf(detailDto.getYears()) + "年");
            payLabel.setText(String.valueOf(orderDto.getSum()) + "元");
            contactPerson.setText("收货人：" + orderDto.getConsignee());
            address.setText("收货地址：" + orderDto.getAddr());
            phoneNum.setText("联系电话：" + orderDto.getConsigneeMobile());
            if (StringUtil.isEmpty(orderDto.getOrderType())) {
                tvPayStatus.setVisibility(View.GONE);
            } else {
                tvPayStatus.setVisibility(View.VISIBLE);
            }
            switch (String.valueOf(orderDto.getOrderType())) {
                case "BUYCR":
                    tvPayStatus.setText("购买");
                    break;
                case "RENEW":
                    tvPayStatus.setText("续卡");
                    break;
                case "MAKUP":
                    tvPayStatus.setText("补办");
                    break;
            }
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
     * 解除订阅
     */
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
    }
}
