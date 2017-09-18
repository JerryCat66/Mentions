package com.byth.lifesaver.bean;

/**
 * Created by Administrator on 2017/8/7 0007.
 * 订单详情bean
 */

public class OrderDetailBean {
    private GoodsDto goodsDto;
    private OrderDto orderDto;
    private DetailDto detailDto;

    public GoodsDto getGoodsDto() {
        return goodsDto;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public DetailDto getDetailDto() {
        return detailDto;
    }

    public static class GoodsDto {
        private int goodsId;//商品主键
        private String goodsCode;//商品编号
        private String goodsName;//商品名称
        private String description;//商品描述
        private String image;//图片
        private double expressCost;//快递费用

        public int getGoodsId() {
            return goodsId;
        }

        public String getGoodsCode() {
            return goodsCode;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public String getDescription() {
            return description;
        }

        public String getImage() {
            return image;
        }

        public double getExpressCost() {
            return expressCost;
        }
    }

    public static class OrderDto {
        private int Id;//订单主键
        private int userId;//用户ID
        private String orderCode;//订单编号
        private double sum;//支付总金额
        private String orderStatus;//订单状态
        private double expressCost;//快递费用
        private String consignee;//收货人
        private String consigneeMobile;//收货人手机
        private int region;//所属地区编码
        private String addr;//具体地址
        private String remarks;//备注
        private String expressOrder;
        private String logisticsCompany;
        private String orderType;//订单状态

        public int getId() {
            return Id;
        }

        public int getUserId() {
            return userId;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public double getSum() {
            return sum;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public double getExpressCost() {
            return expressCost;
        }

        public String getConsignee() {
            return consignee;
        }

        public String getConsigneeMobile() {
            return consigneeMobile;
        }

        public int getRegion() {
            return region;
        }

        public String getAddr() {
            return addr;
        }

        public String getRemarks() {
            return remarks;
        }

        public String getExpressOrder() {
            return expressOrder;
        }

        public String getLogisticsCompany() {
            return logisticsCompany;
        }

        public String getOrderType() {
            return orderType;
        }
    }

    public static class DetailDto {
        private int Id;//订单详情主键
        private int goodsId;//商品ID
        private int orderId;//订单ID
        private int goodsCount;//商品数量
        private int years;// 年限
        private double yearPrice;//年费

        public int getId() {
            return Id;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public int getOrderId() {
            return orderId;
        }

        public int getGoodsCount() {
            return goodsCount;
        }

        public int getYears() {
            return years;
        }

        public double getYearPrice() {
            return yearPrice;
        }
    }
}
