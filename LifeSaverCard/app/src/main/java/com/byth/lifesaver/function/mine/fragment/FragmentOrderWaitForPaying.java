package com.byth.lifesaver.function.mine.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.api.API;
import com.byth.lifesaver.api.OrderAPI;
import com.byth.lifesaver.base.BaseFragment;
import com.byth.lifesaver.bean.AddressListBean;
import com.byth.lifesaver.bean.OrderBean;
import com.byth.lifesaver.bean.OrderPageBean;
import com.byth.lifesaver.function.card.activity.ActivityChoosePayMethod;
import com.byth.lifesaver.function.mine.activity.ActivityOrderDetail;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.Contants;
import com.byth.lifesaver.util.ImageLoaderUtil;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.byth.lifesaver.util.LoadType;
import com.byth.lifesaver.widget.refreshlayout.RefreshLayout;
import com.byth.lifesaver.widget.refreshlayout.RefreshLayoutDirection;
import com.fenguo.library.adapter.BaseAdapterHelper;
import com.fenguo.library.adapter.QuickAdapter;
import com.fenguo.library.util.JsonUtil;
import com.fenguo.library.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/6/12 0012.
 * 待付款fragment
 * TODO
 */

public class FragmentOrderWaitForPaying extends BaseFragment implements AdapterView.OnItemClickListener {
    @InjectView(R.id.list)
    ListView listView;
    @InjectView(R.id.refresh)
    RefreshLayout refreshLayout;
    @InjectView(R.id.ll_noData)
    LinearLayout llNoData;
    private List<OrderBean.data> data;
    private QuickAdapter<OrderBean.data> adapter;
    private HttpSubscriber httpSubscriber;
    private String orderStatus;//订单状态
    private Bundle bundle = new Bundle();
    private Integer pageNum;
    private Integer maxPage;
    private AddressListBean.UserDto userDto;
    private OrderPageBean.OrderDto orderDto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = setContentView(inflater, R.layout.fragment_my_order);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //getUnPayOrderList();
    }

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initComponent() {
        setRefreshLayout(refreshLayout);
        listView.setAdapter(adapter);
        userDto = new AddressListBean.UserDto();
        orderDto = new OrderPageBean.OrderDto();
        pageNum = 1;
        getUnPayOrderList();
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onPullDownToRefresh() {
                pageNum = 1;
                loadType = LoadType.ReplaceALL;
                refreshLayout.setDirection(RefreshLayoutDirection.BOTH);
                getUnPayOrderList();
            }

            @Override
            public void onPullUpToRefresh() {
                pageNum += 1;
                getUnPayOrderList();
            }
        });
        listView.setOnItemClickListener(this);
    }

    /**
     * 获取待付款订单列表
     */
    private void getUnPayOrderList() {
        unSub();
        httpSubscriber = new HttpSubscriber(onUnPayOrderList);
        orderStatus = "UNPAY";
        bundle.putString("status", orderStatus);
        Map<Object, Object> map = new HashMap<>();
        userDto.setId(StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        orderDto.setPageNum(pageNum);
        orderDto.setPageSize(Contants.PAGE_SIZE);
        orderDto.setOrderStatus(orderStatus);
        map.put("userDto", userDto);
        map.put("orderDto", orderDto);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        OrderAPI.getInstance().getOrderList(httpSubscriber, body);
    }

    SubscriberOnNextListener<List<OrderBean.data>> onUnPayOrderList = new SubscriberOnNextListener<List<OrderBean.data>>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(List<OrderBean.data> datas) {
            hideLoadingDialog();
            data = datas;
            refreshDatas();
            getMaxPage();
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
     * 获取最大页数
     */
    private void getMaxPage() {
        unSub();
        httpSubscriber = new HttpSubscriber(onMaxPageListener);
        orderStatus = "UNPAY";
        Map<String, String> map = new HashMap<>();
        map.put("orderStatus", orderStatus);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        OrderAPI.getInstance().getMaxPage(httpSubscriber, body);
    }

    SubscriberOnNextListener<OrderPageBean> onMaxPageListener = new SubscriberOnNextListener<OrderPageBean>() {
        @Override
        public void onStart() {
            //showLoadingDialog();
        }

        @Override
        public void onNext(OrderPageBean orderPageBean) {
            maxPage = orderPageBean.getMaxPage();
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
     * 刷新数据
     */
    @Override
    public void refreshDatas() {
        super.refreshDatas();
        if (pageNum.equals(maxPage)) {
            refreshLayout.setDirection(RefreshLayoutDirection.TOP);
        } else {
            refreshLayout.setDirection(RefreshLayoutDirection.BOTH);
        }
        if (loadType == LoadType.AddAll) {
            adapter.addAll(data);
        } else if (loadType == LoadType.ReplaceALL) {
            adapter.replaceAll(data);
        }
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if (data.size() == 0) {
            llNoData.setVisibility(View.VISIBLE);
        } else {
            llNoData.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        data = new ArrayList<>();
        if (adapter == null) {
            adapter = new QuickAdapter<OrderBean.data>(getActivity(), R.layout.item_my_order) {
                @Override
                protected void convert(BaseAdapterHelper helper, final OrderBean.data item) {
                    ImageView imageView = helper.getView(R.id.image_card);
                    helper.setText(R.id.card_name, item.getGoodsName())
                            .setText(R.id.card_year, "使用年限" + String.valueOf(item.getYears()) + "年")
                            .setText(R.id.money_of_pay, "实付款:￥" + String.valueOf(item.getSum()) + "元");
                    helper.setVisible(R.id.payStatus, StringUtil.isEmpty(item.getOrderType()) ? View.GONE : View.VISIBLE);
                    switch (String.valueOf(item.getOrderType())) {
                        case "BUYCR":
                            helper.setText(R.id.payStatus, "购买");
                            break;
                        case "RENEW":
                            helper.setText(R.id.payStatus, "续卡");
                            break;
                        case "MAKUP":
                            helper.setText(R.id.payStatus, "补办");
                            break;
                    }
                    ImageLoaderUtil.display(getActivity(), imageView, API.url + item.getImage());
                    helper.setText(R.id.card_status, "待付款");
                    helper.setVisible(R.id.checkLogistic, false);
                    helper.setVisible(R.id.buy_again, true);
                    helper.setText(R.id.buy_again, "付款");
                    helper.setOnClickListener(R.id.buy_again, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bundle.putDouble("totalPrice", item.getSum());
                            bundle.putString("imageUrl", API.url + item.getImage());
                            bundle.putString("cardYear", String.valueOf(item.getYears()));
                            bundle.putString("goodsName", item.getGoodsName());
                            bundle.putString("orderCode", item.getOrderCode());
                            openActivityNotClose(ActivityChoosePayMethod.class, bundle);
                        }
                    });
                }
            };
        }
    }

    @Override
    protected void immersionInit() {

    }

    //解除订阅
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        bundle.putInt("id", adapter.getItem(position).getId());
        openActivityNotClose(ActivityOrderDetail.class, bundle);
    }
}
