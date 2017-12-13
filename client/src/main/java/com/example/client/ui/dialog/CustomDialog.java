package com.example.client.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.ui.custom.RectView;
import com.example.client.ui.custom.SlideView;

/**
 * Created by Qiu on 2017/6/7.
 */

public class CustomDialog extends Dialog {

    private Context mContext;
    private RelativeLayout mParentView;
    private LinearLayout mSlideContainer;
    private TextView mTextView;
    private TextView mTitleView;

    public CustomDialog(final Context context) {
        super(context);
        mContext = context;
        getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);

        Resources r = context.getResources();

        // 整个区域
        mParentView = new RelativeLayout(context);
        mParentView.setLayoutParams(new LinearLayout.LayoutParams(580, 260));
        mParentView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        mParentView.setGravity(Gravity.CENTER_HORIZONTAL);

        // 标题
        mTitleView = new TextView(context);
        mTitleView.setText("重启");
        mTitleView.setTextColor(Color.WHITE);
        mTitleView.setAlpha(0.95f);
        mTitleView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, r.getDisplayMetrics()));
        mTitleView.setGravity(Gravity.LEFT);
        mTitleView.setPadding(80, 14, 30, 0);
        mTitleView.setTextColor(getContext().getResources().getColor(R.color.white));

        // 文本
        mTextView = new TextView(context);
        mTextView.setText("正在重启…");
        mTextView.setTextColor(Color.WHITE);
        mTextView.setAlpha(0.6f);
        mTextView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics()));
        mTextView.setGravity(Gravity.LEFT);
        mTextView.setPadding(80, 70, 30, 0);
        mTextView.setTextColor(getContext().getResources().getColor(R.color.white));

        // 滑块
        LinearLayout.LayoutParams slideParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        slideParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -250, r.getDisplayMetrics());
        SlideView slideView = new SlideView(mContext);
        slideView.setLayoutParams(slideParams);

        // 滑动区域
        mSlideContainer = new LinearLayout(context);
        mSlideContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mSlideContainer.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        mSlideContainer.setOrientation(LinearLayout.VERTICAL);
        mSlideContainer.setGravity(Gravity.CENTER_HORIZONTAL);
        mSlideContainer.addView(slideView);

        // 背景区域
        RectView rectView = new RectView(mContext);
        LinearLayout.LayoutParams bgParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bgParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1000, r.getDisplayMetrics());
        rectView.setLayoutParams(bgParams);
        mParentView.addView(rectView);
        mParentView.addView(mSlideContainer);
        mParentView.addView(mTitleView);
        mParentView.addView(mTextView);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(mParentView);
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
//      setCancelable(false);

        AnimationSet animationSet = new AnimationSet(true);
        Animation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        Animation enterAnimation = new AlphaAnimation(0.0f, 1.0f);
        Animation exitAnimation = new AlphaAnimation(1.0f, 0.0f);
        translateAnimation.setDuration(3000);
        enterAnimation.setDuration(600);
        enterAnimation.setStartOffset(1850);
        exitAnimation.setDuration(500);
        exitAnimation.setStartOffset(2500);

        animationSet.addAnimation(enterAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(exitAnimation);
        animationSet.setFillAfter(false);
        animationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animation.reset();
                animation.setAnimationListener(this);
                animation.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        slideView.startAnimation(animationSet);
    }
}