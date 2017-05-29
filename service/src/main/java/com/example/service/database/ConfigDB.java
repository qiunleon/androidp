package com.example.service.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.service.constant.ConfigConstant;

public class ConfigDB extends SQLiteOpenHelper {

    public ConfigDB(Context context) {
        super(context, ConfigConstant.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + ConfigConstant.TABLE_NAME + "(" +
                ConfigConstant.ID + " integer primary key autoincrement," +
                ConfigConstant.KEY + " text," +
                ConfigConstant.VALUE + " text" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
