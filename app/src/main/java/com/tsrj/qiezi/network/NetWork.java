package com.tsrj.qiezi.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.RequiresPermission;

/**
 * Created by Administrator on 2015-04-23.
 */
public class NetWork {

    private Context context;

    public NetWork(Context context) {
        this.context = context;
    }

    /**
     * 判断是否有网络连接
     * <p/>
     * *
     */
    @RequiresPermission ("android.permission.ACCESS_NETWORK_STATE")
    public boolean isNetworkConnected() {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) this.context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
