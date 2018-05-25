package com.chicagoteamapp.chicagoteamapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class LaunchActivity extends AppCompatActivity {

    private TextView mWelcome;
    private Button mCreateAnAccount;
    private Button mLoginWithEmail;
    private Button mFacebook;
    private Button mMoreWaysToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        initUI();
    }

    private void initUI() {
        mWelcome = findViewById(R.id.text_welcome);
        mCreateAnAccount = findViewById(R.id.button_create_an_account);
        mLoginWithEmail = findViewById(R.id.button_login_with_email);
        mFacebook = findViewById(R.id.button_facebook);
        mMoreWaysToLogin = findViewById(R.id.button_more_ways_to_login);
    }
}
