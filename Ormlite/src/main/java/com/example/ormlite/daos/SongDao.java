package com.example.ormlite.daos;

import android.content.Context;

import com.example.ormlite.databases.DBHelper;
import com.example.ormlite.databases.DatabaseContext;
import com.example.ormlite.entities.Song;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDao {

    private Dao<Song, Integer> themeDao;
    private DBHelper dbHelper;

    /**
     * 构造方法
     * 获得数据库帮助类实例，通过传入Class对象得到相应的Dao
     * @param context
     */
    public SongDao(Context context) {
        try {
            dbHelper = DBHelper.getHelper(new DatabaseContext(context));
            themeDao = dbHelper.getDao(Song.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一条记录
     * @param theme
     */
    public void add(Song theme) {
        try {
            themeDao.createOrUpdate(theme);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一条记录
     * @param theme
     */
    public void delete(Song theme) {
        try {
            themeDao.delete(theme);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新一条记录
     * @param theme
     */
    public void update(Song theme) {
        try {
            themeDao.update(theme);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询一条记录
     * @param id
     * @return
     */
    public Song queryForId(int id) {
        Song theme = null;
        try {
            theme = themeDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return theme;
    }


    /**
     * 查询所有记录
     * @return
     */
    public List<Song> queryForAll() {
        List<Song> themes = new ArrayList<Song>();
        try {
            themes = themeDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }

    public List<Song> queryForScoreSong() {
        List<Song> themes = new ArrayList<Song>();
        try {
            themes = themeDao.queryBuilder().where().eq("IsGrand", 0).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }

    public List<Song> queryForLocalSong() {
        List<Song> themes = new ArrayList<Song>();
        try {
            themes = themeDao.queryBuilder().where().eq("IsLocalExist", 1).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }

    public List<Song> queryForSong() {
        List<Song> themes = new ArrayList<Song>();
        try {
            themes = themeDao.queryBuilder().where().between("SongID", 10000000, 80000000).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }
}
