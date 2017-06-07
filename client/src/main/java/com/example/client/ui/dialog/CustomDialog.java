package com.example.client.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.ui.custom.LoadView;

/**
 * Created by Qiu on 2017/6/7.
 */

public class CustomDialog extends Dialog {

    private Context mContext;
    private LinearLayout mParentView;
    private LoadView mLoadView;

    public CustomDialog(Context context) {
        super(context);
        mContext = context;
        getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
        mParentView = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mParentView.setLayoutParams(params);
        mParentView.setBackgroundResource(R.drawable.dialog_no_title_bg);
        mParentView.setOrientation(LinearLayout.VERTICAL);
        mParentView.setGravity(Gravity.CENTER_HORIZONTAL);
//        ProgressBar progressBar = new ProgressBar(context);
//        mParentView.addView(progressBar);

        TextView textView = new TextView(context);
        LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Resources r = context.getResources();
//        ImageView imageView = new ImageView(context);
//        imageView.setBackgroundResource(R.drawable.loading);
//        LoadView loadView = new LoadView(context);

        textViewLayoutParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());
        textViewLayoutParams.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());
        textView.setLayoutParams(textViewLayoutParams);
//        loadView.setLayoutParams(textViewLayoutParams);
        textView.setText("正在重启...");
        textView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics()));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
        textView.setTextColor(getContext().getResources().getColor(R.color.accent));
//        mParentView.addView(loadView);
        mParentView.addView(textView);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(mParentView);
//        setCancelable(false);

//        //创建旋转动画
//        Animation animation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(1500);
//        animation.setRepeatCount(1);//动画的重复次数
////        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
//        animation.setAnimationListener(new ReStartAnimationListener());
//        loadView.startAnimation(animation);//开始动画

    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            post(UpdateLoadViewRunnable(msg.what))
        }
    };


    private class UpdateLoadViewRunnable implements Runnable{

        private int type = -1;

        public UpdateLoadViewRunnable(int type) {
            super();
            this.type = type;
        }

        @Override
        public void run() {
            ((ViewGroup) mLoadView.getParent()).removeView(mLoadView);
            mLoadView = new LoadView(mContext, type);
            mParentView.addView(mLoadView);
        }
    }
//    private class ReStartAnimationListener implements Animation.AnimationListener {
//
//        public void onAnimationEnd(Animation animation) {
//            animation.reset();
//            animation.setAnimationListener(new ReStartAnimationListener());
//            animation.start();
//        }
//
//        public void onAnimationRepeat(Animation animation) {
//        }
//
//        public void onAnimationStart(Animation animation) {
//        }
//    }
}