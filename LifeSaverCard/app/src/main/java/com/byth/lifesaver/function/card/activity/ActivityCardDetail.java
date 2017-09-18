package com.byth.lifesaver.function.card.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.function.card.fragment.FragmentCardDetailNextPager;
import com.byth.lifesaver.function.card.fragment.FragmentCardDetailPager;
import com.byth.lifesaver.widget.WgtDragLayout;
import com.gyf.barlibrary.ImmersionBar;


import butterknife.InjectView;

/**
 * Created by Administrator on 2017/6/1 0001.
 * 产品详情(购买,加入购物车)
 */

public class ActivityCardDetail extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.drag_layout)
    public WgtDragLayout wgtDragLayout;
    /*@InjectView(R.id.add_to_cart)
    TextView tvAddToCart;*/
    @InjectView(R.id.buy_now)
    TextView tvBuyNow;
    @InjectView(R.id.btn_Back)
    ImageButton ibtnBack;

    private FragmentCardDetailPager fragmentCardDetailPager;
    private FragmentCardDetailNextPager fragmentCardDetailNextPager;

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_card_detail);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.transparent)
                .init();
        fragmentCardDetailPager = FragmentCardDetailPager.newInstance(ActivityCardDetail.this, 0);
        fragmentCardDetailNextPager = FragmentCardDetailNextPager.newInstance(ActivityCardDetail.this, 0);
        getSupportFragmentManager().beginTransaction().add(R.id.detail_first_page, fragmentCardDetailPager)
                .add(R.id.detail_second_pager, fragmentCardDetailNextPager).commit();
        WgtDragLayout.ShowNextPageNotifier nextPageNotifier = new WgtDragLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {
                fragmentCardDetailNextPager.initView();
            }
        };
        wgtDragLayout.setNextPageListener(nextPageNotifier);
    }


    @Override
    protected void initListener() {
//        tvAddToCart.setOnClickListener(this);
        tvBuyNow.setOnClickListener(this);
        ibtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.add_to_cart:
                break;*/
            case R.id.buy_now:
                openActivityNotClose(ActivityOrderConfirm.class, null);
                break;
            case R.id.btn_Back:
                onBackPress();
                break;
        }
    }


    /**
     * 返回上一层
     */
    private void onBackPress() {
        onBackPressed();
    }
}