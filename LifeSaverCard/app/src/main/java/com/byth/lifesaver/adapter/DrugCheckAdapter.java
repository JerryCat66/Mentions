package com.byth.lifesaver.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.byth.lifesaver.R;
import com.fenguo.library.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/28 0028.
 * 药物过敏史checkBox适配器
 */

public class DrugCheckAdapter extends BaseAdapter {
    private Context context;
    private String[] data;
    private List<String> remarkList = new ArrayList<>();//存放选中数据

    public DrugCheckAdapter(Context context) {
        this.context = context;
        data = context.getResources().getStringArray(R.array.drug_allergy);
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrugCheckAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_checkbox, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (DrugCheckAdapter.ViewHolder) convertView.getTag();
        }
        holder.position = position;
        holder.cbRemark.setText(data[position]);
        holder.strRemark = data[position];
        return convertView;
    }

    public class ViewHolder {
        public CheckBox cbRemark;
        private String strRemark;//其他的备注
        private int position;

        public ViewHolder(View view) {
            cbRemark = (CheckBox) view.findViewById(R.id.cb_remark);
            cbRemark.setOnCheckedChangeListener(listener);
        }

        private CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cbRemark.isChecked()) {
                    cbRemark.setTextColor(context.getResources().getColor(R.color.color_main));
                    remarkList.add(strRemark);
                } else {
                    cbRemark.setTextColor(context.getResources().getColor(R.color.color_000000));
                    remarkList.remove(strRemark);
                    notifyDataSetChanged();
                }
                getRemark();
            }
        };
    }

    /**
     * 拼接选中数据
     */
    private void getRemark() {
        StringBuffer sbRemark = new StringBuffer();
        for (int i = 0; i < remarkList.size(); i++) {
            String data = remarkList.get(i).toString();
            sbRemark.append(data.concat(","));
        }
        Log.i("msg", "getRemark: " + remarkList.toString());
        String strRemark = StringUtil.isEmpty(sbRemark.toString()) ? sbRemark.toString() : sbRemark.substring(0, sbRemark.length() - 1).toString();
        remarkClickListener.onClick(DrugCheckAdapter.this, strRemark, remarkList);
    }

    private DrugAllergyClickListener remarkClickListener;

    /**
     * 监听接口
     */
    public interface DrugAllergyClickListener {
        void onClick(DrugCheckAdapter context, String remarkStr, List<String> datas);
    }

    /**
     * 回调接口
     *
     * @param listener
     */
    public void registerOnClickRemark(DrugAllergyClickListener listener) {
        this.remarkClickListener = listener;
    }
}
