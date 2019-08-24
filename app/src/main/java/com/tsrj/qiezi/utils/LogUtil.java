package com.tsrj.qiezi.utils;

import android.content.Context;
import android.util.Log;

public class LogUtil {
    private static boolean isDebug=true;
    public static void e(String tag, String msg){
        if(isDebug){
            Log.e(tag,msg);
        }
    }
    public static void info(String tag, String msg){
        if(isDebug){
            Log.i(tag,msg);
        }
    }
}
