package com.example.client.pattern.singleton;

/**
 * Created by Qiu on 2017/12/8.
 */
public class DCL_Singleton {

    private static DCL_Singleton sInstance;

    private DCL_Singleton() {
    }

    public static DCL_Singleton getInstance() {
        if (sInstance == null) {
            synchronized (DCL_Singleton.class) {
                if (sInstance == null) {
                    sInstance = new DCL_Singleton();
                }
            }
        }
        return sInstance;
    }
}
