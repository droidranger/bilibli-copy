package com.ranger.xyg.xygapp.ui.fragment.home;

import android.os.Bundle;
import android.widget.TextView;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.fragment.BaseFragment;

import butterknife.BindView;

/**
 * Created by xyg on 2017/4/8.
 */
public class HomeAfterFragment extends BaseFragment {

    @BindView(R.id.tv_frag_name)
    TextView mFragNameTv;

    public static HomeAfterFragment newInstance(Bundle bundle) {
        HomeAfterFragment fragment = new HomeAfterFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentRes() {
        return R.layout.fragment_home_demo;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mFragNameTv.setText(HomeAfterFragment.class.getSimpleName());
    }
}
