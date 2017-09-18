package com.fenguo.library.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.fenguo.library.R;
import com.fenguo.library.util.ClippingPicture;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片轮播控件
 * Created by Administrator on 2015/6/17.
 */
public class ImageCarouselView extends RelativeLayout {

    private long switchTime = 4000;//2秒切换一次
    /**
     * 模拟无限左右滑动,但是实际上的左右有限
     */
    private int fakerNum = 50000;
    private Context mContext;
    private View converView;
    private ViewPager viewPager;
    private CarouselPagerAdapter adapter;
    private List<String> urls;
    /**
     * 内容
     */
    private List<ImageView> imgs;

    /**
     * 当前选中
     */
    private int currentItemIndex;

    private boolean isStop;

    public ImageCarouselView(Context context) {
        this(context, null);
    }

    public ImageCarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context.getApplicationContext();
        isStop = false;
        converView = LayoutInflater.from(mContext).inflate(R.layout.view_image_carousel, this, true);
        viewPager = (ViewPager) converView.findViewById(R.id.view_pager);
        imgs = new ArrayList<>();
        adapter = new CarouselPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentItemIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setData(List<String> urls, int defaultPic) {
        this.urls = urls;
        //初始化图片

        for (String url : urls) {
            ImageView img = new ImageView(mContext);
            img.setBackgroundColor(getResources().getColor(R.color.white));
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);//CENTER_CROP
            img.setImageBitmap(readBitMap(mContext, defaultPic));
            if (!ImageLoader.getInstance().isInited()) {
                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext).build();
                ImageLoader.getInstance().init(config);
            }
            img.setImageBitmap(readBitMap(mContext,Integer.parseInt(url)));
            imgs.add(img);
        }
        //初始化图片位置
        adapter.notifyDataSetChanged();
        currentItemIndex = fakerNum / 2;
        viewPager.setCurrentItem(currentItemIndex);

        //超过两个开启轮播
        if (urls != null && urls.size() >= 2) {
            if (!loopPlayThread.isAlive()) {
                loopPlayThread.start();
            }
        }
    }

    /**
     * 读取本地资源的图片导致图片过大出现内存溢出
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId){
          BitmapFactory.Options opt = new BitmapFactory.Options();
          opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
          opt.inPurgeable = true;
          opt.inInputShareable = true;
               //获取资源图片
          InputStream is = context.getResources().openRawResource(resId);
          return BitmapFactory.decodeStream(is,null,opt);
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(++currentItemIndex, true);
        }

        ;
    };
    private Thread loopPlayThread = new Thread() {
        private boolean isRun = true;

        public void run() {
            while (isRun) {
                try {
                    if (!isStop) {
                        sleep(switchTime);
                        Message m = new Message();
                        handler.sendMessage(m);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        public void interrupt() {
            isRun = false;
            super.interrupt();
        }
    };

    /**
     * 获取轮播图片的个
     *
     * @return
     */
    private int getCount() {
        if (urls == null) {
            return 0;
        } else {
            return urls.size();
        }
    }

    /**
     * 取模求出真实的位置
     *
     * @param position
     * @return
     */
    private int getRealIndex(int position) {
        int size = getCount();
        if (size == 0) {
            position = -1;
        } else {
            position %= size;
            if (position < 0) {
                position = 0;
            }
        }
        return position;
    }

    public void stop() {
        isStop = true;
    }

    public void reStart() {
        isStop = false;
    }

    public void setSwitchTime(int time) {
        switchTime = time;
    }

    class CarouselPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return fakerNum;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int realIndex = getRealIndex(position);
            if (realIndex >= 0) {
                View v = imgs.get(realIndex);
                ViewGroup parentView = (ViewGroup) v.getParent();
                if (parentView != null) {
                    parentView.removeView(v);
                }
                container.addView(v);
                return v;
            } else {
                return null;
            }

        }

    }

}