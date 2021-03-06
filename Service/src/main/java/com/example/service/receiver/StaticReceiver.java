package com.example.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.service.constant.BroadcastConstant;
import com.example.service.constant.ConfigConstant;
import com.example.service.manager.ConfigManager;

/**
 * 静态注册的广播接收
 * Created by yunliangqiu on 2017/2/19.
 */
public class StaticReceiver extends BroadcastReceiver{

    private static final String TAG = StaticReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }

        String action = intent.getAction();
        if (action.equals(BroadcastConstant.ACTION_STATIC)) {
            ConfigManager.getInstance().putBoolean(ConfigConstant.KEY_SAVE_VALUE, true);
            Log.i(TAG, "Config value: " + ConfigManager.getInstance().getBoolean(ConfigConstant.KEY_SAVE_VALUE, false));
        }
    }
}
