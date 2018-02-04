package com.example.greendao.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;


@Entity(nameInDb = "tblSong")
public class Song {

    @Id
    @NonNull
    @Property(nameInDb = "SongID")
    private Long SongID = 0L;

    @NonNull
    @Property(nameInDb = "SongName")
    private String songName = "";
    
    @NonNull
    @Property(nameInDb = "SongPy")
    private String songPy = "";

    @NonNull
    @Property(nameInDb = "SongWord")
    private Integer songWord = 0;

    @Nullable
    @Property(nameInDb = "songsterName")
    private String singerName = null;

    @Nullable
    @Property(nameInDb = "SongsterID1")
    private Integer singerID1 = -1;

    @Nullable
    @Property(nameInDb = "SongsterID2")
    private Integer singerID2 = -1;

    @Nullable
    @Property(nameInDb = "SongsterID3")
    private Integer singerID3 = -1;

    @Nullable
    @Property(nameInDb = "SongsterID4")
    private Integer singerID4 = -1;

    @Nullable
    @Property(nameInDb = "SongTypeID1")
    private Integer songTypeID1 = -1;

    @Nullable
    @Property(nameInDb = "SongTypeID2")
    private Integer songTypeID2 = -1;

    @Nullable
    @Property(nameInDb = "SongTypeID3")
    private Integer songTypeID3 = -1;

    @Nullable
    @Property(nameInDb = "SongTypeID4")
    private Integer songTypeID4 = -1;

    @NonNull
    @Property(nameInDb = "LanguageTypeID")
    private Integer languageTypeID = -1;

    @Nullable
    @Property(nameInDb = "LanguageTypeID2")
    private Integer languageTypeID2 = -1;

    @Nullable
    @Property(nameInDb = "LanguageTypeID3")
    private Integer languageTypeID3 = -1;

    @Nullable
    @Property(nameInDb = "LanguageTypeID4")
    private Integer languageTypeID4 = -1;

    @Nullable
    @Property(nameInDb = "PlayNum")
    private Integer playNumber = 0;

    @Nullable
    @Property(nameInDb = "IsGrand")
    private Integer isGrand = 0;

    @Nullable
    @Property(nameInDb = "IsMShow")
    private Integer isMShow = 0;

    @Nullable
    @Property(nameInDb = "album")
    private String album = null;

    @Nullable
    @Property(nameInDb = "ercVersion")
    private String ercVersion = null;

    @Nullable
    @Property(nameInDb = "hasRemote")
    private Integer hasRemote = 0;

    @Nullable
    @Property(nameInDb = "LastUpdateTime")
    private String lastUpdateTime = null;

    @Nullable
    @Property(nameInDb = "IsLocalExist")
    private Integer isLocalExist = 0;

    @Generated(hash = 1276491603)
    public Song(@NonNull Long SongID, @NonNull String songName,
            @NonNull String songPy, @NonNull Integer songWord, String singerName,
            Integer singerID1, Integer singerID2, Integer singerID3,
            Integer singerID4, Integer songTypeID1, Integer songTypeID2,
            Integer songTypeID3, Integer songTypeID4,
            @NonNull Integer languageTypeID, Integer languageTypeID2,
            Integer languageTypeID3, Integer languageTypeID4, Integer playNumber,
            Integer isGrand, Integer isMShow, String album, String ercVersion,
            Integer hasRemote, String lastUpdateTime, Integer isLocalExist) {
        this.SongID = SongID;
        this.songName = songName;
        this.songPy = songPy;
        this.songWord = songWord;
        this.singerName = singerName;
        this.singerID1 = singerID1;
        this.singerID2 = singerID2;
        this.singerID3 = singerID3;
        this.singerID4 = singerID4;
        this.songTypeID1 = songTypeID1;
        this.songTypeID2 = songTypeID2;
        this.songTypeID3 = songTypeID3;
        this.songTypeID4 = songTypeID4;
        this.languageTypeID = languageTypeID;
        this.languageTypeID2 = languageTypeID2;
        this.languageTypeID3 = languageTypeID3;
        this.languageTypeID4 = languageTypeID4;
        this.playNumber = playNumber;
        this.isGrand = isGrand;
        this.isMShow = isMShow;
        this.album = album;
        this.ercVersion = ercVersion;
        this.hasRemote = hasRemote;
        this.lastUpdateTime = lastUpdateTime;
        this.isLocalExist = isLocalExist;
    }

