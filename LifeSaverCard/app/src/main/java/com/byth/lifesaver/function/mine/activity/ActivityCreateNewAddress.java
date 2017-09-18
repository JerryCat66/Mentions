package com.byth.lifesaver.function.mine.activity;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.byth.lifesaver.R;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.ProvinceBean;
import com.byth.lifesaver.util.ProvinceJsonParseUtil;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/7/17 0017.
 * 创建新收货地址
 * auth:Lee
 */

public class ActivityCreateNewAddress extends FrameActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.edUserName)
    EditText edUserName;
    @InjectView(R.id.edUserTel)
    EditText edUserTel;
    @InjectView(R.id.edUserAddress)
    TextView edUserAddress;//地址选择
    @InjectView(R.id.edAddressDetail)
    EditText edAddressDetail;//详细地址
    @InjectView(R.id.isDefault)
    CheckBox isDefault;//是否默认
    @InjectView(R.id.saveAndUse)
    Button saveAndUse;
    /**
     * 省市区滚轮选择
     */
    private ArrayList<ProvinceBean> provinceList = new ArrayList<>();//省列表
    private ArrayList<ArrayList<String>> cityList = new ArrayList<>();//市列表
    private ArrayList<ArrayList<ArrayList<String>>> areaList = new ArrayList<>();//区列表
    private static ProvinceJsonParseUtil pc;

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_create_new_address);
        setToolbar(toolbar);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        pc = ProvinceJsonParseUtil.getInstance(this);
        this.provinceList = pc.getProvinceList();
        this.cityList = pc.getCityList();
        this.areaList = pc.getAreaList();
    }

    @Override
    protected void initListener() {
        saveAndUse.setOnClickListener(this);
        edUserAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edUserAddress:
                showCityPicker();
                break;
            case R.id.saveAndUse:
                break;
        }
    }

    /**
     * 弹出省市区选择器
     */
    private void showCityPicker() {
        OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String address = provinceList.get(options1).getPickerViewText() +
                        cityList.get(options1).get(options2) +
                        areaList.get(options1).get(options2).get(options3);
                edUserAddress.setText(address);
            }
        }).setTitleText("请选择城市")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .setOutSideCancelable(false)
                .build();
        optionsPickerView.setPicker(provinceList, cityList, areaList);
        optionsPickerView.show();
    }
}
