package com.byth.lifesaver.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * RadioButton 带有红色圆Tag
 * 
 * @author liutao
 */
public class RadioTagButton extends RadioButton{
	private Paint paint = null;
	private boolean isShow = false;
	private int width = 0;
	private int top = 0;
	
	public RadioTagButton(Context context) {
		super(context);
	}

	public RadioTagButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		paint = new Paint();  
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setDither(true);
        
        Drawable[] drawable = getCompoundDrawables();
        if(drawable[1] != null)
        	width = drawable[1].getMinimumWidth()/2;
        
        top = (int) (context.getResources().getDisplayMetrics().density * 8);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if(isShow) {
			int w = getWidth()/2+width;
			int radius = (getWidth()-w)/6;
			canvas.drawCircle(w+radius/2, top, radius, paint);
		}
	}
	
	public void showTag(boolean isShow) {
		this.isShow = isShow;
		invalidate();
	}
	
}