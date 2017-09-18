package com.byth.lifesaver.bean;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class OrderPageBean {
    public OrderDto orderDtoorder;

    private int maxPage;

    public int getMaxPage() {
        return maxPage;
    }

    public static class OrderDto {
        private String orderStatus;
        private int pageNum;
        private int pageSize;

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }
}
