package com.byth.lifesaver.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/6/19 0019.
 * 获取assets下文件并解析
 *
 */

public class GetJsonFromAssetsUtil {

    public String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)
            ));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
