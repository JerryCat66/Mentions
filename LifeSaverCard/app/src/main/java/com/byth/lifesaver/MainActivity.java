package com.byth.lifesaver;

import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.byth.lifesaver.base.FrameActivity;
import com.byth.lifesaver.function.card.CardFragment;
import com.byth.lifesaver.function.emergency.EmergencyFragment;
import com.byth.lifesaver.function.home.HomeFragment;
import com.byth.lifesaver.function.mine.MineFragment;
import com.byth.lifesaver.function.mine.activity.ActivityUserInfo;
import com.byth.lifesaver.util.LifeSaverPreference;
import com.byth.lifesaver.widget.RadioTagButton;
import com.byth.lifesaver.widget.TipsDialog;

import butterknife.InjectView;

public class MainActivity extends FrameActivity implements RadioGroup.OnCheckedChangeListener {
    @InjectView(R.id.main_radio)
    public RadioGroup rgMain;
    @InjectView(R.id.emergency)
    public RadioTagButton rtbEmergency;
    @InjectView(R.id.card)
    public RadioTagButton rtbCard;
    @InjectView(R.id.mine)
    public RadioTagButton rtbMine;
    @InjectView(R.id.toolbar)
    Toolbar mToolBar;
    @InjectView(R.id.title)
    TextView tvTitle;

    @Override
    protected void receiveDataFromPreActivity() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {
        setContentView(R.layout.activity_main);
        replaceFragment(R.id.fragment_container, HomeFragment.getInstance(), "home", false);
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
        setTitleText(0);
        tvTitle.setText("首页");
        if (preference.getString(LifeSaverPreference.OPERATION_CODE).equals("-1")) {
            TipsDialog.makeDialog(MainActivity.this, "提示", "请完善个人信息", "确定", "取消", new TipsDialog.onDialogListener() {
                @Override
                public void onOkClick() {
                    openActivityNotClose(ActivityUserInfo.class, null);
                }

                @Override
                public void onCancelClick() {

                }
            }).show();
        }
    }

    @Override
    protected void initListener() {
        rgMain.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.home:
                replaceFragment(R.id.fragment_container, "home", HomeFragment.getInstance());
                setTitleText(0);
                break;
            case R.id.emergency:
                replaceFragment(R.id.fragment_container, "emergency", EmergencyFragment.getInstance());
                setTitleText(1);
                break;
            case R.id.card:
                replaceFragment(R.id.fragment_container, "card", CardFragment.getInstance());
                setTitleText(2);
                break;
            case R.id.mine:
                replaceFragment(R.id.fragment_container, "mine", MineFragment.getInstance());
                setTitleText(3);
                break;
        }
    }

    private void setTitleText(int tab) {
        switch (tab) {
            case 0:
                mToolBar.setVisibility(View.VISIBLE);
                tvTitle.setText("首页");
                break;
            case 1:
                mToolBar.setVisibility(View.VISIBLE);
                tvTitle.setText("救援");
                break;
            case 2:
                mToolBar.setVisibility(View.VISIBLE);
                tvTitle.setText("生命宝");
                break;
            case 3:
                mToolBar.setVisibility(View.GONE);
                tvTitle.setText("我的");
                break;
        }
    }

    @Override
    public void onBackPressed() {
    }

    /**
     * 点击返回退出事件
     *
     * @param event
     * @return
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            TipsDialog.makeDialog(this, "提示", "是否确认退出", "是", "否", new TipsDialog.onDialogListener() {
                @Override
                public void onOkClick() {
                    close();
                    finish();
                }

                @Override
                public void onCancelClick() {

                }
            }).show();
        }
        return super.dispatchKeyEvent(event);
    }

}
