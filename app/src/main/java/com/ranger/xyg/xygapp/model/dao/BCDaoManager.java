package com.ranger.xyg.xygapp.model.dao;

import android.content.Context;
import android.text.TextUtils;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by xyg on 2017/4/25.
 */

public class BCDaoManager<T> {
    private static final String DB_NAME = "baichuan.db";

    private static BCDaoManager mManager;
    private DaoMaster mDaoMaster;
    private DaoMaster.DevOpenHelper mHelper;
    private DaoSession mDaoSession;
    private AbstractDao mDao;

    private BCDaoManager(Context context) {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), DB_NAME);
        }
    }

    public static BCDaoManager getInstance(Context context) {
        if (mManager == null) {
            synchronized (DaoMaster.class) {
                if (mManager == null) {
                    mManager = new BCDaoManager(context);
                }
            }
        }
        return mManager;
    }

    public BCDaoManager init(String pwd) {
        if (mDaoMaster == null) {
            if (TextUtils.isEmpty(pwd)) {
                mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
            } else {
                mDaoMaster = new DaoMaster(mHelper.getEncryptedWritableDb(pwd));
            }
        }
        mDaoSession = mDaoMaster.newSession();
        return mManager;
    }

    /**
     * 获取一个数据库连接，一个DaoMaster就代表着一个数据库的连接
     */
    public DaoMaster getDaoMaster() {
        if (mDaoMaster == null) {
            mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        }
        return mDaoMaster;
    }

    /**
     * 完成数据的添加、删除、修改、查询的操作
     *
     * @return DaoSession完成对Entity的基本操作和Dao操作类
     */
    public DaoSession getDaoSession() {
        if (mDaoSession == null) {
            if (mDaoMaster == null) {
                mDaoMaster = getDaoMaster();
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    /**
     * 设置Debug开启，默认关闭
     */
    public void setDebug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭数据库连接,数据库开启的时候，使用完毕了必须要关闭
     */
    public void closeConnection() {
        closeHelper();
        closeDaoSession();
    }

    private void closeDaoSession() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    private void closeHelper() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }

}
