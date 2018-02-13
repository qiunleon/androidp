package com.example.apt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.annotation.BindView;
import com.example.annotation.DaggerInjectActivity;

@DaggerInjectActivity
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerInjectMainActivity.bind(this);
        textView.setText("APT DEMO");
    }
}
