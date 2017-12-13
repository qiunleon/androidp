package com.example.client.manager;

import android.text.TextUtils;

import com.evideo.disk.manager.EvLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Qiu on 2017/12/7.
 */

public class SynchronizedTest {

    private List<Object> mList = new ArrayList<>();

    private static SynchronizedTest sInstance = new SynchronizedTest();
    private static final String TAG = SynchronizedTest.class.getSimpleName();

    public static SynchronizedTest getInstance() {
        return sInstance;
    }

    public void addElement(Object objects) {
        if (objects == null) {
            return;
        }

//        synchronized (this.mList) {
            if (!mList.contains(objects)) {
                //EvLog.d(TAG, "before add: " + content + ", size: " + mList.size());
                mList.add(objects);
                EvLog.d(TAG, "after add: " + objects + ", size: " + mList.size());
            }
//        }
    }

    public void removeElement(Object objects) {
        if (objects == null) {
            return;
        }

//        synchronized (this.mList) {
            if (mList.contains(objects)) {
                //EvLog.d(TAG, "before remove: " + content + ", size: " + mList.size());
                mList.remove(objects);
                EvLog.d(TAG, "after remove: " + objects + ", size: " + mList.size());
            }
//        }
    }

    public void showElement() {
        if (mList == null) {
            return;
        }

//        synchronized (this.mList) {
            int size = this.mList.size();
            //EvLog.d(TAG, "before start show, size: " + mList.size());
            for (int i = 0; i < size; i++) {
                //EvLog.d(TAG, "before show: " + mList.get(i) + ", size: " + mList.size());
                EvLog.e(TAG, "i: " + i + ", content: " + mList.get(i) + ", size: " + mList.size());
                //EvLog.d(TAG, "after show: " + mList.get(i) + ", size: " + mList.size());
            }
//        }
    }
}
