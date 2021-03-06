package com.helen.andbase.utils;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.util.List;

/**
 * Created by Helen on 2015/10/15.
 * 系统环境Util
 */
public class EnvironmentUtil {

    /**
     *
     *  Helen
     * 2014-8-26 下午4:07:19
     * 判断网络连接是否正常
     */
    public static boolean isNetworkConnected(Context context){
        ConnectivityManager manager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.isConnected();
    }

    /**
     * 是否是WiFi联网
     */
    public static boolean isWIFIConnected(Context context){
        ConnectivityManager manager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return info != null && info.isAvailable() && info.isConnected();
    }

    /** 判断MOBILE网络是否可用  */
    public static boolean isMobileConnected(Context context) {
        ConnectivityManager manager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return info != null && info.isAvailable() && info.isConnected();
    }

    /**
     * 打开设置网络界面
     */
    public static void setNetworkMethod(final Context context){
        //提示对话框
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?").setPositiveButton("设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                context.startActivity(intent);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    /**
     * 强制帮用户打开GPS
     */
    public static void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /** 是否开启GPS定位功能 */
    public static boolean isOpenGPS(Context context){
        LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return alm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 获取包名
     */
    public static String getPackageName(Context context){
        return context.getPackageName();
    }

    /**
     * 根据进程id获取进程名称
     * @param context context
     * @param pid 进程id
     */
    public static String getProcessName(Context context, int pid){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
