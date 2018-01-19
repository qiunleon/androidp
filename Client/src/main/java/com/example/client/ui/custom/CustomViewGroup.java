package com.example.client.ui.custom;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.client.application.ClientApp;

/**
 * Created by alienware on 2018/1/1.
 */
public class CustomViewGroup extends LinearLayout {

    public CustomViewGroup(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.EXACTLY);// 宽不变, 确定值, match_parent
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.EXACTLY);// 高度包裹内容, wrap_content;当包裹内容时,
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("Event","CustomViewGroup dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
        // return true; ViewGroup 返回 true：事件停止下发，且不会触发自身和Activity的onTouchEvent事件
        // return false; ViewGroup 返回 false：事件停止下发，触发Activity的onTouchEvent事件
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("Event","CustomViewGroup onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
        // return true; ViewGroup 返回 true：事件停止下发,触发自身的onTouchEvent事件
        // return false; ViewGroup 返回 true：事件继续下发
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("Event","CustomViewGroup onTouchEvent");
        Toast.makeText(ClientApp.getInstance(), "CustomViewGroup onTouchEvent", Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }
}
