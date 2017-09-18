package com.fenguo.library.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.fenguo.library.R;
import com.fenguo.library.adapter.BaseAdapterHelper;
import com.fenguo.library.adapter.QuickAdapter;

import java.util.List;

/**
 * 点击右上角，弹出的对话框（根据是否有icon，需要设置两种listview）
 *
 * @author zhangyb@ifenguo.com
 * @createDate 2014年11月21日
 */
public class VerticalPopupWindowView extends PopupWindow implements OnItemClickListener {

    private ListView mListView;
    private Context context;
    private PopupWindow mPopupWindow;
    private QuickAdapter<String> adapter;
    private View popView;
    private int width = 0;
    private int height = 0;
    private int itemId = 0;
    private OnSelectedListener listener;

    public VerticalPopupWindowView(Context context) {
        this.context = context;
        popView = LayoutInflater.from(context).inflate(R.layout.view_vertical_popup, null);
        mListView = (ListView) popView.findViewById(R.id.list);
        mListView.setOnItemClickListener(this);
    }

    /**
     * @param @param  list
     * @param @return 设定文件
     * @return PopupWindow 返回类型
     * @throws
     * @Title: getPopupWindow
     * @Description:
     */
    public PopupWindow getPopupWindow(List<String> list) {
        if (itemId == 0) {
            adapter = new QuickAdapter<String>(context, R.layout.item_vertical_popup, list) {

                @Override
                protected void convert(BaseAdapterHelper helper, String item) {
                    helper.setText(R.id.content, item);
                }
            };
        } else {
            adapter = new QuickAdapter<String>(context, itemId, list) {

                @Override
                protected void convert(BaseAdapterHelper helper, String item) {
                    helper.setText(R.id.content, item);
                }
            };
        }
        mListView.setAdapter(adapter);
        // DisplayMetrics metric = new DisplayMetrics();
        // BaseActivity activity = (BaseActivity) context;
        // activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(metric);
        // int width = (int) (metric.widthPixels * 0.7); // 屏幕宽度（像素）
        if (height != 0) {
            mPopupWindow = new PopupWindow(popView, width, height, true);
        } else {
            mPopupWindow = new PopupWindow(popView, width, LayoutParams.WRAP_CONTENT, true);
        }
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow
                .setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
        return mPopupWindow;
    }

    public void setPopupWindowWidth(int width) {
        this.width = width;
    }

    public void setPopupWindowHeight(int height) {
        this.height = height;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setOnPopupWindowSelectedListener(OnSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            if (listener != null) {
                listener.setOnPopupWindowSelectedListener(position);
            }
        }

    }


    public interface OnSelectedListener {
        void setOnPopupWindowSelectedListener(int position);
    }

}
