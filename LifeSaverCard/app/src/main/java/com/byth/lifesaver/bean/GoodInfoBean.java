package com.byth.lifesaver.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28 0028.
 * 商品信息bean
 */

public class GoodInfoBean {
    private GoodsDto goodsDto;
    private CostDto costDto;

    public GoodsDto getGoodsDto() {
        return goodsDto;
    }

    public CostDto getCostDto() {
        return costDto;
    }

    public static class GoodsDto {
        private int goodsId;//商品主键
        private String goodsCode;//商品编码
        private String goodsName;//商品名称
        private String description;//商品描述url
        private String image;//商品图片
        private double expressCost;//快递费用
        private double oneYearPrice;//一年费用

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


    private List<CostDto> costList;//商品价格信息

    public List<CostDto> getCostList() {
        return costList;
    }

    public static class CostDto {
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
}
