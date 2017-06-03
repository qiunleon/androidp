package com.example.client.activity.own;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.activity.introduction.ScrollViewMainActivity;
import com.example.client.jni.ExampleJNI;
import com.example.client.manager.NetworkManager;
import com.example.client.manager.RemoteServiceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.button_start)
    Button mStartButton;

    @BindView(R.id.button_stop)
    Button mStopButton;

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
        RemoteServiceManager.getInstance().addBook();
    }

    @OnClick(R.id.button_linear)
    public void onClickLinear() {
        Intent intent = new Intent(this, LinearActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_linear_scroll)
    public void onClickLinearScroll() {
        Intent intent = new Intent(this, LinearScrollActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_grid)
    public void onClickGrid() {
        Intent intent = new Intent(this, GridActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_staggered)
    public void onClickStaggered() {
        Intent intent = new Intent(this, StaggeredActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_jni)
    public void onClickJni() {
        Toast.makeText(this, "GCD(12, 49) = " + String.valueOf(ExampleJNI.gcd(2, 4)), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_scrollview)
    public void onClickScrollView() {
        Intent intent = new Intent(this, ScrollViewMainActivity.class);
        startActivity(intent);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    static {
        System.loadLibrary("example");
    }
}