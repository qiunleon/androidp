package com.example.room.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.room.entities.Song;

import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT * FROM tblSong")
    List<Song> getAll();

    @Query("SELECT * FROM tblSong WHERE SongID IN (:userIds)")
    List<Song> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM tblSong WHERE SongName LIKE :first")
    Song findByName(String first);

    @Insert
    void insert(Song songs);

    @Delete
    void delete(Song song);
}
