package com.fenguo.library.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by linanjs on 2017/3/13.
 * recyclerView的ViewHolder
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mView;
    private View mConvertView;
    private Context mContext;

    public ViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mView = new SparseArray<View>();
    }

    /**
     * 两种构造viewHolder方法
     *
     * @param context
     * @param itemView
     * @return
     */
    public static ViewHolder createViewHolder(Context context, View itemView) {
        ViewHolder holder = new ViewHolder(context, itemView);
        return holder;
    }

    public static ViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder holder = new ViewHolder(context, itemView);
        return holder;
    }

    /**
     * 根据viewId得到控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mView.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mView.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }


    /**
     * 设置viewHolder包含控件的方法
     * eg：设置文字、设置图片、设置背景颜色等
     */
    /**
     * 设置文字
     *
     * @param viewId
     * @param s
     * @return
     */
    public ViewHolder setText(int viewId, String s) {
        TextView tv = getView(viewId);
        tv.setText(s);
        return this;
    }

    /**
     * 设置文字颜色
     *
     * @param viewId
     * @param color
     * @return
     */
    public ViewHolder setTextColor(int viewId, int color) {
        TextView tv = getView(viewId);
        tv.setTextColor(color);
        return this;
    }

    /**
     * 设置图片资源
     *
     * @param viewId
     * @param ResId
     * @return
     */
    public ViewHolder setImage(int viewId, int ResId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(ResId);
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param viewId
     * @param color
     * @return
     */
    public ViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置背景资源
     *
     * @param viewId
     * @param resourceId
     * @return
     */
    public ViewHolder setBackgroundResource(int viewId, int resourceId) {
        View view = getView(viewId);
        view.setBackgroundResource(resourceId);
        return this;
    }

    /**
     * 设置是否可见
     *
     * @param viewId
     * @param visible
     * @return
     */
    public ViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置监听
     *
     * @param viewId
     * @param onClickListener
     * @return
     */
    public ViewHolder setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        View view = getView(viewId);
        view.setOnClickListener(onClickListener);
        return this;
    }

}
