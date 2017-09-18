package com.byth.lifesaver.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 2017/7/7 0007.
 * 权限检查器,安卓6.0以上需要对一些权限进行动态管理
 */

public class PermissionChecker {
    private Context mContext;

    public PermissionChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 权限判断，可能有多个
     *
     * @param permissions
     * @return
     */
    public boolean isLackPermissions(String... permissions) {
        for (String permission : permissions) {
            if (isLackPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否缺少某一个权限
     *
     * @param permission
     * @return
     */
    private boolean isLackPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
}
