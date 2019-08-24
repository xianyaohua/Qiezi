package com.tsrj.qiezi.utils;

import android.util.DisplayMetrics;

import com.tsrj.qiezi.app.MyApplication;


/**
 * 单位换算
 */
public class DensityUtil {

    public static int dip2px(float dpValue) {
        final float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取手机的密度
     */
    public static float getDensity() {
        DisplayMetrics dm = MyApplication.getContext().getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * 获取手机屏幕宽度
     */
    public static int getScreenWidth() {
        DisplayMetrics dm = MyApplication.getContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }
    /**
     * 获取手机屏幕宽度
     */
    public static int getScreenHeight() {
        DisplayMetrics dm = MyApplication.getContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取状态了高度
     * **/
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = MyApplication.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = MyApplication.getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


 /*   public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }*/

    public static int sp2px(float var1) {
        float var2 = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (var1 * var2 + 0.5F);
    }

    public static int px2sp(float var1) {
        float var2 = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (var1 / var2 + 0.5F);
    }
}
