package com.byth.lifesaver.function.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.api.OrderAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.AddressListBean;
import com.byth.lifesaver.function.card.activity.ActivityOrderConfirm;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.Contants;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.byth.lifesaver.util.LoadType;
import com.byth.lifesaver.widget.refreshlayout.RefreshLayout;
import com.byth.lifesaver.widget.refreshlayout.RefreshLayoutDirection;
import com.fenguo.library.adapter.BaseAdapterHelper;
import com.fenguo.library.adapter.QuickAdapter;
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
 * Created by Administrator on 2017/7/17 0017.
 * 收货地址管理
 */

public class ActivityGoodsReceiptAddress extends FrameActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.addressList)
    ListView addressList;
    //    RecyclerView addressList;
    @InjectView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    @InjectView(R.id.btnCreateNewAddress)
    AppCompatButton btnCreateNewAddress;
    private List<AddressListBean.AddressList> data;
    private QuickAdapter<AddressListBean.AddressList> adapter;
    private HttpSubscriber httpSubscriber;
    private Integer pageNum;
    private Integer maxPage;
    private AddressListBean.AddrDto addrDto;
    private AddressListBean.UserDto userDto;
    private String intentTag;//判断是从哪个地方进入地址列表,A为确认订单界面,B为其他

    @Override
    protected void receiveDataFromPreActivity() {
        Bundle bundle = getIntent().getExtras();
        intentTag = bundle.getString("tag");
    }

    @Override
    protected void initData() {
        addrDto = new AddressListBean.AddrDto();
        userDto = new AddressListBean.UserDto();
        pageNum = 1;
        data = new ArrayList<>();
        if (adapter == null) {
            adapter = new QuickAdapter<AddressListBean.AddressList>(this, R.layout.item_receipt_address_manager) {
                @Override
                protected void convert(BaseAdapterHelper helper, final AddressListBean.AddressList item) {
                    helper.setText(R.id.userName, item.getConsignee())
                            .setText(R.id.userTel, item.getConsigneeMobile())
                            .setText(R.id.userAddress, item.getAddr());
                    if (!StringUtil.isEmpty(item.getDefaultFlag())) {
                        helper.setVisible(R.id.isDefault, item.getDefaultFlag().equals("Y") ? View.VISIBLE : View.GONE);
                    }

                    helper.setOnClickListener(R.id.igModifyAddress, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            modifyAddress(item.getAddrId(), item.getConsignee(), item.getAddr(), item.getSex()
                                    , item.getConsigneeMobile(), item.getDefaultFlag(), item.getRegion_belong());
                        }
                    });
                }
            };
        }
    }

    //跳到修改地址界面
    private void modifyAddress(int addrId, String consignee, String addr, int sex, String consigneeMobile, String defaultFlag, String region_belong) {
        Bundle bundle = new Bundle();
        bundle.putInt("addrId", addrId);
        bundle.putString("consignee", consignee);
        bundle.putString("addr", addr);
        bundle.putString("consigneeMobile", consigneeMobile);
        bundle.putInt("sex", sex);
        bundle.putString("defaultFlag", defaultFlag);
        bundle.putString("region_belong", region_belong);
        openActivityNotClose(ActivityModifyAddress.class, bundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressList();
    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_receipt_address_manager);
        setToolbar(toolbar);
        setRefreshLayout(refreshLayout);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        addressList.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        btnCreateNewAddress.setOnClickListener(this);
        addressList.setOnItemClickListener(this);
        refreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onPullDownToRefresh() {
                pageNum = 1;
                loadType = LoadType.ReplaceALL;
                refreshLayout.setDirection(RefreshLayoutDirection.BOTH);
                getAddressList();
            }

            @Override
            public void onPullUpToRefresh() {
                pageNum += 1;
                getAddressList();
            }
        });
    }

    //获取地址列表
    private void getAddressList() {
        unSub();
        httpSubscriber = new HttpSubscriber(onAddressListNextListener);
        Map<Object, Object> map = new HashMap<>();
        addrDto.setPageNum(String.valueOf(pageNum));
        addrDto.setPageSize(String.valueOf(Contants.PAGE_SIZE));
        userDto.setId(StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        map.put("addrDto", addrDto);
        map.put("userDto", userDto);
        /*map.put("pageNum", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(Contants.PAGE_SIZE));*/
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        OrderAPI.getInstance().getAddressList(httpSubscriber, body);
    }

    SubscriberOnNextListener<AddressListBean> onAddressListNextListener = new SubscriberOnNextListener<AddressListBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(AddressListBean addressListBean) {
            hideLoadingDialog();
            data = addressListBean.getAddrList();
            maxPage = addressListBean.getMaxPage();
            refreshDatas();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateNewAddress:
                openActivityNotClose(ActivitySettlementAddress.class, null);
                break;
        }
    }

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
   /*     if (data.size() == 0) {
            llNoData.setVisibility(View.VISIBLE);
        } else {
            llNoData.setVisibility(View.GONE);
        }*/
    }

    //解除订阅
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String consignee = adapter.getItem(position).getConsignee();
        String consigneeMobile = adapter.getItem(position).getConsigneeMobile();
        String address = adapter.getItem(position).getRegion_belong() + adapter.getItem(position).getAddr();
        String defaultFlag = adapter.getItem(position).getDefaultFlag();
   /*     Bundle bundle = new Bundle();
        bundle.putString("consignee", consignee);
        bundle.putString("consigneeMobile", consigneeMobile);
        bundle.putString("address", address);
        bundle.putString("defaultFlag", defaultFlag);*/
        Intent intent = new Intent();
        intent.putExtra("consignee", consignee);
        intent.putExtra("consigneeMobile", consigneeMobile);
        intent.putExtra("address", address);
        intent.putExtra("defaultFlag", defaultFlag);
        if (intentTag.equals("A")) {
            //openActivity(ActivityOrderConfirm.class, bundle);
            setResult(10, intent);
            finish();
        } else {
            showToast("please wait a minute");
        }
    }
}
