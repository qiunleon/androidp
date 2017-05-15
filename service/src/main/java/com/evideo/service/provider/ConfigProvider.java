package com.evideo.service.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.evideo.service.application.BaseApplication;
import com.evideo.service.constant.ConfigConstant;
import com.evideo.service.database.ConfigDB;

public class ConfigProvider extends ContentProvider{

    private static final UriMatcher mMatcher;

    private ConfigDB mConfigDB;

    private static final int SHARE = 1;

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(ConfigConstant.AUTHORITY, ConfigConstant.TABLE_NAME, SHARE);
    }

    @Override
    public boolean onCreate(){
        mConfigDB = new ConfigDB(this.getContext());
        mConfigDB.getWritableDatabase();
        return false;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (mMatcher.match(uri)) {
            case SHARE:
                return "1";
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri.toString());
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs){
        SQLiteDatabase db = mConfigDB.getWritableDatabase();
        if (db == null) {
            return 0;
        }
        switch (mMatcher.match(uri)) {
            case SHARE:
                ContentResolver cr = getContext().getContentResolver();
                if (cr != null) {
                    cr.notifyChange(uri, null);
                }
                return db.update(ConfigConstant.TABLE_NAME, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri.toString());
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = mConfigDB.getWritableDatabase();
        if (db == null) {
            return null;
        }
        switch (mMatcher.match(uri)) {
            case SHARE:
                long id = db.insert(ConfigConstant.TABLE_NAME, ConfigConstant.KEY, values);
                Uri insertUri = ContentUris.withAppendedId(uri , id);
                ContentResolver cr = getContext().getContentResolver();
                if (cr != null) {
                    cr.notifyChange(uri, null);
                }
                return insertUri;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri.toString());
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs){
        SQLiteDatabase db = mConfigDB.getWritableDatabase();
        if (db == null) {
            return 0;
        }
        switch (mMatcher.match(uri)) {
            case SHARE:
                return db.delete(ConfigConstant.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri.toString());
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mConfigDB.getReadableDatabase();
        if (db == null) {
            return null;
        }
        switch (mMatcher.match(uri)) {
            case SHARE:
                return db.query(ConfigConstant.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri.toString());
        }
    }
}

