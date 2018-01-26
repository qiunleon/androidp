package com.example.client.task;

import java.util.concurrent.locks.ReentrantLock;

import hugo.weaving.DebugLog;

/**
 * Created by Qiu on 2018/1/26.
 */

public class Ticket implements Runnable {
    private int num;//票数量
    private boolean flag = true;//若为false则售票停止
    private ReentrantLock lock = new ReentrantLock();

    public Ticket(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        while (flag) {
            ticket();
        }
    }

    @DebugLog
    private void ticket() {
        System.out.println(Thread.currentThread().getName() + "等待中，准备售：" + num);
        lock.lock();
        if (num <= 0) {
            System.out.println(Thread.currentThread().getName() + "发现售光了，关店");
            flag = false;
            return;
        }
        try {
            Thread.sleep(10);//模拟延时操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //输出当前窗口号以及出票序列号
        System.out.println(Thread.currentThread().getName() + "售出：" + num--);
        lock.unlock();
    }
}
