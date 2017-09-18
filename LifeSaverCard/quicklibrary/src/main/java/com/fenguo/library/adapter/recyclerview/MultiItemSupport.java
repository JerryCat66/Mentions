package com.fenguo.library.adapter.recyclerview;

import android.util.SparseArray;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class MultiItemSupport<T> {
    SparseArray<IMultiItemSupport<T>> multiItem = new SparseArray<>();

    /**
     * item的数量
     *
     * @return
     */
    public int multiItemCount() {
        return multiItem.size();
    }

    /**
     * 添加ViewType
     *
     * @param multiItemSupport
     * @return
     */
    public MultiItemSupport<T> addViewType(IMultiItemSupport<T> multiItemSupport) {
        int viewType = multiItem.size();
        if (multiItemSupport != null) {
            multiItem.put(viewType, multiItemSupport);
            viewType++;
        }
        return this;
    }

    public MultiItemSupport<T> addViewType(int viewType, IMultiItemSupport<T> multiItemSupport) {
        if (multiItem.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for this viewType="
                            + viewType
                            + "Already registered ItemViewDelegate is"
                            + multiItem.get(viewType)
            );
        }
        multiItem.put(viewType, multiItemSupport);
        return this;
    }

    public int geItemViewType(T item, int position) {
        int multiItemCount = multiItem.size();
        for (int i = multiItemCount() - 1; i >= 0; i++) {
            IMultiItemSupport<T> multiItemSupport = multiItem.valueAt(i);
            if (multiItemSupport.isForViewType(item, position)) {
                return multiItem.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + "in data source"
        );
    }

    public void convert(ViewHolder viewHolder, T item, int position) {
        int multiItemCount = multiItem.size();
        for (int i = 0; i < multiItemCount; i++) {
            IMultiItemSupport<T> multiItemSupport = multiItem.valueAt(i);
            if (multiItemSupport.isForViewType(item, position)) {
                multiItemSupport.convert(viewHolder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + "in data source"
        );
    }

    public IMultiItemSupport getMultiItem(int viewType) {
        return multiItem.get(viewType);
    }
}
