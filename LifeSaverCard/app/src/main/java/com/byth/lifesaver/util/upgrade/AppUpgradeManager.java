package com.byth.lifesaver.util.upgrade;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.byth.lifesaver.BuildConfig;
import com.byth.lifesaver.bean.VersionInfoBean;
import com.byth.lifesaver.widget.TipsDialog;
import com.fenguo.library.util.NetworkUtil;
import com.fenguo.library.util.StringUtil;

import java.io.File;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/8/25 0025.
 * app升级管理类
 */

public class AppUpgradeManager implements AppUpgrade, IVersionInfoDialog {
    private volatile static AppUpgradeManager manager;
    private Context appContext;
    private DownloadManager downloadManager;
    private VersionInfoBean versionInfoBean;//版本信息
    private AppUpgradePreference appUpgradePreference;
    private DownloadReceiver downloadReceiver;
    private NotificationClickReceiver notificationClickReceiver;
    //是否初始化
    private boolean isInit = false;
    private String uriDownload;

    //true为自动后台检测升级，false为用户手动点击触发升级
    private boolean isCheckLatestVersionBackground = false;
    private String downloadApkPath;//下载apk文件的绝对路径

    private final int WHAT_ID_INSTALL_APK = 1;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT_ID_INSTALL_APK) {
                uriDownload = (String) msg.obj;
                installApkFile();
            }
        }
    };

    public static AppUpgradeManager getInstance() {
        if (manager == null) {
            synchronized (AppUpgradeManager.class) {
                if (manager == null) {
                    manager = new AppUpgradeManager();
                }
            }
        }
        return manager;
    }

    private AppUpgradeManager() {
        downloadReceiver = new DownloadReceiver();
        notificationClickReceiver = new NotificationClickReceiver();
    }

    @Override
    public void init(Context context) {
        if (isInit) {
            return;
        }
        appContext = context.getApplicationContext();//和程序生命周期一致
        isInit = true;
        appUpgradePreference = new AppUpgradePreference();
        //注册广播
        appContext.registerReceiver(downloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        appContext.registerReceiver(notificationClickReceiver, new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));
    }

    @Override
    public void unInit() {
        if (!isInit) {
            return;
        }
        //解除广播注册
        appContext.unregisterReceiver(downloadReceiver);
        appContext.unregisterReceiver(notificationClickReceiver);
        isInit = false;
        appUpgradePreference = null;
        appContext = null;
    }

    @Override
    public void checkLatestVersion(Activity activity) {
        isCheckLatestVersionBackground = false;//手动点击检测升级
        startCheckVersion();
    }

    @Override
    public void checkLatestVersionBackground() {
        isCheckLatestVersionBackground = true;
        startCheckVersion();
    }

    @Override
    public void foundLatestVersion(Activity activity) {
        if (!isInit) {
            return;
        }
        if (versionInfoBean == null) {
            return;
        }
        FoundNewVersionDialog dialog = new FoundNewVersionDialog(activity, versionInfoBean, this);
        dialog.show();
    }

    /**
     * 开始检查版本
     * step1:检查网络是否开启
     * step2:确定下载的绝对路径
     * step3:请求服务器获取最新版本号
     * step4:与当前版本号进行比较,更大就提示升级
     */
    private void startCheckVersion() {
        if (!NetworkUtil.checkConnection(appContext)) {
            if (!isCheckLatestVersionBackground) {
                //手动更新时，提示
                Toast.makeText(appContext, "无法连接网络，请检查后重试", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        final String apkName = appContext.getPackageName();//拿到apk名字
        String dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();//获取绝对路径
        dirPath = dirPath.endsWith(File.separator) ? dirPath : dirPath + File.separator;
        downloadApkPath = dirPath + apkName;//路径+apk名生成完整的路径

        //TODO：向服务器请求获取最新的版本号,需要在此处调用接口
    }

    /**
     * 与当前的版本号进行比较
     *
     * @param versionInfo
     * @return
     */
    private boolean compareWithCurrentVersion(VersionInfoBean versionInfo) {
        if (versionInfo == null) {
            return false;
        }
        int currentVersionCode = 0;
        try {
            //获取当前版本号
            PackageInfo pkg = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
            currentVersionCode = pkg.versionCode;
        } catch (PackageManager.NameNotFoundException pnfe) {
            pnfe.printStackTrace();
        }
        return versionInfo.getVersionCode() > currentVersionCode;
    }

    /**
     * 下载apk包
     */
    private void downloadApk() {
        if (!NetworkUtil.checkConnection(appContext)) {
            if (!isCheckLatestVersionBackground) {
                Toast.makeText(appContext, "无法连接网络，请检查后重试", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        //检查本地是否有需要升级版本的安装包，如果有就不需要再次下载
        File targetApkFile = new File(downloadApkPath);
        if (targetApkFile.exists()) {
            PackageManager pm = appContext.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(downloadApkPath, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                int versionCode = info.versionCode;
                if (versionInfoBean.getVersionCode() == versionCode) {
                    mHandler.obtainMessage(WHAT_ID_INSTALL_APK, downloadApkPath).sendToTarget();
                }

            }
        }
        //检查本地是否有安装包，有则删除重新下
        File apkFile = new File(downloadApkPath);
        if (apkFile.exists()) {
            boolean isDelSuc = apkFile.delete();
        }
        if (downloadManager == null) {
            downloadManager = (DownloadManager) appContext.getSystemService(Context.DOWNLOAD_SERVICE);
        }

        DownloadManager.Query query = new DownloadManager.Query();
        long downloadTaskId = appUpgradePreference.getDownloadTaskId();
        query.setFilterById(downloadTaskId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            int status = cursor.getInt(columnIndex);
            if (DownloadManager.STATUS_PENDING == status || DownloadManager.STATUS_RUNNING == status || DownloadManager.STATUS_PAUSED == status) {
                cursor.close();
                if (BuildConfig.DEBUG) {
                    Log.i("msg", "downloadApk: ");
                }
                return;
            }
        }
        cursor.close();
        DownloadManager.Request task = new DownloadManager.Request(Uri.parse(versionInfoBean.getDownloadUrl()));
        //定制Notification的样式
        String title = "最新版本:" + versionInfoBean.getVersionName();
        task.setTitle(title);
        task.setDescription(versionInfoBean.getVersionDesc());
        task.setVisibleInDownloadsUi(true);
        //设置是否允许手机在漫游状态下下载
        //task.setAllowedOverRoaming(false);
        //限定在WiFi下进行下载
        //task.setAllowedNetworkTypes(Request.NETWORK_WIFI);
        task.setMimeType("application/vnd.android.package-archive");
        // 在通知栏通知下载中和下载完成
        // 下载完成后该Notification才会被显示
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // 3.0(11)以后才有该方法
            //在下载过程中通知栏会一直显示该下载的Notification，在下载完成后该Notification会继续显示，直到用户点击该Notification或者消除该Notification
            task.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        // 可能无法创建Download文件夹，如无sdcard情况，系统会默认将路径设置为/data/data/com.android.providers.downloads/cache/xxx.apk
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String apkName = appContext.getPackageName();
            task.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, apkName);
        }
        downloadTaskId = downloadManager.enqueue(task);
        appUpgradePreference.saveDownloadTaskId(downloadTaskId);
    }

    @Override
    public void doUpdate() {
        if (!NetworkUtil.checkConnection(appContext)) {
            Toast.makeText(appContext, "无法连接网络，请检查网络后重试", Toast.LENGTH_SHORT).show();
        } else if (NetworkUtil.checkWifiConnect(appContext)) {
            //如果是WiFi情况就直接下载
            downloadApk();
        } else {
            //再次确认是否要在非wifi情况下下载
            TipsDialog.makeDialog(appContext, "提示", "您当前使用的不是wifi，更新会产生一些网络流量，是否继续下载？", "是", "否", new TipsDialog.onDialogListener() {
                @Override
                public void onOkClick() {
                    EventBus.getDefault().post("dialog missing");
                    downloadApk();
                }

                @Override
                public void onCancelClick() {
                    EventBus.getDefault().post("dialog missing");
                }
            }).show();
        }
    }

    @Override
    public void remindMeLater() {
        //稍后提示，什么都不用做
        EventBus.getDefault().post("dialog missing");
    }

    /**
     * 安装apk文件
     */
    private void installApkFile() {
        if (StringUtil.isEmpty(uriDownload)) {
            Toast.makeText(appContext, "安装文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        File apkFile = new File(Uri.parse(uriDownload).getPath());//将apk路径转换成文件
        if (!apkFile.exists()) {
            Toast.makeText(appContext, "安装文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }

        long start = System.currentTimeMillis();

        Intent installIntent = new Intent();
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installIntent.setAction(Intent.ACTION_VIEW);

        Uri apkUriFile;
        // 在24及其以上版本，解决崩溃异常：
        // android.os.FileUriExposedException: file:///storage/emulated/0/xxx exposed beyond app through Intent.getData()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            apkUriFile = FileProvider.getUriForFile(appContext, BuildConfig.APPLICATION_ID + ".provider", apkFile);
        } else {
            apkUriFile = Uri.fromFile(apkFile);
        }
        installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        installIntent.setDataAndType(apkUriFile, "application/vnd.android.package-archive");
        try {
            appContext.startActivity(installIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载完成的广播
     */
    class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (downloadManager == null) {
                return;
            }
            long completeId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            long downloadTaskId = appUpgradePreference.getDownloadTaskId();
            if (completeId != downloadTaskId) {
                return;
            }
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadTaskId);
            Cursor cursor = downloadManager.query(query);
            if (!cursor.moveToFirst()) {
                return;
            }
            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex)) {
                String uriString = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            } else {
                Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
            }
            appUpgradePreference.removeDownloadTaskId();
            cursor.close();
        }
    }

    /**
     * 点击通知栏下载项目
     */
    public class NotificationClickReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            long[] completeIds = intent.getLongArrayExtra(
                    DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS
            );
            long downloadTaskId = appUpgradePreference.getDownloadTaskId();
            if (completeIds == null || completeIds.length <= 0) {
                openDownloadPage(context);
                return;
            }
            for (long completed : completeIds) {
                if (completed == downloadTaskId) {
                    openDownloadPage(context);
                    break;
                }
            }
        }
    }

    /**
     * 打开显示所有下载任务的页面
     *
     * @param context
     */
    private void openDownloadPage(Context context) {
        Intent pageView = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
        pageView.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(pageView);
    }

}
