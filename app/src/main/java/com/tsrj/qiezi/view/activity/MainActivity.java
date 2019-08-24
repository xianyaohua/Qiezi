package com.tsrj.qiezi.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tsrj.qiezi.R;
import com.tsrj.qiezi.entity.TabEntity;
import com.tsrj.qiezi.utils.LogUtil;
import com.tsrj.qiezi.view.activity.base.BaseActivity;
import com.tsrj.qiezi.view.adapter.FlyTabFragmentPagerAdapter;
import com.tsrj.qiezi.view.fragment.IndexFragment;
import com.tsrj.qiezi.view.fragment.MineFragment;
import com.tsrj.qiezi.view.fragment.RankingFragment;
import com.tsrj.qiezi.view.fragment.SpecialColumnFragment;
import com.tsrj.qiezi.view.widget.dialog.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    @BindView(R.id.tablayout)
    CommonTabLayout tablayout;
    private IndexFragment indexFragment;//首页
    private SpecialColumnFragment specialColumnFragment;//专栏
    private RankingFragment rankingFragment;//排行榜
    private MineFragment mineFragment;//我的
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private static String[] mTitles={"首页","专栏","排行","我的"};
    private int[] mIconUnselectIds = {
            R.drawable.tab_home_unselect,
            R.drawable.tab_speech_unselect,
            R.drawable.tab_contact_unselect,
            R.drawable.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.drawable.tab_home_select,
            R.drawable.tab_speech_select,
            R.drawable.tab_contact_select,
            R.drawable.tab_more_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(false);
        initView();
        initData();
    }


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    private void initView(){
        LogUtil.e("mainactivity:","初始化视图");
        indexFragment=new IndexFragment();
        specialColumnFragment =new SpecialColumnFragment();
        rankingFragment =new RankingFragment();
        mineFragment=new MineFragment();

        mFragments.add(indexFragment);
        mFragments.add(specialColumnFragment);
        mFragments.add(rankingFragment);
        mFragments.add(mineFragment);

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tablayout.setTabData(mTabEntities);
        tablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                //被选中的table再次被选中时
                if (position == 0) {

                    //tablayout.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        });
        viewPager.setAdapter(new FlyTabFragmentPagerAdapter(getSupportFragmentManager(),mFragments,mTitles));
        viewPager.setOffscreenPageLimit(mFragments.size());//防止回收出现异常，一定要加
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(0);
        tablayout.setCurrentTab(0);
    }
    private void initData(){

    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(false);//退到后台而不是finish掉
    }

}
