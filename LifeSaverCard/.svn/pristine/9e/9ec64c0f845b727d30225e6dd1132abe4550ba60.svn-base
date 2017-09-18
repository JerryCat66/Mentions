package com.byth.lifesaver.util;

import android.view.View;

/**
 * Created by Administrator on 2017/8/22 0022.
 * 防止按钮多次点击-------防重复
 */

public abstract class OnSingleClickListener implements View.OnClickListener {
    private long preTime;
    private int delaySecond = 500;//两次点击的间隔为500毫秒,可自定义

    public OnSingleClickListener(int delaySecond) {
        super();
        this.delaySecond = delaySecond;
    }

    @Override
    public void onClick(View v) {
        if (!isDoubleClick()) {
            onSingleClick(v);
        }
    }

    /**
     * 外部复写
     *
     * @param view
     */
    protected abstract void onSingleClick(View view);

    /**
     * 判断前后点击的时间是否小于500毫秒
     *
     * @return
     */
    private boolean isDoubleClick() {
        long lastTime = System.currentTimeMillis();
        boolean flag = lastTime - preTime < delaySecond ? true : false;
        preTime = lastTime;
        return flag;
    }
}
