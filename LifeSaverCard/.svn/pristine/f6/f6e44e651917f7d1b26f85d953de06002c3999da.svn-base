package com.byth.lifesaver.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.byth.lifesaver.R;
import com.byth.lifesaver.bean.ShoppingCartBean;
import com.byth.lifesaver.util.ShoppingCartUtil;
import com.byth.lifesaver.widget.TipsDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/8 0008.
 * 购物车商品可折叠的adapter
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ShoppingCartBean> goods = new ArrayList<>();
    private OnShoppingCartItemChangeListener shoppingCartItemChangeListener;
    boolean isSelectAll;//是否全选，默认为否

    public MyExpandableListAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<ShoppingCartBean> mListGoods) {
        this.goods = mListGoods;
        setSettleInfo();
    }

    //设置购物车商品变化监听
    public void setOnShoppingCartItemChangeListener(OnShoppingCartItemChangeListener listener) {
        this.shoppingCartItemChangeListener = listener;
    }

    @Override
    public int getGroupCount() {
        return goods.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return goods.get(groupPosition).getGoods().size();
    }

    public View.OnClickListener getAdapterListener() {
        return listener;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return goods.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return goods.get(groupPosition).getGoods().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shopping_cart_parent, null);
            groupViewHolder.tvEdit = (TextView) convertView.findViewById(R.id.tvEdit);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        boolean isEditing = goods.get(groupPosition).isEditing();
        if (isEditing) {
            groupViewHolder.tvEdit.setText("完成");
        } else {
            groupViewHolder.tvEdit.setText("编辑");
        }
        groupViewHolder.tvEdit.setTag(groupPosition);
        groupViewHolder.tvEdit.setOnClickListener(listener);
        return convertView;
    }

    class GroupViewHolder {
        TextView tvEdit;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivSelectAll:
                    break;
                case R.id.txtViewSettlement:
                    if (ShoppingCartUtil.hasSelectedGoods(goods)) {
                        Toast.makeText(context, "结算跳转", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "请选择商品", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.tvEdit:
                    int groupPosition2 = Integer.parseInt(String.valueOf(v.getTag()));
                    boolean isEditing = !(goods.get(groupPosition2).isEditing());
                    goods.get(groupPosition2).setEditing(isEditing);
                    for (int i = 0; i < goods.get(groupPosition2).getGoods().size(); i++) {
                        goods.get(groupPosition2).getGoods().get(i).setEditing(isEditing);
                    }
                    notifyDataSetChanged();
                    break;
                case R.id.ivCheckGood:
                    String tag = String.valueOf(v.getTag());
                    if (tag.contains(",")) {
                        String s[] = tag.split(",");
                        int groupPosition = Integer.parseInt(s[0]);
                        int childPosition = Integer.parseInt(s[1]);
                        isSelectAll = ShoppingCartUtil.selectOne(goods, groupPosition, childPosition);
                        selectAll();
                        setSettleInfo();
                        notifyDataSetChanged();
                    }
                    break;
                case R.id.tvDel:
                    String tagPos = String.valueOf(v.getTag());
                    if (tagPos.contains(",")) {
                        String s[] = tagPos.split(",");
                        int groupPosition = Integer.parseInt(s[0]);
                        int childPosition = Integer.parseInt(s[1]);
                        showDelDialog(groupPosition, childPosition);
                    }
                    break;
                case R.id.ivAdd:
                    ShoppingCartUtil.addOrReduceGoodNum(true, (ShoppingCartBean.Goods) v.getTag(), ((TextView) (((View) (v.getParent())).findViewById(R.id.tvNum2))));
                    setSettleInfo();
                    break;
                case R.id.ivReduce:
                    ShoppingCartUtil.addOrReduceGoodNum(false, (ShoppingCartBean.Goods) v.getTag(), ((TextView) (((View) (v.getParent())).findViewById(R.id.tvNum2))));
                    setSettleInfo();
                    break;
            }
        }
    };

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shopping_cart, null);
            childViewHolder.tvChild = (TextView) convertView.findViewById(R.id.tvItemChild);
            childViewHolder.tvDel = (TextView) convertView.findViewById(R.id.tvDel);
            childViewHolder.ivCheckGood = (ImageView) convertView.findViewById(R.id.ivCheckGood);
            childViewHolder.rlEditStatus = (RelativeLayout) convertView.findViewById(R.id.rlEditStatus);
            childViewHolder.llGoodInfo = (LinearLayout) convertView.findViewById(R.id.llGoodInfo);
            childViewHolder.ivAdd = (ImageView) convertView.findViewById(R.id.ivAdd);
            childViewHolder.ivReduce = (ImageView) convertView.findViewById(R.id.ivReduce);
            childViewHolder.tvGoodsParam = (TextView) convertView.findViewById(R.id.tvGoodsParam);
            childViewHolder.tvPriceNew = (TextView) convertView.findViewById(R.id.tvPriceNew);
            childViewHolder.tvPriceOld = (TextView) convertView.findViewById(R.id.tvPriceOld);
            childViewHolder.tvPriceOld.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//数字被划掉效果
            childViewHolder.tvNum = (TextView) convertView.findViewById(R.id.tvNum);
            childViewHolder.tvNum2 = (TextView) convertView.findViewById(R.id.tvNum2);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        ShoppingCartBean.Goods goodsList = goods.get(groupPosition).getGoods().get(childPosition);
        boolean isChildSelected = goods.get(groupPosition).getGoods().get(childPosition).isChildSelected();
        boolean isEditing = goodsList.isEditing();
        String priceNew = "¥" + goodsList.getPrice();
        String priceOld = "¥" + goodsList.getMkPrice();
        String num = goodsList.getNumber();
        String pdtDesc = goodsList.getPdtDesc();
        String goodName = goods.get(groupPosition).getGoods().get(childPosition).getGoodsName();

        childViewHolder.ivCheckGood.setTag(groupPosition + "," + childPosition);
        childViewHolder.tvChild.setText(goodName);
        childViewHolder.tvPriceNew.setText(priceNew);
        childViewHolder.tvPriceOld.setText(priceOld);
        childViewHolder.tvNum.setText("X " + num);
        childViewHolder.tvNum2.setText(num);
        childViewHolder.tvGoodsParam.setText(pdtDesc);

        childViewHolder.ivAdd.setTag(goods);
        childViewHolder.ivReduce.setTag(goods);
        childViewHolder.tvDel.setTag(groupPosition + "," + childPosition);
        childViewHolder.tvDel.setTag(groupPosition + "," + childPosition);

        ShoppingCartUtil.checkItem(isChildSelected, childViewHolder.ivCheckGood);
        if (isEditing) {
            childViewHolder.llGoodInfo.setVisibility(View.GONE);
            childViewHolder.rlEditStatus.setVisibility(View.VISIBLE);
        } else {
            childViewHolder.llGoodInfo.setVisibility(View.VISIBLE);
            childViewHolder.rlEditStatus.setVisibility(View.GONE);
        }

        childViewHolder.ivCheckGood.setOnClickListener(listener);
        childViewHolder.tvDel.setOnClickListener(listener);
        childViewHolder.ivAdd.setOnClickListener(listener);
        childViewHolder.ivReduce.setOnClickListener(listener);
        childViewHolder.llGoodInfo.setOnClickListener(listener);
        return convertView;
    }

    class ChildViewHolder {
        /**
         * 商品名称
         */
        TextView tvChild;
        /**
         * 商品规格
         */
        TextView tvGoodsParam;
        /**
         * 选中
         */
        ImageView ivCheckGood;
        /**
         * 非编辑状态
         */
        LinearLayout llGoodInfo;
        /**
         * 编辑状态
         */
        RelativeLayout rlEditStatus;
        /**
         * +1
         */
        ImageView ivAdd;
        /**
         * -1
         */
        ImageView ivReduce;
        /**
         * 删除
         */
        TextView tvDel;
        /**
         * 新价格
         */
        TextView tvPriceNew;
        /**
         * 旧价格
         */
        TextView tvPriceOld;
        /**
         * 商品状态的数量
         */
        TextView tvNum;
        /**
         * 编辑状态的数量
         */
        TextView tvNum2;
    }

    private void selectAll() {
        if (shoppingCartItemChangeListener != null) {
            shoppingCartItemChangeListener.onSelectItem(isSelectAll);
        }
    }

    private void setSettleInfo() {
        String[] infos = ShoppingCartUtil.getShoppingCount(goods);
        //删除或者选择商品之后，需要通知结算按钮，更新自己的数据；
        if (shoppingCartItemChangeListener != null && infos != null) {
            shoppingCartItemChangeListener.onDataChange(infos[0], infos[1]);
        }
    }

    /**
     * 点击删除跳出弹框
     *
     * @param groupPosition
     * @param childPosition
     */
    private void showDelDialog(final int groupPosition, final int childPosition) {
        TipsDialog.makeDialog(context, "提示", "是否将该商品移出购物车", "是", "否", new TipsDialog.onDialogListener() {
            @Override
            public void onOkClick() {
                delGoods(groupPosition, childPosition);
                setSettleInfo();
                notifyDataSetChanged();
            }

            @Override
            public void onCancelClick() {

            }
        }).show();
    }

    private void delGoods(int groupPosition, int childPosition) {
        goods.get(groupPosition).getGoods().remove(childPosition);
        if (goods.get(groupPosition).getGoods().size() == 0) {
            goods.remove(groupPosition);
        }
        notifyDataSetChanged();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public interface OnShoppingCartItemChangeListener {
        void onDataChange(String selectCount, String selectMoney);

        void onSelectItem(boolean isSelectedAll);
    }
}
