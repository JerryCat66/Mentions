package com.byth.lifesaver.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by Administrator on 2017/6/19 0019.
 * 省市区bean
 */

public class ProvinceBean implements IPickerViewData {
    /**
     * 省份：name："广东省"
     * city:[{"广州市","佛山市"}]
     */
    private String name;
    private List<CityBean> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来
    @Override
    public String getPickerViewText() {
        return this.name;
    }

    /**
     * 城市bean
     * name:"广州"
     * area:[{"越秀区"、"荔湾区"、"海珠区"}]
     */
    public class CityBean {
        private String name;
        private List<String> area;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }
    }
}
