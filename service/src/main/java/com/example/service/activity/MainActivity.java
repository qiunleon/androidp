package com.example.service.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.service.application.BaseApplication;
import com.example.service.dialog.BaseDialog;
import com.example.service.callback.clickCallBack;
import com.example.service.R;
import com.example.service.receiver.DynamicReceiver;
import com.example.service.util.HttpUtil;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity implements clickCallBack {

    private static final String TAG = "MainActivity";

    private Button mDialogButton;

    private Button mStartTaskBtn;

    private Button mSendBroadCast;

    private Button mPostUrl;

    private Button mRunShell;

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
                        Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "AsyncTask executed successfully", Toast.LENGTH_SHORT).show();
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
                BaseApplication.getInstance().sendBroadcast(firstIntent);

                Intent secondIntent = new Intent();
                secondIntent.setAction("com.evideo.service.second");
                BaseApplication.getInstance().sendBroadcast(secondIntent);
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
//                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scalse);
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
                animation.setDuration(1000);//设置动画持续时间
                animation.setFillAfter(false);//动画执行完后是否停留在执行完的状态
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
                    Process proc =  runtime.exec("ip route add dev eth0 dev wlan0");
                    if (proc.waitFor() != 0) {
                        Log.d(TAG, "exit 2 value: " + proc.waitFor());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* 若此Activity处于onPause()时用户返回此Activity, 则调用该方法 */
    @Override
    protected void onStart() {
        super.onStart();
        mDynamicReceiver = new DynamicReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.evideo.service.second");
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
}
