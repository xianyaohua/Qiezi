package com.tsrj.qiezi.view.activity.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import com.tsrj.qiezi.R;
import com.tsrj.qiezi.network.NetWork;
import com.tsrj.qiezi.utils.AppManager;
import com.tsrj.qiezi.view.util.MyStatusBarUtil;
import com.tsrj.qiezi.view.widget.dialog.CustomProgressDialog;
import com.tsrj.qiezi.view.widget.dialog.CustomToolbar;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

//activity基础类，所有activity都继承自此类
public abstract class BaseActivity extends SwipeBackActivity {
    //自定义加载对话框，所有方法写在对话框类内部
    private CustomProgressDialog progressDialog;//加载进度框
    //网络状态监听
    protected NetWork netWork;
    //滑动关闭
    protected SwipeBackLayout mSwipeBackLayout;
    //activity的管理
    protected AppManager appManager;
    //标题栏
    protected CustomToolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayoutId());

        //StatusBarUtil.setTransparent(this);
        MyStatusBarUtil.myStatusBar(this, getResources().getColor(R.color.white));
        //StatusBarUtil.setColorForSwipeBack(this,getResources().getColor(R.color.white),200);
        //StatusBarUtil.set
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL,EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        appManager = AppManager.getAppManager();
        appManager.addActivity(this);
        netWork = new NetWork(this);
        ButterKnife.bind(this);
        toolbar = (CustomToolbar) findViewById(R.id.toolbar_view);
        if (toolbar != null) {
            toolbar.setTitle("");//去掉默认居左的title
            toolbar.setLeftTitleClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        setupActionBar();
    }

    /**
     * 获取布局文件ID
     * @return layout id
     */
    public abstract int getContentViewLayoutId();

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    public void transparencyBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            );
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 显示进度条
     *
     * @param activity
     * @param loadingMsg
     */
    protected CustomProgressDialog showProgress(Activity activity, String loadingMsg) {
        if (activity != null && !activity.isFinishing()) {
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }

            } else {
                progressDialog = new CustomProgressDialog(activity, loadingMsg);
                progressDialog.show();
            }
        }

        return progressDialog;
    }

    protected void dissMissProgressActivity() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        AppManager.getAppManager().finishActivity(this);
    }
}
