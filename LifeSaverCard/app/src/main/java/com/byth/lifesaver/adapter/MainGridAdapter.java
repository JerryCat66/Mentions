package com.byth.lifesaver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.byth.lifesaver.R;

/**
 * Created by Administrator on 2017/6/13 0013.
 * 首页grid适配器
 */

public class MainGridAdapter extends BaseAdapter {
    private String[] titles = {"购卡", "激活", "卡续费", "我的订单", "账户管理", "消息提醒"};
    private int[] images = {R.drawable.main_icon_buy, R.drawable.main_icon_active, R.drawable.main_icon_renew
            , R.drawable.main_icon_order, R.drawable.main_icon_manager, R.drawable.main_icon_notice};
    private Context context;

    public MainGridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_main_grid, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.position = position;
        viewHolder.tvContent.setText(titles[position]);
        viewHolder.ivImageBtn.setImageResource(images[position]);
        return convertView;
    }

    public class ViewHolder {
        private ImageView ivImageBtn;
        private TextView tvContent;
        private int position;

        public ViewHolder(View view) {
            ivImageBtn = (ImageView) view.findViewById(R.id.ivMainGrid);
            tvContent = (TextView) view.findViewById(R.id.tvMainGrid);
        }
    }
}
