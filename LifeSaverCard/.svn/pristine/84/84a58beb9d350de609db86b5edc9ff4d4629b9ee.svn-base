package com.fenguo.library.clipImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import com.fenguo.library.photowall.ImageLoader;
import com.fenguo.library.photowall.ImageLoader.Type;
import com.fenguo.library.util.LogUtil;

/**
 * http://blog.csdn.net/lmj623565791/article/details/39761281
 * 
 * @author zhy
 * 
 */
public class ClipImageLayout extends RelativeLayout {

    private ClipZoomImageView mZoomImageView;
    private ClipImageBorderView mClipImageView;

    /**
     * 这里测试，直接写死了大小，真正使用过程中，可以提取为自定义属性
     */
    private int mHorizontalPadding = 40;

    public ClipImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mZoomImageView = new ClipZoomImageView(context);
        mClipImageView = new ClipImageBorderView(context);
    }

    /**
     * @Title: init
     * @Description: 设置是否可以缩放以及图片的url
     * @param
     * @return void 返回类型
     * @throws
     */
    public void init(boolean canScale, String url, String caremaPath) {
        // 设置是否可以缩放
        mZoomImageView.setCanScale(canScale);
        android.view.ViewGroup.LayoutParams lp = new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        if (url.equals(caremaPath)) {
            ImageLoader.getInstance(3, Type.LIFO).loadImageNotFromCache(url, mZoomImageView);
        } else {
            ImageLoader.getInstance(3, Type.LIFO).loadImage(url, mZoomImageView);
        }
        this.addView(mZoomImageView, lp);
        this.addView(mClipImageView, lp);

        // 计算padding的px
        mHorizontalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                mHorizontalPadding, getResources().getDisplayMetrics());
        mZoomImageView.setHorizontalPadding(mHorizontalPadding);
        mClipImageView.setHorizontalPadding(mHorizontalPadding);
    }

    /**
     * 对外公布设置边距的方法,单位为dp
     * 
     * @param mHorizontalPadding
     */
    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }

    /**
     * 裁切图片
     * 
     * @return
     */
    public Bitmap clip() {
        LogUtil.i("msg", "内存大小？？" + mZoomImageView.clip().getByteCount() / 1024);
        return mZoomImageView.clip();
    }

}
