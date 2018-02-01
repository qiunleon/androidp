package com.example.room.application;

import android.app.Application;

public class RoomApplication extends Application {

    private static final RoomApplication sInstance = new RoomApplication();

    public static RoomApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
