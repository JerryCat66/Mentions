package com.byth.lifesaver.bean;

/**
 * Created by Administrator on 2017/7/19 0019.
 * 微信第三方sdk  bean
 */

public class WxInfoBean {
    private WeixinCodeDto weixinCodeDto;
    private String openid;
    private String session_id;
    private int id;
    private UserDto userDto;
    private int status;
    private String operation_code;//用户是否可以操作
    private String headImg;//用户头像

    public String getOpenid() {
        return openid;
    }

    public int getStatus() {
        return status;
    }

    public String getSession_id() {
        return session_id;
    }

    public int getId() {
        return id;
    }

    public String getOperation_code() {
        return operation_code;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setWeixinCodeDto(WeixinCodeDto weixinCodeDto) {
        this.weixinCodeDto = weixinCodeDto;
    }

    public static class WeixinCodeDto {
        private String code;
        private String symbols;

        public void setCode(String code) {
            this.code = code;
        }

        public void setSymbols(String symbols) {
            this.symbols = symbols;
        }
    }

    public static class UserDto {
        private String loginName;
        private String password;
        private String openid;

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
