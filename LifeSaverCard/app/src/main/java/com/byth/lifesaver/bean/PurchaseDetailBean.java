package com.byth.lifesaver.bean;

/**
 * Created by Administrator on 2017/7/14 0014.
 * 下单提交商品信息bean
 */

public class PurchaseDetailBean {
    private PurchaseGoodsInfo goodsInfo;
    private PurchaseOrderInfo orderInfo;
    private PurchaseDetailInfo detailInfo;

    public void setGoodsInfo(PurchaseGoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public void setOrderInfo(PurchaseOrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public void setDetailInfo(PurchaseDetailInfo detailInfo) {
        this.detailInfo = detailInfo;
    }

    public static class PurchaseGoodsInfo {
        private int goodsId;

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }
    }

    public static class PurchaseOrderInfo {
        private double sum;
        private int userId;
        private String orderStatus;
        private String consignee;
        private String consigneeMobile;
        private int region;
        private String addr;
        private String remarks;
        private String expressOrder;
        private String logisticsCompany;
        private String orderType;//订单类型

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setSum(double sum) {
            this.sum = sum;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public void setConsigneeMobile(String consigneeMobile) {
            this.consigneeMobile = consigneeMobile;
        }

        public void setRegion(int region) {
            this.region = region;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public void setExpressOrder(String expressOrder) {
            this.expressOrder = expressOrder;
        }

        public void setLogisticsCompany(String logisticsCompany) {
            this.logisticsCompany = logisticsCompany;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }
    }

    public static class PurchaseDetailInfo {
        private int goodsCount;
        private int years;
        private double yearPrice;
        private int goodsId;

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
        }

        public void setYears(int years) {
            this.years = years;
        }

        public void setYearPrice(double yearPrice) {
            this.yearPrice = yearPrice;
        }
    }
}