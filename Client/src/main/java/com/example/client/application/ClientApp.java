package com.example.client.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;

import com.example.client.dao.DaoMaster;
import com.example.client.dao.DaoSession;
import com.example.client.pattern.servicelocator.AvServiceLocator;
import com.example.client.pattern.servicelocator.EvService;
import com.example.client.pattern.servicelocator.EvServiceLocator;
import com.example.client.util.Utils;

/**
 * 应用基类
 */
public class ClientApp extends Application {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static ClientApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Utils.init(this);
        setDatabase();

        AvServiceLocator avServiceLocator = AvServiceLocator.getInstance();
        avServiceLocator.showCacheService();
        avServiceLocator.init();
        avServiceLocator.showCacheService();
        EvService serviceA = avServiceLocator.getService("serviceImpla");
        serviceA.execute();
        EvService serviceB = avServiceLocator.getService("serviceImplb");
        serviceB.execute();
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public static ClientApp getInstance() {
        return sInstance;
    }

    private static class HandlerHolder {
        private static final Handler sHandler = new Handler(Looper.getMainLooper());
    }

    public static Handler getHandler() {
        return HandlerHolder.sHandler;
    }
}
