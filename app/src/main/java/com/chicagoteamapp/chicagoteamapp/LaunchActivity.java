package com.chicagoteamapp.chicagoteamapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.chicagoteamapp.chicagoteamapp.ui.SplashLoginFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LaunchActivity extends FragmentActivity {
    private FirebaseAuth mAuth;
    public FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

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
        else Log.d("Fragment Manager", "fragment is null");
    }
}
