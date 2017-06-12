package com.yy.lib.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * author : yueyang
 * date : 2017/6/12
 */
public class PermissionChecker {

    private Activity activity;
    private PermissionsCallback permissionsCallback;

    private static final int REQUEST_PERMISSIONS = 11131;

    private PermissionChecker() {}

    public PermissionChecker(Activity activity) {

        if (activity == null) {
            throw new IllegalArgumentException("activity cannot be null");
        }
        this.activity = activity;
    }

    public PermissionChecker callBack(PermissionsCallback callback) {
        permissionsCallback = callback;
        return this;
    }

    /**
     *  申请单个权限
     * author: yueyang
     **/
    public PermissionChecker requestPermission(String permission) {
        return requestPermissionList(new String[]{permission});
    }

    /**
     *  申请多个权限
     * author: yueyang
     **/
    public PermissionChecker requestPermissionList(String[] permissions) {

        if (permissions == null) {
            throw new IllegalArgumentException("permissions cannot be null");
        }

        // 已经获取到所有权限
        if (PermissionHelper.hasPermissions(activity,permissions)) {
            return this;
        }

        List<String> needExplainPermissionList = PermissionHelper.permissionNeedExplain(activity,permissions);
        //如果有不再提示的权限，则提示用户跳转到设置界面，否则执行获取对应的权限
        if (needExplainPermissionList.size() > 0) {
            // TODO: 2017/6/12  弹出对应的对话框
        } else {
            // 获取对应的权限信息
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_PERMISSIONS);
        }

        return this;
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            List<String> grantedList = new ArrayList<>();
            List<String> definedList = new ArrayList<>();

            for (int i = 0; i < grantResults.length; i++) {
                String currentPermission = permissions[i];
                if (PackageManager.PERMISSION_GRANTED == grantResults[i]) {
                    grantedList.add(currentPermission);
                } else {
                    definedList.add(currentPermission);
                }
            }

            // 所有的权限都已授权
            if (!grantedList.isEmpty() && definedList.isEmpty()) {
                permissionsCallback.onAllPermissionDefined();
            } else {
                permissionsCallback.onPermissionDefined(definedList);
            }
        }
    }
}