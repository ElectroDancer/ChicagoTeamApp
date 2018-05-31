package com.chicagoteamapp.chicagoteamapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SplashLoginFragment extends Fragment {
    private Button mCreateAnAccount;
    private Button mLoginWithEmail;
    private Button mFacebook;
    private Button mMoreWaysToLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_login, container, false);
    }

    public void moreWaysToLogin(View view) {
        Fragment fragment = new LoginOptionsFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
//        transaction.replace(R.id.a, fragment);
//        transaction.commit();
    }
}
