package com.tsrj.qiezi.httprequest;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;


import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tsrj.qiezi.R;
import com.tsrj.qiezi.app.MyApplication;
import com.tsrj.qiezi.utils.NetUtil;
import com.tsrj.qiezi.utils.ToastUtil;
import com.tsrj.qiezi.utils.LogUtil;
import com.tsrj.qiezi.view.widget.dialog.CustomProgressDialog;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ResponseCallBack<T> extends BaseCallBack<T> implements Callback<T> {

    private Context mContext;

    private Object loadingView;

    //标记接口请求成功后，loading是否diss，默认都是true，如果是连续多个接口请求则需要false
    private boolean cancleLoading = true;
    /**
     * 数据请求加载进度
     */
//    private CustomProgressDialog progressDialog;

    /**
     * @param context
     * @param loadingView 进度条
     * @param msg
     */
    public ResponseCallBack(Context context, Object loadingView, String msg) {
        mContext = context;
        this.loadingView = loadingView;
        cancleLoading = true;
        if (this.loadingView != null) {
            showProgress(context, msg);
        }
    }
    public ResponseCallBack(Context context, Object loadingView, String msg,boolean cancleLoading) {
        mContext = context;
        this.loadingView = loadingView;
        this.cancleLoading = cancleLoading;
        if (this.loadingView != null) {
            showProgress(context, msg);
        }
    }

    @Override
    public void serverError(Response<?> response) {
        LogUtil.e("Error serverError:" + response.code() ,"--response.message:" + response.message());
        ToastUtil.show(MyApplication.getInstance(), MyApplication.getInstance().getString(R.string.request_server_error));
    }

    @Override
    public void unexpectedError(Throwable t) {
        LogUtil.e("Error unexpectedError:" , t.getMessage());

        ToastUtil.show(MyApplication.getInstance(), MyApplication.getInstance().getString(R.string.request_failed));
    }

    @Override
    public void networkError(IOException e) {
        ToastUtil.show(MyApplication.getInstance(),MyApplication.getInstance().getString(R.string.no_network));
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!call.isCanceled()) {
            int code = response.code();
            if (code >= 200 && code < 300) {
                success(response);
                if (response.body() != null) {
//                    LogUtil.info("response.raw()--->" + response.raw() + "");
                }
                if(cancleLoading){
                    if (loadingView != null) {
                        dissMissProgress();
                    }
                }
            } else if (code >= 400 && code < 600) {
                if (loadingView != null) {
                    dissMissProgress();
                }
                serverError(response);
            } else {
                if (loadingView != null) {
                    dissMissProgress();
                }
                unexpectedError(new RuntimeException("Unexpected response " + response));
            }
        }else {
            if (loadingView != null) {
                dissMissProgress();
            }

        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (loadingView != null) {
            dissMissProgress();
        }
        if (!NetUtil.isConnected()) {
            networkError((IOException) t);
        } else {
            unexpectedError(t);
        }
    }


    /**
     * 显示进度条
     *
     * @param context
     * @param loadingMsg
     */
    protected void showProgress(Context context, String loadingMsg) {
        if (context == null) {
            return;
        }
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        if (loadingView instanceof CustomProgressDialog) {
            if (!((CustomProgressDialog) loadingView).isShowing()) {
                ((CustomProgressDialog) loadingView).show();
            }
        } else if (loadingView instanceof SwipeRefreshLayout) {
            if (!((SwipeRefreshLayout) loadingView).isRefreshing()) {
                ((SwipeRefreshLayout) loadingView).setRefreshing(true);
            }

        }

    }

    protected void dissMissProgress() {
        if (mContext == null) {
            return;
        }
        if (mContext instanceof Activity && ((Activity) mContext).isFinishing()) {
            return;
        }
        if (loadingView instanceof CustomProgressDialog) {
//            mHandler.sendEmptyMessageDelayed(0, 300);
            ((CustomProgressDialog) loadingView).dismiss();
        } else if (loadingView instanceof SwipeRefreshLayout) {
            if (((SwipeRefreshLayout) loadingView).isRefreshing()) {
                ((SwipeRefreshLayout) loadingView).setRefreshing(false);
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            ((CustomProgressDialog) loadingView).dismiss();

        }
    };
}
