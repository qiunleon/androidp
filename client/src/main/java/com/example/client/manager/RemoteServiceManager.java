package com.example.client.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.client.application.BaseApp;
import com.example.service.aidl.IRemoteCallback;
import com.example.service.aidl.IRemoteService;

import static android.content.Context.MODE_PRIVATE;
import static com.example.client.constant.CommonConstant.FILE_NAME_OF_SHARE_DATA_;
import static com.example.client.constant.CommonConstant.KEY_NAME_OF_VALUE;

/**
 * 远程服务连接管理类
 * Created by yunliangqiu on 2017/5/30.
 */
public class RemoteServiceManager {

    private static final String TAG = RemoteServiceManager.class.getName();

    private static RemoteServiceManager sInstance;

    private IRemoteService mRemoteService;

    private boolean mIsBound;

    public static RemoteServiceManager getInstance() {
        if (sInstance == null) {
            synchronized (RemoteServiceManager.class) {
                if (sInstance == null) {
                    sInstance = new RemoteServiceManager();
                }
            }
        }
        return sInstance;
    }

    public void bindRemoteService() {
        Intent intent = new Intent("com.example.service.service.IRemoteService");
        ComponentName componentName = new ComponentName("com.example.service", "com.example.service.service.RemoteService");
        intent.setComponent(componentName);
        BaseApp.getInstance().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public void startRemoteService() {
        Intent intent = new Intent("com.example.service.service.IRemoteService");
        ComponentName componentName = new ComponentName("com.example.service", "com.example.service.service.RemoteService");
        intent.setComponent(componentName);
        BaseApp.getInstance().startService(intent);
    }

    public void stopRemoteService() {
        Intent intent = new Intent("com.example.service.service.IRemoteService");
        ComponentName componentName = new ComponentName("com.example.service", "com.example.service.service.RemoteService");
        intent.setComponent(componentName);
        BaseApp.getInstance().stopService(intent);
    }

    public void unBindRemoteService() {
        BaseApp.getInstance().unbindService(mConnection);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteService = IRemoteService.Stub.asInterface(service);
            if (mRemoteService != null) {
                try {
                    mRemoteService.registerCallback(mRemoteCallback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mIsBound = true;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (mRemoteService != null) {
                try {
                    mRemoteService.unregisterCallback(mRemoteCallback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mIsBound = false;
                mRemoteService = null;
            }
        }
    };

    private IRemoteCallback mRemoteCallback = new IRemoteCallback.Stub() {
        public void actionPerformed(int value) {
            SharedPreferences sp = BaseApp.getInstance().getSharedPreferences(FILE_NAME_OF_SHARE_DATA_, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(KEY_NAME_OF_VALUE, value);
            editor.apply();
        }
    };

    public boolean isBound() {
        return mIsBound;
    }
}
