package com.ranger.xyg.demos.shortcuts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ranger.xyg.library.utils.SPUtils;
import com.ranger.xyg.library.utils.ToastUtils;
import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.activity.BaseActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ranger.xyg.xygapp.XygApplication.HAS_SHORTCUTS_API;

/**
 * Created by xingyugang on 2017/10/19.
 */

public class ShortcutsDemoActivity extends BaseActivity {

    @BindView(R.id.btn_open_baidu)
    Button mOpenBaiduBtn;
    @BindView(R.id.btn_remove_baidu)
    Button mRemoveBaiduBtn;

    private ShortcutManager mShortcutsManager;

    @Override
    protected int getContentResId() {
        return R.layout.activity_demo_shortcuts;
    }

    @Override
    protected void initData() {
        super.initData();
        initShortcutsManager();
    }

    private void initShortcutsManager() {
        if (!SPUtils.getBoolean(this, HAS_SHORTCUTS_API, false)) {
            ToastUtils.showShort(this, R.string.txt_shortcuts_has_no_api);
            return;
        }
        mShortcutsManager = (ShortcutManager) getSystemService(Context.SHORTCUT_SERVICE);
    }

    @OnClick({R.id.btn_open_baidu, R.id.btn_remove_baidu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open_baidu:
                addShortcut();
                break;
            case R.id.btn_remove_baidu:
                removeShortcut();
                break;
        }
    }

    private void addShortcut() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            List<ShortcutInfo> infoList = mShortcutsManager.getDynamicShortcuts();
            String shortcutId = null;
            for (ShortcutInfo shortcutInfo : infoList) {
                if (ShortcutsId.WEB_DEV_BAIDU.equals(shortcutInfo.getId())) {
                    shortcutId = shortcutInfo.getId();
                }
            }
            if (shortcutId == null) {
                ShortcutInfo shortcut = new ShortcutInfo.Builder(this, ShortcutsId.WEB_DEV_BAIDU)
                        .setShortLabel(getString(R.string.short_label_baidu))
                        .setLongLabel(getString(R.string.long_label_baidu))
                        .setIcon(Icon.createWithResource(this, R.drawable.baidu_jgylogo3))
                        .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com/")))
                        .build();
                mShortcutsManager.addDynamicShortcuts(Arrays.asList(shortcut));
                Toast.makeText(this, R.string.add_shortcut_success, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.shortcut_already_exist, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.txt_shortcuts_has_no_api, Toast.LENGTH_SHORT).show();
        }
    }

    private void removeShortcut() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            List<ShortcutInfo> infoList = mShortcutsManager.getDynamicShortcuts();
            String shortcutId = null;
            for (ShortcutInfo shortcutInfo : infoList) {
                if (ShortcutsId.WEB_DEV_BAIDU.equals(shortcutInfo.getId())) {
                    shortcutId = shortcutInfo.getId();
                }
            }
            if (shortcutId == null) {
                Toast.makeText(this, R.string.shortcut_not_exist, Toast.LENGTH_SHORT).show();
            } else {
                mShortcutsManager.removeDynamicShortcuts(Arrays.asList(shortcutId));
                Toast.makeText(this, R.string.remove_shortcut_success, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.txt_shortcuts_has_no_api, Toast.LENGTH_SHORT).show();
        }
    }
}
