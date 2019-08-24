package com.tsrj.qiezi.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tsrj.qiezi.R;
import com.tsrj.qiezi.view.activity.base.BaseActivity;
import com.tsrj.qiezi.view.util.MyStatusBarUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 欢迎页
 */
public class WelcomActivity extends BaseActivity {
    @BindView(R.id.iv_wel_bg)
    ImageView ivWelBg;
    @BindView(R.id.tv_tip)
    TextView tvTip;

    private Timer timer;
    private int MAX_TIMES =5;//暂留时间5秒
    private int times;
    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(false);
        MyStatusBarUtil.transparencyBar(this);
        init();
    }

    private void init(){
        times=MAX_TIMES;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTip(times);
                    }
                });
                if(times==0){
                    timer.cancel();
                    timer=null;
                    return;
                }
                times--;
            }
        },1000,1000);
    }

    /**
     *
     * @param times 剩余多少秒
     */
    private void setTip(int times){
        if(times>0){
            tvTip.setEnabled(false);
            tvTip.setText(times+"");
        }else {
            tvTip.setEnabled(true);
            tvTip.setText("跳过");
        }
    }
    @OnClick({R.id.iv_wel_bg, R.id.tv_tip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_wel_bg:

                break;
            case R.id.tv_tip:
                startActivity(new Intent(this,MainActivity.class));
                this.finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
