package com.chicagoteamapp.chicagoteamapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class LaunchActivity extends AppCompatActivity {

    private TextView welcome;
    private Button createAnAccount;
    private Button loginWithEmail;
    private Button facebook;
    private Button moreWaysToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        initUI();
    }

    private void initUI() {
        welcome = findViewById(R.id.welcome);
        createAnAccount = findViewById(R.id.create_an_account);
        loginWithEmail = findViewById(R.id.login_with_email);
        facebook = findViewById(R.id.facebook);
        moreWaysToLogin = findViewById(R.id.more_ways_to_login);
    }
}
