package com.example.client.application;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

/**
 * 应用基类
 */
public class ClientApp extends Application {

    private static ClientApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static ClientApp getInstance() {
        return sInstance;
    }

    private static class HandlerHolder {
        private static final Handler sHandler = new Handler(Looper.getMainLooper());
    }

    public static Handler getHandler() {
        return HandlerHolder.sHandler;
    }
}
