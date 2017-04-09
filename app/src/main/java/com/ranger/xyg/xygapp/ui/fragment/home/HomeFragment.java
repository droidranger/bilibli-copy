package com.ranger.xyg.xygapp.ui.fragment.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.fragment.BaseFragment;
import com.ranger.xyg.xygapp.ui.fragment.TabWithFragInfo;
import com.ranger.xyg.xygapp.ui.view.common.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xyg on 2017/4/8.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    TabLayout mHomeTabView;
    @BindView(R.id.vp_home_viewpager)
    ViewPager mViewPager;

    private MyPagerAdapter mAdapter;
    private final ArrayList<TabWithFragInfo> mFragViewList = new ArrayList<>();

    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {
        super.initViews();
        String[] tabArray = getResources().getStringArray(R.array.HomeTabArray);
        ArrayList<Fragment> fragList = getFragList();
        if (fragList == null || fragList.isEmpty()) {
            return;
        }
        for (int i = 0; i < tabArray.length; i++) {
            TabWithFragInfo info = new TabWithFragInfo();
            info.tabTitle = tabArray[i];
            info.fragment = fragList.get(i);
            mFragViewList.add(info);
        }
        mAdapter = new MyPagerAdapter(getFragmentManager(), mFragViewList);
        mViewPager.setAdapter(mAdapter);
        mHomeTabView.setupWithViewPager(mViewPager);
    }

    private ArrayList<Fragment> getFragList() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        Fragment liveFragment = HomeLiveFragment.newInstance(null);
        fragments.add(liveFragment);
        Fragment recommendFragment = HomeRecommendFragment.newInstance(null);
        fragments.add(recommendFragment);
        Fragment afterFragment = HomeAfterFragment.newInstance(null);
        fragments.add(afterFragment);
        Fragment partitionFragment = HomePartitionFragment.newInstance(null);
        fragments.add(partitionFragment);
        Fragment dynamicFragment = HomeDynamicFragment.newInstance(null);
        fragments.add(dynamicFragment);
        Fragment finderFragment = HomeFinderFragment.newInstance(null);
        fragments.add(finderFragment);
        return fragments;
    }

    private static class MyPagerAdapter extends FragmentPagerAdapter {

        private List<TabWithFragInfo> mFragInfoList;

        MyPagerAdapter(FragmentManager fm, List<TabWithFragInfo> fragInfoList) {
            super(fm);
            mFragInfoList = fragInfoList;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragInfoList.get(position).tabTitle;
        }

        @Override
        public int getCount() {
            return mFragInfoList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragInfoList.get(position).fragment;
        }

    }
}
