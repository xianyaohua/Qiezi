package com.tsrj.qiezi.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tsrj.qiezi.R;
import com.tsrj.qiezi.utils.LogUtil;
import com.tsrj.qiezi.view.adapter.FlyTabFragmentPagerAdapter;
import com.tsrj.qiezi.view.fragment.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 */
public class IndexFragment extends BaseFragment {
    @BindView(R.id.tab_channel)
    SlidingTabLayout tabChannel;
    @BindView(R.id.btn_channel)
    AppCompatButton btnChannel;
    @BindView(R.id.vp_channel)
    ViewPager vpChannel;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "精选"
    };
    private FlyTabFragmentPagerAdapter mAdapter;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_index;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onContentViewSeted() {
        initView();
    }

    private void initView() {
        LogUtil.e("indexFragment:","初始化视图");
        for (int i=0; i<mTitles.length;i++) {
            if(i==0){
                mFragments.add(new IndexSpecialSelectFragment());
            }else{
                mFragments.add(new IndexChannelFragment());
            }
        }
        vpChannel.setOffscreenPageLimit(mFragments.size());
        mAdapter = new FlyTabFragmentPagerAdapter(getActivity().getSupportFragmentManager(),mFragments,mTitles);
        vpChannel.setAdapter(mAdapter);
        tabChannel.setViewPager(vpChannel);
        tabChannel.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpChannel.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                //再次选择一样的tab时
            }
        });
        vpChannel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabChannel.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabChannel.setCurrentTab(0);
        vpChannel.setCurrentItem(0);
    }

    private void initData() {

    }

    @OnClick(R.id.btn_channel)
    public void onViewClicked() {
    }
}
