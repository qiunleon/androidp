package com.example.room.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.room.Converters;
import com.example.room.daos.SongDao;
import com.example.room.entities.Song;

@TypeConverters({Converters.class})
@Database(entities = {Song.class}, version = 5)
public abstract class SongDatabase extends RoomDatabase {
    public abstract SongDao userDao();
}