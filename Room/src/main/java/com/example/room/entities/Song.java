package com.example.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "tblSong", indices = {@Index(value = {"SongID"}, unique = true),
        @Index(value = {"PlayNum", "SongPy", "SongsterID1", "SongsterID2", "SongsterID3", "SongsterID4"}, unique = true)})
public class Song {
    @NonNull
    @PrimaryKey
    private Integer SongID = 0;

    @NonNull
    @ColumnInfo(name = "SongName")
    private String songName = "";

    @NonNull
    @ColumnInfo(name = "SongPy")
    private String songPy = "";

    @NonNull
    @ColumnInfo(name = "SongWord")
    private Integer songWord = 0;

    @Nullable
    @ColumnInfo(name = "songsterName")
    private String singerName;

    @Nullable
    @ColumnInfo(name = "SongsterID1")
    private Integer singerID1;

    @Nullable
    @ColumnInfo(name = "SongsterID2")
    private Integer singerID2;

    @Nullable
    @ColumnInfo(name = "SongsterID3")
    private Integer singerID3;

    @Nullable
    @ColumnInfo(name = "SongsterID4")
    private Integer singerID4;

    @Nullable
    @ColumnInfo(name = "SongTypeID1")
    private Integer songTypeID1;

    @Nullable
    @ColumnInfo(name = "SongTypeID2")
    private Integer songTypeID2;

    @Nullable
    @ColumnInfo(name = "SongTypeID3")
    private Integer songTypeID3;

    @Nullable
    @ColumnInfo(name = "SongTypeID4")
    private Integer songTypeID4;

    @NonNull
    @ColumnInfo(name = "LanguageTypeID")
    private Integer languageTypeID = 0;

    @Nullable
    @ColumnInfo(name = "LanguageTypeID2")
    private Integer languageTypeID2;

    @Nullable
    @ColumnInfo(name = "LanguageTypeID3")
    private Integer languageTypeID3;

    @Nullable
    @ColumnInfo(name = "LanguageTypeID4")
    private Integer languageTypeID4;

    @Nullable
    @ColumnInfo(name = "PlayNum")
    private Integer playNumber;

    @Nullable
    @ColumnInfo(name = "IsGrand")
    private Integer isGrand;

    @Nullable
    @ColumnInfo(name = "IsMShow")
    private Integer isMShow;

    @Nullable
    @ColumnInfo(name = "album")
    private String album;

    @Nullable
    @ColumnInfo(name = "ercVersion")
    private String ercVersion;

    @Nullable
    @ColumnInfo(name = "hasRemote")
    private Integer hasRemote;

    @Nullable
    @ColumnInfo(name = "LastUpdateTime")
    private String lastUpdateTime;

    @Nullable
    @ColumnInfo(name = "IsLocalExist")
    private Integer isLocalExist;

    @NonNull
    public Integer getSongID() {
        return SongID;
    }

    public void setSongID(@NonNull Integer songID) {
        SongID = songID;
    }

    @NonNull
    public String getSongName() {
        return songName;
    }

    public void setSongName(@NonNull String songName) {
        this.songName = songName;
    }

    @NonNull
    public String getSongPy() {
        return songPy;
    }

    public void setSongPy(@NonNull String songPy) {
        this.songPy = songPy;
    }

    @NonNull
    public Integer getSongWord() {
        return songWord;
    }

    public void setSongWord(@NonNull Integer songWord) {
        this.songWord = songWord;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public Integer getSingerID1() {
        return singerID1;
    }

    public void setSingerID1(Integer singerID1) {
        this.singerID1 = singerID1;
    }

    public Integer getSingerID2() {
        return singerID2;
    }

    public void setSingerID2(Integer singerID2) {
        this.singerID2 = singerID2;
    }

    public Integer getSingerID3() {
        return singerID3;
    }

    public void setSingerID3(Integer singerID3) {
        this.singerID3 = singerID3;
    }

    public Integer getSingerID4() {
        return singerID4;
    }

    public void setSingerID4(Integer singerID4) {
        this.singerID4 = singerID4;
    }

    public Integer getSongTypeID1() {
        return songTypeID1;
    }

    public void setSongTypeID1(Integer songTypeID1) {
        this.songTypeID1 = songTypeID1;
    }

    public Integer getSongTypeID2() {
        return songTypeID2;
    }

    public void setSongTypeID2(Integer songTypeID2) {
        this.songTypeID2 = songTypeID2;
    }

    public Integer getSongTypeID3() {
        return songTypeID3;
    }

    public void setSongTypeID3(Integer songTypeID3) {
        this.songTypeID3 = songTypeID3;
    }

    public Integer getSongTypeID4() {
        return songTypeID4;
    }

    public void setSongTypeID4(Integer songTypeID4) {
        this.songTypeID4 = songTypeID4;
    }

    @NonNull
    public Integer getLanguageTypeID() {
        return languageTypeID;
    }

    public void setLanguageTypeID(@NonNull Integer languageTypeID) {
        this.languageTypeID = languageTypeID;
    }

    public Integer getLanguageTypeID2() {
        return languageTypeID2;
    }

    public void setLanguageTypeID2(Integer languageTypeID2) {
        this.languageTypeID2 = languageTypeID2;
    }

    public Integer getLanguageTypeID3() {
        return languageTypeID3;
    }

    public void setLanguageTypeID3(Integer languageTypeID3) {
        this.languageTypeID3 = languageTypeID3;
    }

    public Integer getLanguageTypeID4() {
        return languageTypeID4;
    }

    public void setLanguageTypeID4(Integer languageTypeID4) {
        this.languageTypeID4 = languageTypeID4;
    }

    public Integer getPlayNumber() {
        return playNumber;
    }

    public void setPlayNumber(Integer playNumber) {
        this.playNumber = playNumber;
    }

    public Integer getIsGrand() {
        return isGrand;
    }

    public void setIsGrand(Integer isGrand) {
        this.isGrand = isGrand;
    }

    public Integer getIsMShow() {
        return isMShow;
    }

    public void setIsMShow(Integer isMShow) {
        this.isMShow = isMShow;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getErcVersion() {
        return ercVersion;
    }

    public void setErcVersion(String ercVersion) {
        this.ercVersion = ercVersion;
    }

    public Integer getHasRemote() {
        return hasRemote;
    }

    public void setHasRemote(Integer hasRemote) {
        this.hasRemote = hasRemote;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getIsLocalExist() {
        return isLocalExist;
    }

    public void setIsLocalExist(Integer isLocalExist) {
        this.isLocalExist = isLocalExist;
    }
}
