package com.chicagoteamapp.chicagoteamapp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.chicagoteamapp.chicagoteamapp.Fragments.SplashLoginFragment;


public class LaunchActivity extends FragmentActivity {

    private FragmentManager manager;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        initFragmentLast();
    }

    private void initFragmentLast() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.container, new SplashLoginFragment())
                .commit();
    }

}
