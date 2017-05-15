package com.evideo.service.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.evideo.service.aidl.IClickCallback;
import com.evideo.service.aidl.IClickService;

public class ConfigService extends Service {

    private static final String TAG = "ConfigService";

    int mStartMode; // indicates how to behave if the service is killed

    boolean mAllowRebind; // indicates whether onRebind should be used

    private IClickService.Stub mBinder = new IClickService.Stub() { // interface for clients that bind

        public void registerCallback(IClickCallback callback) {
            if (callback != null) {
                mCallbackList.register(callback);
                Log.i(TAG, "register()");
            }
        }

        public void unregisterCallback(IClickCallback callback) {
            if (callback != null) {
                mCallbackList.unregister(callback);
            }
        }
    };

    final RemoteCallbackList<IClickCallback> mCallbackList = new RemoteCallbackList<IClickCallback>();

    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
        Log.i(TAG, "onBind()");
        mHandler.sendEmptyMessageDelayed(1, 1000);
        return mBinder;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        Log.i(TAG, "onUnbind()");
        return super.onUnbind(intent);

//        return mAllowRebind;
    }

    @Override
    public void onRebind(Intent intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
        Log.i(TAG, "onRebind()");
        super.onRebind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand(), startId: " + startId);
        return START_REDELIVER_INTENT;
//        return START_STICKY;
//        如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。随后系统会尝试重新创建
//        service，由于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。如果在
//        此期间没有任何启动命令被传递到service，那么参数Intent将为null。

//        return START_NOT_STICKY;
//        使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统不会自动重启该服务

//        return START_STICKY_COMPATIBILITY;
//        重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统会自动重启该服务，并
//        将Intent的值传入。

//        return mStartMode;
    }

    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
        Log.i(TAG, "onDestroy()");
        super.onDestroy();
    }

    public void callback(int value) {
        int N = mCallbackList.beginBroadcast();
        Log.i(TAG, "callback(), N: " + N);
        for (int i = 0; i < N; i++) {
            try {
                Log.i(TAG, "onServiceConnected(), registerCallback()");
                mCallbackList.getBroadcastItem(i).actionPerformed(value);
            } catch (RemoteException e) {
                Log.e(TAG, "callback(), exception: " + e.getMessage());
            }
        }
        mCallbackList.finishBroadcast();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            callback(msg.what);
        }
    };
}
