package com.byth.lifesaver.function.card.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.byth.lifesaver.MainActivity;
import com.byth.lifesaver.R;
import com.byth.lifesaver.base.FrameActivity;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/7/4 0004.
 * 激活成功页面
 */

public class ActivityActiveSuccess extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.btnBackToHome)
    Button btnBackToHome;
    @InjectView(R.id.activation)
    TextView activation;
    @InjectView(R.id.cardNum)
    TextView cardNum;//卡号
    @InjectView(R.id.deadDate)
    TextView deadDate;//过期时间
    @InjectView(R.id.userName)
    TextView tvUserName;//用户名
    @InjectView(R.id.bindPhone)
    TextView bindPhone;//绑定手机
    @InjectView(R.id.idNum)
    TextView idNum;//身份证号码
    @InjectView(R.id.bloodType)
    TextView tvBloodType;//血型
    @InjectView(R.id.hot_line)
    TextView hotLine;//公司电话
    private String cardCode;//卡号
    private String expiryDate;//失效时间
    private String phone;//绑定手机
    private String userName;//用户姓名
    private String idCode;//身份证号码
    private String bloodType;//血型

    @Override
    protected void receiveDataFromPreActivity() {
        Bundle bundle = getIntent().getExtras();
        cardCode = bundle.getString("cardCode");
        expiryDate = bundle.getString("expiryDate");
        phone = bundle.getString("phone");
        userName = bundle.getString("userName");
        idCode = bundle.getString("idCode");
        bloodType = bundle.getString("bloodType");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_active_success);
        setToolbar(toolbar);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.color_main)
                .statusBarDarkFont(true)
                .init();
        cardNum.setText(cardCode);
        deadDate.setText(expiryDate);
        bindPhone.setText(phone);
        tvUserName.setText(userName);
        tvBloodType.setText(bloodType);
        idNum.setText(idCode);
    }

    @Override
    protected void initListener() {
        btnBackToHome.setOnClickListener(this);
        hotLine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackToHome:
                openActivity(MainActivity.class, null);
                break;
            case R.id.hot_line:
                telPhone(getResources().getString(R.string.hot_line_of_company));
                break;
        }
    }
}
