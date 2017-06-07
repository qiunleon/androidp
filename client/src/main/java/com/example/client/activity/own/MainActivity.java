package com.example.client.activity.own;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.example.client.R;
import com.example.client.manager.NetworkManager;
import com.example.client.manager.RemoteServiceManager;
import com.example.client.ui.custom.CustomToast;
import com.example.client.ui.dialog.CustomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

@SuppressWarnings("unused")
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
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.show();
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
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.spinner_item, null);
        new CustomToast().setContext(this).setCustomView(null).setDuration(1000).create().show();
    }

//    @OnClick(R.id.button_scrollview)
//    public void onClickScrollView() {
//        Intent intent = new Intent(this, ScrollViewMainActivity.class);
//        startActivity(intent);
//        CustomDialog customDialog = new CustomDialog(this);
//        customDialog.show();
//    }

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