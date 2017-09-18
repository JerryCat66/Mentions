package com.byth.lifesaver.function.card.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.base.FrameActivity;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/6/14 0014.
 * 挂失
 */

public class ActivityReportForLoss extends FrameActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.ivBtnForLoss)
    ImageView ivBtnForLoss;

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_report_for_loss);
        setToolbar(mToolbar);
        ImmersionBar.with(this)
                .statusBarColor(R.color.color_main)
                .fitsSystemWindows(true)
                .init();
    }

    @Override
    protected void initListener() {
        ivBtnForLoss.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBtnForLoss:
                telPhone(getResources().getString(R.string.hot_line_of_company));
                break;
        }
    }
}
