package com.byth.lifesaver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.byth.lifesaver.R;

/**
 * Created by Administrator on 2017/6/5 0005.
 * radioButton适配器
 */

public class RadioButtonAdapter extends BaseAdapter {
    private Context mContext;
    private String[] num_year;
    private int status;
    private int index = -1;

    public RadioButtonAdapter(Context context, int status) {
        this.status = status;
        this.mContext = context;
        if (status == 0) {
            num_year = context.getResources().getStringArray(R.array.num_year);
        } else {
            num_year = context.getResources().getStringArray(R.array.blood_type);
        }

    }

    @Override
    public int getCount() {
        return num_year.length;
    }

    @Override
    public Object getItem(int position) {
        return num_year[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_radio_button, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.rbYear.setChecked(false);
        viewHolder.position = position;
        String[] temp = num_year[position].split(";");
        viewHolder.rbYear.setText(temp[0]);
        viewHolder.year = temp[0];
        if (index == position) {
            viewHolder.rbYear.setChecked(true);
        } else {
            viewHolder.rbYear.setChecked(false);
        }
        return convertView;
    }

    public class ViewHolder {
        public RadioButton rbYear;
        public String year;
        private int position;

        public ViewHolder(View view) {
            rbYear = (RadioButton) view.findViewById(R.id.rb_num);
            rbYear.setOnClickListener(onClickListener);
            rbYear.setChecked(false);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbYear.isChecked()) {
                    index = position;
                    notifyDataSetChanged();
                }
                listener.onClick(RadioButtonAdapter.this, year);
            }
        };
    }

    private IRadioNumClickListener listener;

    public interface IRadioNumClickListener {
        void onClick(RadioButtonAdapter context, String numYear);
    }

    public void registerOnClick(IRadioNumClickListener listener) {
        this.listener = listener;
    }
}