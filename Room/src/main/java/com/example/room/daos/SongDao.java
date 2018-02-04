package com.example.room.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.room.entities.Song;

import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT * FROM tblSong")
    List<Song> getAll();

    @Query("SELECT * FROM tblSong WHERE IsGrand = 0")
    List<Song> getScoreSong();

    @Query("SELECT * FROM tblSong WHERE IsLocalExist = 1")
    List<Song> getLocalSong();

    @Query("SELECT * FROM tblSong WHERE SongID BETWEEN 10000000 AND 80000000")
    List<Song> getSong();

    @Query("SELECT * FROM tblSong WHERE SongID IN (:userIds)")
    List<Song> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM tblSong WHERE SongName LIKE :first")
    Song findByName(String first);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Song songs);

    @Delete
    void delete(Song song);

    @Update
    void update(Song song);
}
