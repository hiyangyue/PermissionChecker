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

    public AlertDialog show() {
        return new AlertDialog.Builder(mContext)
                .setCancelable(isCancelable)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonName, positiveClickListener)
                .setNegativeButton(negativeButtonName, cancelClickListener)
                .show();
    }

    public static class Builder {

        private Context mContext;
        private String title, message, positiveButtonName, negativeButtonName;
        private boolean isCancelable = false;
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

        public Builder setPositiveButton(DialogInterface.OnClickListener onClickListener) {
            this.positiveClickListener = onClickListener;
            return this;
        }

        public Builder setNegativeButton(DialogInterface.OnClickListener onClickListener) {
            this.cancelClickListener = onClickListener;
            return this;
        }

        public PermissionDialog build() {
            title = TextUtils.isEmpty(title) ? "这里是标题" : title;
            message = TextUtils.isEmpty(message) ? "这里是提示" : message;
            positiveButtonName = TextUtils.isEmpty(positiveButtonName) ? "确定" : positiveButtonName;
            negativeButtonName = TextUtils.isEmpty(negativeButtonName) ? "取消" : negativeButtonName;
            return new PermissionDialog(mContext,
                    title,
                    message,
                    positiveButtonName,
                    negativeButtonName,
                    isCancelable,
                    positiveClickListener,
                    cancelClickListener);
        }
    }
}