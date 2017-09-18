package com.byth.lifesaver.function.card.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.api.AuthAPI;
import com.byth.lifesaver.api.GoodsAPI;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.bean.CardActiveInfoBean;
import com.byth.lifesaver.bean.CardActiveSuccessBean;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpResult;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.Contants;
import com.byth.lifesaver.util.GlideWithGalleryImageLoader;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.byth.lifesaver.util.MyCountDownTimer;
import com.byth.lifesaver.util.PermissionActivity;
import com.byth.lifesaver.util.PermissionChecker;
import com.byth.lifesaver.widget.DialogPopupBottom;
import com.fenguo.library.util.JsonUtil;
import com.fenguo.library.util.StringUtil;
import com.gyf.barlibrary.ImmersionBar;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.widget.GFImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/6/12 0012.
 * 激活使用
 * TODO
 */

public class ActivityActiveForUsing extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.img_choose_image)
    GFImageView imgChooseImage;
    @InjectView(R.id.btnVerify)
    Button btnVerify;
    @InjectView(R.id.editVerify)
    EditText edVerify;
    @InjectView(R.id.tv_bind_phone)
    TextView tvBindPhone;
    @InjectView(R.id.btn_finish)
    Button btnFinish;
    @InjectView(R.id.hot_line)
    TextView tvHotLine;
    @InjectView(R.id.btnUploadPic)
    AppCompatButton btnUploadPic;
    private MyCountDownTimer downTimer;
    private String type;
    private HttpSubscriber httpSubscriber;
    private FunctionConfig functionConfig;
    private File picFile;
    private String userName;//用户名
    private String idCode;//身份证
    private String phoneNum;//电话号码
    private int gender;//性别：1男   2女
    private String user_address;//用户地址
    private String linkman;//紧急联系人
    private String relationship;//关系
    private String tel;//紧急联系人电话
    private String bloodType;//血型
    private String illItem;//病史
    private String drugAllergy;//过敏药物
    private String illRemarks;//病史备注
    private String drugRemarks;//过敏备注
    private String cardCode;//卡号
    private String activatePic;//图片url
    private CardActiveInfoBean.CardDto cardDto;//卡片信息对象
    private CardActiveInfoBean.UserDto userDto;//用户信息对象
    private CardActiveInfoBean.ContactsDto contactsDto;//紧急联系人对象
    private CardActiveInfoBean.HealthDto healthDto;//健康信息对象
    private CardActiveInfoBean.CodeDto codeDto;//手机验证码对象

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
    };
    private static final int REQUEST_CODE = 0; // 请求码
    private PermissionChecker permissionChecker;

    @Override
    protected void receiveDataFromPreActivity() {
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString("userName");
        idCode = bundle.getString("idCode");
        gender = bundle.getInt("gender");
        user_address = bundle.getString("user_address");
        linkman = bundle.getString("linkMan");
        relationship = bundle.getString("relationship");
        tel = bundle.getString("tel");
        bloodType = bundle.getString("bloodType");
        illItem = bundle.getString("illItem");
        drugAllergy = bundle.getString("drugAllergy");
        illRemarks = bundle.getString("illRemarks");
        drugRemarks = bundle.getString("drugRemarks");
        cardCode = bundle.getString("cardCode");
        phoneNum = bundle.getString("phoneNum");
    }

    @Override
    protected void initData() {
        cardDto = new CardActiveInfoBean.CardDto();
        userDto = new CardActiveInfoBean.UserDto();
        contactsDto = new CardActiveInfoBean.ContactsDto();
        healthDto = new CardActiveInfoBean.HealthDto();
        codeDto = new CardActiveInfoBean.CodeDto();
    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_active_for_using);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        //设置主题
        //ThemeConfig.CYAN
        ThemeConfig theme = ThemeConfig.DEFAULT;
        //配置功能
        functionConfig = new FunctionConfig.Builder().setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

        //配置imageLoader
        cn.finalteam.galleryfinal.ImageLoader imageLoader = new GlideWithGalleryImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageLoader, theme).setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
        permissionChecker = new PermissionChecker(this);
        tvBindPhone.setText(phoneNum);
    }

    @Override
    protected void initListener() {
        btnVerify.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
        imgChooseImage.setOnClickListener(this);
        btnUploadPic.setOnClickListener(this);
        tvHotLine.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVerify://获取验证码
                downTimer = new MyCountDownTimer(btnVerify);
                downTimer.start();
                btnVerify.setBackgroundResource(R.drawable.btn_gray);
                getVerifyCode(tvBindPhone.getText().toString().trim());
                break;
            case R.id.btn_finish://完成按钮点击
                getCardActiveSuccessInfo();
                break;
            case R.id.img_choose_image://选择图片
                new DialogPopupBottom(this)
                        .builder()
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .addSheetItem("从相册选择照片", DialogPopupBottom.SheetItemColor.Blue, new DialogPopupBottom.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                GalleryFinal.openGallerySingle(Contants.REQUEST_CODE_GALLERY, functionConfig, onHanlderResultCallback);
                            }
                        })
                        .addSheetItem("拍照", DialogPopupBottom.SheetItemColor.Blue, new DialogPopupBottom.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                if (permissionChecker.isLackPermissions(PERMISSIONS)) {
                                    startPermissionsActivity();
                                } else {
                                    GalleryFinal.openCamera(Contants.REQUEST_CODE_CAMERA, functionConfig, onHanlderResultCallback);
                                }
                            }
                        }).show();
                break;
            case R.id.btnUploadPic:
                new DialogPopupBottom(this)
                        .builder()
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .addSheetItem("从相册选择照片", DialogPopupBottom.SheetItemColor.Blue, new DialogPopupBottom.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                GalleryFinal.openGallerySingle(Contants.REQUEST_CODE_GALLERY, functionConfig, onHanlderResultCallback);
                            }
                        })
                        .addSheetItem("拍照", DialogPopupBottom.SheetItemColor.Blue, new DialogPopupBottom.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                // 缺少权限时, 进入权限配置页面
                                if (permissionChecker.isLackPermissions(PERMISSIONS)) {
                                    startPermissionsActivity();
                                } else {
                                    GalleryFinal.openCamera(Contants.REQUEST_CODE_CAMERA, functionConfig, onHanlderResultCallback);
                                }
                            }
                        }).show();
                break;
            case R.id.hot_line:
                telPhone(getResources().getString(R.string.hot_line_of_company));
                break;
        }
    }

    private GalleryFinal.OnHanlderResultCallback onHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            picFile = new File(resultList.get(0).getPhotoPath());
            if (reqeustCode == Contants.REQUEST_CODE_GALLERY) {
                GlideWithGalleryImageLoader.getInstance().displayImage(ActivityActiveForUsing.this, "file://" + resultList.get(0).getPhotoPath(), imgChooseImage,
                        getResources().getDrawable(R.drawable.active_default_image), Contants.IMAGE_WIDTH, Contants.IMAGE_HEIGHT);
                Log.i(TAG, "onHanlderSuccess: " + resultList.get(0).getPhotoPath());
                uploadCardPic(picFile);//上传图片
            } else if (reqeustCode == Contants.REQUEST_CODE_CAMERA) {
                GlideWithGalleryImageLoader.getInstance().displayImage(ActivityActiveForUsing.this, "file://" + resultList.get(0).getPhotoPath(), imgChooseImage,
                        getResources().getDrawable(R.drawable.active_default_image), Contants.IMAGE_WIDTH, Contants.IMAGE_HEIGHT);
                uploadCardPic(picFile);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    private void startPermissionsActivity() {
        PermissionActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionActivity.PERMISSIONS_DENIED) {
            finish();
        } else {
            GalleryFinal.openCamera(Contants.REQUEST_CODE_CAMERA, functionConfig, onHanlderResultCallback);
        }
    }

    /**
     * 获取验证码
     *
     * @param phone
     */
    private void getVerifyCode(String phone) {
        unSub();
        httpSubscriber = new HttpSubscriber(onGetVerifyCodeNextListener);
        phone = tvBindPhone.getText().toString().trim();
        type = "AC";
        HashMap<String, String> map = new HashMap<>();
        map.put("mobileNo", phone);
        map.put("type", type);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        AuthAPI.getInstance().getRegisterCodeWithRetrofit(httpSubscriber, body);

    }

    SubscriberOnNextListener<HttpResult> onGetVerifyCodeNextListener = new SubscriberOnNextListener<HttpResult>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(HttpResult result) {
            showToast("获取验证码成功");
            hideLoadingDialog();
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
            showToast(e.getMessage());
            hideLoadingDialog();
        }

        @Override
        public void onCompleted() {
            hideLoadingDialog();
        }
    };

    //上传图片
    private void uploadCardPic(File file) {
        file = this.picFile;
        unSub();
        httpSubscriber = new HttpSubscriber(onUploadPicNextListener);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);//后台接收参数流的名字（file）,文件名(fileName),传输文件(requestFile)
        /*String descriptionString = "This is a description";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);*/
        GoodsAPI.getInstance().uploadCardPic(httpSubscriber, body);
    }

    SubscriberOnNextListener<CardActiveSuccessBean> onUploadPicNextListener = new SubscriberOnNextListener<CardActiveSuccessBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(CardActiveSuccessBean result) {
            hideLoadingDialog();
            activatePic = result.getUrl();
            showToast("上传成功");
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
            showToast("上传完成");
            hideLoadingDialog();
        }
    };

    /**
     * 获取激活成功后的卡信息
     */
    private void getCardActiveSuccessInfo() {
        unSub();
        httpSubscriber = new HttpSubscriber(onActiveSuccessNextListener);
        cardDto.setActivatePic(activatePic);
        cardDto.setCardCode(cardCode);
        userDto.setGender(gender);
        userDto.setIdCode(idCode);
        userDto.setUser_address(user_address);
        userDto.setUserName(userName);
        userDto.setId(StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        contactsDto.setLinkman(linkman);
        contactsDto.setRelationship(relationship);
        contactsDto.setTel(tel);
        healthDto.setBloodType(bloodType);
        healthDto.setDrugAllergy(drugAllergy);
        healthDto.setIllItem(illItem);
        healthDto.setDrugRemarks(drugRemarks);
        healthDto.setIllRemarks(illRemarks);
        codeDto.setMobileNo(phoneNum);
        codeDto.setType("AC");
        codeDto.setRandCode(edVerify.getText().toString().trim());
        Map<Object, Object> map = new HashMap<>();
        map.put("codeDto", codeDto);
        map.put("healthDto", healthDto);
        map.put("cardDto", cardDto);
        map.put("contactsDto", contactsDto);
        map.put("userDto", userDto);
        String postInfo = JsonUtil.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), postInfo);
        GoodsAPI.getInstance().getActiveSuccessInfo(httpSubscriber, body);
    }

    SubscriberOnNextListener<CardActiveSuccessBean> onActiveSuccessNextListener = new SubscriberOnNextListener<CardActiveSuccessBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(CardActiveSuccessBean cardActiveSuccessBean) {
            hideLoadingDialog();
            Bundle bundle = new Bundle();
            bundle.putString("bloodType", cardActiveSuccessBean.getBloodType());
            bundle.putString("cardCode", cardActiveSuccessBean.getCardCode());
            bundle.putString("expiryDate", cardActiveSuccessBean.getExpiryDate());
            bundle.putString("phone", cardActiveSuccessBean.getPhone());
            bundle.putString("userName", cardActiveSuccessBean.getUserName());
            bundle.putString("idCode", cardActiveSuccessBean.getIdCode());
            openActivityNotClose(ActivityActiveSuccess.class, bundle);
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

    /**
     * 解除订阅
     */
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (downTimer != null) {
            downTimer.cancel();
            btnVerify.setClickable(true);
            btnVerify.setText("获取验证码");
        }
    }

    /**
     * activity销毁时删除文件
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (picFile.exists()) {
            picFile.delete();
        }
    }
}
