package com.byth.lifesaver.function.auth;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.byth.lifesaver.R;
import com.byth.lifesaver.base.FrameActivity;
import com.fenguo.library.view.RoundImageViewWithBorder;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/7/21 0021.
 * 微信和生命宝账号联合登录
 */

public class UnionLoginActivity extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.avatar)
    RoundImageViewWithBorder avatar;
    @InjectView(R.id.quickRegister)
    AppCompatButton quickRegister;
    @InjectView(R.id.unionImmediately)
    AppCompatButton unionImmediately;
    private String headImg;

    @Override
    protected void receiveDataFromPreActivity() {
        headImg = getIntent().getStringExtra("headImg");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_union_login);
        setToolbar(toolbar);
        avatar.setImageResource(R.drawable.avatar_default);
        Glide.with(UnionLoginActivity.this)
                .load(headImg)
                .asBitmap()
                .placeholder(R.drawable.avatar_default)
                .error(R.drawable.avatar_default)
                .into(new BitmapImageViewTarget(avatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        avatar.setImageBitmap(resource, Color.WHITE, 20, true);
                    }
                });
    }

    @Override
    protected void initListener() {
        quickRegister.setOnClickListener(this);
        unionImmediately.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quickRegister:
                openActivityNotClose(QuickRegisterActivity.class, null);
                break;
            case R.id.unionImmediately:
                openActivityNotClose(LoginBindingActivity.class, null);
                break;
        }
    }
}
