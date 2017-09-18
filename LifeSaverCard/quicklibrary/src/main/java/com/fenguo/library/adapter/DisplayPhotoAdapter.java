package com.fenguo.library.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.fenguo.library.R;
import com.fenguo.library.util.PatternUtils;
import com.fenguo.library.view.photoview.PhotoView;
import com.fenguo.library.view.photoview.PhotoViewAttacher;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by ewhale on 2015/6/11.
 */
public class DisplayPhotoAdapter extends PagerAdapter {
    public static String TAG = "PhotoDisplayAdapter";
    private String[] mImagePaths;
    private Activity mContext;
    private int mDefaultPic;

    public DisplayPhotoAdapter(Activity mContext, String[] mImagePaths,int mDefaultPic) {
        this.mContext = mContext;
        this.mImagePaths = mImagePaths;
        this.mDefaultPic=mDefaultPic;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView mPhotoView = new PhotoView(container.getContext());
        mPhotoView.setImageResource(mDefaultPic);

        if (PatternUtils.getInstance().checkURL(mImagePaths[position])) {
            if (!ImageLoader.getInstance().isInited()) {
                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext).build();
                ImageLoader.getInstance().init(config);
            }
            ImageLoader.getInstance().displayImage(mImagePaths[position], mPhotoView);
        } else {
            File mFile = new File(mImagePaths[position]);
            if (mFile.exists()) {
                Bitmap mBitmap = BitmapFactory.decodeFile(mImagePaths[position]);
                mPhotoView.setImageBitmap(mBitmap);
            }
        }

        container.addView(mPhotoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mPhotoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                ((Activity) mContext).finish();
//                mContext.overridePendingTransition(R.anim.alpha_into,
//                        R.anim.zoomout);
            }
        });
        return mPhotoView;
    }

    @Override
    public int getCount() {
        return null != mImagePaths ? mImagePaths.length : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
