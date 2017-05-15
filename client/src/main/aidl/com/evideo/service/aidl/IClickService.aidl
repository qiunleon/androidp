// IPracticeInterface.aidl
package com.evideo.service.aidl;

import com.evideo.service.aidl.IClickCallback;

// Declare any non-default types here with import statements

interface IClickService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

     /* 注册客户端回调 */
     void registerCallback(IClickCallback callback);

     /* 注销客户端回调 */
     void unregisterCallback(IClickCallback callback);
}
