package com.example.client.activity.own;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.data.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by alienware on 2017/12/14.
 */
public class EventPostActivity extends Activity {

    private TextView tv_message;
    private Button bt_post_normal;
    private Button bt_post_sticky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_post);
        tv_message = (TextView) this.findViewById(R.id.tv_message);
        tv_message.setText("SecondActivity");
        bt_post_normal = (Button) this.findViewById(R.id.bt_post_normal);
        bt_post_normal.setText("POST");
        bt_post_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("Normal event"));
                finish();
            }
        });
        bt_post_sticky = (Button) this.findViewById(R.id.bt_post_sticky);
        bt_post_sticky.setText("POST");
        bt_post_sticky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new MessageEvent("Sticky event"));
                finish();
            }
        });
    }
}
