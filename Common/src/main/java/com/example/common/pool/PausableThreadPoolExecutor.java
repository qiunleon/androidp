package com.example.common.pool;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 支持暂停/恢复的线程池
 */
public class PausableThreadPoolExecutor extends ThreadPoolExecutor {

    private static final String TAG = PausableThreadPoolExecutor.class.getSimpleName();

    private boolean isPaused;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition pauseCondition = pauseLock.newCondition();

    public PausableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                      TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        Log.i(TAG, "construct executed.");
    }

    @Override
    public long getCompletedTaskCount() {
        return super.getCompletedTaskCount();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
            while (isPaused) pauseCondition.await();
        } catch (InterruptedException e) {
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
    }

    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } catch (Exception e) {
            Log.i(TAG, "pause with exception.");
        } finally {
            pauseLock.unlock();
        }
    }

    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            pauseCondition.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }
}
