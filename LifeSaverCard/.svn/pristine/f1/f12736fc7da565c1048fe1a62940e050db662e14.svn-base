package com.byth.lifesaver.function.card.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.byth.lifesaver.R;
import com.byth.lifesaver.api.GoodsAPI;
import com.byth.lifesaver.base.BaseFragment;
import com.byth.lifesaver.bean.GoodInfoBean;
import com.byth.lifesaver.http.ApiException;
import com.byth.lifesaver.http.HttpSubscriber;
import com.byth.lifesaver.http.SubscriberOnNextListener;
import com.byth.lifesaver.widget.WgtScrollView;
import com.fenguo.library.util.JsonUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/6/1 0001.
 * 详情第一页
 */

public class FragmentCardDetailPager extends BaseFragment {
    @InjectView(R.id.lyt_top_detail)
    LinearLayout pushTopToDetail;
    @InjectView(R.id.my_scroll_view)
    WgtScrollView myScrollView;
    @InjectView(R.id.detailBanner)
    Banner detailBanner;
    @InjectView(R.id.detail_goods_name)
    TextView tvGoodsName;
    @InjectView(R.id.card_price)
    TextView tvPrice;
    private GoodInfoBean.GoodsDto goodsDto;
    private GoodInfoBean.CostDto costDto;

    private List<String> mPicList;//图片集和
    private HttpSubscriber httpSubscriber;

    public static FragmentCardDetailPager newInstance(Context context, int position) {
        FragmentCardDetailPager f = new FragmentCardDetailPager();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = setContentView(inflater, R.layout.fragment_product_detail);
        ButterKnife.inject(view);
        return view;
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initComponent() {
        mPicList = new ArrayList<String>();//图片地址
        goodsDto = new GoodInfoBean.GoodsDto();
        costDto = new GoodInfoBean.CostDto();
        getProductDetail();


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    /**
     * 获取商品详情
     */
    private void getProductDetail() {
        myScrollView.setScroll(true);
        pushTopToDetail.setVisibility(View.VISIBLE);
        if (httpSubscriber != null) {
            httpSubscriber.unsubscribe();
        } else {
            httpSubscriber = new HttpSubscriber<>(onNextListener);
            Map<String, String> map = new HashMap<>();
            GoodsAPI.getInstance().getGoodsInfo(httpSubscriber, map);
        }
    }

    SubscriberOnNextListener<GoodInfoBean> onNextListener = new SubscriberOnNextListener<GoodInfoBean>() {
        @Override
        public void onStart() {
            showLoadingDialog();
        }

        @Override
        public void onNext(GoodInfoBean goodInfoBeanHttpResult) {
            goodsDto = goodInfoBeanHttpResult.getGoodsDto();
            costDto = goodInfoBeanHttpResult.getCostDto();
            tvGoodsName.setText(goodsDto.getGoodsName());
            tvPrice.setText(String.valueOf(costDto.getYearPrice()) + "元");
            mPicList.add(goodsDto.getImage() + "");
            mPicList.add("http://img.hb.aicdn.com/49272784153f7ab33419819a66371058934d84c3e303-qDMbdW_fw658" + "");
            detailBanner.setBannerStyle(BannerConfig.NUM_INDICATOR)//数字导航
                    .setImages(mPicList)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            Glide.with(context.getApplicationContext())
                                    .load((String) path)
                                    .crossFade()
                                    .placeholder(R.drawable.card_default)
                                    .into(imageView);
                        }
                    })
                    .isAutoPlay(true)//是否自动轮播
                    .setViewPagerIsScroll(true)//是否允许手指滑动
                    .setDelayTime(5000).start();//5秒间隔
            hideLoadingDialog();
        }

        @Override
        public void onApiError(ApiException e) {
            showToast(e.getMessage());
            hideLoadingDialog();
        }

        @Override
        public void onNetworkError(Throwable e) {
            showToast(e.getMessage());
            hideLoadingDialog();
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

    @Override
    public void onStop() {
        super.onStop();
        detailBanner.stopAutoPlay();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
