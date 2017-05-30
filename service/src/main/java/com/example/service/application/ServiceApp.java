package com.example.service.application;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

/**
 * 应用基类
 */
public class ServiceApp extends Application {

    private static ServiceApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static ServiceApp getInstance() {
        return sInstance;
    }

    private static class HandlerHolder {
        private static Handler sHandler = new Handler(Looper.getMainLooper());
    }

    public static Handler getHandler() {
        return HandlerHolder.sHandler;
    }
}
