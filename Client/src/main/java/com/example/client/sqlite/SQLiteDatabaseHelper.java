package com.example.client.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Qiu on 2017/11/21.
 */

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static int version = 1;

    private static String name = "androidp";

    public SQLiteDatabaseHelper(Context context) {
        super(new DatabaseContext(context), name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists person (id integer primary key autoincrement, name varchar(20), age integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("alert table person add phone varchar(20)");
    }
}
