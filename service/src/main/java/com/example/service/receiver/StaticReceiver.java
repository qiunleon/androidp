package com.example.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.service.manager.ConfigManager;

public class StaticReceiver extends BroadcastReceiver{

    private static final String TAG = "StaticReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }

        String action = intent.getAction();
        if (action.equals("com.evideo.service.first")) {
            ConfigManager.getInstance().putBoolean("config", true);
            Log.i(TAG, "Config value: " + ConfigManager.getInstance().getBoolean("config", false));
        }
    }
}
