package com.example.client.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.client.application.ClientApp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import hugo.weaving.DebugLog;

/**
 * 网络管理类
 * Created by yunliangqiu on 2017/5/28.
 */
@SuppressWarnings("unused")
public class NetworkManager {

    private static final String TAG = NetworkManager.class.getName();

    @SuppressLint("StaticFieldLeak")
    private static NetworkManager sInstance;

    private WifiManager mWifiManager;

    private Context mContext;

    private NetworkManager() {
        mContext = ClientApp.getInstance().getBaseContext();
        mWifiManager = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public static NetworkManager getInstance() {
        if (sInstance == null) {
            synchronized (NetworkManager.class) {
                if (sInstance == null) {
                    sInstance = new NetworkManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取无线管理服务对象
     *
     * @return 无线管理独享
     */
    public WifiManager getWifiManager() {
        return mWifiManager;
    }

    /**
     * 设置热点模式是否开启
     *
     * @param enable 热点模式使能
     * @return 设置是否成功
     */
    @DebugLog public boolean setWifiApEnabled(boolean enable) {
        if (enable) {
            this.setWifiEnabled(false);
        }

        WifiConfiguration apConfig = new WifiConfiguration();
        apConfig.SSID = mContext.getPackageName();
        apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        try {
            Method method = WifiManager.class.getMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            return (Boolean) method.invoke(mWifiManager, apConfig, enable);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "no setWifiApEnabled method, got exception: " + e.getMessage());
            return false;
        } catch (IllegalAccessException e) {
            Log.e(TAG, "setWifiApEnabled method isn't assessable, got exception: " + e.getMessage());
            return false;
        } catch (InvocationTargetException e) {
            Log.e(TAG, "setWifiApEnabled method invocation target, got exception: " + e.getTargetException());
            return false;
        }
    }

    /**
     * 设置无线模式开启或关闭
     *
     * @param enable 无线模式使能
     * @return 设置是否成功
     */
    public boolean setWifiEnabled(boolean enable) {
        return mWifiManager.setWifiEnabled(enable);
    }

    /**
     * 获取无线模式是否开启
     * @return 无线模式是否开启
     */
    public boolean getWifiEnabled() {
        return mWifiManager.isWifiEnabled();
    }

    /**
     * 获取无线状态
     *
     * @return 无线状态值
     */
    public int getWifiState() {
        return mWifiManager.getWifiState();
    }

    /**
     * 获取热点状态
     *
     * @return 热点状态值
     */
    public int getWifiApState() {
        try {
            Method method = mWifiManager.getClass().getMethod("getWifiApState");
            return (Integer) method.invoke(mWifiManager);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "there is no getWifiApState method, got exception: " + e.getMessage());
            return -1;
        } catch (IllegalAccessException e) {
            Log.e(TAG, "getWifiApState method isn't assessable, got exception: " + e.getMessage());
            return -1;
        } catch (InvocationTargetException e) {
            Log.e(TAG, "getWifiApState method invocation target, got exception: " + e.getMessage());
            return -1;
        }
    }
}
