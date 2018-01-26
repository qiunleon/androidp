package com.example.client.activity.own;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.activity.imported.ScrollViewMainActivity;
import com.example.client.application.ClientApp;
import com.example.client.dao.UserDao;
import com.example.client.greendao.User;
import com.example.client.jni.Example;
import com.example.client.manager.NetworkManager;
import com.example.client.manager.RemoteServiceManager;
import com.example.client.manager.SynchronizedTest;
import com.example.client.sqlite.SQLiteDatabaseHelper;
import com.example.client.task.CmdAsyncTask;
import com.example.client.task.Ticket;
import com.example.client.ui.custom.CustomToast;
import com.example.client.ui.dialog.CustomDialog;
import com.example.client.util.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

@SuppressWarnings("unused")
public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    static {
        System.loadLibrary("example");
    }

    @BindView(R.id.button_start_service)
    Button mStartButton;
    @BindView(R.id.button_stop_service)
    Button mStopButton;
    @BindView(R.id.switch_wifi)
    Switch mWifiSwitch;
    @BindView(R.id.switch_ap)
    Switch mApSwitch;

    private boolean isStop = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveDataAsGson();
    }

    @OnClick(R.id.button_sync_thread_b)
    public void onClickStopThread() {
        isStop = true;
    }

    @OnClick(R.id.button_sync_thread_a)
    public void onClickStartThread() {
        isStop = false;
        final Object[] objectses = {new Object(), new Object(), new Object(), new Object(), new Object()};
        for (Object o : objectses) {
            Log.w("SynchronizedTest", "object: " + o.toString());
        }
        final Random random = new Random();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    SynchronizedTest.getInstance().addElement(objectses[random.nextInt(objectses.length)]);
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "add");
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    SynchronizedTest.getInstance().removeElement(objectses[random.nextInt(objectses.length)]);
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "remove");
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    SynchronizedTest.getInstance().showElement();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "show");
        thread1.start();
        thread2.start();
        thread3.start();

        Ticket ticket = new Ticket(10);
        Thread window01 = new Thread(ticket, "窗口01");
        Thread window02 = new Thread(ticket, "窗口02");
        Thread window03 = new Thread(ticket, "窗口03");
        window01.start();
        window02.start();
        window03.start();
    }

    @OnClick(R.id.button_start_service)
    public void onClickStart() {
        RemoteServiceManager.getInstance().bindRemoteService();
        mStartButton.setEnabled(false);
        mStopButton.setEnabled(true);
    }

    @OnClick(R.id.button_stop_service)
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
        Toast.makeText(this, Example.gcd(3, 12), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_scrollview)
    public void onClickScrollView() {
        Intent intent = new Intent(this, ScrollViewMainActivity.class);
        startActivity(intent);
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
        User mUser = new User((long) 2, "1", "client", "client horse");
        mUserDao.insert(mUser);  // add
        mUserDao.deleteByKey(0L);  // delete
        mUser = new User((long) 2, "1", "update", "update horse");
        mUserDao.insertOrReplace(mUser);  // update
        List<User> users = mUserDao.loadAll();  // query
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
                new HashMap<>(), new TypeToken<Map<String, ArrayList<String>>>() {
                }.getType());
        Log.d(TAG, "write relationship: " + relationship);
        SPUtils.getInstance().put("shared_preference_file", "aaaaaaaaaaaaaaa");
    }


    private void getDataAsGson() {
        Gson gson = new Gson();
        String relationship = gson.toJson(
                new HashMap<>(), new TypeToken<Map<String, ArrayList<String>>>() {
                }.getType());

        Log.d(TAG, "get relationship: " + SPUtils.getInstance().getString("shared_preference_file", "bbbbbbbb"));
    }

    @OnClick(R.id.touch_event_dispatch_text_view)
    public void clickEventDispatch() {
        this.startActivity(new Intent(MainActivity.this, EventDispatchActivity.class));
    }

    @OnClick(R.id.custom_dialog)
    public void clickShowCustomDialog() {
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.show();
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.spinner_item, null);
        new CustomToast().setContext(this).setCustomView(null).setDuration(1000).create().show();
    }

    @OnClick(R.id.execute_cmd)
    public void clickExecuteCmd() {
        new CmdAsyncTask().execute();
    }
}