package com.byth.lifesaver.function.card.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.base.FrameActivity;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/6/30 0030.
 * 生命宝卡首页点进去卡详情
 */

public class ActivityLifeSaverCardDetail extends FrameActivity {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.cardNum)
    TextView cardNum;
    @InjectView(R.id.activeDate)
    TextView activeDate;
    @InjectView(R.id.deadDate)
    TextView deadDate;
    @InjectView(R.id.userName)
    TextView userName;
    @InjectView(R.id.sex)
    TextView sex;
    @InjectView(R.id.idNum)
    TextView idNum;
    @InjectView(R.id.bindPhone)
    TextView bindPhone;
    @InjectView(R.id.contactAddress)
    TextView contactAddress;
    @InjectView(R.id.useArea)
    TextView useArea;
    @InjectView(R.id.waySell)
    TextView waySell;
    @InjectView(R.id.emergencyContact)
    TextView emergencyContact;
    @InjectView(R.id.emergencyCall)
    TextView emergencyCall;
    @InjectView(R.id.relationShip)
    TextView tvRelationShip;
    @InjectView(R.id.bloodType)
    TextView tvBloodType;
    @InjectView(R.id.medicalHistory)
    TextView medicalHistory;
    @InjectView(R.id.drugAllergy)
    TextView tvDrugAllergy;
    private Bundle bundle;
    private String goodsName;//卡类别
    private String activeTime;//激活时间
    private String expiryDate;//失效时间
    private String distribName;//渠道商
    private String nickName;//持卡人
    private String phone;//绑定手机
    private String idCode;//身份证
    private int gender;//性别：1男，2女
    private String address;//联系地址
    private String linkman;//紧急联系人
    private String tel;//紧急联系人电话
    private String relationship;//与持卡人关系
    private String illItem;//既往病史
    private String drugAllergy;//药物过敏情况
    private String bloodType;//血型


    @Override
    protected void receiveDataFromPreActivity() {
        bundle = getIntent().getExtras();
        activeTime = bundle.getString("activeTime");
        address = bundle.getString("address");
        bloodType = bundle.getString("bloodType");
        distribName = bundle.getString("distribute");
        drugAllergy = bundle.getString("drugAllergy");
        expiryDate = bundle.getString("expiryDate");
        goodsName = bundle.getString("goodsName");
        idCode = bundle.getString("idCode");
        illItem = bundle.getString("illItem");
        linkman = bundle.getString("linkMan");
        relationship = bundle.getString("relationShip");
        phone = bundle.getString("phone");
        tel = bundle.getString("tel");
        gender = bundle.getInt("gender");
        nickName = bundle.getString("nickName");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_life_saver_card_detail);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        cardNum.setText("卡号:" + goodsName);
        activeDate.setText("激活日期:" + activeTime);
        deadDate.setText("失效日期:" + expiryDate);
        userName.setText("持卡人:" + nickName);
        if (gender == 1) {
            sex.setText("性别:男");
        } else if (gender == 2) {
            sex.setText("性别:女");
        }
        idNum.setText("身份证:" + idCode);
        bindPhone.setText("绑定手机:" + phone);
        contactAddress.setText("联系地址:" + address);
        useArea.setText("使用区域:");
        waySell.setText("销售渠道:" + distribName);
        emergencyContact.setText("紧急联系人:" + linkman);
        emergencyCall.setText("紧急联系人电话:" + tel);
        tvRelationShip.setText("与持卡人关系:" + relationship);
        tvDrugAllergy.setText("药物过敏情况:" + drugAllergy);
        medicalHistory.setText("既往病史:" + illItem);
        tvBloodType.setText("血型:" + bloodType);
    }

    @Override
    protected void initListener() {

    }

}
