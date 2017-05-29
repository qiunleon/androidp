package com.example.client.util;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.example.client.application.BaseApp;

import java.util.List;

/**
 * 意图工具类
 * Created by yunliangqiu on 2017/5/29.
 */
@SuppressWarnings("unused")
public final class IntentUtil {

    private static final String TAG = IntentUtil.class.getName();

    public static Intent getExplicitIntent(Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = BaseApp.getInstance().getApplicationContext().getPackageManager();
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
}
