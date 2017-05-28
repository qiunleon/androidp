package com.evideo.client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.evideo.client.swig.exampleJNI;
import com.evideo.client.view.PanelView;
import com.evideo.service.aidl.IClickCallback;
import com.evideo.service.aidl.IClickService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getName();
    private Button mStart;
    private Button mStop;
    private Button mExit;
    private Button mLoad;
    private Switch mWifiSwitch;
    private Switch mApSwitch;
    private WifiManager mWifiManager;
    private Context mContext;

    private LinearLayout mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mContext = BaseApplication.getInstance().getApplicationContext();
        mStart = (Button) findViewById(R.id.button_start);
        mStop = (Button) findViewById(R.id.button_stop);
        mExit = (Button) findViewById(R.id.button_exit);
        mView = (LinearLayout) findViewById(R.id.view_llyt);
        mLoad = (Button) findViewById(R.id.button_load);

        mView.addView(new PanelView(this));

        mWifiSwitch = (Switch) findViewById(R.id.switch_wifi);
        mApSwitch = (Switch) findViewById(R.id.switch_ap);
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);

        mStart.setEnabled(true);
        mStop.setEnabled(false);

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPracticeService();
                mStart.setEnabled(false);
                mStop.setEnabled(true);
            }
        });

        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPracticeService();
                mStart.setEnabled(true);
                mStop.setEnabled(false);
                mStart.setText(String.valueOf(exampleJNI.gcd(2, 4)));

            }
        });

        boolean wifiState;
        switch (mWifiManager.getWifiState()) {
            case WifiManager.WIFI_STATE_DISABLED:
            case WifiManager.WIFI_STATE_DISABLING:
            case WifiManager.WIFI_STATE_UNKNOWN:
                wifiState = false;
                break;
            case WifiManager.WIFI_STATE_ENABLED:
            case WifiManager.WIFI_STATE_ENABLING:
                wifiState = true;
                break;
            default:
                wifiState = false;
                break;
        }
        mWifiSwitch.setChecked(wifiState);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (mWifiManager == null) {
//                    Log.e(TAG, "mWifiManager is null");
//                }
//                setWifiApEnabled(mWifiManager, true);
//            }
//        }).start();

        mWifiSwitch.setOnCheckedChangeListener(mOnCheckedChangeListener);

        mApSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton buttonView,final boolean isChecked) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (isChecked) {
                            BaseApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    mApSwitch.setEnabled(true);
                                }
                            });

                            if (!setWifiApEnabled(mWifiManager, isChecked)) {
                                BaseApplication.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mApSwitch.setEnabled(false);
                                        Toast.makeText(mContext, "Error" + isChecked, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            BaseApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    mApSwitch.setEnabled(false);
                                }
                            });

                            if (!setWifiApEnabled(mWifiManager, isChecked)) {
                                BaseApplication.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mApSwitch.setEnabled(true);
                                        Toast.makeText(mContext, "Error" + isChecked, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                });
                thread.start();
            }
        });


        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onContentChanged(){
        // setContentView Callback
    }

    private void startPracticeService() {
        final Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.evideo.service",
                "com.evideo.service.service.ConfigService");
        intent.setComponent(componentName);
        intent.setAction("com.evideo.service.service.IClickService");
        final Intent eintent = getExplicitIntent(intent);
//              startService(eintent);
        bindService(eintent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void stopPracticeService() {
        unbindService(mConnection);
//        Intent intent = new Intent("com.evideo.practise.Service.IClickService");
//        stopService(intent);
    }

    IClickService mService;
    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = IClickService.Stub.asInterface(service);
            try {
                mService.registerCallback(mCallback);
                Log.i(TAG, "onServiceConnected(), registerCallback()");
            } catch (RemoteException e) {
                Log.e(TAG, "onServiceConnected(), exception: " + e.getMessage());
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
        }
    };

    private IClickCallback mCallback = new IClickCallback.Stub() {
        public void actionPerformed(int value) {
            Log.i(TAG, "actionPerformed(), id: " + value);
            SharedPreferences sp = getSharedPreferences("com_evideo_practice", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("callback", value);
            editor.apply();
        }
    };

    public static Intent getExplicitIntent(Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = BaseApplication.getInstance().getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);
        return explicitIntent;
    }

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                int wifiApState = 0;
                try {
                    Method method = mWifiManager.getClass().getMethod("getWifiApState");
                    wifiApState = (Integer) method.invoke(mWifiManager);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "no getWifiApState method, got exception: " + e.getMessage());
                } catch (IllegalAccessException e) {
                    Log.e(TAG, "getWifiApState method isn't assessable, got exception: " + e.getMessage());
                } catch (InvocationTargetException e) {
                    Log.e(TAG, "getWifiApState method invocation target, got exception: " + e.getMessage());
                }

                if (((wifiApState == 12) || (wifiApState == 13))) {
                    setWifiApEnabled(mWifiManager, false);
                }

                mWifiSwitch.setEnabled(true);
                if (!mWifiManager.setWifiEnabled(true)) {
                    mWifiSwitch.setEnabled(false);
                    Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                mWifiSwitch.setEnabled(false);
                if (!mWifiManager.setWifiEnabled(false)) {
                    mWifiSwitch.setEnabled(true);
                    Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public boolean setWifiApEnabled(WifiManager wifiManager, boolean enabled) {
        if (enabled) {
            wifiManager.setWifiEnabled(false);
        }

        WifiConfiguration apConfig = new WifiConfiguration();
        apConfig.SSID = "0000000000000nimab";
        apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        try {
            Method method = WifiManager.class.getMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            Log.d(TAG, "involk success");
            return (Boolean) method.invoke(wifiManager, apConfig, enabled);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "no setWifiApEnabled method, got exception: " + e.getMessage());
            return false;
        } catch (IllegalAccessException e) {
            Log.e(TAG, "setWifiApEnabled method isn't assessable, got exception: " + e.getMessage());
            return false;
        } catch (InvocationTargetException e) {
            Log.e(TAG, "setWifiApEnabled method invocation target, got exception: " + e.getTargetException());
            return false;
        }
    }
}

