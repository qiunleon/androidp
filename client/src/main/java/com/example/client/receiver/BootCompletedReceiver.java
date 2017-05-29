package com.example.client.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }

        String action = intent.getAction();
        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            Intent newIntent = new Intent();
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName componentName = new ComponentName("com.example.client", "com.example.client.activity.MainActivity");
            newIntent.setComponent(componentName);
            newIntent.setAction("android.intent.action.MAIN");
            context.startActivity(newIntent);
        }
    }
}

