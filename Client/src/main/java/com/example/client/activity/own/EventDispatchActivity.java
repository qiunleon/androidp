package com.example.client.activity.own;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.ui.custom.CustomTextView;
import com.example.client.ui.custom.CustomViewGroup;

/**
 * Created by alienware on 2018/1/1.
 */

public class EventDispatchActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomTextView mCustomTextView = new CustomTextView(getBaseContext());
        CustomViewGroup mCustomViewGroup = new CustomViewGroup(getBaseContext());
        mCustomTextView.setBackgroundColor(getBaseContext().getResources().getColor(R.color.colorPrimary));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mCustomTextView.setLayoutParams(params);
        mCustomViewGroup.addView(mCustomTextView);
        setContentView(mCustomViewGroup);

        mCustomTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("Event","CustomTextView onTouch ACTION_DOWN");
                        Toast.makeText(getBaseContext(), "CustomTextView", Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("Event","CustomTextView onTouch ACTION_UP");
                }
                return false;
            }
        });

        mCustomViewGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("Event","CustomViewGroup onTouch ACTION_DOWN");
                        Toast.makeText(getBaseContext(), "CustomViewGroup", Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("Event","CustomViewGroup onTouch ACTION_UP");
                }
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("Event","Activity dispatchTouchEvent");
        return super.dispatchTouchEvent(ev); // 仅当Activity返回super方法时事件继续下发
        // return true; Activity返回true时事件停止下发
        // return false; Activity返回false时事件停止下发
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("Event","Activity onTouchEvent");
        return super.onTouchEvent(event);
    }
}
