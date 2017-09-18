package com.fenguo.library.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11 0011.
 * 通用适配器
 */

public abstract class UniversalAdapter<T> extends MultiItemAdapter<T> {
    protected Context mContext;
    protected LayoutInflater inflater;
    protected List<T> mData;
    protected int layoutId;

    public UniversalAdapter(Context context, final int layoutId, List<T> data) {
        super(context, data);
        this.mContext = context;
        this.layoutId = layoutId;
        this.mData = data;
        inflater = LayoutInflater.from(context);
        addMultiItem(new IMultiItemSupport<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder viewHolder, T t, int position) {
                UniversalAdapter.this.convert(viewHolder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder viewHolder, T t, int position);
}
