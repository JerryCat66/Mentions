package com.byth.lifesaver.function.mine.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.RadioGroup;

import com.byth.lifesaver.R;
import com.byth.lifesaver.adapter.FragmentAdapter;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.function.mine.fragment.FragmentOrderAll;
import com.byth.lifesaver.function.mine.fragment.FragmentOrderFinished;
import com.byth.lifesaver.function.mine.fragment.FragmentOrderWaitForPaying;
import com.byth.lifesaver.function.mine.fragment.FragmentOrderWaitForRecieving;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/6/12 0012.
 * 我的订单
 * TODO
 */

public class ActivityMyOrder extends FrameActivity {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.order_radioGroup)
    RadioGroup mRadioGroup;
    @InjectView(R.id.order_viewPager)
    ViewPager mViewPager;

    private List<Fragment> pageList = new ArrayList<>();//四个page

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_my_order);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
        pageList.add(new FragmentOrderAll());
        pageList.add(new FragmentOrderWaitForPaying());
        pageList.add(new FragmentOrderWaitForRecieving());
        pageList.add(new FragmentOrderFinished());
        mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), pageList));
        mViewPager.setOffscreenPageLimit(4);
    }

    @Override
    protected void initListener() {
        mRadioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        mViewPager.addOnPageChangeListener(onPageChangeListener);
    }

    /**
     * radioButton点击监听
     */
    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.radio_order_all://全部
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.radio_order_wait_for_paying://待付款
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.radio_order_wait_for_receive://待收货
                    mViewPager.setCurrentItem(2);
                    break;
                case R.id.radio_order_is_finished://已完成
                    mViewPager.setCurrentItem(3);
                    break;
            }
        }
    };
    /**
     * 左右滑动viewPager监听
     */
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int current = mViewPager.getCurrentItem();//获取viewPager数量
            switch (current) {
                case 0:
                    mRadioGroup.check(R.id.radio_order_all);
                    break;
                case 1:
                    mRadioGroup.check(R.id.radio_order_wait_for_paying);
                    break;
                case 2:
                    mRadioGroup.check(R.id.radio_order_wait_for_receive);
                    break;
                case 3:
                    mRadioGroup.check(R.id.radio_order_is_finished);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
