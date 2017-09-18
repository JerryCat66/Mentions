package com.byth.lifesaver.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.fenguo.library.adapter.BaseAdapterHelper;
import com.fenguo.library.adapter.QuickAdapter;
import com.fenguo.library.widgets.dialog.BaseDialog;

import java.util.List;

/**
 * @Description
 * @auth Sunflower
 * @date 2015/6/19
 */
public class DialogWithList extends BaseDialog implements AdapterView.OnItemClickListener {
    private Context context;
    private TextView mTitle;
    private ListView mListView;
    private List<String> list;
    private ArrayAdapter<String> adapter;
    private QuickAdapter<String> quickAdapter;
    private OnSelectedListener listener;

    public DialogWithList(Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_with_list);
        mTitle = (TextView) findViewById(R.id.title);
        mListView = (ListView) findViewById(R.id.list);
        mListView.setOnItemClickListener(this);
        setDialogHeight();
    }

    public void setTitle(String content) {
        mTitle.setText(content);
    }

    public void setTitle(int resId) {
        mTitle.setText(resId);
    }

    public void hideTitle() {
        mTitle.setVisibility(View.GONE);
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        this.listener = listener;
    }

    /**
     * @param @param list 设定文件
     * @return void 返回类型
     * @throws
     * @Title: setList
     * @Description: 为listview 填充数据
     */
    public void setList(List<String> list) {
        this.list = list;
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(adapter);
    }

    /**
     * 调整对话框的高度
     */
    private void setDialogHeight() {
        View view = findViewById(android.R.id.content);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (int) (getHeight() * 0.5);
        view.setLayoutParams(params);
    }

    /**
     * 为listview 填充数据
     *
     * @param list
     * @param resId
     */
    public void setList(List<String> list, int resId) {
        this.list = list;
        quickAdapter = new QuickAdapter<String>(context, resId, this.list) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                String[] temp = item.split(";");
                helper.setText(R.id.content, temp[0]);
            }
        };
        mListView.setAdapter(quickAdapter);
    }

    /**
     * 没有hot标识的list
     *
     * @param list
     * @param resId
     */
    public void setListNotHot(List<String> list, int resId) {
        this.list = list;
        quickAdapter = new QuickAdapter<String>(context, resId, this.list) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                String[] temp = item.split(";");
                helper.setText(R.id.content, temp[0]);
            }
        };
        mListView.setAdapter(quickAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (listener != null) {
            String content = list.get(position).split(";")[0];
            position++;
            listener.setOnSelectedListener(position, content);
            dismiss();
        }
    }

    public interface OnSelectedListener {
        void setOnSelectedListener(int position, String content);

    }
}
