package com.example.service.binder;

import android.os.RemoteException;

import com.example.service.aidl.ISecurityCenter;

/**
 * 加解密接口
 * Created by yunliangqiu on 2017/5/29.
 */
public class SecurityCenterImpl extends ISecurityCenter.Stub {

    private static final char SECRET_CODE = '^';

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
