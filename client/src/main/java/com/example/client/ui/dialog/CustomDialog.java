package com.example.client.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
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
    private TextView mTextView;
    /* 检测任务线程 */
    private HandlerThread mHandlerThread;
    /* 检测消息处理 */
    private Handler mHandler;
    /* 检测周期三秒*/
    private static final int CHECK_SERIAL_PERIOD = 50;
    /* 检测消息标志 */
    private static final int MSG_CHECK_USB_WIFI = 0xAA;
    private int mType = 0;

    public CustomDialog(final Context context) {
        super(context);
        mContext = context;
        getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
        mParentView = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mParentView.setLayoutParams(params);
        mParentView.setBackgroundColor(Color.parseColor("#104B99"));
        mParentView.getBackground().setAlpha(235);
        mParentView.setOrientation(LinearLayout.VERTICAL);
        mParentView.setGravity(Gravity.CENTER_HORIZONTAL);
        mTextView = new TextView(context);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(1000, 100);
        Resources r = context.getResources();
        mLayoutParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());
        mLayoutParams.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());
        mTextView.setLayoutParams(mLayoutParams);
        mTextView.setText("正在重启…");
        mTextView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics()));
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextColor(getContext().getResources().getColor(R.color.white));
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(mParentView);
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
//        setCancelable(false);
//        mHandlerThread = new HandlerThread("");
//        mHandlerThread.start();
//        mHandler = new Handler(mHandlerThread.getLooper()) {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (mLoadView != null && (mLoadView.getParent()) != null) {
                    ((ViewGroup) mLoadView.getParent()).removeView(mLoadView);
                }
                if (mTextView != null && (mTextView.getParent()) != null) {
                    ((ViewGroup) mTextView.getParent()).removeView(mTextView);
                }
                mLoadView = null;
                if (mType >= 11) {
                    mType = 0;
                } else {
                    mType++;
                }
                mLoadView = new LoadView(mContext, mType);
                LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                Resources r = context.getResources();
                mLayoutParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, r.getDisplayMetrics());
                mLoadView.setLayoutParams(mLayoutParams);
                mParentView.addView(mLoadView);
                mParentView.addView(mTextView);

                mHandler.sendEmptyMessageDelayed(MSG_CHECK_USB_WIFI, CHECK_SERIAL_PERIOD);
            }
        };

    }

    @Override
    public void show() {
        super.show();
        mHandler.sendEmptyMessage(MSG_CHECK_USB_WIFI);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mHandler.removeMessages(MSG_CHECK_USB_WIFI);
//        mHandler.removeMessages(MSG_CHECK_USB_WIFI);
//        mHandler.getLooper().quitSafely();
    }
//    
//    private class UpdateLoadViewRunnable implements Runnable{
//
//        private int type = -1;
//
//        public UpdateLoadViewRunnable(int type) {
//            super();
//            this.type = type;
//        }
//
//        @Override
//        public void run() {
//            ((ViewGroup) mLoadView.getParent()).removeView(mLoadView);
//            mLoadView = new LoadView(mContext, type);
//            mParentView.addView(mLoadView);
//        }
//    }
}