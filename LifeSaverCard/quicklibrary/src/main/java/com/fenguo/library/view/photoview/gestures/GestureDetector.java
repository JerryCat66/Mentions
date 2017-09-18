package com.fenguo.library.view.photoview.gestures;

/**
 * Created by ewhale on 2015/6/9.
 */
import android.view.MotionEvent;

public interface GestureDetector {

    public boolean onTouchEvent(MotionEvent ev);

    public boolean isScaling();

    public boolean isDragging();

    public void setOnGestureListener(OnGestureListener listener);

}