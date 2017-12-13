package com.example.client.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import com.example.client.R;

public class EvProgressDialog extends Dialog {

    private Context mContext;

    public EvProgressDialog(Context context) {
        super(context);
        mContext = context;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_evideo_custom_loading);
//        this.setCancelable(false);
        // this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        this.getWindow().setGravity(Gravity.CENTER);
        this.getWindow().setBackgroundDrawable(mContext.getResources().getDrawable(R.color.transparent));

        AnimationSet set = new AnimationSet(true);
        Animation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);

        Animation alpha = new AlphaAnimation(0.0f, 1.0f);
        Animation alpha3 = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(3000);
        alpha.setDuration(500);
        alpha.setStartOffset(1500);
        alpha3.setDuration(500);
        alpha3.setStartOffset(2000);

        set.addAnimation(alpha);
        set.addAnimation(animation);
        set.addAnimation(alpha3);
        set.setFillAfter(false);
        set.setAnimationListener(new Animation.AnimationListener(){

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

        findViewById(R.id.dialog_evideo_custom_top).startAnimation(set);
    }
}