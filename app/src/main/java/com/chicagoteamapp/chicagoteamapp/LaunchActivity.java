package com.chicagoteamapp.chicagoteamapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.chicagoteamapp.chicagoteamapp.Fragments.SplashLoginFragment;


public class LaunchActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new SplashLoginFragment(), SplashLoginFragment.class.getName())
                .commit();
    }
}
