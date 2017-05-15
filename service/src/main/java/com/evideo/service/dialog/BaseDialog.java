package com.evideo.service.dialog;

import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.evideo.service.activity.MainActivity;
import com.evideo.service.adapter.CommonAdapter;
import com.evideo.service.application.BaseApplication;
import com.evideo.service.manager.NotifyManager;
import com.evideo.service.R;

import java.util.ArrayList;

public class BaseDialog extends Dialog implements NotifyManager.ISomethingChangeListener {

    private MainActivity mActivity;

    private Button mButton;

    private ListView mListView;

    private CommonAdapter<String> mCommonAdapter;

    private ArrayList<String> mData = new ArrayList<>();

    public BaseDialog(MainActivity activity) {
        super(activity);
        this.mActivity = activity;
        initDialog();
    }

    private void initDialog() {
        this.setContentView(R.layout.dialog_main);

        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setTitle("BaseDialog");
        window.getDecorView().setPadding(0,0,0,0);
        window.setType(WindowManager.LayoutParams.TYPE_PHONE);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.y = -20;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        mButton = (Button) findViewById(R.id.dialog_button);
        mButton.setOnClickListener(mOnClickListener);

        mData.clear();
        mData.add("A");
        mData.add("B");
        mData.add("C");
        mListView = (ListView) findViewById(R.id.dialog_listview);
        mCommonAdapter = new CommonAdapter(BaseApplication.getInstance().getApplicationContext(), mData);
        mListView.setAdapter(mCommonAdapter);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mActivity.callback();
        }
    };

    @Override
    public void show() {
        super.show();
        NotifyManager.getInstance().registerSomethingChangeListener(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        NotifyManager.getInstance().unregisterSomethingChangeListener(this);
    }

    public void doSomething() {

    }
}
