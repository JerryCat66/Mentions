package com.byth.lifesaver.util;

import android.widget.ImageView;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.byth.lifesaver.bean.ShoppingCartBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/8 0008.
 * 购物车全选删除等操作util
 */

public class ShoppingCartUtil {
    /**
     * 选择全部，点下全选按钮,改变购物车所有商品的选中状态
     *
     * @param goods
     * @param isSelectAll
     * @param ivChecked
     * @return
     */
    public static boolean selectAll(List<ShoppingCartBean> goods, boolean isSelectAll, ImageView ivChecked) {
        isSelectAll = !isSelectAll;//不全选
        ShoppingCartUtil.checkItem(isSelectAll, ivChecked);
        for (int i = 0; i < goods.size(); i++) {
            goods.get(i).setGroupSelected(isSelectAll);
            for (int j = 0; j < goods.get(i).getGoods().size(); j++) {
                goods.get(i).getGoods().get(j).setChildSelected(isSelectAll);
            }
        }
        return isSelectAll;
    }

    /**
     * 是否全选(组内的所有东西都被选)
     *
     * @param goods
     * @return
     */
    private static boolean isGroupAllSelect(List<ShoppingCartBean> goods) {
        for (int i = 0; i < goods.size(); i++) {
            boolean isGroupSelect = goods.get(i).isGroupSelected();
            if (!isGroupSelect) {
                return false;
            }
        }
        return true;
    }

    /**
     * 组内所有子元素是否被选中
     *
     * @param goods
     * @return
     */
    private static boolean isChildAllSelect(List<ShoppingCartBean.Goods> goods) {
        for (int i = 0; i < goods.size(); i++) {
            boolean isChildSelect = goods.get(i).isChildSelected();
            if (!isChildSelect) {
                return false;
            }
        }
        return true;
    }

    /**
     * 单选一个，需要判断是否单选了全选或者取消
     *
     * @param goods
     * @param groupPosition
     * @param childPosition
     * @return
     */
    public static boolean selectOne(List<ShoppingCartBean> goods, int groupPosition, int childPosition) {
        boolean isSelectAll;
        boolean isSelectOne = !(goods.get(groupPosition).getGoods().get(childPosition).isChildSelected());
        goods.get(groupPosition).getGoods().get(childPosition).setChildSelected(isSelectOne);//单选一个
        boolean isSelectCurrentGroup = isChildAllSelect(goods.get(groupPosition).getGoods());
        goods.get(groupPosition).setGroupSelected(isSelectCurrentGroup);//组图标处理
        isSelectAll = isGroupAllSelect(goods);
        return isSelectAll;
    }

    public static boolean selectGroup(List<ShoppingCartBean> goods, int groupPosition) {
        boolean isSelectAll;
        boolean isSelected = !(goods.get(groupPosition).isGroupSelected());
        goods.get(groupPosition).setGroupSelected(isSelected);
        for (int i = 0; i < goods.get(groupPosition).getGoods().size(); i++) {
            goods.get(groupPosition).getGoods().get(i).setChildSelected(isSelected);
        }
        isSelectAll = isGroupAllSelect(goods);
        return isSelectAll;
    }

    /**
     * 是否选择
     *
     * @param isSelect
     * @param ivChecked
     * @return
     */
    public static boolean checkItem(boolean isSelect, ImageView ivChecked) {
        if (isSelect) {
            ivChecked.setImageResource(R.drawable.ic_checked);
        } else {
            ivChecked.setImageResource(R.drawable.ic_uncheck);
        }
        return isSelect;
    }

    /**
     * 获取结算信息
     *
     * @param goodsList
     * @return
     */
    public static String[] getShoppingCount(List<ShoppingCartBean> goodsList) {
        String[] infos = new String[2];
        String selectCount = "0";//数量
        String selectMoney = "0";//金额
        for (int i = 0; i < goodsList.size(); i++) {
            for (int j = 0; j < goodsList.get(i).getGoods().size(); j++) {
                boolean isSelected = goodsList.get(i).getGoods().get(j).isChildSelected();
                if (isSelected) {
                    String price = goodsList.get(i).getGoods().get(j).getPrice();
                    String num = goodsList.get(i).getGoods().get(j).getNumber();
                    String countMoney = DecimalUtil.multiply(price, num);
                    selectMoney = DecimalUtil.add(selectMoney, countMoney);
                    selectCount = DecimalUtil.add(selectCount, "1");
                }
            }
        }
        infos[0] = selectCount;
        infos[1] = selectMoney;
        return infos;
    }

    /**
     * 增加或者减少数量,如果数量大于1，点减少就减1，否则数量就为1
     */
    public static void addOrReduceGoodNum(boolean isPlus, ShoppingCartBean.Goods goods, TextView tvNum) {
        String currentNum = goods.getNumber().trim();
        String num = "1";
        if (isPlus) {
            num = String.valueOf(Integer.parseInt(currentNum) + 1);
        } else {
            int i = Integer.parseInt(currentNum);
            if (i > 1) {
                num = String.valueOf(i - 1);
            } else {
                num = "1";
            }
        }
        tvNum.setText(num);
        goods.setNumber(num);
        upDateGoodsNum();
    }

    /**
     * 购物车界面是否选择商品
     * @param goodsList
     * @return
     */
    public static boolean hasSelectedGoods(List<ShoppingCartBean> goodsList) {
        String count = getShoppingCount(goodsList)[0];
        if ("0".equals(count)) {
            return false;
        }
        return true;
    }
    /*============================以下为需要连接服务器进行操作======================*/

    /**
     * 将商品添加到数据库，具体要添加的字段TODO
     */
    public static void addToCart() {

    }

    /**
     * 删除商品
     */
    public static void delGood() {

    }

    /**
     * 删除所有商品
     */
    public static void delAllGood() {

    }

    /**
     * 更新商品数量
     */
    public static void upDateGoodsNum() {

    }

    /**
     * 获取商品数量
     *
     * @return
     */
    public static int GoodsCount() {
        return 1;
    }
}
