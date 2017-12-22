package com.example.client.activity.own;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.client.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

/**
 * Created by alienware on 2017/5/26.
 */
public class ImageActivity extends Activity {

    @DebugLog
    @Override
    protected void onCreate(Bundle save) {
        super.onCreate(save);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_start)
    void startImageView() {
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }
}