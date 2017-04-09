package com.ranger.xyg.xygapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.demos.JSBridgeTestActivity;
import com.ranger.xyg.xygapp.demos.SampleHomeActivity;
import com.ranger.xyg.xygapp.demos.recycleview.RecyclerViewDemoActivity;
import com.ranger.xyg.xygapp.demos.rxjava.MoreRxDemosActivity;
import com.ranger.xyg.xygapp.demos.rxjava.RxSimpleActivity;
import com.ranger.xyg.xygapp.ui.fragment.home.HomeFragment;
import com.ranger.xyg.xygapp.ui.fragment.member.VipMemberFragment;
import com.ranger.xyg.xygapp.ui.view.XygDraweeView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private RelativeLayout mContentMain;
    private FloatingActionButton mFabBtn;
    private NavigationView mNavView;
    private DrawerLayout mDrawerLayout;
    private XygDraweeView mHeadView;
    private TextView mUserNameTv;
    private TextView mUserLevelTv;
    private TextView mUserTypeTv;

    private Fragment mContentFragment;
    private HomeFragment mHomeFragment;
    private VipMemberFragment mVipFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(mToolbar);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mContentMain = (RelativeLayout) findViewById(R.id.content_main);
        mFabBtn = (FloatingActionButton) findViewById(R.id.fab);
        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mHeadView = (XygDraweeView) findViewById(R.id.xdv_header_view);
        mUserNameTv = (TextView) findViewById(R.id.tv_user_name);
        mUserLevelTv = (TextView) findViewById(R.id.tv_user_level);
        mUserTypeTv = (TextView) findViewById(R.id.tv_user_type);

        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);

        stateCheck(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            switchContent(mContentFragment, mHomeFragment, "home");
        } else if (id == R.id.nav_my_member) {
            switchContent(mContentFragment, mVipFragment, "vip");
        } else if (id == R.id.nav_memeber_points) {

        } else if (id == R.id.nav_free_flow_service) {
            startActivity(new Intent(this, RecyclerViewDemoActivity.class));
        } else if (id == R.id.nav_outline_cache) {
            startActivity(new Intent(this, RecyclerViewDemoActivity.class));
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(this, MoreRxDemosActivity.class));
        } else if (id == R.id.nav_send) {
            startActivity(new Intent(this, RxSimpleActivity.class));
        } else if (id == R.id.nav_test) {
            Intent intent = new Intent(this, JSBridgeTestActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_test_home) {
            startActivity(new Intent(this, SampleHomeActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 状态检测 用于内存不足的时候保证fragment不会重叠
     */
    private void stateCheck(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mHomeFragment = HomeFragment.newInstance(null);
            mContentFragment = mHomeFragment;
            mVipFragment = VipMemberFragment.newInstance(null);
            getSupportFragmentManager().beginTransaction().add(R.id.content_main, mHomeFragment, "home").commit();
        } else {
            mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("home");
            mContentFragment = mHomeFragment;
            mVipFragment = (VipMemberFragment) getSupportFragmentManager().findFragmentByTag("vip");
            getSupportFragmentManager().beginTransaction().show(mHomeFragment).hide(mVipFragment).commit();
        }
    }

    public void switchContent(Fragment from, Fragment to, String tag) {
        if (mContentFragment != to) {
            mContentFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in, R.anim.slide_out);
            if (!to.isAdded()) {	// 先判断是否被add过
                transaction.hide(from).add(R.id.content_main, to, tag).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
}
