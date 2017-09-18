package com.byth.lifesaver.function.mine.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.base.FrameActivity;
import com.fenguo.library.util.FenguoUtil;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/7/18 0018.
 * 关于我们
 */

public class ActivityAboutUs extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.version)
    TextView version;//版本号
    @InjectView(R.id.officialAccount)
    TextView officialAccount;//公众号
    @InjectView(R.id.tel)
    TextView tel;//电话
    @InjectView(R.id.email)
    TextView email;//邮箱
    @InjectView(R.id.web)
    TextView web;//官网
    @InjectView(R.id.hot_line)
    TextView hotLine;

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_about_us);
        setToolbar(toolbar);
        ImmersionBar.with(this)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
    }

    @Override
    protected void initListener() {
        tel.setOnClickListener(this);
        hotLine.setOnClickListener(this);
        web.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tel:
                telPhone(getResources().getString(R.string.hot_line_of_company));
                break;
            case R.id.hot_line:
                telPhone(getResources().getString(R.string.hot_line_of_company));
                break;
            case R.id.web:
                FenguoUtil.openWebViewActivity(this, "白云控股", "http://www.baiyunholdings.com/", "");
                break;
        }
    }
}
