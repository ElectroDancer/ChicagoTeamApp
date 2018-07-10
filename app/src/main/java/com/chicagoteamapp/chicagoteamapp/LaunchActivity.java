package com.chicagoteamapp.chicagoteamapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.chicagoteamapp.chicagoteamapp.fragments.SplashLoginFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import java.util.Objects;


public class LaunchActivity extends FragmentActivity {
    public FirebaseUser mUser;

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

//        FirebaseApp.initializeApp(this);
//        mUser = FirebaseAuth.getInstance().getCurrentUser();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_container, new SplashLoginFragment())
                    .commit();
            setContentView(R.layout.activity_launch);
        }

}
