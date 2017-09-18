package com.byth.lifesaver.function.emergency;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.byth.lifesaver.MainActivity;
import com.byth.lifesaver.R;
import com.byth.lifesaver.api.HomeAPI;
import com.byth.lifesaver.base.BaseFragment;
import com.byth.lifesaver.bean.EmergencyCallBean;
import com.byth.lifesaver.function.card.activity.ActivityCardDetail;
import com.byth.lifesaver.function.mine.activity.ActivityModifyPhoneStepOne;
import com.byth.lifesaver.function.mine.activity.ActivityModifyWithPassword;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.byth.lifesaver.widget.DialogPopupBottom;
import com.fenguo.library.util.JsonUtil;
import com.fenguo.library.util.StringUtil;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2017/5/31 0031.
 * 拨打救援电话
 */

public class EmergencyFragment extends BaseFragment implements View.OnClickListener {
    @InjectView(R.id.igBtnEmergency)
    ImageButton btnEmergencyCall;//救援按钮
    @InjectView(R.id.emergencyNotice)
    TextView tvEmergencyNotice;//救援须知
    @InjectView(R.id.btnOpenService)
    AppCompatButton btnOpenService;//开通服务
    @InjectView(R.id.hot_line)
    TextView tvHotLine;//客服电话
    private volatile static EmergencyFragment fragment;
    private HttpSubscriber httpSubscriber;
    private int code;//1为允许拨打电话，-1为不允许
    private boolean isAllowedToCall;//是否可以拨打电话


    public static EmergencyFragment getInstance() {
        if (fragment == null) {
            synchronized (EmergencyFragment.class) {
                if (fragment == null) {
                    fragment = new EmergencyFragment();
                }
            }
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = setContentView(inflater, R.layout.fragment_emergency);
        return view;
    }

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    public void onResume() {
        super.onResume();
        isAllowedCall();
    }

    @Override
    protected void initComponent() {
        isAllowedToCall = false;
    }

    //是否允许拨打紧急救援电话
    private void isAllowedCall() {
        unSub();
        httpSubscriber = new HttpSubscriber(isAllowedCallListener);
        Map<String, Integer> map = new HashMap<>();
        map.put("id", StringUtil.toInt(preference.getString(LifeSaverPreference.ID)));
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), JsonUtil.toJson(map));
        HomeAPI.getInstance().isAllowedCall(httpSubscriber, body);
    }

    SubscriberOnNextListener<EmergencyCallBean> isAllowedCallListener = new SubscriberOnNextListener<EmergencyCallBean>() {
        @Override
        public void onStart() {
            ((MainActivity) getActivity()).showLoadingDialog();
        }

        @Override
        public void onNext(EmergencyCallBean emergencyCallBean) {
            code = emergencyCallBean.getCode();
            isAllowedToCall = true;
            showEmergencyCallWithStatus();
        }

        @Override
        public void onApiError(ApiException e) {
            ((MainActivity) getActivity()).hideLoadingDialog();
            isAllowedToCall = false;
            showToast(e.getMessage());
        }

        @Override
        public void onNetworkError(Throwable e) {
            ((MainActivity) getActivity()).hideLoadingDialog();
            isAllowedToCall = false;
            showToast(e.getMessage());
        }

        @Override
        public void onOtherError(Throwable e) {
            ((MainActivity) getActivity()).hideLoadingDialog();
            isAllowedToCall = false;
            showToast(e.getMessage());
        }

        @Override
        public void onCompleted() {
            ((MainActivity) getActivity()).hideLoadingDialog();
        }
    };

    @Override
    protected void initListener() {
        btnEmergencyCall.setOnClickListener(this);
        tvEmergencyNotice.setOnClickListener(this);
        btnOpenService.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void immersionInit() {
        ImmersionBar.with(getActivity())
                .statusBarDarkFont(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        ImmersionBar.with(getActivity()).destroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.igBtnEmergency:
                /*telPhone(getResources().getString(R.string.hot_line_of_company));*/
                new DialogPopupBottom(getActivity())
                        .builder()
                        .setTitle("请选择客服")
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("梁小姐", DialogPopupBottom.SheetItemColor.Blue, new DialogPopupBottom.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                telPhone("020-84890825");
                            }
                        })
                        .addSheetItem("孙小姐", DialogPopupBottom.SheetItemColor.Blue, new DialogPopupBottom.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                telPhone("020-84890836");
                            }
                        })
                        .addSheetItem("曾小姐", DialogPopupBottom.SheetItemColor.Blue, new DialogPopupBottom.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                telPhone("020-84890829");
                            }
                        }).show();
                break;
            case R.id.emergencyNotice:
                break;
            case R.id.btnOpenService:
                openActivityNotClose(ActivityCardDetail.class, null);//跳转到购卡界面
                break;
            case R.id.hot_line:
                telPhone(getResources().getString(R.string.hot_line_of_company));
                break;
        }
    }

    /**
     * 根据用户状态显示救援按钮是否可以点击
     * 如果不可以点击则引导用户开通救援服务
     */
    private void showEmergencyCallWithStatus() {
        if (code == 1 && isAllowedToCall) {
            btnOpenService.setVisibility(View.GONE);
            btnEmergencyCall.setBackgroundResource(R.drawable.selector_btn_emergency_call);
            btnEmergencyCall.setClickable(true);
        } else {
            btnOpenService.setVisibility(View.VISIBLE);
            btnEmergencyCall.setBackgroundResource(R.drawable.btn_emergency_call_unactive);
            btnEmergencyCall.setClickable(false);
        }
    }

    //解除订阅
    private void unSub() {
        if (httpSubscriber != null) {
            httpSubscriber.unSubscribe();
        }
    }
}
