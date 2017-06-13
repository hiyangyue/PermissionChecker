package com.yy.lib.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * author : yueyang
 * date : 2017/6/12
 */
class PermissionHelper {

    /**
     *  检查是否拥有了必要的权限 , true ： 已拥有， false : 未拥有
     * @param permissions 需要检测的权限
     *                    author: yueyang
     **/
    static boolean hasPermissions(Activity activity,String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission : permissions) {
            if (!isPermissionGranted(activity, permission)) {
                return false;
            }
        }

        return true;
    }

    /**
     *  检查是否已经拥有某个权限
     * author: yueyang
     **/
    private static boolean isPermissionGranted(Activity activity, String permission) {
        int checkoutSelfPermission = ContextCompat.checkSelfPermission(activity, permission);
        return checkoutSelfPermission == PackageManager.PERMISSION_GRANTED;
    }

    /**
     *  返回选择"不再提示" 的权限
     * author: yueyang
     **/
    static List<String> permissionNeedExplain(Activity activity,String[] permissions) {
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            // 是否已经点了"不再提示"按钮
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                permissionList.add(permission);
            }
        }
        return permissionList;
    }
}