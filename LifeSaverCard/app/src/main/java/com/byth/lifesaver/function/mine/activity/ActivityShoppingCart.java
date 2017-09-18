package com.byth.lifesaver.function.mine.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.adapter.MyExpandableListAdapter;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.ShoppingCartBean;
import com.byth.lifesaver.function.card.activity.ActivityCardDetail;
import com.byth.lifesaver.util.ShoppingCartUtil;
import com.byth.lifesaver.widget.refreshlayout.RefreshLayout;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/6/7 0007.
 * 购物车
 */

public class ActivityShoppingCart extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.refresh_layout)
    RefreshLayout mRefresh;
    @InjectView(R.id.list)
    ExpandableListView listView;
    @InjectView(R.id.shopping_cart_no_data)
    LinearLayout llNoData;//列表为空
    @InjectView(R.id.ivSelectAll)
    ImageView ivChooseAll;//全选
    @InjectView(R.id.txtViewPrice)
    TextView tvTotal;//合计
    @InjectView(R.id.txtViewSettlement)
    TextView tvSettlement;//结算
    @InjectView(R.id.go_shopping)
    TextView tvGoShopping;
    private List<ShoppingCartBean> goodsList = new ArrayList<>();
    private MyExpandableListAdapter adapter;

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_shopping_cart);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        adapter = new MyExpandableListAdapter(this);
        listView.setAdapter(adapter);
        adapter.setOnShoppingCartItemChangeListener(new MyExpandableListAdapter.OnShoppingCartItemChangeListener() {
            @Override
            public void onDataChange(String selectCount, String selectMoney) {
                int goodCount = ShoppingCartUtil.GoodsCount();//统计商品数量
                if (goodCount == 0) {
                    llNoData.setVisibility(View.VISIBLE);
                } else {
                    llNoData.setVisibility(View.GONE);
                }
                String countMoney = String.format(getResources().getString(R.string.shopping_cart_total_money), selectMoney);
                String countGoods = String.format(getResources().getString(R.string.shopping_cart_settlement), selectCount);
                tvTotal.setText(countMoney);
                tvSettlement.setText(countGoods);
            }

            @Override
            public void onSelectItem(boolean isSelectedAll) {
                ShoppingCartUtil.checkItem(isSelectedAll, ivChooseAll);
            }
        });
        //通过监听器关联Activity和Adapter的关系，解耦；
        View.OnClickListener listener = adapter.getAdapterListener();
        if (listener != null) {
            //即使换了一个新的Adapter，也要将“全选事件”传递给adapter处理；
            ivChooseAll.setOnClickListener(adapter.getAdapterListener());
            //结算时，一般是需要将数据传给订单界面的
            tvSettlement.setOnClickListener(adapter.getAdapterListener());
        }
        getShoppingCartList();//获取购物车数据
    }

    @Override
    protected void initListener() {
        tvGoShopping.setOnClickListener(this);
        mRefresh.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onPullDownToRefresh() {

            }

            @Override
            public void onPullUpToRefresh() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_shopping:
                openActivityNotClose(ActivityCardDetail.class, null);
                break;
        }
    }

    /**
     * 获取购物车数据
     */
    private void getShoppingCartList() {
        updateListView();
    }

    private void updateListView() {
        adapter.setList(goodsList);
        adapter.notifyDataSetChanged();
        expandAllGroup();
    }

    /**
     * 展开所有数据
     */
    private void expandAllGroup() {
        for (int i = 0; i < goodsList.size(); i++) {
            listView.expandGroup(i);
        }
    }
}
