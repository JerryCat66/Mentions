package com.fenguo.library.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenguo.library.R;
import com.fenguo.library.adapter.DisplayPhotoAdapter;
import com.fenguo.library.util.ClippingPicture;
import com.fenguo.library.util.PatternUtils;
import com.fenguo.library.view.photoview.PhotoView;
import com.fenguo.library.view.photoview.PhotoViewAttacher;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by Lee on 2015/6/10.
 */
public class DisplayPhotoActivity extends AppCompatActivity {
    public static String TAG = "DisplayPhotoActivity";

    private ViewPager mViewPager;
    private DisplayPhotoAdapter mPhotoDisplayAdapter;
    private RelativeLayout mMultiple;
    private PhotoView mLeaflet;//单张图片显示所使用的控件
    private TextView mPhotoNumber;//多张图片显示时，照片页码

    private int mDefaultPic;//默认的显示图片
    private boolean mOnlyOne;//是否为单张显示
    private int mPosition;//当前显示的照片页码
    private String[] mImagePaths;//保存显示的图片数组的路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_photo);
        initData();
        initView();
        initListener();
    }

    /**
     * 获取数据
     */
    protected void initData() {
        Bundle mBundle = getIntent().getExtras();
        mOnlyOne = mBundle.getBoolean("onlyOne");
        mImagePaths = mBundle.getStringArray("imagePaths");
        mPosition = mBundle.getInt("position",0);
        mDefaultPic = mBundle.getInt("defaultPic");
    }

    /**
     * 初始化控件
     */
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mMultiple = (RelativeLayout) findViewById(R.id.multipleRL);
        mLeaflet = (PhotoView) findViewById(R.id.leafletPV);
        mPhotoNumber = (TextView) findViewById(R.id.photo_number);
        if (mImagePaths[0] == null) {
            mLeaflet.setVisibility(View.VISIBLE);
            mLeaflet.setImageResource(mDefaultPic);
            return;
        }
        if (!mOnlyOne) {
            mMultiple.setVisibility(View.VISIBLE);
            mPhotoDisplayAdapter = new DisplayPhotoAdapter(this, mImagePaths, mDefaultPic);
            mViewPager.setAdapter(mPhotoDisplayAdapter);
            mViewPager.setCurrentItem(mPosition);
            mPhotoNumber.setText((mPosition + 1) + "/" + mImagePaths.length);
        } else {
            mLeaflet.setVisibility(View.VISIBLE);
            mLeaflet.setImageResource(mDefaultPic);
            mLeaflet.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    DisplayPhotoActivity.this.finish();
                }
            });
            if(null ==mImagePaths[0]){
                File  mIsmagePaths= (File)getIntent().getSerializableExtra("remoteFile");
                if (mIsmagePaths.exists()) {
                   /* Bitmap mBitmap = BitmapFactory.decodeFile(mImagePaths[0]);
                    mLeaflet.setImageBitmap(mBitmap);*/
                    int degree = ClippingPicture.readPictureDegree(mImagePaths[0]);
                    Bitmap bitmap = ClippingPicture.decodeResizeBitmapSd(mImagePaths[0], 480, 800);
                    bitmap = ClippingPicture.rotateBitmap(bitmap, degree);
                    mLeaflet.setImageBitmap(bitmap);
                }
                return;
            }
            if (PatternUtils.getInstance().checkURL(mImagePaths[0])) {
                if (!ImageLoader.getInstance().isInited()) {
                    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
                    ImageLoader.getInstance().init(config);
                }
                ImageLoader.getInstance().displayImage(mImagePaths[0], mLeaflet);
            } else {
                File mFile = new File(mImagePaths[0]);
                if (mFile.exists()) {
                    int degree = ClippingPicture.readPictureDegree(mImagePaths[0]);
                    Bitmap bitmap = ClippingPicture.decodeResizeBitmapSd(mImagePaths[0], 480, 800);
                    bitmap = ClippingPicture.rotateBitmap(bitmap, degree);
                    mLeaflet.setImageBitmap(bitmap);
                    /*Bitmap mBitmap = BitmapFactory.decodeFile(mImagePaths[0]);
                    mLeaflet.setImageBitmap(mBitmap);*/
                }
            }
        }
    }

    /**
     * 初始化监听器
     */
    protected void initListener() {
        if (true != mOnlyOne) {
            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mPhotoNumber.setText((position + 1) + "/" + mImagePaths.length);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }
}
