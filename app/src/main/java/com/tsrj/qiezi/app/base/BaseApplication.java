package com.tsrj.qiezi.app.base;

import android.app.Application;

import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * app基类
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }
    private void init(){
        initLiveEventBus();

    }
    //初始化消息总线
    private void initLiveEventBus(){
        LiveEventBus.get()
                .config()
                .supportBroadcast(this)
                .lifecycleObserverAlwaysActive(true)
                .autoClear(false);
    }

}