    @Generated(hash = 87031450)
    public Song() {
    }

    public Long getSongID() {
        return this.SongID;
    }

    public void setSongID(Long SongID) {
        this.SongID = SongID;
    }

    public String getSongName() {
        return this.songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongPy() {
        return this.songPy;
    }

    public void setSongPy(String songPy) {
        this.songPy = songPy;
    }

    public Integer getSongWord() {
        return this.songWord;
    }

    public void setSongWord(Integer songWord) {
        this.songWord = songWord;
    }

    public String getSingerName() {
        return this.singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public Integer getSingerID1() {
        return this.singerID1;
    }

    public void setSingerID1(Integer singerID1) {
        this.singerID1 = singerID1;
    }

    public Integer getSingerID2() {
        return this.singerID2;
    }

    public void setSingerID2(Integer singerID2) {
        this.singerID2 = singerID2;
    }

    public Integer getSingerID3() {
        return this.singerID3;
    }

    public void setSingerID3(Integer singerID3) {
        this.singerID3 = singerID3;
    }

    public Integer getSingerID4() {
        return this.singerID4;
    }

    public void setSingerID4(Integer singerID4) {
        this.singerID4 = singerID4;
    }

    public Integer getSongTypeID1() {
        return this.songTypeID1;
    }

    public void setSongTypeID1(Integer songTypeID1) {
        this.songTypeID1 = songTypeID1;
    }

    public Integer getSongTypeID2() {
        return this.songTypeID2;
    }

    public void setSongTypeID2(Integer songTypeID2) {
        this.songTypeID2 = songTypeID2;
    }

    public Integer getSongTypeID3() {
        return this.songTypeID3;
    }

    public void setSongTypeID3(Integer songTypeID3) {
        this.songTypeID3 = songTypeID3;
    }

    public Integer getSongTypeID4() {
        return this.songTypeID4;
    }

    public void setSongTypeID4(Integer songTypeID4) {
        this.songTypeID4 = songTypeID4;
    }

    public Integer getLanguageTypeID() {
        return this.languageTypeID;
    }

    public void setLanguageTypeID(Integer languageTypeID) {
        this.languageTypeID = languageTypeID;
    }

    public Integer getLanguageTypeID2() {
        return this.languageTypeID2;
    }

    public void setLanguageTypeID2(Integer languageTypeID2) {
        this.languageTypeID2 = languageTypeID2;
    }

    public Integer getLanguageTypeID3() {
        return this.languageTypeID3;
    }

    public void setLanguageTypeID3(Integer languageTypeID3) {
        this.languageTypeID3 = languageTypeID3;
    }

    public Integer getLanguageTypeID4() {
        return this.languageTypeID4;
    }

    public void setLanguageTypeID4(Integer languageTypeID4) {
        this.languageTypeID4 = languageTypeID4;
    }

    public Integer getPlayNumber() {
        return this.playNumber;
    }

    public void setPlayNumber(Integer playNumber) {
        this.playNumber = playNumber;
    }

    public Integer getIsGrand() {
        return this.isGrand;
    }

    public void setIsGrand(Integer isGrand) {
        this.isGrand = isGrand;
    }

    public Integer getIsMShow() {
        return this.isMShow;
    }

    public void setIsMShow(Integer isMShow) {
        this.isMShow = isMShow;
    }

    public String getAlbum() {
        return this.album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getErcVersion() {
        return this.ercVersion;
    }

    public void setErcVersion(String ercVersion) {
        this.ercVersion = ercVersion;
    }

    public Integer getHasRemote() {
        return this.hasRemote;
    }

    public void setHasRemote(Integer hasRemote) {
        this.hasRemote = hasRemote;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getIsLocalExist() {
        return this.isLocalExist;
    }

    public void setIsLocalExist(Integer isLocalExist) {
        this.isLocalExist = isLocalExist;
    }
}
