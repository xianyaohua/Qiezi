package com.tsrj.qiezi.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresPermission;

import com.tsrj.qiezi.app.MyApplication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class AppManager {

    private ArrayList<Activity> activityStack;
    private static AppManager instance;
    private Context mContext = null;
    public final static String packagename = MyApplication.getContext().getPackageName();

    private AppManager() {
        this.mContext = MyApplication.getInstance();
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 判断一个Activity 是否存在
     *
     * @param clz
     * @return
     */
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public <T extends Activity> boolean isActivityExist(Class<T> clz) {
        boolean res;
        Activity activity = getActivity(clz);
        if (activity == null) {
            res = false;
        } else {
            if (activity.isFinishing()
                    || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
                res = false;
            } else {
                res = true;
            }
        }

        return res;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new ArrayList<>();
        }
        activityStack.add(activity);
    }

//    public void getActivityStack() {
//        Iterator<Activity> iterator = activityStack.iterator();
//        while (iterator.hasNext()) {
//            Activity activity = iterator.next();
//            LogUtil.info("getActivityStack activity:"+activity.getLocalClassName());
//        }
//    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack.size() > 0) {
            return activityStack.get(activityStack.size() - 1);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if (activityStack.size() > 0) {
            Activity activity = activityStack.get(activityStack.size() - 1);
            finishActivity(activity);
        } else {
            throw new NoSuchElementException();
        }

    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && activityStack.contains(activity)) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    private static final byte[] lock = new byte[0];

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        Iterator<Activity> iterator = activityStack.iterator();
        synchronized (lock) {
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                if (cls.equals(activity.getClass())) {
                    activity.finish();
                    iterator.remove();
//                    finishActivity(activity);
                }
            }
        }
    }

    /**
     * 获取指定类名的Activity
     */
    public Activity getActivity(Class<?> cls) {
        if (activityStack == null) {
            return null;
        }
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (cls.equals(activity.getClass())) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack != null) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }
    }

    /**
     * 结束所有Activity 除了main
     */
    public void finishAllActivityExceptMain() {
        if (activityStack != null) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                Activity activity = activityStack.get(i);
                if (null != activity && !activity.getLocalClassName().contains("MainActivityNew")) {
                    activity.finish();
                }
            }
            activityStack.clear();
        }
    }


    /**
     * 退出应用程序
     */
    @RequiresPermission ("android.permission.KILL_BACKGROUND_PROCESSES")
    public void AppExit() {
        finishAllActivity();
        ActivityManager activityMgr = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        activityMgr.killBackgroundProcesses(mContext.getPackageName());
        System.exit(0);
    }

    /**
     * 获取当前进程名
     *
     * @param context
     * @return 进程名
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;

                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 判断应用是否已经启动
     *
     * @return boolean
     */
    public static boolean isAppAlive() {
        ActivityManager activityManager =
                (ActivityManager) MyApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(packagename)) {
//                Log.i("NotificationLaunch",
//                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
//        Log.i("NotificationLaunch",
//                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }

    public boolean serviceWorked(String className) {
        ActivityManager myManager = (ActivityManager) MyApplication.getContext()
                .getApplicationContext().getSystemService(
                        Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断手机设备是否安装指定包名的apk应用程序
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isSpecialApplInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}