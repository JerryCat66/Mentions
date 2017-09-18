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
 * Created by Administrator on 2017/6/6 0006.
 * 卡片fragment适配器
 */

public class CardGridAdapter extends BaseAdapter {
    private String[] titles = {"购买生命保卡", "激活生命保卡", "生命宝卡续费", "生命宝卡挂失", "生命宝卡补办"};
    private String[] titlesEnglish = {"Buying cards", "Active card", "Renew the card", "Report the card", "Card reissue"};
    private int[] images = {R.drawable.card_icon_buy, R.drawable.card_icon_active, R.drawable.card_icon_renew
            , R.drawable.card_icon_report_for_loss, R.drawable.card_icon_issue};
    private Context context;

    public CardGridAdapter(Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_card_fragment, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.position = position;
        viewHolder.tvContent.setText(titles[position]);
        viewHolder.tvContentEnglish.setText(titlesEnglish[position]);
        viewHolder.ivImageBtn.setImageResource(images[position]);
        return convertView;
    }

    public class ViewHolder {
        private ImageView ivImageBtn;
        private TextView tvContent;
        private TextView tvContentEnglish;
        private int position;

        public ViewHolder(View view) {
            ivImageBtn = (ImageView) view.findViewById(R.id.ig_item_image);
            tvContent = (TextView) view.findViewById(R.id.tv_item_name);
            tvContentEnglish = (TextView) view.findViewById(R.id.tv_item_name_english);
        }
    }
}
