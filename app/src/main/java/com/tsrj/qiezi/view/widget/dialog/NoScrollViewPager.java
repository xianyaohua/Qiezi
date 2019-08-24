package com.tsrj.qiezi.view.widget.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * 禁止滑动的viewpager
 * 设置isNoScroll为false，则可以滑动
 */
public class NoScrollViewPager extends ViewPager {
    private boolean isNoScroll=true;
    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isNoScroll() {
        return isNoScroll;
    }

    public void setNoScroll(boolean noScroll) {
        isNoScroll = noScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (isNoScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isNoScroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }


}
