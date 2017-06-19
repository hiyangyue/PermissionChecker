package com.yy.lib.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * author : yueyang
 * date : 2017/6/19
 */
public class Utils {

    /**
     * 打开应用信息界面
     * author: yueyang
     **/
    public static void gotoAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));

        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(localIntent, PermissionChecker.REQUEST_PERMISSIONS);
        } else {
            context.startActivity(localIntent);
        }
    }

    public static int dip2px(Context context,float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
