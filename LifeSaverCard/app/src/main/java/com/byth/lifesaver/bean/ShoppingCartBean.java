package com.byth.lifesaver.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/8 0008.
 * 购物车Bean
 */

public class ShoppingCartBean {
    //商品失效为0
    public static final String GOOD_INVALID = "0";
    //商品不失效为1
    public static final String GOOD_VALID = "1";
    //购物车商品数量
    public static final String CART_NUM = "num";
    //是否处于编辑状态
    private boolean isEditing;
    /**
     * 组是否被选中
     */
    private boolean isGroupSelected;
    /**
     * 是否失效列表
     */
    private boolean isInvalidList;

    private boolean isAllGoodsInvalid;

    private List<Goods> goods;

    public boolean isGroupSelected() {
        return isGroupSelected;
    }
    public void setGroupSelected(boolean groupSelected) {
        isGroupSelected = groupSelected;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    public boolean isInvalidList() {
        return isInvalidList;
    }

    public void setInvalidList(boolean invalidList) {
        isInvalidList = invalidList;
    }

    public boolean isAllGoodsInvalid() {
        return isAllGoodsInvalid;
    }

    public void setAllGoodsInvalid(boolean allGoodsInvalid) {
        isAllGoodsInvalid = allGoodsInvalid;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    /**
     * 商品类
     */
    public static class Goods {
        /**
         * 数量
         */
        private String number = "1";
        /**
         * 商品ID
         */
        private String goodsID;
        /**
         * 商品名称
         */
        private String goodsName;
        /**
         * 商品宣传图片
         */
        private String goodsLogo;
        /**
         * 商品规格
         */
        private String pdtDesc;
        /**
         * 市场价，原价
         */
        private String mkPrice;
        /**
         * 价格，当前价格
         */
        private String price;
        /**
         * 是否失效,0删除(失效),1正常
         */
        private String status;
        /**
         * 是否是编辑状态
         */
        private boolean isEditing;
        /**
         * 是否被选中
         */
        private boolean isChildSelected;
        /**
         * 规格ID
         */
        private String productID;

        /***/
        private String sellPloyID;

        /**
         * 是否是失效列表的子项
         */
        private boolean isInvalidItem;

        /**
         * 是否属于
         */
        private boolean isBelongInvalidList;

        /**
         * 临时解决方案，为了避免尾部重绘两次，增加一个虚ITEM，不显示，但是没有子项的组项，会有一条黑线，所以需要做特殊处理
         */
        private boolean isLastTempItem;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getGoodsID() {
            return goodsID;
        }

        public void setGoodsID(String goodsID) {
            this.goodsID = goodsID;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsLogo() {
            return goodsLogo;
        }

        public void setGoodsLogo(String goodsLogo) {
            this.goodsLogo = goodsLogo;
        }

        public String getPdtDesc() {
            return pdtDesc;
        }

        public void setPdtDesc(String pdtDesc) {
            this.pdtDesc = pdtDesc;
        }

        public String getMkPrice() {
            return mkPrice;
        }

        public void setMkPrice(String mkPrice) {
            this.mkPrice = mkPrice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public boolean isEditing() {
            return isEditing;
        }

        public void setEditing(boolean editing) {
            isEditing = editing;
        }

        public boolean isChildSelected() {
            return isChildSelected;
        }

        public void setChildSelected(boolean childSelected) {
            isChildSelected = childSelected;
        }

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public String getSellPloyID() {
            return sellPloyID;
        }

        public void setSellPloyID(String sellPloyID) {
            this.sellPloyID = sellPloyID;
        }

        public boolean isInvalidItem() {
            return isInvalidItem;
        }

        public void setInvalidItem(boolean invalidItem) {
            isInvalidItem = invalidItem;
        }

        public boolean isBelongInvalidList() {
            return isBelongInvalidList;
        }

        public void setBelongInvalidList(boolean belongInvalidList) {
            isBelongInvalidList = belongInvalidList;
        }

        public boolean isLastTempItem() {
            return isLastTempItem;
        }

        public void setLastTempItem(boolean lastTempItem) {
            isLastTempItem = lastTempItem;
        }
    }
}
