package com.fenguo.library.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fenguo.library.R;
import com.fenguo.library.adapter.BaseAdapterHelper;
import com.fenguo.library.adapter.QuickAdapter;
import com.fenguo.library.photowall.ImageFloder;
import com.fenguo.library.photowall.ImageLoader;
import com.fenguo.library.photowall.ImageLoader.Type;
import com.fenguo.library.photowall.ListImageDirPopupWindow;
import com.fenguo.library.photowall.ListImageDirPopupWindow.OnImageDirSelected;
import com.fenguo.library.photowall.PhotoWallType;
import com.fenguo.library.util.ContantsUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * 图片墙，可以进行图片选择<br/>
 * bundle可以设置ContantsUtil.MAX_PHOTO_COUNT(数量)<br/>
 * ContantsUtil.PHOTO_TYPE(类型，值为PHOTO_PICK或PHOTO_CLIP) <br/>
 * 如果类型为PHOTO_CLIP，可设置 ContantsUtil.PHOTO_CLIP_SCALE,其值为true或false,默认为false<br/>
 * 如果图片数量多于1张，默认只能选择，不能剪裁
 *
 * @author Lee
 * @createDate 2015年3月27日
 */
public class PhotoWallActivity extends AppCompatActivity implements OnImageDirSelected {

    private GridView mGirdView;
    private RelativeLayout mBottomLy;
    private TextView mChooseDir;
    private TextView mImageCount;
    private ProgressDialog mProgressDialog;
    private Toolbar mToolbar;
    private Menu mMenu;
    private MenuItem mSaveMenu;
    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;

    /**
     * @Fields path : 图片数量最多的文件夹的路径
     */
    private String maxImgsPath;

    /**
     * 所有的图片
     */
    private List<String> mImgs;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();

    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();

    int totalCount = 0;
    private int mScreenHeight;
    private ListImageDirPopupWindow mListImageDirPopupWindow;

    private QuickAdapter<String> mAdapter;

    /**
     * @Fields mCaremaPath : 照片的途径
     */
    private String mCaremaPath;

    /**
     * @Fields mMaxPictureCount : 最多可以选择几张图片
     */
    private int mMaxPictureCount;
    /**
     * @Fields mSelectedUrl : 选择的图片url
     */
    private List<String> mSelectedUrl;

    /**
     * @Fields mType : 是否需要剪裁
     */
    private int type;

    /**
     * @Fields caScale : 是否可以缩放
     */
    private boolean canScale;

    /**
     * @Fields allFile : 所有的图片文件
     */
    private List<File> allFile;

