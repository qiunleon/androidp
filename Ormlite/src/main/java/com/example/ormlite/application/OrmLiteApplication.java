package com.example.ormlite.application;

import android.app.Application;
import android.util.Log;

import com.example.ormlite.daos.SongDao;
import com.example.ormlite.databases.DatabaseContext;
import com.example.ormlite.entities.Song;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrmLiteApplication extends Application {

    private static final OrmLiteApplication sInstance = new OrmLiteApplication();

    public static OrmLiteApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final SongDao mSongDao = new SongDao(this);
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");

        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                List<Song> list = mSongDao.queryForScoreSong();
                System.out.println("Ormlite score cost: " + (System.currentTimeMillis() - start) + " ,list: " + list.size());
            }
        }, "Ormlite score").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                List<Song> list = mSongDao.queryForLocalSong();
                System.out.println("Ormlite local cost: " + (System.currentTimeMillis() - start) + " ,list: " + list.size());
            }
        }, "Ormlite local").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                List<Song> list = mSongDao.queryForSong();
                System.out.println("Ormlite [10M,80M] cost: " + (System.currentTimeMillis() - start) + " ,list: " + list.size());
            }
        }, "Ormlite id").start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                long start = System.currentTimeMillis();
//                Song song = new Song();
//                song.setLastUpdateTime(formatter.format(new Date()));
//                mSongDao.add(song);
//                Log.i("Room insert", "Room insert cost: " + (System.currentTimeMillis() - start));
//            }
//        }, "Room insert").start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                long start = System.currentTimeMillis();
//                Song song;
//                for (int i = 0; i< 10000; i++){
//                    song = new Song();
//                    song.setSongID(i);
//                    song.setLastUpdateTime(formatter.format(new Date()));
//                    mSongDao.add(song);
//                }
//                Log.i("Room insert 100000", "Room insert 10000 cost: " + (System.currentTimeMillis() - start));
//            }
//        }, "Room insert 100000").start();
    }
}
