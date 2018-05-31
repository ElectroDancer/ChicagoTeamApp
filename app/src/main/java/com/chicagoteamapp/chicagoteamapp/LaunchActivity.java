package com.chicagoteamapp.chicagoteamapp;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class LaunchActivity extends FragmentActivity {

    private TextView mWelcome;
    private Button mCreateAnAccount;
    private Button mLoginWithEmail;
    private Button mFacebook;
    private Button mMoreWaysToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
//        initUI();
    }


    @SuppressLint("WrongViewCast")
    private void initUI() {
        mWelcome = findViewById(R.id.text_welcome);
        mCreateAnAccount = findViewById(R.id.button_create_an_account);
        mLoginWithEmail = findViewById(R.id.button_login_with_email);
        mFacebook = findViewById(R.id.layout_facebook);
        mMoreWaysToLogin = findViewById(R.id.button_more_ways_to_login);
    }
}
