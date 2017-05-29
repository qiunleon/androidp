package com.example.service.application;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

/**
 * [应用基类]
 */
public class BaseApplication extends Application {

    private static BaseApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    /* 获取BaseApplication实例 */
    public static BaseApplication getInstance() {
        return sInstance;
    }

    /* 嵌套类, 用于初始化MainLooper的Handler实例 */
    private static class HandlerHolder{
        private static Handler sHandler = new Handler(Looper.getMainLooper());
    }

    /* 获取MainLooper的Handler实例 */
    public static Handler getHandler() {
        return HandlerHolder.sHandler;
    }
}
