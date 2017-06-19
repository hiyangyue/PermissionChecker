package com.yy.lib;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yy.lib.permission.Utils;

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
    private CompoundButton.OnCheckedChangeListener checkedChangeListener;
    private AlertDialog mAlertDialog;

    private PermissionDialog(Context context,
                             String title,
                             String message,
                             String positiveButtonName,
                             String negativeButtonName,
                             boolean isCancelable,
                             DialogInterface.OnClickListener positiveClickListener,
                             DialogInterface.OnClickListener cancelClickListener,
                             CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        mContext = context;
        this.title = title;
        this.message = message;
        this.positiveButtonName = positiveButtonName;
        this.negativeButtonName = negativeButtonName;
        this.isCancelable = isCancelable;
        this.positiveClickListener = positiveClickListener;
        this.cancelClickListener = cancelClickListener;
        this.checkedChangeListener = checkedChangeListener;
    }

    private AlertDialog create() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.CustomAlertDialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_permission, null);
        builder.setView(view, Utils.dip2px(mContext,19), 0, Utils.dip2px(mContext,19), 0);

        TextView tvMsg = (TextView) view.findViewById(R.id.tv_msg);
        AppCompatCheckBox checkBox = (AppCompatCheckBox) view.findViewById(R.id.checkbox);
        builder.setCancelable(false);
        builder.setTitle(title);
        tvMsg.setText(message);
        if (checkedChangeListener != null) {
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setOnCheckedChangeListener(checkedChangeListener);
        } else {
            checkBox.setVisibility(View.GONE);
        }
        builder.setPositiveButton(positiveButtonName, positiveClickListener);
        builder.setNegativeButton(negativeButtonName, cancelClickListener);
        return builder.create();
    }

    public void show() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) return;

        mAlertDialog = create();
        mAlertDialog.show();
    }

    public void dismiss() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
    }

    public static class Builder {

        private Context mContext;
        private String title, message, positiveButtonName, negativeButtonName;
        private boolean isCancelable = false;
        private boolean isGoToSetting = false;
        private DialogInterface.OnClickListener positiveClickListener;
        private DialogInterface.OnClickListener cancelClickListener;
        private CompoundButton.OnCheckedChangeListener mCheckedChangeListener;

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

        public Builder setCheckListener(CompoundButton.OnCheckedChangeListener mCheckedChangeListener) {
            this.mCheckedChangeListener = mCheckedChangeListener;
            return this;
        }

        public PermissionDialog create() {
            title = TextUtils.isEmpty(title) ? mContext.getString(R.string.request_permission) : title;
            message = TextUtils.isEmpty(message) ? mContext.getString(R.string.permission_reminder) : message;
            positiveButtonName = TextUtils.isEmpty(positiveButtonName) ? mContext.getString(R.string.sure) : positiveButtonName;
            negativeButtonName = TextUtils.isEmpty(negativeButtonName) ? mContext.getString(R.string.cancel) : negativeButtonName;

            if (isGoToSetting) {
                positiveClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.gotoAppDetailSettingIntent(mContext);
                        dialog.dismiss();
                    }
                };
            }

            return new PermissionDialog(mContext,
                    title,
                    message,
                    positiveButtonName,
                    negativeButtonName,
                    isCancelable,
                    positiveClickListener,
                    cancelClickListener,mCheckedChangeListener);
        }
    }
}