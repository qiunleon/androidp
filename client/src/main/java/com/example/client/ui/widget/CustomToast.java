package com.example.client.ui.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.message;

/**
 * Created by alienware on 2017/6/5.
 */

public class CustomToast {

    private static final String TAG = CustomToast.class.getName();

    private Toast mToast = null;
    private Context mContext = null;
    private View mCustomView = null;
    private CharSequence mMessage = "你甚么都没和我说";
    private int mDuration = 1000;

    public CustomToast setContext(Context context) {
        if (context == null) {
            return null;
        }
        mContext = context;
        mToast = new Toast(context);
        return this;
    }

    public CustomToast setCustomView(View view) {
        if (view != null) {
            mToast.setView(view);
        } else {
            mCustomView = null;
        }
        return this;
    }

    public CustomToast setDuration(int duration) {
        mToast.setDuration(duration);
        return this;
    }

    public CustomToast setTextColor(int color) {
        if (mCustomView == null) {
            return this;
        }
        View view = mToast.getView();
        if (view != null) {
            ((TextView) view.findViewById(message)).setTextColor(color);
        }
        return this;
    }

    public CustomToast setBackgroundColor(int color) {
        if (mCustomView == null) {
            return this;
        }
        View view = mToast.getView();
        if (view != null) {
            view.setBackgroundResource(color);
        }
        return this;
    }

    public CustomToast setmMessage(CharSequence message) {
        mMessage = message;
        return this;
    }

    public CustomToast create() {
        return this;
    }

    public void show() {
        if (mContext == null || mToast == null) {
            Log.e(TAG, "try to call CustomToast.show(), but it is not initialized.");
            return;
        }
        if (mCustomView == null) {
            mToast = Toast.makeText(mContext, mMessage, Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
