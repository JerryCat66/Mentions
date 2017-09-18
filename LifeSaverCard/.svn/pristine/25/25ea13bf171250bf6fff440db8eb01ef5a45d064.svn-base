package com.byth.lifesaver.function.mine.activity;

import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.base.FrameActivity;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/6/13 0013.
 * 物流详情
 */

public class ActivityLogisticDetail extends FrameActivity {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.product_image)
    ImageView ivProduct;
    @InjectView(R.id.logistic_state_label)
    TextView tvState;
    @InjectView(R.id.logistic_company)
    TextView tvCompany;
    @InjectView(R.id.logistic_number)
    TextView tvNumber;
    @InjectView(R.id.logistic_tel)
    TextView tvTel;

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_logistic_detail);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
    }

    @Override
    protected void initListener() {

    }
}
