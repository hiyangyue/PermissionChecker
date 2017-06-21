package com.yy.permissionchecker;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.yy.lib.permission.BasePermissionsCallback;
import com.yy.lib.permission.PermissionChecker;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    private static final String WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private PermissionChecker mPermissionChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPermissionChecker = new PermissionChecker(this);
    }

    public void test(View view) {
        String[] permissions = new String[]{CAMERA_PERMISSION,WRITE_PERMISSION};
        mPermissionChecker.requestPermissionList(permissions, new BasePermissionsCallback() {
            @Override
            public void onPermissionDefined(List<String> definedPermissions) {
                Log.e("onPermissionDefined","  size : " + definedPermissions.size());
            }

            @Override
            public void onAllPermissionGranted() {
                Log.e("onAllPermissionGranted","");
            }

            @Override
            public void onPermissionNeedExplain(String explainPermission) {
                Log.e("onPermissionNeedExplain","   " + explainPermission);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionChecker.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 ) {
            Log.e("onActivityResult","____");
        }
    }
}