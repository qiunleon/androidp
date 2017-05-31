package com.example.service.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.service.aidl.ICompute;
import com.example.service.aidl.ISecurityCenter;
import com.example.service.application.ServiceApp;
import com.example.service.binder.ComputeImpl;
import com.example.service.binder.SecurityCenterImpl;
import com.example.service.constant.BroadcastConstant;
import com.example.service.dialog.BaseDialog;
import com.example.service.callback.CommonCallBack;
import com.example.service.R;
import com.example.service.jni.record;
import com.example.service.manager.BinderPoolManager;
import com.example.service.receiver.DynamicReceiver;
import com.example.service.util.HttpUtil;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity implements CommonCallBack {

    private static final String TAG = "MainActivity";

    private Button mDialogButton;

    private Button mStartTaskBtn;

    private Button mSendBroadCast;

    private Button mPostUrl;

    private Button mCompute;

    private Button mEncrypt;

    private Button mRunShell;

    private Button mStartRecord;

    private Button mStopRecord;

    private ImageView mGetView;

    private ImageView mExpandView;

    private Bitmap mBitmap;

    private DynamicReceiver mDynamicReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGetView = (ImageView) findViewById(R.id.imageview_get);
        mExpandView = (ImageView) findViewById(R.id.imageview_expand);

        mDialogButton = (Button) findViewById(R.id.button_dialog);
        mDialogButton.setOnClickListener(mOnClickListener);

        mStartTaskBtn = (Button) findViewById(R.id.button_asynctask);
        mStartTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... params) {
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean result) {
                        Log.i(TAG, "AsyncTask onPostExecute result: " + result);
                        Toast.makeText(ServiceApp.getInstance().getApplicationContext(), "AsyncTask executed successfully", Toast.LENGTH_SHORT).show();
                    }
                }.execute();
            }
        });

        mSendBroadCast = (Button) findViewById(R.id.button_broadcast);
        mSendBroadCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent firstIntent = new Intent();
                firstIntent.setAction("android.net.ethernet.ETHERNET_STATE_CHANGED");
                firstIntent.putExtra("ethernet_state", 2);
                ServiceApp.getInstance().sendBroadcast(firstIntent);

                Intent secondIntent = new Intent();
                secondIntent.setAction("com.evideo.service.second");
                ServiceApp.getInstance().sendBroadcast(secondIntent);
            }
        });

        mCompute = (Button) findViewById(R.id.button_compute);
        mCompute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BinderPoolManager binderPoolManager = BinderPoolManager.getInstance(MainActivity.this);
                        IBinder computeBinder = binderPoolManager.queryBinderByCode(0);
                        ICompute mCompute = ComputeImpl.asInterface(computeBinder);
                        try {
                            Log.d(TAG, String.valueOf(mCompute.add(1, 2)));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        mEncrypt = (Button) findViewById(R.id.button_encrypt);
        mEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BinderPoolManager binderPoolManager = BinderPoolManager.getInstance(MainActivity.this);
                        IBinder computeBinder = binderPoolManager.queryBinderByCode(1);
                        ISecurityCenter mSecurity = SecurityCenterImpl.asInterface(computeBinder);
                        try {
                            Log.d(TAG, String.valueOf(mSecurity.encrypt("001")));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


        mPostUrl = (Button) findViewById(R.id.button_post);
        mPostUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, File>() {
                    @Override
                    protected File doInBackground(Void... Params) {
                        return HttpUtil.getInstance().get("https://www.baidu.com/img/bd_logo1.png");
                    }

                    @Override
                    protected void onPostExecute(File result) {
                        if (result != null) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 1;
                            mBitmap = BitmapFactory.decodeFile(result.getPath(), options);
                            mGetView.setImageBitmap(mBitmap);
                        }
                    }
                }.execute();
            }
        });

        mGetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scalse);
                WindowManager windowManager = MainActivity.this.getWindowManager();
                int widthDisplay = windowManager.getDefaultDisplay().getWidth();
                int heightDisplay = windowManager.getDefaultDisplay().getHeight();

                float scaleX;
                float scaleY;
                if (mBitmap.getWidth() >= mBitmap.getHeight()) {
                    // 宽度放大至与屏幕一样宽
                    scaleX = (float) (widthDisplay * 1.0 / mGetView.getWidth());
                    // 高度应等比例放大，先计算图片比原始图片放大了多少倍
                    float scale = (float) (widthDisplay * 1.0 / mBitmap.getWidth());
                    float scaleHeight = mBitmap.getHeight() * scale;
                    scaleY = (float) (scaleHeight * 1.0 / mGetView.getHeight());
                } else {
                    // 高度放大至与屏幕一样高
                    scaleY = (float) (heightDisplay * 1.0 / mGetView.getHeight());
                    // 宽度应等比例放大，先计算图片比原始图片放大了多少倍
                    float scale = (float) (heightDisplay * 1.0 / mBitmap.getHeight());
                    float scaleWidth = mBitmap.getWidth() * scale;
                    scaleX = (float) (scaleWidth * 1.0 / mGetView.getWidth());
                }
                final ScaleAnimation animation = new ScaleAnimation(1.0f, scaleY, 1.0f, scaleY,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.15f);
                animation.setDuration(1000);
                animation.setFillAfter(false);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mExpandView.setVisibility(View.VISIBLE);
                        mExpandView.setImageBitmap(mBitmap);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mGetView.startAnimation(animation);
            }
        });

        mExpandView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandView.setVisibility(View.GONE);
            }
        });

        mRunShell = (Button) findViewById(R.id.button_shell);
        mRunShell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Runtime runtime = Runtime.getRuntime();
                    Process process =  runtime.exec("ip route add dev eth0 dev wlan0");
                    if (process.waitFor() != 0) {
                        Log.d(TAG, "Exit value: " + process.waitFor());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mStartRecord = (Button) findViewById(R.id.button_start_record);
        mStartRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String source = "rtsp://192.168.1.112:8088/ch1/2";
                String destation = Environment.getExternalStorageDirectory().getPath() + "/kmbox/records/a.mp4";
                record.start(source, destation);
            }
        });

        mStopRecord = (Button) findViewById(R.id.button_stop_record);
        mStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record.stop();
            }
        });

    }

    /* 若此Activity处于onPause()时用户返回此Activity, 则调用该方法 */
    @Override
    protected void onStart() {
        super.onStart();
        mDynamicReceiver = new DynamicReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastConstant.ACTION_DYNAMIC);
        this.registerReceiver(mDynamicReceiver, intentFilter);
        Log.i(TAG, "onStart()");
    }

    /* 调用onResume()后, Activity开始运行 */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    /* 若此Activity被其他遮挡时调用该方法, 返回此Activity则调用onResume(); 若其他程序需要内存结束该进程时, 用户返回此Activity则调用onCreate() */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    /* 若此Activity不可见时调用该方法, 返回此Activity则调用onRestart(); 若其他程序需要内存结束该进程时, 用户返回此Activity则调用onCreate() */
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    /* 若此Activity调用onStop()后，用户返回此Activity则调用该方法，进而调用onStart() */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    /* 若调用finish()或系统销毁时调用该方法 */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mDynamicReceiver);
        Log.i(TAG, "onDestroy()");
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BaseDialog dialog = new BaseDialog(MainActivity.this);
            dialog.show();
            Toast.makeText(MainActivity.this, "Click", Toast.LENGTH_SHORT).show();
        }
    };

    public void callback() {
        Toast.makeText(MainActivity.this, "Callback", Toast.LENGTH_SHORT).show();
    }

    static {
        System.loadLibrary("avutil-55");
        System.loadLibrary("swresample-2");
        System.loadLibrary("avcodec-57");
        System.loadLibrary("avformat-57");
        System.loadLibrary("swscale-4");
        System.loadLibrary("avfilter-6");
        System.loadLibrary("record");
    }
}
