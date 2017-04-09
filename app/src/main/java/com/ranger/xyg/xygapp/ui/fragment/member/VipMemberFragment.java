package com.ranger.xyg.xygapp.ui.fragment.member;

import android.os.Bundle;
import android.widget.TextView;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.fragment.BaseFragment;
import com.ranger.xyg.xygapp.ui.fragment.home.HomeLiveFragment;

import butterknife.BindView;

/**
 * Created by xyg on 2017/4/8.
 */
public class VipMemberFragment extends BaseFragment {

    @BindView(R.id.tv_frag_name)
    TextView mFragNameTv;

    public static VipMemberFragment newInstance(Bundle bundle) {
        VipMemberFragment fragment = new VipMemberFragment();
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
        mFragNameTv.setText(HomeLiveFragment.class.getSimpleName());
    }
}
