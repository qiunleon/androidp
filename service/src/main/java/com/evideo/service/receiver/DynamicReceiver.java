package com.evideo.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.evideo.service.manager.ConfigManager;

/**
 * Created by alienware on 2017/2/19.
 */

public class DynamicReceiver extends BroadcastReceiver {

    private static final String TAG = "DynamicReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if (action.equals("com.evideo.service.second")) {
            ConfigManager.getInstance().putBoolean("config", false);
            Log.i(TAG, "Config value: " + ConfigManager.getInstance().getBoolean("config", true));
        }
    }
}
