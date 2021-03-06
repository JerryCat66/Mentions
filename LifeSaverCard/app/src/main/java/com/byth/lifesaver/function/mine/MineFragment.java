package com.byth.lifesaver.function.mine;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.byth.lifesaver.MainActivity;
import com.byth.lifesaver.R;
import com.byth.lifesaver.api.API;
import com.byth.lifesaver.api.MineAPI;
import com.byth.lifesaver.base.BaseFragment;
import com.byth.lifesaver.bean.CustomerInfoBean;
import com.byth.lifesaver.function.mine.activity.ActivityAccountManager;
import com.byth.lifesaver.function.mine.activity.ActivityGoodsReceiptAddress;
import com.byth.lifesaver.function.mine.activity.ActivityMyOrder;
import com.byth.lifesaver.function.mine.activity.ActivitySetting;
import com.byth.lifesaver.function.mine.activity.ActivityUserInfo;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.byth.lifesaver.util.LifeSaverStatusBarUtil;
import com.fenguo.library.util.JsonUtil;
import com.fenguo.library.util.StringUtil;
import com.fenguo.library.view.RoundImageViewWithBorder;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/5/26 0026.
 * 我的
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    @InjectView(R.id.avatar)
    RoundImageViewWithBorder ivAvatar;
    @InjectView(R.id.nickname)
    TextView tvNickName;
    @InjectView(R.id.appBar)
    AppBarLayout appBarLayout;
    /* @InjectView(R.id.mine_icon_shopping_cart)
     TextView tvShoppingCart;*/
    @InjectView(R.id.mine_icon_manager)
    TextView tvManager;
    @InjectView(R.id.mine_icon_settlement)
    TextView tvSettlement;
    @InjectView(R.id.mine_icon_order)
    TextView tvOrder;
    @InjectView(R.id.mine_icon_setting)
    TextView tvSetting;
    @InjectView(R.id.collapsing_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @InjectView(R.id.toolbar)
    Toolbar mToolBar;
    private volatile static MineFragment fragment;
    private HttpSubscriber httpSubscriber;
    private Bundle bundle = new Bundle();

    public MineFragment() {

    }


    public static MineFragment getInstance() {
        if (fragment == null) {
            synchronized (MineFragment.class) {
                if (fragment == null) {
                    fragment = new MineFragment();
                }
            }
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = setContentView(inflater, R.layout.fragment_mine);
        ButterKnife.inject(view);
        return view;
    }

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initComponent() {
        LifeSaverStatusBarUtil.setStatusBarColorForCollapsingToolbar(getActivity(), appBarLayout, collapsingToolbarLayout, mToolBar, ContextCompat.getColor(getActivity(), R.color.color_main));
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }

    @Override
    protected void initListener() {
//        tvShoppingCart.setOnClickListener(this);
        tvManager.setOnClickListener(this);
        tvSettlement.setOnClickListener(this);
        tvOrder.setOnClickListener(this);
        tvSetting.setOnClickListener(this);
        appBarLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void immersionInit() {
        ImmersionBar.with(getActivity())
                .statusBarDarkFont(true)
                .fitsSystemWindows(false)
                .init();
    }

    //获取用户信息
    private void getUserInfo() {
        unSub();
        httpSubscriber = new HttpSubscriber(onGetUserInfoNext);
        Map<String, Integer> map = new HashMap<>();
        map.put("id", StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        MineAPI.getInstance().getUserInfo(httpSubscriber, body);
    }

    SubscriberOnNextListener<CustomerInfoBean> onGetUserInfoNext = new SubscriberOnNextListener<CustomerInfoBean>() {
        @Override
        public void onStart() {
            ((MainActivity) getActivity()).showLoadingDialog();
        }

        @Override
        public void onNext(CustomerInfoBean customerInfoBean) {
            ((MainActivity) getActivity()).hideLoadingDialog();
            String avatarImage = API.url + customerInfoBean.getHeadImg();
            if (customerInfoBean != null) {
                tvNickName.setText(customerInfoBean.getNickName());
                Glide.with(getActivity())
                        .load(avatarImage)
                        .asBitmap()
                        .placeholder(R.drawable.avatar_default)
                        .error(R.drawable.avatar_default)
                        .into(new BitmapImageViewTarget(ivAvatar) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                ivAvatar.setImageBitmap(resource, Color.WHITE, 20, true);
                            }
                        });
            }
        }

        @Override
        public void onApiError(ApiException e) {
            showToast(e.getMessage());
            ((MainActivity) getActivity()).hideLoadingDialog();
        }

        @Override
        public void onNetworkError(Throwable e) {
            showToast(e.getMessage());
            ((MainActivity) getActivity()).hideLoadingDialog();
        }

        @Override
        public void onOtherError(Throwable e) {
            showToast(e.getMessage());
            ((MainActivity) getActivity()).hideLoadingDialog();
        }

        @Override
        public void onCompleted() {
            ((MainActivity) getActivity()).hideLoadingDialog();
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        ImmersionBar.with(getActivity()).destroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.mine_icon_shopping_cart:
//                openActivityNotClose(ActivityShoppingCart.class, null);
//                showToast("购物车暂未开放");
//                break;
            case R.id.mine_icon_manager:
                openActivityNotClose(ActivityAccountManager.class, null);//账户管理
                break;
            case R.id.mine_icon_settlement:
                bundle.putString("tag", "B");
                openActivityNotClose(ActivityGoodsReceiptAddress.class, bundle);//地址
                break;
            case R.id.mine_icon_order:
                openActivityNotClose(ActivityMyOrder.class, null);//订单
                break;
            case R.id.mine_icon_setting:
                openActivityNotClose(ActivitySetting.class, null);//设置
                break;
            case R.id.appBar:
                openActivityNotClose(ActivityUserInfo.class, null);//个人中心
                break;
        }
    }

    //解除订阅
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
