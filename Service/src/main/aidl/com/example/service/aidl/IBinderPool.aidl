// IBinderPool.aidl
package com.example.service.aidl;

// Declare any non-default types here with import statements

interface IBinderPool {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    IBinder queryBinderByCode(int binderCode);
}
