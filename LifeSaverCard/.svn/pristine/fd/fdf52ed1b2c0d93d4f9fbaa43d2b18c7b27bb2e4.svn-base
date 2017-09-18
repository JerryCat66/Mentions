package com.byth.lifesaver.util;

import android.content.Context;

import com.byth.lifesaver.bean.ProvinceBean;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/19 0019.
 * 省市区json数据解析
 */

public class ProvinceJsonParseUtil {
    private Context mContext;
    private ArrayList<ProvinceBean> provinceList;
    private ArrayList<ArrayList<String>> cityList;
    private ArrayList<ArrayList<ArrayList<String>>> areaList;
    private static ProvinceJsonParseUtil pjpu;

    private ProvinceJsonParseUtil(Context context) {
        this.mContext = context;
        provinceList = new ArrayList<>();
        cityList = new ArrayList<>();
        areaList = new ArrayList<>();
        initJsonData();
    }

    public static ProvinceJsonParseUtil getInstance(Context context) {
        pjpu = new ProvinceJsonParseUtil(context);
        return pjpu;
    }

    /**
     * 解析json数据
     */
    private void initJsonData() {
        String jsonData = new GetJsonFromAssetsUtil().getJson(mContext, "province.json");
        ArrayList<ProvinceBean> provinceBeen = parseData(jsonData);

        provinceList = provinceBeen;
        for (int i = 0; i < provinceBeen.size(); i++) {//遍历省份
            ArrayList<String> province_cityList = new ArrayList<>();//该省的城市列表
            ArrayList<ArrayList<String>> province_areaList = new ArrayList<>();//该省得地区列表

            //遍历该省份的所有城市
            for (int c = 0; c < provinceBeen.get(i).getCity().size(); c++) {
                String cityName = provinceBeen.get(i).getCity().get(c).getName();
                province_cityList.add(cityName);//添加城市

                ArrayList<String> city_areaList = new ArrayList<>();//该城市全部的地区

                //如果没有去。则为空字符串
                if (provinceBeen.get(i).getCity().get(c).getArea() == null
                        || provinceBeen.get(i).getCity().get(c).getArea().size() == 0) {
                    city_areaList.add("");
                } else {
                    for (int a = 0; a < provinceBeen.get(i).getCity().get(c).getArea().size(); a++) {
                        String areaName = provinceBeen.get(i).getCity().get(c).getArea().get(a);
                        city_areaList.add(areaName);//添加该城市所有的区
                    }
                }
                province_areaList.add(city_areaList);//添加该省所有地区数据
            }
            //添加城市数据
            cityList.add(province_cityList);
            //添加地区数据
            areaList.add(province_areaList);
        }
    }

    public ArrayList<ProvinceBean> getProvinceList() {
        return provinceList;
    }

    public ArrayList<ArrayList<String>> getCityList() {
        return cityList;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getAreaList() {
        return areaList;
    }

    public ArrayList<ProvinceBean> parseData(String result) {
        ArrayList<ProvinceBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceBean provinceBean = gson.fromJson(data.optJSONObject(i).toString(), ProvinceBean.class);
                detail.add(provinceBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
