package com.ranger.xyg.xygapp.demos.ldia;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ranger.xyg.gyapp.IAppManager;
import com.ranger.xyg.gyapp.model.Book;
import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.activity.BaseActivity;
import com.ranger.xyg.library.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xyg on 2017/8/8.
 */

public class AidlClientActivity extends BaseActivity {

    private static final String TAG = AidlClientActivity.class.getSimpleName();
    @BindView(R.id.btn_get_aidl)
    Button btnGetAidl;
    @BindView(R.id.btn_add_aidl)
    Button btnAddAidl;
    @BindView(R.id.tv_aidl_result)
    TextView tvAidlResult;
    private IAppManager mAppManager;
    //标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private boolean mBound = false;

    @Override
    protected int getContentResId() {
        return R.layout.activity_aidl_client;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            tryBindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceImpl);
            mBound = false;
        }
    }

    private void tryBindService() {
        Intent intent = new Intent();
        intent.setAction("com.ranger.xyg.aidl.test");
        intent.setPackage("com.ranger.xyg.gyapp");
        bindService(intent, mServiceImpl, BIND_AUTO_CREATE);
    }

    @OnClick({R.id.btn_get_aidl, R.id.btn_add_aidl})
    public void onViewClicked(View view) {
        if (!mBound) {
            tryBindService();
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.btn_get_aidl:
                try {
                    tvAidlResult.setText(mAppManager.getBooks().toString());
                } catch (RemoteException e) {
                    LogUtils.e(TAG, "aidl getBooks error");
                }
                break;
            case R.id.btn_add_aidl:
                Book book = new Book();
                book.setName("xyg");
                book.setPrice(123);
                try {
                    mAppManager.addBook(book);
                } catch (RemoteException e) {
                    LogUtils.e(TAG, "aidl addBook error");
                }
                break;
        }
    }

    private ServiceConnection mServiceImpl = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mAppManager = IAppManager.Stub.asInterface(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mAppManager = null;
            mBound = false;
        }
    };

}
