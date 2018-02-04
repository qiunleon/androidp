package com.example.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.database.SQLException;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.room.application.RoomApplication;
import com.example.room.databases.DatabaseContext;
import com.example.room.databases.SongDatabase;
import com.example.room.entities.Song;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SongDatabase db = Room.databaseBuilder(
                new DatabaseContext(RoomApplication.getInstance()), SongDatabase.class, "a.db").addMigrations(MIGRATION_4_5).build();
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                long start = System.currentTimeMillis();
//                Song song = new Song();
//                song.setLastUpdateTime(formatter.format(new Date()));
//                db.userDao().insert(song);
//                Log.i("Room insert", "Room insert cost: " + (System.currentTimeMillis() - start));
//            }
//        }, "Room insert").start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                long start = System.currentTimeMillis();
//                Song song;
//                for (int i = 0; i< 10000; i++){
//                    song = new Song();
//                    song.setLastUpdateTime(formatter.format(new Date()));
//                    db.userDao().insert(song);
//                }
//                Log.i("Room insert 100000", "Room insert 10000 cost: " + (System.currentTimeMillis() - start));
//            }
//        }, "Room insert 100000").start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                long start = System.currentTimeMillis();
//                List<Song> list = db.userDao().loadAllByIds(new int[]{24830});
//                Log.i("loadAllByIds", "query: " + list.size());
//                Log.i("loadAllByIds", "cost: " + (System.currentTimeMillis() - start));
//            }
//        }, "loadAllByIds").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                List<Song> list = db.userDao().getScoreSong();
                Log.i("loadAllByIds", "query: " + list.size());
                Log.i("loadAllByIds", "Room score cost: " + (System.currentTimeMillis() - start));
            }
        }, "loadAllByIds").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                List<Song> list = db.userDao().getLocalSong();
                Log.i("loadAllByIds", "query: " + list.size());
                Log.i("loadAllByIds", "Room local cost: " + (System.currentTimeMillis() - start));
            }
        }, "loadAllByIds").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                List<Song> list = db.userDao().getSong();
                Log.i("loadAllByIds", "query: " + list.size());
                Log.i("loadAllByIds", "Room id cost: " + (System.currentTimeMillis() - start));
            }
        }, "loadAllByIds").start();
    }

    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL("ALTER TABLE tblSong RENAME TO tblSongOld");
                database.execSQL("CREATE TABLE tblSong (SongID INTEGER NOT NULL DEFAULT NULL, SongName TEXT NOT NULL DEFAULT NULL, SongPy TEXT NOT NULL DEFAULT NULL, SongWord INTEGER NOT NULL DEFAULT NULL,songsterName TEXT DEFAULT NULL, SongsterID1 INTEGER DEFAULT -1, SongsterID2 INTEGER DEFAULT -1, SongsterID3 INTEGER DEFAULT -1, SongsterID4 INTEGER DEFAULT -1, SongTypeID1 INTEGER DEFAULT -1,SongTypeID2 INTEGER DEFAULT -1,SongTypeID3 INTEGER DEFAULT -1,SongTypeID4 INTEGER DEFAULT -1, LanguageTypeID INTEGER NOT NULL DEFAULT NULL, LanguageTypeID2 INTEGER DEFAULT NULL,LanguageTypeID3 INTEGER DEFAULT NULL, LanguageTypeID4 INTEGER DEFAULT NULL, PlayNum INTEGER DEFAULT 0, IsGrand INTEGER DEFAULT 0, IsMShow INTEGER DEFAULT 0, album TEXT DEFAULT NULL,ercVersion TEXT DEFAULT NULL, hasRemote INTEGER DEFAULT 0, LastUpdateTime TEXT DEFAULT NULL, IsLocalExist INTEGER DEFAULT 0, PRIMARY KEY (SongID))\n");
                database.execSQL("INSERT INTO tblSong (SongID, SongName, SongPy, SongWord,songsterName, SongsterID1, SongsterID2, SongsterID3, SongsterID4, SongTypeID1,SongTypeID2 ,SongTypeID3,SongTypeID4 , LanguageTypeID, LanguageTypeID2 ,LanguageTypeID3, LanguageTypeID4, PlayNum, IsGrand, IsMShow, album,ercVersion, hasRemote, LastUpdateTime, IsLocalExist) SELECT SongID, SongName, SongPy, SongWord,songsterName, SongsterID1, SongsterID2, SongsterID3, SongsterID4, SongTypeID1,SongTypeID2 ,SongTypeID3,SongTypeID4 , LanguageTypeID, LanguageTypeID2 ,LanguageTypeID3, LanguageTypeID4, PlayNum, IsGrand, IsMShow, album,ercVersion, hasRemote, LastUpdateTime, IsLocalExist FROM tblSongOld");
                database.execSQL("DROP TABLE tblSongOld");
                database.execSQL("CREATE INDEX [tblSong_index_SongPy] ON tblSong ( [SongPy] DESC )");
                database.execSQL("CREATE INDEX [tblSong_index_PlayNum] ON tblSong ( [PlayNum] DESC )");
                database.execSQL("CREATE INDEX [tblSong_index_SongsterID1] ON tblSong ( SongsterID1 )");
                database.execSQL("CREATE INDEX [tblSong_index_SongsterID2] ON tblSong ( SongsterID2 )");
                database.execSQL("CREATE INDEX [tblSong_index_SongsterID3] ON tblSong ( SongsterID3 )");
                database.execSQL("CREATE INDEX [tblSong_index_SongsterID4] ON tblSong ( SongsterID4 )");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
}
