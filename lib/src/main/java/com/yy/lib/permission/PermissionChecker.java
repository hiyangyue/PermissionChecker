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
    private BasePermissionsCallback mPermissionsCallback;

    private int REQUEST_PERMISSIONS = 11131;

    private PermissionChecker() {}

    public PermissionChecker(Activity activity) {

        if (activity == null) {
            throw new IllegalArgumentException("activity cannot be null");
        }
        this.activity = activity;
    }

    /**
     *  申请单个权限
     * author: yueyang
     **/
    public PermissionChecker requestPermission(String permission,BasePermissionsCallback callback) {
        return requestPermissionList(new String[]{permission},callback);
    }

    /**
     *  申请多个权限
     * author: yueyang
     **/
    public PermissionChecker requestPermissionList(String[] permissions,BasePermissionsCallback callback) {

        if (permissions == null) {
            throw new IllegalArgumentException("permissions cannot be null");
        }

        mPermissionsCallback = callback;

        // 已经获取到所有权限
        if (PermissionHelper.hasPermissions(activity,permissions)) {
            return this;
        }

        ActivityCompat.requestPermissions(activity, permissions, REQUEST_PERMISSIONS);
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
                    // 用户选择了不再提示
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, currentPermission)) {
                        mPermissionsCallback.onPermissionNeedExplain(currentPermission);
                    } else {
                        definedList.add(currentPermission);
                    }
                }
            }

            // 所有的权限都已授权
            if (!grantedList.isEmpty() && definedList.isEmpty()) {
                mPermissionsCallback.onAllPermissionGranted();
            } else {
                mPermissionsCallback.onPermissionDefined(definedList);
            }
        }
    }
}