package com.tsrj.qiezi.view.fragment.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tsrj.qiezi.view.widget.dialog.CustomProgressDialog;

import butterknife.ButterKnife;

//fragment的基础类，每个Fragment都继承此类
//view初始化和点击事件都用butterknife注解
public abstract class BaseFragment extends Fragment {
    //自定义加载对话框
    private CustomProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(getContentViewLayoutId(),container,false);
        ButterKnife.bind(this,view);
        onContentViewSeted();
        return view;
    }
    /**
     * 获取布局文件ID
     * @return layout id
     */
    public abstract int getContentViewLayoutId();

    /**
     * contentview设置之后可以做一些view的初始化
     */
    public abstract void onContentViewSeted();
    /**
     * 显示进度框
     *
     * @param activity
     * @param loadingMsg
     */
    protected CustomProgressDialog showProgress(Activity activity, String loadingMsg) {
        if (activity != null && !activity.isFinishing()) {
//            if (progressDialog != null) {
//                progressDialog.cancel();
//            }
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
    //消失对话框
    protected void dissMissProgressActivity(){
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
