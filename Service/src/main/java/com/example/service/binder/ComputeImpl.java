package com.example.service.binder;

import android.os.RemoteException;

import com.example.service.aidl.ICompute;

/**
 * 计算接口
 * Created by yunliangqiu on 2017/5/29.
 */
public class ComputeImpl extends ICompute.Stub {

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
