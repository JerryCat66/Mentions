package com.byth.lifesaver.bean;


import java.util.Date;

/**
 * Created by Administrator on 2017/8/9 0009.
 * 续卡bean
 */

public class CardRenewBean {
    String nickName;//使用人
    String phone; //绑定人手机
    String idCode; //身份证
    String cardCode; //订单号
    String expiryDate; //有效期至
    double yearPrice; //一年的费用
    String image;//图片url
    boolean isOperatingCard;//是否可进行续费

    public String getNickName() {
        return nickName;
    }

    public String getPhone() {
        return phone;
    }

    public String getIdCode() {
        return idCode;
    }

    public String getCardCode() {
        return cardCode;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public double getYearPrice() {
        return yearPrice;
    }

    public String getImage() {
        return image;
    }

    public boolean isOperatingCard() {
        return isOperatingCard;
    }

}
