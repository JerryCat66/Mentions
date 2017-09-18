package com.fenguo.library.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.fenguo.library.R;

/**
 * Created by ewhale on 2015/6/15.
 */
public class BaseDialog extends Dialog {
    private DisplayMetrics metrics;

    public BaseDialog(Context context) {
        super(context, R.style.TransparentDialog);
        setCanceledOnTouchOutside(false);
        metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        setDialogWidth();
    }

    public int getWidth() {
        return metrics.widthPixels;
    }

    public int getHeight() {
        return metrics.heightPixels;
    }

    private void setDialogWidth() {
        View view = findViewById(android.R.id.content);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        // params.width = (int) (getWidth() * 0.7);
        params.height = (int) (getHeight() * 0.5);
        view.setLayoutParams(params);
    }

    protected void setDialogWidth(double width) {
        View view = findViewById(android.R.id.content);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (int) (getWidth() * width);
        view.setLayoutParams(params);
    }

}
