package com.fenguo.library.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11 0011.
 * recyclerView适配器
 */

public class MultiItemAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List<T> mData;

    protected OnItemClickListener mOnItemClickListener;
    protected MultiItemSupport multiItemSupport;

    public MultiItemAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mData = data;
        multiItemSupport = new MultiItemSupport();
    }

    /**
     * 创建viewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IMultiItemSupport iMultiItemSupport = multiItemSupport.getMultiItem(viewType);
        int layoutId = iMultiItemSupport.getItemViewLayoutId();
        ViewHolder viewHolder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        setListener(parent, viewHolder, viewType);
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public MultiItemAdapter addMultiItem(IMultiItemSupport<T> iMultiItemSupport) {
        multiItemSupport.addViewType(iMultiItemSupport);
        return this;
    }

    public MultiItemAdapter addMultiItem(int viewType, IMultiItemSupport<T> iMultiItemSupport) {
        multiItemSupport.addViewType(viewType, iMultiItemSupport);
        return this;
    }

    public List<T> getData() {
        return mData;
    }

    /**
     * 几种加载方式
     * addAll();
     * add();
     * replaceAll();
     *
     * @param elem
     */
    public void addAll(List<T> elem) {
        mData.addAll(elem);
        notifyDataSetChanged();
    }

    public void add(T elem) {
        mData.add(elem);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        mData.clear();
        mData.addAll(elem);
        notifyDataSetChanged();
    }

    public void convert(ViewHolder viewHolder, T t) {
        multiItemSupport.convert(viewHolder, t, viewHolder.getAdapterPosition());
    }

    protected boolean isEnable(int viewType) {
        return true;
    }

    /**
     * 设置监听
     *
     * @param parent
     * @param viewHolder
     * @param viewType
     */
    protected void setListener(ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnable(viewType)) {
            return;
        }
        //普通点击事件
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.OnItemClick(view, viewHolder, position);
                }
            }
        });
        //长按点击
        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.OnItemLongClick(view, viewHolder, position);
                }
                return false;
            }
        });
    }

    //item点击接口
    public interface OnItemClickListener {
        void OnItemClick(View view, RecyclerView.ViewHolder viewHolder, int position);//点击事件

        void OnItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int position);//长按事件
    }

    //接口回調
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
