package com.evideo.client.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.evideo.client.MainActivity;

public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "StaticReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }

//        String action = intent.getAction();
//        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
//            Intent eintent = new Intent();
//            eintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            ComponentName componentName = new ComponentName("com.evideo.client",
//                    "com.evideo.client.MainActivity");
//            eintent.setComponent(componentName);
//            eintent.setAction("android.intent.action.MAIN");
//            context.startActivity(eintent);
//        }
    }
}

