package com.fenguo.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenguo.library.R;

/**
 * 自定义xml属性:
 * menuTitle：设置LineMenuView标题文字，默认为空
 * menuImage：设置LineMenuView首部的图标，默认不显示，设置该属性后显示，设置时传图片资源
 * arrowImage：设置LineMenuView尾部箭头的图标，默认显示，设置时传图片资源
 * menuTitleColor：设置LineMenuView标题文字的颜色，默认为#333333
 * menuTitleSize：设置LineMenuView标题文字的字体大小，默认为12sp
 * withTop：是否显示LineMenuView顶部的横线，默认为显示。LineMenuView底部的横线默认显示，顶部的横线可
 * 控制显示，已达到列表效果。
 * paddingTitle：设置首部图标与标题文字之间的间距，默认为6dp
 * showArrow:是否显示箭头图标。默认为true,显示箭头图标。
 *
 * @author zhangdm
 * @createDate 2015年6月12日
 */
public class LineMenuView extends LinearLayout {

    public String TAG = "LineMenuView";

    private View mTopLine;
    private View mBottomLine;
    private ImageView mMenuImage;
    private TextView mMenuTitle;
    private ImageView mMenuArrow;
    private ImageView mMenuTip;

    private String mTitle;//保存标题的文字
    private Drawable mImage;//保存首部图标
    private Drawable mArrowImage;//保存尾部箭头的图标
    private int mTitleColor;//保存标题文字的颜色
    private int mTitleSize;//保存标题文字的字体大小
    private boolean withTop;//是否显示顶部的横线
    private int paddingTitle;//首部图标和标题文字的间距
    private boolean showArrow;//是否显示尾部箭头图标

    public LineMenuView(Context context) {
        this(context, null);
    }

    public LineMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public LineMenuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_line_menu, this, true);
        initView(context, attrs);
        setView();
    }

    protected void initView(Context context, AttributeSet attrs) {
        //初始化控件
        mTopLine = (View) findViewById(R.id.top_line);
        mBottomLine = (View) findViewById(R.id.bottom_line);
        mMenuTitle = (TextView) findViewById(R.id.menu_title);
        mMenuImage = (ImageView) findViewById(R.id.menu_image);
        mMenuArrow = (ImageView) findViewById(R.id.menu_arrow);
        mMenuTip = (ImageView) findViewById(R.id.menu_tip);
        //获取自定义的属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineMenu);
        try {
            mTitle = array.getString(R.styleable.LineMenu_menuTitle);
            mImage = array.getDrawable(R.styleable.LineMenu_menuImage);
            mArrowImage = array.getDrawable(R.styleable.LineMenu_arrowImage);
            mTitleColor = array.getColor(R.styleable.LineMenu_menuTitleColor,
                    Color.parseColor("#666666"));
//            mTitleSize = array.getDimensionPixelOffset(R.styleable.LineMenu_menuTitleSize, 12);
            mTitleSize = (int) array.getDimension(R.styleable.LineMenu_menuTitleSize, 15);

//            mTitleSize = (int) TypedValue.applyDimension(
//                    TypedValue.COMPLEX_UNIT_SP, R.styleable.LineMenu_menuTitleSize, getResources().getDisplayMetrics());
            withTop = array.getBoolean(R.styleable.LineMenu_withTop, true);
            paddingTitle = array.getDimensionPixelOffset(R.styleable.LineMenu_paddingTitle, 6);
            showArrow = array.getBoolean(R.styleable.LineMenu_showArrow, true);

        } finally {
            array.recycle();
        }
    }

    /**
     * 通过自定义属性中的值设置各控件中的属性
     */
    protected void setView() {
        if (null != mTitle) {
            mMenuTitle.setText(mTitle);
        }
        if (null != mImage) {
            mMenuImage.setImageDrawable(mImage);
            mMenuImage.setVisibility(View.VISIBLE);
        }
        mMenuTitle.setTextColor(mTitleColor);
        mMenuTitle.setTextSize(mTitleSize);
        mMenuTitle.setPadding(paddingTitle, mMenuTitle.getPaddingTop(),
                mMenuTitle.getPaddingRight(), mMenuTitle.getPaddingBottom());
        if (withTop) {
            mTopLine.setVisibility(View.VISIBLE);
        }
        if (showArrow) {
            mMenuArrow.setVisibility(View.VISIBLE);
            if (null != mArrowImage) {
                mMenuArrow.setImageDrawable(mArrowImage);
            }
        } else {
            mMenuArrow.setVisibility(View.GONE);
        }
    }

    /**
     * 显示提示用的小红点
     */
    public void showTip() {
        mMenuTip.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏提示用的小红点
     */
    public void hideTip() {
        mMenuTip.setVisibility((View.GONE));
    }

    /**
     * 修改标题文字
     *
     * @param title 标题内容
     */
    public void setMenuTitle(String title) {
        mMenuTitle.setText(title);
    }

    /**
     * 修改标题文字的颜色
     *
     * @param color 标题内容的颜色值
     */
    public void setMenuTitleColor(int color) {
        mMenuTitle.setTextColor(color);
    }

    /**
     * 修改标题文字的字体大小
     *
     * @param size 标题文字的字体大小
     */
    public void setMenuTitleTextSize(int size) {
        mMenuTitle.setTextSize(size);
    }

    /**
     * 修改标题文字与首部图标的间距
     *
     * @param paddingTitle 间距值
     */
    public void setPaddingTitle(int paddingTitle) {
        mMenuTitle.setPadding(paddingTitle, mMenuTitle.getPaddingTop(),
                mMenuTitle.getPaddingRight(), mMenuTitle.getPaddingBottom());
    }

    /**
     * 修改首部图标的图片
     *
     * @param drawable 图片
     */
    public void setMenuImage(Drawable drawable) {
        mMenuImage.setImageDrawable(drawable);
        if (mMenuImage.getVisibility() != View.VISIBLE) {
            mMenuImage.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 修改尾部箭头图标的图案
     *
     * @param drawable 图片
     */
    public void setArrowImage(Drawable drawable) {
        mMenuArrow.setImageDrawable(drawable);
        if (mMenuArrow.getVisibility() != View.VISIBLE) {
            mMenuArrow.setVisibility(View.VISIBLE);
        }
    }


}
