package com.tsrj.qiezi.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.multidex.MultiDex;

import com.tsrj.qiezi.app.base.BaseApplication;
import com.tsrj.qiezi.network.NetStateChangeReceiver;
import com.tsrj.qiezi.utils.CrashHandler;
import com.tsrj.qiezi.utils.LogUtil;

public class MyApplication extends BaseApplication {
    private static Context context;
    private static MyApplication instance;
    public boolean isBackGround = true;//APP是否在前端展示
    @Override
    public void onCreate() {
        super.onCreate();
        context=this.getApplicationContext();
        instance=this;
        //initCrashHandler();
        // 注册网络BroadcastReceiver
        NetStateChangeReceiver.registerReceiver(this);
        //监听生命周期
        this.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);

    }
    public static Context getContext(){
        return context;
    }
    public static MyApplication getInstance(){
        return instance;
    }

    private void initCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext(), this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        // 注销网络BroadcastReceiver
        NetStateChangeReceiver.unregisterReceiver(this);
        System.exit(0);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            isBackGround = true;
            LogUtil.info("isBackGround:", "APP进入后台");
        }
    }



    ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            if (isBackGround) {
                isBackGround = false;
                LogUtil.info("isBackGround:" , "APP回到前台");
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };
}
