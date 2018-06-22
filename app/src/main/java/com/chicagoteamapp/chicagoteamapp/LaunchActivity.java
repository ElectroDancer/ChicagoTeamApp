package com.chicagoteamapp.chicagoteamapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.chicagoteamapp.chicagoteamapp.Fragments.SplashLoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LaunchActivity extends FragmentActivity {
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_container, new SplashLoginFragment())
                    .commit();
        }

//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setApplicationId("2133840813355457") // Required for Analytics.
//                .setApiKey("bb288b37d636beeb551506658f6ac8e7") // Required for Auth.
//                .build();
//        FirebaseApp.initializeApp(Objects.requireNonNull(this) /* Context */, options, "secondary");
//        FirebaseInstanceId.getInstance().getToken();
//        FirebaseApp.initializeApp(this);
//        mAuth = FirebaseAuth.getInstance();
//        currentUser = mAuth.getCurrentUser();
    }
}
