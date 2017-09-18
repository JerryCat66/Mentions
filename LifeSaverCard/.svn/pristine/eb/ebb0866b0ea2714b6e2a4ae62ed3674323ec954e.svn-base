package com.byth.lifesaver.bean;

/**
 * Created by Administrator on 2017/9/14 0014.
 * 补卡确认bean
 */

public class CardReissueConfirmBean {
    private CompGoodsAddrCostListDto compGoodsAddrCostListDto;

    public CompGoodsAddrCostListDto getCompGoodsAddrCostListDto() {
        return compGoodsAddrCostListDto;
    }

    public static class CompGoodsAddrCostListDto {
        private GoodsDto goodsDto;
        private AddressDto addressDto;

        public GoodsDto getGoodsDto() {
            return goodsDto;
        }

        public AddressDto getAddressDto() {
            return addressDto;
        }

        public static class GoodsDto {
            private int goodsId;
            private String goodsCode;
            private String goodsName;
            private String description;
            private String image;
            private double expressCost;
            private double makeUpCost;

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

            public double getMakeUpCost() {
                return makeUpCost;
            }
        }

        public static class AddressDto {
            private int addrId;
            private int userId;
            private String consignee;
            private String consigneeMobile;
            private String addr;
            private String defaultFlag;

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
        }
    }
}
