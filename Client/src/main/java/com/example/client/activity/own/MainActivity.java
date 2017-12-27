package com.example.client.activity.own;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;

import com.example.client.R;
import com.example.client.application.ClientApp;
import com.example.client.constant.TimeConstant;
import com.example.client.data.User;
import com.example.client.gen.UserDao;
import com.example.client.manager.NetworkManager;
import com.example.client.manager.RemoteServiceManager;
import com.example.client.sqlite.SQLiteDatabaseHelper;
import com.example.client.ui.dialog.CustomDialog;
import com.example.client.ui.dialog.EvProgressDialog;
import com.example.client.util.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

@SuppressWarnings("unused")
public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.button_start)
    Button mStartButton;

    @BindView(R.id.button_stop)
    Button mStopButton;

    @BindView(R.id.switch_wifi)
    Switch mWifiSwitch;

    @BindView(R.id.switch_ap)
    Switch mApSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Class clz = Class.forName("TimeConstant");
            Annotation annotation = clz.getAnnotation(TimeConstant.Unit.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean isStop = true;

    @OnClick(R.id.button_sync_thread_b)
    public void onClickStopThread() {
        isStop = true;
    }

    @OnClick(R.id.button_sync_thread_a)
    public void onClickStartThread() {
        isStop = false;
//        final Object[] objectses = {new Object(), new Object(), new Object(), new Object(), new Object()};
//        for (Object o : objectses) {
//            EvLog.w("SynchronizedTest", "object: " + o.toString());
//        }
//        final Random random = new Random();
//
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (!isStop) {
//                    SynchronizedTest.getInstance().addElement(objectses[random.nextInt(objectses.length)]);
//                    try {
//                        Thread.sleep(800);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, "add");
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (!isStop) {
//                    SynchronizedTest.getInstance().removeElement(objectses[random.nextInt(objectses.length)]);
//                    try {
//                        Thread.sleep(400);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, "remove");
//        Thread thread3 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (!isStop) {
//                    SynchronizedTest.getInstance().showElement();
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, "show");
//        thread1.start();
//        thread2.start();
//        thread3.start();

        Ticket ticket = new Ticket(10);
        Thread window01 = new Thread(ticket, "窗口01");
        Thread window02 = new Thread(ticket, "窗口02");
        Thread window03 = new Thread(ticket, "窗口03");
        window01.start();
        window02.start();
        window03.start();
    }


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

        private void ticket() {
            System.out.println(Thread.currentThread().getName() + "准备卖，票数：" + num);
            lock.lock();
            if (num <= 0) {
                flag = false;
                return;
            }
            try {
                Thread.sleep(10);//模拟延时操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //输出当前窗口号以及出票序列号
            System.out.println(Thread.currentThread().getName() + "售出票序列号：" + num--);
            lock.unlock();
        }
    }

    @OnClick(R.id.button_start)
    public void onClickStart() {
        RemoteServiceManager.getInstance().bindRemoteService();
        mStartButton.setEnabled(false);
        mStopButton.setEnabled(true);
    }

    @OnClick(R.id.button_stop)
    public void onClickStop() {
        RemoteServiceManager.getInstance().unBindRemoteService();
        mStartButton.setEnabled(true);
        mStopButton.setEnabled(false);
    }

    @OnClick(R.id.button_exit)
    public void onClickExit() {
        MainActivity.this.onBackPressed();
    }

    @OnClick(R.id.button_load)
    public void onClickLoad() {
        Intent intent = new Intent(MainActivity.this, ImageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_binder)
    public void onClickBinder() {
        RemoteServiceManager.getInstance().addBook();
    }

    @OnClick(R.id.button_linear)
    public void onClickLinear() {
        Intent intent = new Intent(this, LinearActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_linear_scroll)
    public void onClickLinearScroll() {
        Intent intent = new Intent(this, LinearScrollActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_grid)
    public void onClickGrid() {
        Intent intent = new Intent(this, GridActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_staggered)
    public void onClickStaggered() {
        Intent intent = new Intent(this, StaggeredActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_jni)
    public void onClickJni() {
//        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.spinner_item, null);
//        new CustomToast().setContext(this).setCustomView(null).setDuration(1000).create().show();
        EvProgressDialog dialog = new EvProgressDialog(MainActivity.this);
        dialog.show();
    }

    @OnClick(R.id.button_scrollview)
    public void onClickScrollView() {
//        Intent intent = new Intent(this, ScrollViewMainActivity.class);
//        startActivity(intent);
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.show();
    }

    @OnClick(R.id.button_sqlite)
    public void onClickCreateSQLite() {
        SQLiteDatabaseHelper sqLiteDatabaseHelper = new SQLiteDatabaseHelper(getBaseContext());
        SQLiteDatabase db = sqLiteDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", "john");
        values.put("age", 18);
        db.insert("person", null, values);
    }


    @OnClick(R.id.button_greendao)
    public void onClickCreateGreenDAO() {
        UserDao mUserDao = ClientApp.getInstance().getDaoSession().getUserDao();

        // add
        User mUser = new User((long) 2, "client");
        mUserDao.insert(mUser);//添加一个

        // delete
        mUserDao.deleteByKey(0L);

        // update
        mUser = new User((long) 2, "client");
        mUserDao.update(mUser);

        // query
        List<User> users = mUserDao.loadAll();
        String userName = "";
        for (int i = 0; i < users.size(); i++) {
            userName += users.get(i).getName() + ",";
        }
    }

    @OnClick(R.id.button_eventbus_normal)
    public void onClickNormalEvent() {
        this.startActivity(new Intent(this, EventNormalActivity.class));
    }

    @OnClick(R.id.button_eventbus_sticky)
    public void onClickStickyEvent() {
        this.startActivity(new Intent(this, EventStickyActivity.class));
    }

    @OnCheckedChanged(R.id.switch_wifi)
    public void onCheckedChangedWifiMode(boolean isChecked) {
        NetworkManager.getInstance().setWifiEnabled(isChecked);
    }

    @OnCheckedChanged(R.id.switch_ap)
    public void onCheckedChangedApMode(boolean isChecked) {
        NetworkManager.getInstance().setWifiApEnabled(isChecked);
    }

    /**
     * Called when setContentView() or addContentView() finished.
     */
    @Override
    public void onContentChanged() {
        ButterKnife.bind(this);
        mStartButton.setEnabled(true);
        mStopButton.setEnabled(false);
        mWifiSwitch.setChecked(NetworkManager.getInstance().getWifiEnabled());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void saveDataAsGson() {
        Gson gson = new Gson();
        String relationship = gson.toJson(
                new HashMap<>(), new TypeToken<Map<String, ArrayList<String>>>() {}.getType());
        Log.d(TAG, "write relationship: " + relationship);
        SPUtils.getInstance().put("shared_preference_file", relationship);
    }

    static {
        System.loadLibrary("example");
    }
}