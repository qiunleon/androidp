package com.example.service.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.service.application.BaseApplication;
import com.example.service.constant.ConfigConstant;

public class ConfigManager {

    private static final String SELECT_STR = "content://" + ConfigConstant.AUTHORITY + "/" + ConfigConstant.TABLE_NAME;

    private static final Uri SELECT_URI = Uri.parse(SELECT_STR);

    private static final String SELECT = "key=?";

    public static ConfigManager getInstance() {
        return ConfigManagerHolder.sInstance;
    }

    private static class ConfigManagerHolder {
        private static final ConfigManager sInstance = new ConfigManager();
    }

    public boolean putBoolean(String key, boolean value) {
        String stringValue = String.valueOf(value);
        return put(key, stringValue);
    }

    private boolean put(String key, String value) {
        ContentResolver cr = BaseApplication.getInstance().getContentResolver();
        if (cr == null) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConfigConstant.KEY, key);
        contentValues.put(ConfigConstant.VALUE, value);
        if (contain(key)) {
            cr.update(SELECT_URI, contentValues, SELECT, new String[]{key});
        } else {
            cr.insert(SELECT_URI, contentValues);
        }
        return true;
    }

    private boolean contain(String key) {
        ContentResolver cr = BaseApplication.getInstance().getContentResolver();
        Cursor c = cr.query(SELECT_URI, null, SELECT, new String[]{key}, null, null);
        if (c == null) {
            return false;
        }
        if (!c.moveToFirst()) {
            c.close();
            return false;
        }
        c.close();
        return true;
    }

    public boolean getBoolean(String key, boolean def) {
        ContentResolver cr = BaseApplication.getInstance().getContentResolver();
        Cursor c = cr.query(SELECT_URI, null, SELECT, new String[]{key}, null, null);
        if (c == null) {
            return def;
        }
        if(!c.moveToFirst()){
            c.close();
            return def;
        }
        int id = c.getColumnIndex(ConfigConstant.VALUE);
        String value = c.getString(id);
        c.close();
        return Boolean.valueOf(value);
    }
}
