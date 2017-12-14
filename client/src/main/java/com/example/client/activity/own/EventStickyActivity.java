package com.example.client.activity.own;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.data.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by alienware on 2017/12/14.
 */
public class EventStickyActivity extends Activity {

    private TextView tv_message;
    private Button bt_message;
    private Button bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_sticky);
        tv_message = (TextView) this.findViewById(R.id.tv_message);
        tv_message.setText("EventStickyActivity");
        bt_message = (Button) this.findViewById(R.id.bt_message);
        bt_message.setText("Open EventPostActivity");
        bt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventStickyActivity.this, EventPostActivity.class));
            }
        });
        bt_register = (Button) this.findViewById(R.id.bt_register);
        bt_register.setText("Open EventPostActivity");
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册事件
                EventBus.getDefault().register(EventStickyActivity.class);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onMoonStickyEvent(MessageEvent messageEvent) {
        tv_message.setText(messageEvent.getMessage());
    }
}
