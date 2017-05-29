package com.example.service.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.service.manager.BinderPoolManager;

/**
 * Binder连接池远程服务
 * Created by yunliangqiu on 2017/5/29.
 */
@SuppressWarnings("unused")
public class BinderPoolService extends Service {

    private static final String TAG = BinderPoolService.class.getName();

    private Binder mBinderPool = new BinderPoolManager.BinderPoolImpl();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinderPool;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
