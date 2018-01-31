package com.example.core.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 核心服务
 */
public class CoreService extends Service {

    private static final String TAG = CoreService.class.getSimpleName();

    public CoreService() {
        Log.i(TAG, "CoreService construct executed.");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, TAG + " onCreate executed.");

        // 设置通知状态不可清除
        Notification notification = new Notification();
        notification.flags = notification.flags | Notification.FLAG_NO_CLEAR;

        // 调用startForeground()可以将该服务转为前台服务，默认服务启动均为后台，内存不足可能导致服务被杀死而没
        // 有任何通知，因此，需要为核心服务添加停止运行的提示，且通知不可以通过点击取消清除。
        startForeground(getClass().hashCode(), notification);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
