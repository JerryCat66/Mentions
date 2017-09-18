package com.byth.lifesaver.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10 0010.
 * 地址list
 */

public class AddressListBean {
    private AddrDto addrDto;
    private UserDto userDto;
    private List<AddressList> addrList;
    private int maxPage;

    public List<AddressList> getAddrList() {
        return addrList;
    }

    public class AddressList {
        private int addrId;//收货地址ID
        private int userId; //用户ID
        private String consignee;//收货人
        private String consigneeMobile;//收货人手机
        private String addr;//具体地址
        private String defaultFlag;//默认地址标识，默认地址Y，非默认地址
        private int sex;//性别
        private String region_belong;

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

        public int getSex() {
            return sex;
        }

        public String getRegion_belong() {
            return region_belong;
        }
    }

    public static class AddrDto {
        private String pageNum;
        private String pageSize;

        public void setPageNum(String pageNum) {
            this.pageNum = pageNum;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }
    }

    public static class UserDto {
        private int id;

        public void setId(int id) {
            this.id = id;
        }
    }

    public int getMaxPage() {
        return maxPage;
    }
}