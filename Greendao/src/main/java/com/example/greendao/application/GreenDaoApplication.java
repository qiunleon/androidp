package com.example.greendao.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.greendao.daos.DaoMaster;
import com.example.greendao.daos.DaoSession;
import com.example.greendao.daos.SongDao;
import com.example.greendao.databases.DatabaseContext;
import com.example.greendao.entities.Song;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GreenDaoApplication extends Application {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
    private static final GreenDaoApplication sInstance = new GreenDaoApplication();

    public static GreenDaoApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHelper = new DaoMaster.DevOpenHelper(new DatabaseContext(this), "a.db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        final SongDao mSongDao = mDaoSession.getSongDao();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("start: " + formatter.format(System.currentTimeMillis()));
//                List<Song> list = mSongDao.queryBuilder()
//                        .where(SongDao.Properties.IsGrand.eq(0))
//                        .build()
//                        .list();
//                System.out.println("query: " + list.size());
//                System.out.println("end: " + formatter.format(System.currentTimeMillis()));
//            }
//        }, "").start();


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                long start = System.currentTimeMillis();
//                Song song = new Song();
//                song.setLastUpdateTime(formatter.format(new Date()));
//                mSongDao.insertOrReplace(song);
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
//                    song.setSongID((long)i);
//                    song.setLastUpdateTime(formatter.format(new Date()));
                    mSongDao.delete(song);
//                }
//                Log.i("Room insert 100000", "Room insert 10000 cost: " + (System.currentTimeMillis() - start));
//            }
//        }, "Room insert 100000").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                List<Song> list = mSongDao.queryBuilder().where(SongDao.Properties.IsGrand.eq(0)).build().list();
                System.out.println("Greendao score cost: " + (System.currentTimeMillis() - start) + " ,list: " + list.size());
            }
        }, "Greendao score").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                List<Song> list = mSongDao.queryBuilder().where(SongDao.Properties.IsLocalExist.eq(1)).build().list();
                System.out.println("Greendao local cost: " + (System.currentTimeMillis() - start) + " ,list: " + list.size());
            }
        }, "Greendao local").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                List<Song> list = mSongDao.queryBuilder().where(SongDao.Properties.SongID.between(10000000, 80000000)).build().list();
                System.out.println("Greendao [10M,80M] cost: " + (System.currentTimeMillis() - start) + " ,list: " + list.size());
            }
        }, "Greendao local").start();
    }
}
