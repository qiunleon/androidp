package com.example.room.databases;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * 用于支持对存储在SD卡上的数据库的访问
 **/
public class DatabaseContext extends ContextWrapper {

    /**
     * 数据库存放路径
     */
    public static final String DB_SAVE_PATH = Environment.getExternalStorageDirectory() + "/kmbox/db";

    /**
     * 构造函数
     *
     * @param base 上下文环境
     */
    public DatabaseContext(Context base) {
        super(base);
    }

    /**
     * 获得数据库路径，如果不存在，则创建对象对象
     *
     * @param name
     */
    @Override
    public File getDatabasePath(String name) {

        String dbDir = DB_SAVE_PATH;

        File dirFile = new File(dbDir);
        if (!dirFile.exists()) {
            Log.e("QIU1", dbDir + " is not exist  ");
            if (!dirFile.mkdirs()) {
                Log.d("QIU2", "mkdir failed.");
                return null;
            }
        }

        String dbPath = dbDir + "/" + name;// 数据库路径
        // 判断文件是否存在，不存在则创建该文件
        File dbFile = new File(dbPath);
        if (!dbFile.exists()) {
            Log.e("QIU3", dbPath + " is not exist  ");
            try {
                if (!dbFile.createNewFile()) {
                    Log.e("QIU4",  "create failed.");
                    return null;
                } else {
                    Log.e("QIU5",  "create succeed.");
                    return dbFile;// 返回数据库文件对象
                }
            } catch (IOException e) {
                Log.e("QIU6",  "create exception.");
                e.printStackTrace();
                return null;
            }
        } else {
            return dbFile;// 返回数据库文件对象
        }
    }

    /**
     * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
     *
     * @param name
     * @param mode
     * @param factory
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, CursorFactory factory) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
    }

    /**
     * Android 4.0会调用此方法获取数据库。
     *
     * @param name
     * @param mode
     * @param factory
     * @param errorHandler
     * @see ContextWrapper#openOrCreateDatabase(String,
     * int, CursorFactory,
     * DatabaseErrorHandler)
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, CursorFactory factory, DatabaseErrorHandler errorHandler) {
        Log.d("QIU", name);
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
    }
}