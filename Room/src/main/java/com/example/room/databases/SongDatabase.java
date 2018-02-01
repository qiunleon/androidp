package com.example.room.databases;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.database.sqlite.SQLiteDatabase;

import com.example.room.application.RoomApplication;
import com.example.room.daos.SongDao;
import com.example.room.entities.Song;

@Database(entities = {Song.class}, version = 4)
public abstract class SongDatabase extends RoomDatabase {
    public abstract SongDao userDao();
}