    /**
     * @Fields tempFile : 照相缓存文件
     */
    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_wall);
        receiveFromParent();
        initData();
        initComponent();
        initListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        this.mMenu = menu;
        mSaveMenu = mMenu.findItem(R.id.action_save);
        mSaveMenu.setIcon(null);
        if (type == PhotoWallType.PICK.getType()) {
            mSaveMenu.setTitle(mSelectedUrl.size() + "/" + mMaxPictureCount);
        } else {
            mSaveMenu.setTitle("");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_save) {
            if (mSelectedUrl.size() >= 1 && mSelectedUrl.size() < mMaxPictureCount) {
                Intent intent = new Intent();
                String[] strArr = new String[mSelectedUrl.size()];
                mSelectedUrl.toArray(strArr);
                intent.putExtra(ContantsUtil.PHOTO_RESULT, strArr);
                setResult(RESULT_OK, intent);
                finish();
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    protected void receiveFromParent() {
        Bundle bundle = getIntent().getExtras();
        mCaremaPath = bundle.getString(ContantsUtil.PHOTO_CAREMA_PATH);
        mMaxPictureCount = bundle.getInt(ContantsUtil.MAX_PHOTO_COUNT);
        // 如果最大图片数不为1，不能剪裁
        if (mMaxPictureCount != 1) {
            // type = ContantsUtil.PHOTO_PICK;
            type = PhotoWallType.PICK.getType();
        } else {
            type = bundle.getInt(ContantsUtil.PHOTO_TYPE);
        }
        if (type == PhotoWallType.CLIP.getType()) {
            canScale = bundle.getBoolean(ContantsUtil.PHOTO_CLIP_SCALE);
        }

    }

    protected void initData() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        mSelectedUrl = new ArrayList<String>();
        allFile = new ArrayList<File>();
        getImages();
    }

    protected void initComponent() {
        mGirdView = (GridView) findViewById(R.id.id_gridView);
        mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);
        mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
        mImageCount = (TextView) findViewById(R.id.id_total_count);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

    }

    protected void initListener() {
        mGirdView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = mImgs.get(position);
                if (url.equals("take_photo")) {// 拍照
                    tempFile = new File(mCaremaPath);
                    if (tempFile.exists()) {
                        tempFile.delete();
                    }
                    openCamera();
                    return;
                }
                // 如果当前选中的数量和最大数量一致，则提示“最多可以选择**张”
                if (mSelectedUrl.size() == mMaxPictureCount) {
                    Toast.makeText(PhotoWallActivity.this, "最多可以选择" + mMaxPictureCount + "张",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                View itemView = mAdapter.getView(position, view, parent);
                ImageView mImageView = (ImageView) itemView.findViewById(R.id.id_item_image);
                ImageView mSelect = (ImageView) itemView.findViewById(R.id.id_item_select);
                if (mSelectedUrl.contains(url)) {
                    mSelectedUrl.remove(url);
                    mSelect.setImageResource(R.drawable.picture_unselected);
                    mImageView.setColorFilter(null);
                } else {
                    mSelectedUrl.add(url);
                    mSelect.setImageResource(R.drawable.pictures_selected);
                    mImageView.setColorFilter(Color.parseColor("#77000000"));
                }
                if (mMaxPictureCount == 1 && type == PhotoWallType.CLIP.getType()) {
                    openClipImageActivity(url);
                } else if (mMaxPictureCount == 1 && type == PhotoWallType.PICK.getType()) {
                    Intent intent = new Intent();
                    String[] strArr = new String[mSelectedUrl.size()];
                    mSelectedUrl.toArray(strArr);
                    intent.putExtra(ContantsUtil.PHOTO_RESULT, strArr);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    changeTitleView();
                }
            }
        });

        /**
         * 为底部的布局设置点击事件，弹出popupWindow
         */
        mBottomLy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);

                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = .3f;
                getWindow().setAttributes(lp);
            }
        });
    }

    /**
     * @param
     * @return void 返回类型
     * @throws
     * @Title: changeTitleView
     * @Description: 更改titleview的文字
     */
    private void changeTitleView() {
        if (type == PhotoWallType.PICK.getType()) {
//            mTitleView.setRightTv(mSelectedUrl.size() + "/" + mMaxPictureCount);
            mSaveMenu.setTitle(mSelectedUrl.size() + "/" + mMaxPictureCount);
        }
    }

    /**
     * @param @param url 需要剪切的图片url
     * @return void 返回类型
     * @throws
     * @Title: openClipImageActivity
     * @Description: 进入图片剪切的Activity
     */
    private void openClipImageActivity(String url) {
        Intent intent = new Intent(PhotoWallActivity.this, ClipImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContantsUtil.PHOTO_CLIP_SCALE, canScale);
        bundle.putString(ContantsUtil.PHOTO_CAREMA_PATH, mCaremaPath);
        bundle.putString("url", url);
        intent.putExtras(bundle);
        startActivityForResult(intent, PhotoWallType.CLIP.getType());
    }

    /**
     * 为View绑定数据
     */
    private void data2View() {
        if (mImgDir == null) {
            Toast.makeText(getApplicationContext(), "没有扫描到任何图片", Toast.LENGTH_SHORT).show();
        }
        if(mImgDir != null){
            maxImgsPath = mImgDir.getAbsolutePath();
        }
        mImgs = sortAllPicture();
        mAdapter = new QuickAdapter<String>(this, R.layout.grid_item, mImgs) {

            @Override
            protected void convert(BaseAdapterHelper helper, final String item) {
                if (item.equals("take_photo")) {
                    helper.setImageResource(R.id.id_item_image, R.drawable.pictures_take);
                    helper.setVisible(R.id.id_item_select, View.GONE);
                } else {
                    helper.setVisible(R.id.id_item_select, View.VISIBLE);
                    // 设置no_pic
                    helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
                    // 设置no_selected
                    helper.setImageResource(R.id.id_item_select, R.drawable.picture_unselected);
                    // 设置图片
                    ImageLoader.getInstance(3, Type.LIFO).loadImage(item,
                            (ImageView) helper.getView(R.id.id_item_image));

                    final ImageView mImageView = helper.getView(R.id.id_item_image);
                    final ImageView mSelect = helper.getView(R.id.id_item_select);

                    mImageView.setColorFilter(null);
                    /**
                     * 已经选择过的图片，显示出选择过的效果
                     */
                    if (mSelectedUrl.contains(item)) {
                        mSelect.setImageResource(R.drawable.pictures_selected);
                        mImageView.setColorFilter(Color.parseColor("#77000000"));
                    } else {
                        mSelect.setImageResource(R.drawable.picture_unselected);
                        mImageView.setColorFilter(null);
                    }
                }

            }
        };

        mGirdView.setAdapter(mAdapter);
        mGirdView.setBackgroundColor(Color.BLACK);
        mImageCount.setText(totalCount + "张");
    }

    /**
     * @param @return 设定文件
     * @return List<String> 返回类型
     * @throws
     * @Title: sortAllPicture
     * @Description: 对所有图片进行排序
     */
    private List<String> sortAllPicture() {
        List<String> mImgs = new ArrayList<String>();
        for (File file : allFile) {
            String filename = file.getName();
            if (filename.endsWith(".jpg") || filename.endsWith(".png")
                    || filename.endsWith(".jpeg")) {
                mImgs.add(file.getAbsolutePath());
            }
        }
        mImgs.add(0, "take_photo");
        return mImgs;
    }

    /**
     * @param @param  files
     * @param @return 设定文件
     * @return List<String> 返回类型
     * @throws
     * @Title: sort
     * @Description: 对文件进行排序
     */
    private List<String> sort(File mImgDir) {
        List<File> files = Arrays.asList(mImgDir.listFiles());
        List<String> mImgs = new ArrayList<String>();
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
            }
        });
        for (File file : files) {
            String filename = file.getName();
            if (filename.endsWith(".jpg") || filename.endsWith(".png")
                    || filename.endsWith(".jpeg")) {
                mImgs.add(file.getAbsolutePath());
            }
        }
        return mImgs;

    }

    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindw() {
        mListImageDirPopupWindow = new ListImageDirPopupWindow(LayoutParams.MATCH_PARENT,
                (int) (mScreenHeight * 0.7), mImageFloders, LayoutInflater.from(
                getApplicationContext()).inflate(R.layout.list_dir, null));

        mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */

    private void getImages() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = PhotoWallActivity.this.getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?", new String[]{
                                "image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED
                                + " desc");

                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    allFile.add(new File(path));
                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }
                    if (parentFile.list() == null) {
                        continue;
                    }

                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg") || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg"))
                                return true;
                            return false;
                        }
                    }).length;
                    totalCount += picSize;

                    imageFloder.setCount(picSize);
                    mImageFloders.add(imageFloder);

                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                // 遍历完成，额外添加所有图片的选项
                ImageFloder all = new ImageFloder();
                all.setDir("all");
                if (mImageFloders.size() > 0) {
                    all.setFirstImagePath(mImageFloders.get(0).getFirstImagePath());
                } else {
                    all.setFirstImagePath("");
                }
                all.setCount(totalCount);
                mImageFloders.add(0, all);

                mCursor.close();
                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;
                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);
            }
        }).start();

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            // 为View绑定数据
            data2View();
            // 初始化展示文件夹的popupWindw
            initListDirPopupWindw();
        }
    };

    @Override
    public void selected(ImageFloder floder) {
        if (mImgs != null) {
            mImgs.clear();
        }
        if (floder.getDir().equals("all")) {
            mImgs = sortAllPicture();
            mAdapter.replaceAll(mImgs);
        } else {
            mImgDir = new File(floder.getDir());
            maxImgsPath = mImgDir.getAbsolutePath();
            mImgs = sort(mImgDir);
            mAdapter.replaceAll(mImgs);
        }
        mImageCount.setText(floder.getCount() + "张");
        mChooseDir.setText(floder.getName());
        mListImageDirPopupWindow.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoWallType.CLIP.getType() && data != null) {
            Bundle bundle = data.getExtras();
            byte[] datas = bundle.getByteArray(ContantsUtil.PHOTO_RESULT);
            Intent intent = new Intent();
            intent.putExtra(ContantsUtil.PHOTO_RESULT, datas);
            setResult(RESULT_OK, intent);
            finish();
        } else if (requestCode == PhotoWallType.CLIP.getType() && data == null) {
            // 如果从剪裁页面没有返回任何数据，则直接finish
            finish();
        } else if (requestCode == ContantsUtil.REQUEST_CODE_CAMERA/* && tempFile.exists()*/) {
            if (type == PhotoWallType.PICK.getType()) {
                Intent intent = new Intent();
                String[] strArr = {mCaremaPath};
                intent.putExtra(ContantsUtil.PHOTO_RESULT, strArr);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                openClipImageActivity(mCaremaPath);
            }

        }
    }

    /**
     * 打开照相
     */
    protected void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, ContantsUtil.REQUEST_CODE_CAMERA);
    }
}