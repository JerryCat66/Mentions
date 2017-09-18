package com.fenguo.library.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fenguo.library.R;
import com.fenguo.library.clipImage.ClipImageLayout;
import com.fenguo.library.util.ContantsUtil;

import java.io.ByteArrayOutputStream;

/**
 * 图片剪裁
 *
 * @author Lee
 * @createDate 2015年5月25日
 */
public class ClipImageActivity extends AppCompatActivity {

    private ClipImageLayout mClipImageLayout;
    private Toolbar mToolbar;

    /**
     * @Fields url : 图片的URL
     */
    private String url;
    private String mCaremaPath;

    private boolean canScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_image);
        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
        receiveFromParent();
        initComponent();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_save) {
            Bitmap bitmap = mClipImageLayout.clip();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] datas = baos.toByteArray();
            Intent intent = new Intent();
            intent.putExtra(ContantsUtil.PHOTO_RESULT, datas);
            setResult(RESULT_OK, intent);
            finish();
            bitmap.recycle();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected void receiveFromParent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = bundle.getString("url");
            canScale = bundle.getBoolean(ContantsUtil.PHOTO_CLIP_SCALE);
            mCaremaPath = bundle.getString(ContantsUtil.PHOTO_CAREMA_PATH);
            mClipImageLayout.init(canScale, url, mCaremaPath);
        }
    }

    protected void initComponent() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("剪裁");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    /*
     * 重写onBackPressed事件
     * 
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra(ContantsUtil.PHOTO_RESULT, "");
        setResult(RESULT_OK, intent);
        finish();
    }

    public interface ClipImageResult {
        void setOnClipImage(byte[] datas);
    }
}
