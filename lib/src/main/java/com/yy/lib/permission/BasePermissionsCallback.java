package com.yy.lib.permission;

import java.util.List;

/**
 * author : yueyang
 * date : 2017/6/12
 */
public interface BasePermissionsCallback {

    void onPermissionDefined(List<String> definedPermissions);

    void onAllPermissionDefined();

    void onPermissionNeedExplain(List<String> explainPermission);
}
