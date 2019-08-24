package com.tsrj.qiezi.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tsrj.qiezi.R;
import com.tsrj.qiezi.view.activity.base.BaseActivity;

/**
 * 加载页，过渡
 */
public class SplashActivity extends BaseActivity {
    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isTaskRoot()){
            finish();
            return;
        }
        startActivity(new Intent(this,WelcomActivity.class));
        this.finish();
    }

    @Override
    public void onBackPressed() {

    }
}
