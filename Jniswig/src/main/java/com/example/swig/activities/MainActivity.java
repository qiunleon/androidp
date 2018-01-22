package com.example.swig.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.swig.R;
import com.example.swig.jni.example;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("example");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, ""+example.gcd(3, 12), Toast.LENGTH_SHORT).show();
    }
}
