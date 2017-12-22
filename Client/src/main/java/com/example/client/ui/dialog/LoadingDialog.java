package com.example.client.ui.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.animation.Animation;
import android.widget.TextView;

/**
 * Created by Qiu on 2017/6/8.
 */
public   class LoadingDialog extends ProgressDialog {

    private Context mContext;

    private TextView mTextView;

    public LoadingDialog(Context context) {
        super(context);
        mContext = context;

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        Window dialogWindow = this.getWindow();
//        dialogWindow.setGravity(Gravity.CENTER);
//        dialogWindow.setBackgroundDrawable(mContext.getResources().getDrawable(android.R.color.transparent));
//        this.setContentView(R.layout.dialog_evideo_custom_loading);
//        ((TextView)findViewById(R.id.dialog_evideo_custom_text)).setText("5445");
//        setCancelable(false);
//        Animation animation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(1000);
//        animation.setRepeatCount(0);
//        animation.setFillAfter(false);
//        animation.setAnimationListener(new RepeatAnimationListener());
//        findViewById(R.id.dialog_evideo_custom_progressbar).startAnimation(animation);
    }

     public class RepeatAnimationListener implements Animation.AnimationListener {

        public void onAnimationEnd(Animation animation) {
            animation.reset();
            animation.setAnimationListener(new RepeatAnimationListener());
            animation.start();
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }
}
