package com.chicagoteamapp.chicagoteamapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.chicagoteamapp.chicagoteamapp.Fragments.SplashLoginFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import java.util.Objects;


public class LaunchActivity extends FragmentActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterConfig config = new TwitterConfig.Builder(Objects.requireNonNull(this))
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(
                        getString(R.string.twitter_consumer_key),
                        getString(R.string.twitter_consumer_secret)))
                .debug(false)
                .build();
        Twitter.initialize(config);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_container, new SplashLoginFragment())
                    .commit();
        setContentView(R.layout.activity_launch);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragment = getSupportFragmentManager();
        if (fragment != null) {
            fragment.findFragmentById(R.id.main_container).onActivityResult(requestCode, resultCode, data);
        }
        else Log.d("Twitter", "fragment is null");
    }
}
