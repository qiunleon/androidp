package com.example.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import com.example.client.R;
import com.example.client.manager.NetworkManager;
import com.example.client.manager.RemoteServiceManager;
import com.example.client.jni.exampleJNI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.button_start)
    Button mStartButton;

    @BindView(R.id.button_stop)
    Button mStopButton;

    @BindView(R.id.button_exit)
    Button mExitButton;

    @BindView(R.id.button_load)
    Button mLoadButton;

    @BindView(R.id.button_binder)
    Button mGetBinderButton;

    @BindView(R.id.switch_wifi)
    Switch mWifiSwitch;

    @BindView(R.id.switch_ap)
    Switch mApSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.button_start)
    public void onClickStart() {
        RemoteServiceManager.getInstance().bindRemoteService();
        mStartButton.setEnabled(false);
        mStopButton.setEnabled(true);
    }

    @OnClick(R.id.button_stop)
    public void onClickStop() {
        RemoteServiceManager.getInstance().unBindRemoteService();
        mStartButton.setEnabled(true);
        mStopButton.setEnabled(false);
        mStartButton.setText(String.valueOf(exampleJNI.gcd(2, 4)));
    }

    @OnClick(R.id.button_exit)
    public void onClickExit() {
        MainActivity.this.onBackPressed();
    }

    @OnClick(R.id.button_load)
    public void onClickLoad() {
        Intent intent = new Intent(MainActivity.this, ImageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_binder)
    public void onClickBinder() {

    }

    @OnCheckedChanged(R.id.switch_wifi)
    public void onCheckedChangedWifiMode(boolean isChecked) {
        NetworkManager.getInstance().setWifiEnabled(isChecked);
    }

    @OnCheckedChanged(R.id.switch_ap)
    public void onCheckedChangedApMode(boolean isChecked) {
        NetworkManager.getInstance().setWifiApEnabled(isChecked);
    }

    /**
     * Called when setContentView() or addContentView() finished.
     */
    @Override
    public void onContentChanged() {
        ButterKnife.bind(this);
        mStartButton.setEnabled(true);
        mStopButton.setEnabled(false);
        mWifiSwitch.setChecked(NetworkManager.getInstance().getWifiEnabled());
    }

    static {
        System.loadLibrary("example");
    }
}

