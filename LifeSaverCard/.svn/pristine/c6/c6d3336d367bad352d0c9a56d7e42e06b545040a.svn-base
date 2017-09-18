package com.fenguo.library.adapter.recyclerview;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public interface IMultiItemSupport<T> {
    int getItemViewLayoutId();//根据itemView告知是哪一个layout

    boolean isForViewType(T item, int position);//是否针对某一个view的类型

    void convert(ViewHolder viewHolder, T t, int position);//复写convert方法
}
