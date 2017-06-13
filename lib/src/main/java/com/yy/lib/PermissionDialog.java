package com.yy.lib;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

/**
 * author : yueyang
 * date : 2017/6/12
 */
public class PermissionDialog {

    private Context mContext;
    private String title, message, positiveButtonName, negativeButtonName;
    private boolean isCancelable = false;
    private DialogInterface.OnClickListener positiveClickListener;
    private DialogInterface.OnClickListener cancelClickListener;

    private PermissionDialog(Context context,
                             String title,
                             String message,
                             String positiveButtonName,
                             String negativeButtonName,
                             boolean isCancelable,
                             DialogInterface.OnClickListener positiveClickListener,
                             DialogInterface.OnClickListener cancelClickListener) {
        mContext = context;
        this.title = title;
        this.message = message;
        this.positiveButtonName = positiveButtonName;
        this.negativeButtonName = negativeButtonName;
        this.isCancelable = isCancelable;
        this.positiveClickListener = positiveClickListener;
        this.cancelClickListener = cancelClickListener;
    }

    private AlertDialog create() {
        return new AlertDialog.Builder(mContext)
                .setCancelable(isCancelable)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonName, positiveClickListener)
                .setNegativeButton(negativeButtonName, cancelClickListener)
                .show();
    }

    private void show() {
        AlertDialog alertDialog = create();
        alertDialog.show();
    }

    public static class Builder {

        private Context mContext;
        private String title, message, positiveButtonName, negativeButtonName;
        private boolean isCancelable = false;
        private boolean isGoToSetting = false;
        private DialogInterface.OnClickListener positiveClickListener;
        private DialogInterface.OnClickListener cancelClickListener;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setPositiveButtonName(String positiveButtonName) {
            this.positiveButtonName = positiveButtonName;
            return this;
        }

        public Builder setNegativeButtonName(String negativeButtonName) {
            this.negativeButtonName = negativeButtonName;
            return this;
        }

        public Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        public Builder setGotoSetting(boolean gotoSetting) {
            this.isGoToSetting = gotoSetting;
            return this;
        }

        public Builder setPositiveButton(DialogInterface.OnClickListener onClickListener) {
            this.positiveClickListener = onClickListener;
            return this;
        }

        public Builder setNegativeButton(DialogInterface.OnClickListener onClickListener) {
            this.cancelClickListener = onClickListener;
            return this;
        }

        public void show() {
            title = TextUtils.isEmpty(title) ? "这里是标题" : title;
            message = TextUtils.isEmpty(message) ? "这里是提示" : message;
            positiveButtonName = TextUtils.isEmpty(positiveButtonName) ? "确定" : positiveButtonName;
            negativeButtonName = TextUtils.isEmpty(negativeButtonName) ? "取消" : negativeButtonName;

            if (isGoToSetting) {
                positiveClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2017/6/13 isGoToSetting
                    }
                };
            }

            new PermissionDialog(mContext,
                    title,
                    message,
                    positiveButtonName,
                    negativeButtonName,
                    isCancelable,
                    positiveClickListener,
                    cancelClickListener).show();
        }
    }
}