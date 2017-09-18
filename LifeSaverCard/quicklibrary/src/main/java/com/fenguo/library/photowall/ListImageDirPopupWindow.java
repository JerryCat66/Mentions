package com.fenguo.library.photowall;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.fenguo.library.R;
import com.fenguo.library.adapter.BaseAdapterHelper;
import com.fenguo.library.adapter.QuickAdapter;
import com.fenguo.library.photowall.ImageLoader.Type;

import java.util.List;

public class ListImageDirPopupWindow extends BasePopupWindowForListView<ImageFloder> {
    private ListView mListDir;
    private QuickAdapter<ImageFloder> adapter;

    public ListImageDirPopupWindow(int width, int height, List<ImageFloder> datas, View convertView) {
        super(convertView, width, height, true, datas);
    }

    @Override
    public void initViews() {
        mListDir = (ListView) findViewById(R.id.id_list_dir);
        if (adapter == null) {
            adapter = new QuickAdapter<ImageFloder>(context, R.layout.list_dir_item, mDatas) {

                @Override
                protected void convert(BaseAdapterHelper helper, ImageFloder item) {
                    helper.setText(R.id.id_dir_item_name, item.getName());
                    ImageLoader.getInstance(3, Type.LIFO).loadImage(item.getFirstImagePath(),
                            (ImageView) helper.getView(R.id.id_dir_item_image));
                    helper.setText(R.id.id_dir_item_count, item.getCount() + "张");
                    if (item.isSelected()) {
                        helper.getView(R.id.id_dir_item_selected).setVisibility(View.VISIBLE);
                    } else {
                        helper.getView(R.id.id_dir_item_selected).setVisibility(View.INVISIBLE);
                    }
                }
            };
        }
        mListDir.setAdapter(adapter);
    }

    public interface OnImageDirSelected {
        void selected(ImageFloder floder);
    }

    private OnImageDirSelected mImageDirSelected;

    public void setOnImageDirSelected(OnImageDirSelected mImageDirSelected) {
        this.mImageDirSelected = mImageDirSelected;
    }

    @Override
    public void initEvents() {
        mListDir.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (ImageFloder floder : mDatas) {
                    floder.setSelected(false);
                }
                mDatas.get(position).setSelected(true);
                if (mImageDirSelected != null) {
                    mImageDirSelected.selected(mDatas.get(position));
                }
            }
        });
    }

    @Override
    public void init() {

    }

    @Override
    protected void beforeInitWeNeedSomeParams(Object... params) {
    }

}
