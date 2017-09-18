package com.byth.lifesaver.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class MyWebView extends WebView {
    boolean allowDrawBottom = true;//true则允许拖动到下一页
    float downY = 0;
    boolean needConsumeTouch = true;// 是否需要承包touch事件，needConsumeTouch一旦被定性，则不会更改

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean dispatchKeyEvent(MotionEvent mv) {
        if (mv.getAction() == MotionEvent.ACTION_DOWN) {
            downY = mv.getRawY();
            needConsumeTouch = true;// 默认情况下，listView内部的滚动优先，默认情况下由该listView去消费touch事件
            allowDrawBottom = isAtBottom();
        } else if (mv.getAction() == MotionEvent.ACTION_MOVE) {
            // 在最顶端且向上拉了，则这个touch事件交给父类去处理
            if (!needConsumeTouch) {
                getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            } else if (allowDrawBottom) {
                // needConsumeTouch尚未被定性，此处给其定性
                // 允许拖动到底部的下一页，而且又向上拖动了，就将touch事件交给父view
                if (mv.getRawY() - downY > 2) {
                    // flag设置，由父类去消费
                    needConsumeTouch = false;
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            }
        }
        // 通知父view是否要处理touch事件
        getParent().requestDisallowInterceptTouchEvent(needConsumeTouch);
        return super.dispatchTouchEvent(mv);
    }

    /**
     * 判断list是否到达底部
     *
     * @return
     */
    private boolean isAtBottom()

    {
        return getScrollY() == 0;
    }
}
