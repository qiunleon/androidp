package com.example.client.pattern.singleton;

/**
 * Created by Qiu on 2017/12/8.
 */

public class SIC_Singleton {

    private SIC_Singleton() {

    }

    private static class SIC_SingletonHolder {
        private static SIC_Singleton sInstance = new SIC_Singleton();
    }

    public static SIC_Singleton getInstance() {
        return SIC_SingletonHolder.sInstance;
    }
}
