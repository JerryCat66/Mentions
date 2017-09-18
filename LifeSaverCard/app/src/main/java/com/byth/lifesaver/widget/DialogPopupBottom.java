package com.byth.lifesaver.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.byth.lifesaver.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15 0015.
 * 底部弹出框
 */

public class DialogPopupBottom {
    private Context mContext;
    private Dialog mDialog;
    private TextView tvTitle;//标题
    private TextView tvCancel;//取消
    private LinearLayout llContent;//内容
    private ScrollView svContent;
    private boolean showTitle;//是否展示标题
    private List<SheetItem> sheetItemList;//弹出条目
    private Display display;

    public DialogPopupBottom(Context context) {
        this.mContext = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    /**
     * 构造弹出框
     */
    public DialogPopupBottom builder() {
        //获取dialog布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_bottom_popup, null);
        //设置dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());

        //获取自定义dialog布局中的控件
        svContent = (ScrollView) view.findViewById(R.id.sLayout_content);
        llContent = (LinearLayout) view.findViewById(R.id.lLayout_content);
        tvTitle = (TextView) view.findViewById(R.id.txt_title);
        tvCancel = (TextView) view.findViewById(R.id.txt_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();//点击取消，dialog消失
            }
        });

        //定义dialog布局和参数
        mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        mDialog.setContentView(view);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }

    /**
     * 添加标题
     *
     * @param title
     * @return
     */
    public DialogPopupBottom setTitle(String title) {
        showTitle = true;
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        return this;
    }

    /**
     * 是否点击删除
     *
     * @param cancel
     * @return
     */
    public DialogPopupBottom setCancelable(boolean cancel) {
        mDialog.setCancelable(cancel);
        return this;
    }

    /**
     * 点击外面是否关闭dialog
     *
     * @param cancel
     * @return
     */
    public DialogPopupBottom setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 条目名称,条目字体颜色，设置null则为默认颜色blue
     *
     * @param name
     * @param color
     * @param itemClickListener
     * @return
     */
    public DialogPopupBottom addSheetItem(String name, SheetItemColor color
            , OnSheetItemClickListener itemClickListener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        sheetItemList.add(new SheetItem(name, color, itemClickListener));
        return this;
    }

    /**
     * 显示dialog,需要配置
     */
    public void show() {
        setSheetItems();
        mDialog.show();
    }

    /**
     * 配置条目
     */
    private void setSheetItems() {
        if (sheetItemList.size() <= 0 || sheetItemList == null) {
            return;
        }
        int size = sheetItemList.size();
        //如果添加的条目大于7条,控制一下高度 ps:超过7条或者是更多条目,用个毛的弹出框啊
        if (size >= 7) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) svContent.getLayoutParams();
            params.height = display.getHeight() / 2;
            svContent.setLayoutParams(params);
        }
        //循环添加条目
        for (int i = 1; i <= size; i++) {
            final int index = i;
            SheetItem sheetItem = sheetItemList.get(i - 1);
            String strItem = sheetItem.name;
            SheetItemColor color = sheetItem.color;
            final OnSheetItemClickListener listener = (OnSheetItemClickListener) sheetItem.itemClickListener;

            TextView textView = new TextView(mContext);
            textView.setText(strItem);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);

            //背景图片,top、middle、bottom的背景都不一样，具体设置如下
            if (size == 1) {
                if (showTitle) {
                    textView.setBackgroundResource(R.drawable.action_sheet_bottom_selector);
                } else {
                    textView.setBackgroundResource(R.drawable.action_sheet_single_selector);
                }
            } else {
                if (showTitle) {
                    if (i >= 1 && i < size) {
                        textView.setBackgroundResource(R.drawable.action_sheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.action_sheet_bottom_selector);
                    }
                } else {
                    if (i == 1) {
                        textView.setBackgroundResource(R.drawable.action_sheet_top_selector);
                    } else if (i < size) {
                        textView.setBackgroundResource(R.drawable.action_sheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.action_sheet_bottom_selector);
                    }
                }
            }

            // 字体颜色
            if (color == null) {
                textView.setTextColor(Color.parseColor(SheetItemColor.Blue
                        .getName()));
            } else {
                textView.setTextColor(Color.parseColor(color.getName()));
            }

            // 高度
            float scale = mContext.getResources().getDisplayMetrics().density;
            int height = (int) (45 * scale + 0.5f);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, height));

            // 点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(index);
                    mDialog.dismiss();
                }
            });

            llContent.addView(textView);
        }
    }

    /**
     * 条目bean
     */
    public class SheetItem {
        String name;
        OnSheetItemClickListener itemClickListener;
        SheetItemColor color;

        public SheetItem(String name, SheetItemColor color, OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }
    }

    /**
     * 枚举条目颜色,目前只设置红色和蓝色，需要的可以自己加
     */
    public enum SheetItemColor {
        Blue("#037BFF"), Red("#FD4A2E");

        private String name;

        private SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 设置点击监听
     */
    public interface OnSheetItemClickListener {
        void onClick(int which);
    }
}
