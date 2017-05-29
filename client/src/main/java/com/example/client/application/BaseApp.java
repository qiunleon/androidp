package com.example.client.application;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

/**
 * 应用基类
 */
public class BaseApp extends Application {

    private static BaseApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static BaseApp getInstance() {
        return sInstance;
    }

    private static class HandlerHolder {
        private static final Handler sHandler = new Handler(Looper.getMainLooper());
    }

    public static Handler getHandler() {
        return HandlerHolder.sHandler;
    }
}
