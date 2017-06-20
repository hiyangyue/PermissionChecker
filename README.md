# PermissionChecker
### 在AndroidManifest.xml中申明权限
```
<uses-permission android:name="android.permission.CAMERA" />
```

### 初始化PermissionChecker

```
private PermissionChecker mPermissionChecker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ...
        mPermissionChecker = new PermissionChecker(this);
        mPermissionChecker.requestPermission(PermissionType.CAMERA_PERMISSION, new BasePermissionsCallback() {
             @Override
             public void onPermissionDefined(List<String> definedPermissions) {

             }

            @Override
            public void onAllPermissionGranted() {

            }

            @Override
            public void onPermissionNeedExplain(String explainPermission) {

            }
        });
    }
```

如果用户点了“Never Ask Again”，则回调中的explainPermissions的size()方法的大小，为”Never Ask Aganin”的数量，这种情况下，优先跳转到应用设置界面。

当申请的所有的权限被授予，或者必要权限被授予，非必要权限全部点了PermissionDialog中的不再提示，这种情况下，会执行onAllPermissionGranted()这个方法

### 重写Activity/Fragment的onRequestPermissionsResult && onActivityResult方法
onRequestPermissionsResult ：
```
@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionChecker.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
```

onActivityResult  :

```
 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 用户点完去设置以后，还需要重新检查权限是否已经被正确授予
        if (requestCode == PermissionType.PERMISSION_REQUEST_CODE) {
            requestPermission(delayMillis);
        }
    }
```

（*无需做其他的额外操作*）

### PermissionDialog用法
```
   PermissionDialog.Builder builder = new PermissionDialog.Builder(this);
   builder.setTitle(title); // 标题
   builder.setMessage(message); //message
   builder.setGotoSetting(isGoToSetting);//是否去设置界面
   builder.setPositiveButtonName("去开启");//确定按钮的名字
   builder.setNegativeButtonName("取消");//取消按钮的名字
   builder.setPositiveButton(...); //确定按钮的点击事件
   builder.setNegativeButton(...); //取消按钮的点击事件
   builder.setCheckListener(...); //不在提示选择框的点击事件
   builder.setCancelable(isCancelable); //点击外部区域是否可以隐藏
   builder.create().show();
```


