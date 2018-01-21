package com.example.architecture;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static final String FRAGMENT_TAG_EXAMPLE = "fragment_tag_example";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ExampleFragment fragment = (ExampleFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG_EXAMPLE);
        if (fragment == null) {
            fragment = ExampleFragment.newInstance("param1", "param2");
            fragmentManager.beginTransaction().add(R.id.fl_container, fragment, FRAGMENT_TAG_EXAMPLE).commit();
        }

        new ExamplePresenter(fragment);
    }
}
