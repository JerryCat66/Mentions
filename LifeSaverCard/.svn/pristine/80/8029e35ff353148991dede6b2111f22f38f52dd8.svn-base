package com.byth.lifesaver.function.card.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.adapter.DrugCheckAdapter;
import com.byth.lifesaver.adapter.MedicalCheckAdapter;
import com.byth.lifesaver.adapter.RadioButtonAdapter;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.util.IdKeyboardUtil;
import com.byth.lifesaver.widget.DialogWithList;
import com.byth.lifesaver.widget.MyGridView;
import com.fenguo.library.util.StringUtil;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/6/12 0012.
 * 使用人信息填写
 * TODO
 */

public class ActivityCardUserInfo extends FrameActivity implements MedicalCheckAdapter.RemarkClickListener, RadioButtonAdapter.IRadioNumClickListener
        , View.OnClickListener, DrugCheckAdapter.DrugAllergyClickListener {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.edName)
    EditText edName;
    @InjectView(R.id.edSex)
    TextView edSex;//性别
    @InjectView(R.id.edIdNum)
    EditText edIdNum;//身份证号码
    @InjectView(R.id.edAddress)
    EditText edAddress;//住址
    @InjectView(R.id.edPhoneNum)
    EditText edPhoneNum;//电话号码
    @InjectView(R.id.edEmergencyName)
    EditText edEmergencyName;//紧急联系人
    @InjectView(R.id.edEmergencyPhone)
    EditText edEmergencyPhone;//紧急联系人电话号码
    @InjectView(R.id.edRelationShip)
    TextView edRelationShip;//关系
    @InjectView(R.id.bloodType)
    MyGridView mgvBloodType;//血型
    @InjectView(R.id.medicalHistory)
    MyGridView mgvMedicalHistory;//既往病史
    @InjectView(R.id.others)
    EditText edOthers;//其他病史
    @InjectView(R.id.drugAllergy)
    MyGridView mgvDrugAllergy;//药物过敏
    @InjectView(R.id.ll_drug_others)
    LinearLayout llDrugOthers;
    @InjectView(R.id.others_allergy)
    EditText edOthersAllergy;//其他药物过敏史
    @InjectView(R.id.ll_blood_medical)
    LinearLayout llBloodMedical;
    @InjectView(R.id.cb_null_blood_medical)
    CheckBox cbBloodMedical;
    @InjectView(R.id.cb_null_drug_allergy)
    CheckBox cbDrugAllergy;
    @InjectView(R.id.btn_next_step)
    Button btnNextStep;
    @InjectView(R.id.hot_line)
    TextView tvHotLine;
    private String bloodType;
    private List<String> medicalList;
    private List<String> allergyList;

    private RadioButtonAdapter radioButtonAdapter;
    private MedicalCheckAdapter medicalBoxAdapterMedical;
    private DrugCheckAdapter medicalCheckAdapterAllergy;
    private String cardCode;
    private int gender;//性别:1男,2女
    private Bundle bundle = new Bundle();
    private IdKeyboardUtil idKeyboardUtil;//自定义键盘

    @Override
    protected void receiveDataFromPreActivity() {
        Bundle bundle = getIntent().getExtras();
        cardCode = bundle.getString("cardCode");
    }

    @Override
    protected void initData() {
        medicalList = new ArrayList<>();
        allergyList = new ArrayList<>();
    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_user_infomation);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        medicalBoxAdapterMedical = new MedicalCheckAdapter(this);
        medicalCheckAdapterAllergy = new DrugCheckAdapter(this);
        radioButtonAdapter = new RadioButtonAdapter(this, 1);
        mgvBloodType.setAdapter(radioButtonAdapter);
        mgvMedicalHistory.setAdapter(medicalBoxAdapterMedical);
        mgvDrugAllergy.setAdapter(medicalCheckAdapterAllergy);
        idKeyboardUtil = new IdKeyboardUtil(this, getApplicationContext(), edIdNum);
        edIdNum.setOnFocusChangeListener(onFocusChangeListener);
    }

    @Override
    protected void initListener() {
        radioButtonAdapter.registerOnClick(this);
        medicalCheckAdapterAllergy.registerOnClickRemark(this);
        medicalBoxAdapterMedical.registerOnClickRemark(this);
        btnNextStep.setOnClickListener(this);
        edSex.setOnClickListener(this);
        edRelationShip.setOnClickListener(this);
        tvHotLine.setOnClickListener(this);
        cbDrugAllergy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mgvDrugAllergy.setVisibility(View.GONE);
                    llDrugOthers.setVisibility(View.GONE);
                    allergyList.add("无");
                } else {
                    mgvDrugAllergy.setVisibility(View.VISIBLE);
                    llDrugOthers.setVisibility(View.VISIBLE);
                }
            }
        });
        cbBloodMedical.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llBloodMedical.setVisibility(View.GONE);
                    medicalList.add("无");
                } else {
                    llBloodMedical.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //点击显示键盘
    private View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                idKeyboardUtil.showKeyboard();
                idKeyboardUtil.hideSoftInputMethod();
            } else {
                idKeyboardUtil.hideKeyboard();
            }
        }
    };

    //病史adapter的Click事件
    @Override
    public void onClick(MedicalCheckAdapter context, String remarkStr, List<String> datas) {
        this.medicalList = datas;

    }

    //药物过敏史adapter的Click事件
    @Override
    public void onClick(DrugCheckAdapter context, String remarkStr, List<String> datas) {
        this.allergyList = datas;
    }

    @Override
    public void onClick(RadioButtonAdapter context, String numYear) {
        this.bloodType = numYear;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edSex:
                lifeSaverUtil.showDialogList(this, R.array.choose_sex, "请选择性别", new DialogWithList.OnSelectedListener() {
                    @Override
                    public void setOnSelectedListener(int position, String content) {
                        edSex.setText(content);
                    }
                });
                break;
            case R.id.edRelationShip:
                lifeSaverUtil.showDialogList(this, R.array.choose_relationship, "请选择关系", new DialogWithList.OnSelectedListener() {
                    @Override
                    public void setOnSelectedListener(int position, String content) {
                        edRelationShip.setText(content);
                    }
                });
                break;
            case R.id.btn_next_step:
                checkIsNull();
                break;
            case R.id.hot_line:
                telPhone(getResources().getString(R.string.hot_line_of_company));
                break;
        }
    }

    /**
     * 检查输入的各个必填项是否为空
     */
    private void checkIsNull() {
        String userName = edName.getText().toString().trim();
        String sex = edSex.getText().toString().trim();
        if (sex.equals("男")) {
            gender = 1;
        } else {
            gender = 2;
        }
        String idNum = edIdNum.getText().toString().trim();
        String userAddress = edAddress.getText().toString().trim();
        String phoneNum = edPhoneNum.getText().toString().trim();
        String emergencyName = edEmergencyName.getText().toString().trim();
        String emergencyPhone = edEmergencyPhone.getText().toString().trim();
        String relationShip = edRelationShip.getText().toString().trim();
        String otherMedical = edOthers.getText().toString().trim();
        String otherAllergy = edOthersAllergy.getText().toString().trim();
        if (StringUtil.isEmpty(userName)) {
            showToast("请输入姓名");
        } else if (StringUtil.isEmpty(sex)) {
            showToast("请选择性别");
        } else if (StringUtil.isEmpty(idNum) || !StringUtil.isIdNumber(idNum)) {
            showToast("请输入正确的身份证号码");
        } else if (StringUtil.isEmpty(phoneNum) || !StringUtil.isMobile(phoneNum)) {
            showToast("请输入正确的电话号码");
        } else if (StringUtil.isEmpty(emergencyName)) {
            showToast("请输入紧急联系人姓名");
        } else if (StringUtil.isEmpty(emergencyPhone) || !StringUtil.isMobile(emergencyPhone)) {
            showToast("请输入正确的紧急联系人号码");
        } else if (StringUtil.isEmpty(relationShip)) {
            showToast("请输入关系");
        } else if (StringUtil.isEmpty(bloodType)) {
            showToast("请选择血型");
        } else if (medicalList.size() == 0 && medicalList.isEmpty() && !cbBloodMedical.isChecked()) {
            showToast("请选择是否有病史");
        } else if (allergyList.size() == 0 && allergyList.isEmpty() && !cbDrugAllergy.isChecked()) {
            showToast("请选择是否有药物过敏史");
        } else {
            bundle.putString("userName", userName);
            bundle.putString("idCode", idNum);
            bundle.putInt("gender", gender);
            bundle.putString("user_address", userAddress);
            bundle.putString("linkMan", emergencyName);
            bundle.putString("relationship", relationShip);
            bundle.putString("tel", emergencyPhone);
            bundle.putString("bloodType", bloodType);
            bundle.putString("illItem", StringUtil.toString(medicalList).replace("[", "").replace("]", ""));
            bundle.putString("drugAllergy", StringUtil.toString(allergyList).replace("[", "").replace("]", ""));
            bundle.putString("illRemarks", otherMedical);
            bundle.putString("drugRemarks", otherAllergy);
            bundle.putString("phoneNum", phoneNum);
            bundle.putString("cardCode", cardCode);
            toActiveCard();
        }
//        toActiveCard();
    }

    /**
     * 确认填写信息无误的话进入下一步激活卡片
     */
    private void toActiveCard() {
        openActivityNotClose(ActivityActiveForUsing.class, bundle);
    }


}
