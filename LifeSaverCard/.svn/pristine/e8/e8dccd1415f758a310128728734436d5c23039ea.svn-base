package com.byth.lifesaver.base;

import android.app.Activity;
import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.byth.lifesaver.util.Contants;
import com.facebook.stetho.Stetho;
import com.fenguo.library.http.VolleyUtil;
import com.fenguo.library.util.Preference;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.LinkedList;


public class BaseApplication extends MultiDexApplication {
    public LinkedList<Activity> activityList = new LinkedList<Activity>();
    private static Application application;
    public static IWXAPI iwxapi;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //初始化Preference
        Preference.instance(this);
        //初始化volley
        VolleyUtil.init(this);
        // 初始ImageLoader
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
        registerToWx();//注册微信
    }

    public static Application getContext()

    {
        return application;
    }

    private void registerToWx() {
        iwxapi = WXAPIFactory.createWXAPI(this, Contants.APP_ID, false);
        iwxapi.registerApp(Contants.APP_ID);//将该app注册到微信

    }


}
