package com.example.service.manager;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.service.aidl.IBinderPool;
import com.example.service.binder.ComputeImpl;
import com.example.service.binder.SecurityCenterImpl;
import com.example.service.service.BinderPoolService;

import java.util.concurrent.CountDownLatch;

import static com.example.service.constant.BinderPoolConstant.BINDER_COMPUTE;
import static com.example.service.constant.BinderPoolConstant.BINDER_NONE;
import static com.example.service.constant.BinderPoolConstant.BINDER_SECURITY_CENTER;

/**
 * 服务接口管理类
 * Created by yunliangqiu on 2017/5/29.
 */
@SuppressWarnings("unused")
public class BinderPoolManager {

    private static final String TAG = BinderPoolManager.class.getName();

    @SuppressLint("StaticFieldLeak")
    private static BinderPoolManager sInstance;

    private Context mContext;

    private IBinderPool mBinderPool;

    private CountDownLatch mConnectBinderPoolCountDownLatch;

    private BinderPoolManager(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPoolManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPoolManager.class) {
                if (sInstance == null) {
                    sInstance = new BinderPoolManager(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * 连接Binder连接池服务
     */
    private synchronized void connectBinderPoolService() {
        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent intent = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(intent, mBinderPoolConnection, Context.BIND_AUTO_CREATE);

        try {
            mConnectBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mConnectBinderPoolCountDownLatch.countDown();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient, 0);
            mBinderPool = null;
            connectBinderPoolService();
        }
    };

    public IBinder queryBinderByCode(int binderCode) {
        IBinder binder = null;
        try {
            if (mBinderPool != null) {
                binder = mBinderPool.queryBinderByCode(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

    public static class BinderPoolImpl extends IBinderPool.Stub {
        @Override
        public IBinder queryBinderByCode(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode) {
                case BINDER_COMPUTE:
                    binder = new ComputeImpl();
                    break;
                case BINDER_SECURITY_CENTER:
                    binder = new SecurityCenterImpl();
                    break;
                case BINDER_NONE:
                    break;
                default:
                    break;
            }
            return binder;
        }
    }
}
