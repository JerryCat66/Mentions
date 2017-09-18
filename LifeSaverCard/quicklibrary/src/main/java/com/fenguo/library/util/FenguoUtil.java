package com.fenguo.library.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fenguo.library.R;
import com.fenguo.library.activity.DisplayPhotoActivity;
import com.fenguo.library.activity.EditContentActivity;
import com.fenguo.library.activity.PhotoWallActivity;
import com.fenguo.library.activity.WebViewActivity;
import com.fenguo.library.photowall.PhotoWallType;
import com.fenguo.library.view.VerticalPopupWindowView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FenguoUtil {

    /**
     * 需要重写onActivityResult方法<br/>
     * 图片剪裁<br/>
     * Bundle bundle = data.getExtras();<br/>
     * byte[] datas = bundle.getByteArray(ContantsUtil.PHOTO_RESULT);<br/>
     * 图片选择<br/>
     * String[] str = data.getStringArrayExtra(ContantsUtil.PHOTO_RESULT);str存储的是文件路径
     *
     * @param activity
     * @param cameraPath  照片保存的位置,SdCardUtil.getCaremaPath(this)
     * @param count       可以选择的图片张数
     * @param type        是否需要剪裁（当选择的图片张数为1时，需要设置）,PhotoWallType
     * @param scale       是否需要缩放（当选择剪裁时，需要设置）,
     * @param requestCode 设定文件
     * @return 返回类型
     * @throws
     * @Title: openPhotoWallActivity
     * @Description: 打开图片墙
     */
    public static void openPhotoWallActivity(Activity activity, String cameraPath, int count,
                                             int type, boolean scale, int requestCode) {
        Intent intent = new Intent(activity, PhotoWallActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ContantsUtil.PHOTO_CAREMA_PATH, cameraPath);
        bundle.putInt(ContantsUtil.MAX_PHOTO_COUNT, count);
        bundle.putInt(ContantsUtil.PHOTO_TYPE, type);
        bundle.putBoolean(ContantsUtil.PHOTO_CLIP_SCALE, scale);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开图片墙，不需要传递cameraPath
     *
     * @param activity
     * @param count
     * @param type
     * @param scale
     * @param requestCode
     */
    public static void openPhotoWallActivity(Activity activity, int count, PhotoWallType type,
                                             boolean scale, int requestCode) {
        Intent intent = new Intent(activity, PhotoWallActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ContantsUtil.PHOTO_CAREMA_PATH, SdCardUtil.getCaremaPath(activity));
        bundle.putInt(ContantsUtil.MAX_PHOTO_COUNT, count);
        bundle.putInt(ContantsUtil.PHOTO_TYPE, type.getType());
        bundle.putBoolean(ContantsUtil.PHOTO_CLIP_SCALE, scale);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 需要重写onActivityResult方法<br/>
     * Bundle bundle = data.getExtras();<br/>
     * String content = bundle.getString("content");
     *
     * @param activity
     * @param title       标题
     * @param lenght      最多可编辑的汉字长度
     * @param content     可编辑的文本内容
     * @param hint        提示内容
     * @param requestCode 设定文件
     * @return void 返回类型
     * @throws
     * @Title: openEditContentActivity
     * @Description: 可编辑文本、提醒剩余字数的activity
     */
    public static void openEditContentActivity(Activity activity, String title, int lenght,
                                               String content, String hint, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("maxLength", lenght);
        bundle.putString("content", content);
        bundle.putString("hint", hint);
        Intent intent = new Intent(activity, EditContentActivity.class);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * url和content只填写一个即可
     *
     * @param context
     * @param title   标题
     * @param url     跳转的url
     * @param content html内容
     * @return void    返回类型
     * @throws
     * @Title: openWebViewActivity
     * @Description: 跳转进入webview
     */
    public static void openWebViewActivity(Context context, String title, String url,
                                           String content) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        bundle.putString("content", content);
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * @param context
     * @param width
     * @param data
     * @param listener
     * @return
     */
    public static PopupWindow getVerticalPopupWindow(Context context, int width, List<String> data, VerticalPopupWindowView.OnSelectedListener listener) {
        VerticalPopupWindowView view = new VerticalPopupWindowView(context);
        view.setPopupWindowWidth(DisplayUtil.dip2px(context, width));
        view.setOnPopupWindowSelectedListener(listener);
        return view.getPopupWindow(data);
    }

    /**
     * 跳转到图片显示界面的基础方法
     *
     * @param activity
     * @param onlyOne    是否只显示一张图片
     * @param urls       图片组的路径数组
     * @param position   查看图片组中初始显示的位置，若onlyOne为true,则可不传
     * @param defaultPic 默认的显示图片
     */
    private static void openDisplayPhotoActivity(Activity activity, boolean onlyOne, String[] urls, int position, int defaultPic) {
        Bundle mBundle = new Bundle();
        mBundle.putBoolean("onlyOne", onlyOne);
        mBundle.putStringArray("imagePaths", urls);
        mBundle.putInt("position", position);
        mBundle.putInt("defaultPic", defaultPic);
        Intent intent = new Intent(activity, DisplayPhotoActivity.class);
        intent.putExtras(mBundle);
        activity.startActivity(intent);
    }

    /**
     * @param activity
     * @param url      查看图片的路径（可网络路径或本地路径）
     */
    public static void openDisplayPhotoActivity(Activity activity, String url) {
        openDisplayPhotoActivity(activity, true, new String[]{url}, 0, R.drawable.ic_launcher);
    }

    /**
     * @param activity
     * @param url        查看图片的路径（可网络路径或本地路径）
     * @param defaultPic 默认的图片
     */
    public static void openDisplayPhotoActivity(Activity activity, String url, int defaultPic) {
        openDisplayPhotoActivity(activity, true, new String[]{url}, 0, defaultPic);
    }

    /**
     * @param activity
     * @param photoUrls 图片的路径数组
     * @param position  查看图片组中初始显示的位置
     */
    public static void openDisplayPhotoActivity(Activity activity, String[] photoUrls, int position) {
        openDisplayPhotoActivity(activity, false, photoUrls, position, R.drawable.ic_launcher);
    }

    /**
     * @param activity
     * @param photoUrls  图片的路径数组
     * @param position   查看图片组中初始显示的位置
     * @param defaultPic 默认的图片
     */
    public static void openDisplayPhotoActivity(Activity activity, String[] photoUrls, int position, int defaultPic) {
        openDisplayPhotoActivity(activity, false, photoUrls, position, defaultPic);
    }

    /**
     * 打开选择日期对话框
     *
     * @param context
     * @param dateStr
     * @param textView
     */
    public static void openDatePickerDialog(Context context, String dateStr, final TextView textView) {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String month;
                String day;
                if ((monthOfYear + 1) < 10) {
                    month = "0" + (monthOfYear + 1);
                } else {
                    month = String.valueOf((monthOfYear + 1));
                }
                if (dayOfMonth < 10) {
                    day = "0" + dayOfMonth;
                } else {
                    day = String.valueOf(dayOfMonth);
                }
                textView.setText(year + "-" + month + "-" + day);
//                Date d = DateUtil.parseToDate(textView.getText().toString(), DateUtil.yyyyMMdd);
//                LogUtil.i("msg", "time1---" + d.getTime());
//                LogUtil.i("msg", "time1---" + DateUtil.parseToString(d.getTime(), DateUtil.yyyyMMddHHmm));
//                Calendar c = Calendar.getInstance();
//                c.setTime(d);
//                LogUtil.i("msg", "time2---" + c.getTimeInMillis());
//                LogUtil.i("msg", "time2---" + DateUtil.parseToString(c.getTimeInMillis(), DateUtil.yyyyMMddHHmm));
            }

        };
        Calendar calendar = Calendar.getInstance();
        Date date;
        if (dateStr == null) {
            date = new Date(System.currentTimeMillis());
        } else {
            date = DateUtil.parseToDate(dateStr, DateUtil.yyyyMMdd);
        }
        calendar.setTime(date);
        DatePickerDialog dialog = new DatePickerDialog(context, onDateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)) {
            @Override
            protected void onStop() {
            }
        };
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();

    }

    /**
     * @param context
     * @param dateStr
     * @param textView
     */
    public static void openTimePickerDialog(Context context, String dateStr, final TextView textView) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                textView.setText(hourOfDay + ":" + minute);
            }
        };
        Calendar calendar = Calendar.getInstance();
        Date date;
        if (dateStr == null) {
            date = new Date(System.currentTimeMillis());
        } else {
            date = DateUtil.parseToDate(dateStr, DateUtil.HHmm);
        }
        calendar.setTime(date);
        new TimePickerDialog(context, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true) {
            @Override
            protected void onStop() {
            }
        }.show();
    }

}
