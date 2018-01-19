package com.example.client.ui.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by alienware on 2018/1/1.
 */

public class CustomTextView extends AppCompatTextView {
    public CustomTextView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.EXACTLY);// 宽不变, 确定值, match_parent
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.EXACTLY);// 高度包裹内容, wrap_content;当包裹内容时,
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("Event","CustomTextView dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("Event","CustomTextView onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
