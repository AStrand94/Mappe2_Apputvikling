package com.example.astrand.mappe2_s305036.sms_service;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;


public class PermissionHelper {

    public static final String SMS_PERMISSION = Manifest.permission.SEND_SMS;
    private final static String PHONE_STATE_PERMISSION = Manifest.permission.READ_PHONE_STATE;
    private final static String BOOT_PERMISSION = Manifest.permission.RECEIVE_BOOT_COMPLETED;

    public static final int PERMISSION_CODE = 1;

    public void loopPermissions(Activity activity){

        ArrayList<String> permissions = new ArrayList<>();

        if (!hasPermission(activity,SMS_PERMISSION)){
            permissions.add(SMS_PERMISSION);
        }

        if (!hasPermission(activity,PHONE_STATE_PERMISSION)){
            permissions.add(PHONE_STATE_PERMISSION);
        }

        if (!hasPermission(activity,BOOT_PERMISSION)){
            permissions.add(BOOT_PERMISSION);
        }

        if (!permissions.isEmpty()) requestPermissions(activity, PERMISSION_CODE, permissions.toArray(new String[permissions.size()]));
    }

    private void requestPermissions(Activity activity, int requestCode, String... permissions){
        ActivityCompat.requestPermissions(activity,permissions,requestCode);
    }

    public void promptSMSPermission(Activity activity){
        requestPermissions(activity,PERMISSION_CODE,SMS_PERMISSION);
    }

    private static boolean hasPermission(Context context, String permission){
        return ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasSendSMSPermission(Context context){
        return hasPermission(context,SMS_PERMISSION);
    }

}
