package com.byth.lifesaver.function.mine.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.byth.lifesaver.R;
import com.byth.lifesaver.api.MineAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.ProvinceBean;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpResult;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.byth.lifesaver.util.PermissionActivity;
import com.byth.lifesaver.util.PermissionChecker;
import com.byth.lifesaver.util.ProvinceJsonParseUtil;
import com.fenguo.library.util.JsonUtil;
import com.fenguo.library.util.StringUtil;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/9/8 0008.
 * 修改地址
 */

public class ActivityModifyAddress extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.bottom_btn_save)
    TextView tvSave;//修改地址
    @InjectView(R.id.edit_name)
    EditText edName;//姓名
    @InjectView(R.id.edit_phone)
    EditText edPhone;//电话
    @InjectView(R.id.radioGroup)
    RadioGroup rgChooseSex;//性别选择
    @InjectView(R.id.ed_address_detail)
    EditText edAddressDetail;//详细地址
    @InjectView(R.id.default_checkbox)
    CheckBox cbDefault;//是否默认
    @InjectView(R.id.add_contact)
    ImageView ivAddContact;//添加联系人
    @InjectView(R.id.Lyt_editAddress)
    RelativeLayout rlEditAddress;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.man)
    RadioButton rbMan;
    @InjectView(R.id.women)
    RadioButton rbWomen;
    private int addrId;//地址id
    private int sex;//性别
    private String defaultFlag = "N";//是否默认
    private String consignee;//收货人
    private String consigneeMobile;//收货人手机
    private String region_belong;//所属区域
    private String addr;//详细地址
    private HttpSubscriber httpSubscriber;
    private ArrayList<ProvinceBean> provinceList = new ArrayList<>();//省列表
    private ArrayList<ArrayList<String>> cityList = new ArrayList<>();//市列表
    private ArrayList<ArrayList<ArrayList<String>>> areaList = new ArrayList<>();//区列表
    private static ProvinceJsonParseUtil pc;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
    };
    private static final int REQUEST_CODE = 0; // 请求码
    private PermissionChecker permissionChecker;

    @Override
    protected void receiveDataFromPreActivity() {
        Bundle bundle = getIntent().getExtras();
        addrId = bundle.getInt("addrId");
        consignee = bundle.getString("consignee");
        addr = bundle.getString("addr");
        consigneeMobile = bundle.getString("consigneeMobile");
        sex = bundle.getInt("sex");
        defaultFlag = bundle.getString("defaultFlag");
        region_belong = bundle.getString("region_belong");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_settlement_address);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        pc = ProvinceJsonParseUtil.getInstance(this);
        this.provinceList = pc.getProvinceList();
        this.cityList = pc.getCityList();
        this.areaList = pc.getAreaList();
        permissionChecker = new PermissionChecker(this);
        tvSave.setText("修改地址");
        edName.setText(consignee);
        if (sex == 1) {
            rbMan.setChecked(true);
        } else {
            rbWomen.setChecked(true);
        }
        edPhone.setText(consigneeMobile);
        tvAddress.setText(region_belong);
        if (defaultFlag.equals("Y")) {
            cbDefault.setChecked(true);
        } else {
            cbDefault.setChecked(false);
        }
        edAddressDetail.setText(addr);
    }

    @Override
    protected void initListener() {
        rgChooseSex.setOnCheckedChangeListener(onCheckedChangeListener);
        ivAddContact.setOnClickListener(this);
        rlEditAddress.setOnClickListener(this);//地址选择
        tvSave.setOnClickListener(this);
        cbDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cbDefault.isChecked()) {
                    defaultFlag = "Y";
                    Log.i(TAG, "onCheckedChanged: " + defaultFlag);
                } else {
                    defaultFlag = "N";
                    Log.i(TAG, "onCheckedChanged: " + defaultFlag);
                }

            }
        });
    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.man:
                    sex = 1;
                    break;
                case R.id.women:
                    sex = 2;
                    break;
            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_contact:
                if (permissionChecker.isLackPermissions(PERMISSIONS)) {
                    startPermissionsActivity();
                } else {
                    startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE);
                }
                break;
            case R.id.Lyt_editAddress:
                showCityPicker();
                break;
            case R.id.bottom_btn_save:
                if (StringUtil.isEmpty(edName.getText().toString().trim())) {
                    showToast("请填写姓名");
                    return;
                } else if (!rbMan.isChecked() && !rbWomen.isChecked()) {
                    showToast("请选择性别");
                    return;
                } else if ((StringUtil.isEmpty(edPhone.getText().toString().trim()))) {
                    showToast("请填写手机号码");
                    return;
                } else if (StringUtil.isEmpty(region_belong)) {
                    showToast("请选择所在区域");
                    return;
                } else if (StringUtil.isEmpty(edAddressDetail.getText().toString().trim())) {
                    showToast("请填写详细地址");
                    return;
                } else {
                    modifyAddress();
                }
                break;
        }
    }

    private void startPermissionsActivity() {
        PermissionActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    /**
     * 重写onActivityResult
     * 读取通讯录
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == PermissionActivity.PERMISSIONS_DENIED) {
            finish();
        } else {
            try {
                ContentResolver contentResolver = getContentResolver();
                Uri contactData = data.getData();
                Cursor cursor = managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                        null,
                        null);
                while (phone.moveToNext()) {
                    String phoneNum = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("\\s*", "");
                    edPhone.setText(phoneNum);
                    consigneeMobile = phoneNum;
                }
                phone.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        if (requestCode == Activity.RESULT_FIRST_USER) {
//        }
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
                tvAddress.setText(address);
                region_belong = address;
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

    //修改地址
    private void modifyAddress() {
        unSub();
        httpSubscriber = new HttpSubscriber(onModifyAddressNextListener);
        Map<Object, Object> map = new HashMap<>();
        map.put("addrId", addrId);
        map.put("userId", StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        map.put("consignee", edName.getText().toString().trim());
        map.put("sex", sex);
        map.put("consigneeMobile", edPhone.getText().toString().trim());
        map.put("region_belong", tvAddress.getText().toString().trim());
        map.put("addr", edAddressDetail.getText().toString().trim());
        map.put("defaultFlag", defaultFlag);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        MineAPI.getInstance().modifyAddress(httpSubscriber, body);
    }

    SubscriberOnNextListener<HttpResult> onModifyAddressNextListener = new SubscriberOnNextListener<HttpResult>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(HttpResult httpResult) {
            hideLoadingDialog();
            showToast("修改成功");
            finish();
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

    //解除订阅
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
    }
}
