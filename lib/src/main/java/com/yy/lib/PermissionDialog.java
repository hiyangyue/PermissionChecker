package com.yy.lib;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

/**
 * author : yueyang
 * date : 2017/6/12
 */
public class PermissionDialog extends Dialog{

    public PermissionDialog(@NonNull Context context) {
        super(context);
    }

    public PermissionDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
}
