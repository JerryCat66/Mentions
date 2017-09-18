package com.byth.lifesaver.function.auth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.gyf.barlibrary.ImmersionBar;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/5/23 0023.
 * 新手指引
 */

public class GuideActivity extends FrameActivity implements View.OnClickListener {
    private List<String> urls;
    private ArrayList<View> viewList;
    private GuidePageAdapter guidePageAdapter;

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;
    @InjectView(R.id.open)
    Button btnOpen;

    private ImageView iv;

    @Override
    protected void receiveDataFromPreActivity() {

    }

    /**
     * 添加要显示的url，图片可在本地
     */
    @Override
    protected void initData() {
        urls = new ArrayList<String>();
        urls.add(R.drawable.guide_01 + "");
        urls.add(R.drawable.guide_02 + "");
        urls.add(R.drawable.guide_03 + "");
        viewList = new ArrayList<View>();
    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_guide);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.transparent)
                .init();
        for (String url : urls) {
            iv = new ImageView(this);
            iv.setImageBitmap(readBitmap(this, Integer.parseInt(url)));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewList.add(iv);
        }
        guidePageAdapter = new GuidePageAdapter();
        mViewPager.setAdapter(guidePageAdapter);
    }

    @Override
    protected void initListener() {
        btnOpen.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == (urls.size() - 1)) {
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openActivity(LoginActivity.class, null);
                            preference.putBoolean(LifeSaverPreference.FIRST_IN, true);
                        }
                    });
                    btnOpen.setVisibility(View.VISIBLE);
                } else {
                    btnOpen.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * bitmap获取本地文件防止内存溢出
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open:
                openActivity(LoginActivity.class, null);
                preference.putBoolean(LifeSaverPreference.FIRST_IN, true);
                break;
        }
    }

    /**
     * viewpager适配器
     */
    private class GuidePageAdapter extends PagerAdapter {
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }
    }
}
