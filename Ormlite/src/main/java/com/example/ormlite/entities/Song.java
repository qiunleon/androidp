package com.example.ormlite.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

@DatabaseTable(tableName = "tblSong")
public class Song implements Serializable {

    public Song() {
    }

    @NonNull
    @DatabaseField(id = true, columnName = "SongID", defaultValue = "-1")
    private Integer SongID = 0;

    @NonNull
    @DatabaseField(columnName = "SongName", defaultValue = "")
    private String songName = "";

    @NonNull
    @DatabaseField(columnName = "SongPy", defaultValue = "")
    private String songPy = "";

    @NonNull
    @DatabaseField(columnName = "SongWord", defaultValue = "0")
    private Integer songWord = 0;

    @Nullable
    @DatabaseField(columnName = "songsterName", defaultValue = "")
    private String singerName = null;

    @Nullable
    @DatabaseField(columnName = "SongsterID1", defaultValue = "-1")
    private Integer singerID1 = -1;

    @Nullable
    @DatabaseField(columnName = "SongsterID2", defaultValue = "-1")
    private Integer singerID2 = -1;

    @Nullable
    @DatabaseField(columnName = "SongsterID3", defaultValue = "-1")
    private Integer singerID3 = -1;

    @Nullable
    @DatabaseField(columnName = "SongsterID4", defaultValue = "-1")
    private Integer singerID4 = -1;

    @Nullable
    @DatabaseField(columnName = "SongTypeID1", defaultValue = "-1")
    private Integer songTypeID1 = -1;

    @Nullable
    @DatabaseField(columnName = "SongTypeID2", defaultValue = "-1")
    private Integer songTypeID2 = -1;

    @Nullable
    @DatabaseField(columnName = "SongTypeID3", defaultValue = "-1")
    private Integer songTypeID3 = -1;

    @Nullable
    @DatabaseField(columnName = "SongTypeID4", defaultValue = "-1")
    private Integer songTypeID4 = -1;

    @NonNull
    @DatabaseField(columnName = "LanguageTypeID", defaultValue = "-1")
    private Integer languageTypeID = -1;

    @Nullable
    @DatabaseField(columnName = "LanguageTypeID2", defaultValue = "-1")
    private Integer languageTypeID2 = -1;

    @Nullable
    @DatabaseField(columnName = "LanguageTypeID3", defaultValue = "-1")
    private Integer languageTypeID3 = -1;

    @Nullable
    @DatabaseField(columnName = "LanguageTypeID4", defaultValue = "-1")
    private Integer languageTypeID4 = -1;

    @Nullable
    @DatabaseField(columnName = "PlayNum", defaultValue = "0")
    private Integer playNumber = 0;

    @Nullable
    @DatabaseField(columnName = "IsGrand", defaultValue = "0")
    private Integer isGrand = 0;

    @Nullable
    @DatabaseField(columnName = "IsMShow", defaultValue = "0")
    private Integer isMShow = 0;

    @Nullable
    @DatabaseField(columnName = "album", defaultValue = "")
    private String album = null;

    @Nullable
    @DatabaseField(columnName = "ercVersion", defaultValue = "")
    private String ercVersion = null;

    @Nullable
    @DatabaseField(columnName = "hasRemote", defaultValue = "0")
    private Integer hasRemote = 0;

    @Nullable
    @DatabaseField(columnName = "LastUpdateTime", defaultValue = "")
    private String lastUpdateTime = null;

    @Nullable
    @DatabaseField(columnName = "IsLocalExist", defaultValue = "0")
    private Integer isLocalExist = 0;

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
