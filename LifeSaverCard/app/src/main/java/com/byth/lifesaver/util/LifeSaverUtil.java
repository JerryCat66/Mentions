package com.byth.lifesaver.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.byth.lifesaver.R;
import com.byth.lifesaver.widget.DialogWithList;

import java.util.Arrays;
import java.util.regex.Pattern;


/**
 * Created by Administrator on 2017/5/26 0026.
 * 通用工具类
 */

public class LifeSaverUtil {
    private static LifeSaverUtil util;
    private Context context;

    private LifeSaverUtil(Context context) {
        this.context = context.getApplicationContext();
    }

    public static LifeSaverUtil getInstance(Context context) {
        if (util == null) {
            util = new LifeSaverUtil(context);
        }
        return util;
    }

    /**
     * 验证是否符合密码格式
     * `
     *
     * @param content
     * @param min
     * @param max
     * @return
     */
    public boolean checkPassword(String content, int min, int max) {
        String regular = "^([0-9a-zA-Z]{" + min + "," + max + "})$";
        return Pattern.matches(regular, content);
    }

    /**
     * 打电话
     *
     * @param phone
     */
    public void telPhone(String phone) {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    /**
     * 显示选择对话框
     *
     * @param context
     * @param resId
     * @param title
     * @param listener
     */
    public void showDialogList(Context context, int resId, String title, DialogWithList.OnSelectedListener listener) {
        DialogWithList dialogWithList = new DialogWithList(context);
        dialogWithList.setTitle(title);
        dialogWithList.setOnSelectedListener(listener);
        dialogWithList.setList(Arrays.asList(context.getResources().getStringArray(resId)), R.layout.item_dialog_list);
        dialogWithList.show();
    }


}