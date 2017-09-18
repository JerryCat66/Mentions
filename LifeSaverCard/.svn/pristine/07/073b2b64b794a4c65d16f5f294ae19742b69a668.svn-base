package com.fenguo.library.activity.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.fenguo.library.R;
import com.fenguo.library.util.StringUtil;
import com.fenguo.library.view.DialogLoading;

/**
 * @Description
 * @auth Lee
 * @date 2015/6/1
 */
public class BaseActivity extends AppCompatActivity {


    protected static final int SHOW_SHORT_TIME = Toast.LENGTH_SHORT;
    protected DialogLoading loading;
    protected Toast mToast = null;
    protected Fragment tempFragment;
    protected BaseActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 禁止横竖屏切换
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        context = this;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 显示一个Toast信息
     *
     * @param resId
     */
    public void showToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(this, resId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

    /**
     * 显示一个Toast信息
     *
     * @param content
     */
    public void showToast(final String content) {
        if (mToast == null) {
            mToast = Toast.makeText(BaseActivity.this, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        if (StringUtil.isEmpty(content) || content == null || content == "null") {
            return;
        }
        mToast.show();
    }

    /**
     * 打开一个新的activity，不关闭当前的activity
     *
     * @param pClass
     * @param bundle
     */
    protected void openActivityNotClose(Class<?> pClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, pClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    /**
     * 打开一个新的activity，关闭当前的activity
     *
     * @param pClass
     * @param bundle
     */
    protected void openActivity(Class<?> pClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, pClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        finish();
    }


    /**
     * 带回调的跳转
     *
     * @param bundle
     * @param requestCode
     * @param target
     */
    protected void startForResult(Bundle bundle, int requestCode, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    /**
     * 初始化toolbar
     *
     * @param toolbar
     */
    protected void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    /**
     * 用tempFragment替代当前Fragment, 并给tempFragment增加一个tag，以便下次调用，不用新建
     *
     * @param containerViewId
     * @param tempFragment
     * @param tag
     * @param isAdd           设定文件
     */
    protected void replaceFragment(int containerViewId, Fragment tempFragment, String tag,
                                   boolean isAdd) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerViewId, tempFragment, tag);
        if (!isAdd) {
            transaction.addToBackStack(tag);
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 用tempFragment替代当前Fragment, 并给tempFragment增加一个tag，以便下次调用，不用新建
     *
     * @param tag
     * @param fragment 设定文件
     */
    protected void replaceFragment(int containerViewId, String tag, Fragment fragment) {
        boolean isAdd = true;
        tempFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (tempFragment == null) {
            tempFragment = fragment;
            isAdd = false;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerViewId, tempFragment, tag);
        if (!isAdd) {
            transaction.addToBackStack(tag);
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 覆写finish方法，覆盖默认方法，加入切换动画
     */
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }


}
