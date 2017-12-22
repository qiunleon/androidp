// IPracticeInterface.aidl
package com.example.service.aidl;

import com.example.service.aidl.IRemoteCallback;
import com.example.service.aidl.Book;

// Declare any non-default types here with import statements

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

     /* 注册客户端回调 */
     void registerCallback(IRemoteCallback callback);

     /* 注销客户端回调 */
     void unregisterCallback(IRemoteCallback callback);

     void addBook(in Book book);
}
