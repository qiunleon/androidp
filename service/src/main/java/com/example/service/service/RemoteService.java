package com.example.service.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.example.service.aidl.IRemoteCallback;
import com.example.service.aidl.IRemoteService;

@SuppressWarnings("unused")
public class RemoteService extends Service {

    private static final String TAG = RemoteService.class.getName();

    final RemoteCallbackList<IRemoteCallback> mRemoteCallBackList = new RemoteCallbackList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        mHandler.sendEmptyMessageDelayed(1, 1000);
        return mBinder;
    }

    private IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public void registerCallback(IRemoteCallback notifyRemoteCallBack) {
            if (notifyRemoteCallBack != null) {
                mRemoteCallBackList.register(notifyRemoteCallBack);
            }
        }

        @Override
        public void unregisterCallback(IRemoteCallback notifyRemoteCallBack) {
            if (notifyRemoteCallBack != null) {
                mRemoteCallBackList.unregister(notifyRemoteCallBack);
            }
        }
    };

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int id) {
        return START_REDELIVER_INTENT;
        /*
         return START_STICKY;
         如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。随后系统会尝试重新创建
         service，由于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。如果在
         此期间没有任何启动命令被传递到service，那么参数Intent将为null。

         return START_NOT_STICKY;
         使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统不会自动重启该服务

         return START_STICKY_COMPATIBILITY;
         重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统会自动重启该服务，并
         将Intent的值传入。
        */
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            notifyRemoteCallBack(msg.what);
        }
    };

    public void notifyRemoteCallBack(int value) {
        int N = mRemoteCallBackList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            try {
                mRemoteCallBackList.getBroadcastItem(i).actionPerformed(value);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mRemoteCallBackList.finishBroadcast();
    }
}
