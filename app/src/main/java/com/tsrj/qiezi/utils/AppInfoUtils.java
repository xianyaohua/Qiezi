package com.tsrj.qiezi.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 版本检测
 * Author: zhuxiaohong
 * Date: 2017/6/12 10:57
 */
public class AppInfoUtils {

    /**
     * 获取当前客户端版本号
     */
    public static int getCurVersionCode(Context mContext) {
        int curVersionCode = -1;
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
            //            curVersionName = info.versionName;
            curVersionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        return curVersionCode;
    }


    /**
     * 获取当前客户端版本信息
     */
    public static String getCurVersionName(Context mContext) {
        String curVersionName = "";
        try {
            curVersionName = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        return curVersionName;
    }

    /**
     * 获取当前设备androidid
     */
    public static String getAndroidID(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    /**
     * 获取TD充值事件所需参数
     *//*
    public static String getIDJson(Context context) {
        //TD 参数
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("platform", 1);//平台 1：android；2：ios
            jsonObject.put("account", PreferencesUtils.getShowID());
            jsonObject.put("osversion", Build.VERSION.RELEASE);//OS版本 Android 4.2
            jsonObject.put("tdid", TalkingDataAppCpa.getDeviceId(context));//TalkingData的唯一设备标识号
            jsonObject.put("androidid", AppInfoUtils.getAndroidID(context));
            jsonObject.put("manufacturer", Build.MANUFACTURER);//厂商 如：“Xiaomi”
            jsonObject.put("devicemodel", Build.MODEL);//设备类型 如：“iPhone5,2”
        } catch (JSONException e) {
        }
        return jsonObject.toString();
    }*/
}
