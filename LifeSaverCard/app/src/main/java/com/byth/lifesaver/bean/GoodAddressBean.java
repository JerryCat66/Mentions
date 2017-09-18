package com.byth.lifesaver.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29 0029.
 * 商品地址信息bean
 */

public class GoodAddressBean {
    private GoodsDto goodsDto;
    private AddressDto addrDto;
    private List<CostDtoList> costDtoList;//价格与年限键值对

    public AddressDto getAddrDto() {
        return addrDto;
    }

    public GoodsDto getGoodsDto() {
        return goodsDto;
    }

    public List<CostDtoList> getCostDtoList() {
        return costDtoList;
    }

    public static class GoodsDto {
        private int goodsId;//商品id
        private String goodsCode;//商品编码
        private String goodsName;//商品名称
        private String description;//商品描述
        private String image;//图片
        private double expressCost;//快递费用
        private double oneYearPrice;//单价

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

        public double getOneYearPrice() {
            return oneYearPrice;
        }

    }

    public class CostDtoList {
        private int goodsId;
        private int years;//年限
        private double yearPrice;//年费

        public int getGoodsId() {
            return goodsId;
        }

        public int getYears() {
            return years;
        }

        public double getYearPrice() {
            return yearPrice;
        }
    }

    public static class AddressDto {
        private int addrId;//地址主键
        private int userId;//用户主键
        private String consignee;//收货人
        private String consigneeMobile;//收货人手机
        private String addr;//收货地址
        private String defaultFlag;//是否默认地址
        private String region_belong;//所属区域

        public int getAddrId() {
            return addrId;
        }

        public int getUserId() {
            return userId;
        }

        public String getConsignee() {
            return consignee;
        }

        public String getConsigneeMobile() {
            return consigneeMobile;
        }

        public String getAddr() {
            return addr;
        }

        public String getDefaultFlag() {
            return defaultFlag;
        }

        public String getRegion_belong() {
            return region_belong;
        }
    }
}
