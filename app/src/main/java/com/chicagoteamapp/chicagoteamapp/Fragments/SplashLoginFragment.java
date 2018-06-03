package com.chicagoteamapp.chicagoteamapp.Fragments;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;


import com.chicagoteamapp.chicagoteamapp.Account;
import com.chicagoteamapp.chicagoteamapp.R;

import java.util.Map;


public class SplashLoginFragment extends Fragment {
    final String LOG_TAG = "My log";

    private Button mCreateAnAccount;
    private Button mLoginWithEmail;
    private FrameLayout mFacebook;
    private Button mMoreWaysToLogin;
    public static Map<String, Account> accountMap = new ArrayMap<String, Account>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_login, container, false);
//        mCreateAnAccount =  view.findViewById(R.id.button_create_an_account_fragment_splash_login);
//        mCreateAnAccount.setOnClickListener(this::onClick);
//
//        mLoginWithEmail =  view.findViewById(R.id.button_login_with_email_fragment_splash_login);
//        mLoginWithEmail.setOnClickListener(this::onClick);
//
//        mFacebook =  view.findViewById(R.id.layout_facebook_fragment_splash_login);
//        mFacebook.setOnClickListener(this::onClick);
//
//        mMoreWaysToLogin =  view.findViewById(R.id.button_more_ways_to_login_fragment_splash_login);
//        mMoreWaysToLogin.setOnClickListener(this::onClick);

        Log.d(LOG_TAG, "SplashLoginFragment created views");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mCreateAnAccount =  getActivity().findViewById(R.id.button_create_an_account_fragment_splash_login);
        mCreateAnAccount.setOnClickListener(this::onClick);

        mLoginWithEmail =  getActivity().findViewById(R.id.button_login_with_email_fragment_splash_login);
        mLoginWithEmail.setOnClickListener(this::onClick);

        mFacebook =  getActivity().findViewById(R.id.layout_facebook_fragment_splash_login);
        mFacebook.setOnClickListener(this::onClick);

        mMoreWaysToLogin =  getActivity().findViewById(R.id.button_more_ways_to_login_fragment_splash_login);
        mMoreWaysToLogin.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_create_an_account_fragment_splash_login:
                createAnAccount();
                Log.d(LOG_TAG, "SplashLoginFragment, Create An Account is clicked");
                break;

            case R.id.button_login_with_email_fragment_splash_login:

                Log.d(LOG_TAG, "SplashLoginFragment, Login With Email is clicked");
                break;

            case R.id.layout_facebook_fragment_splash_login:

                Log.d(LOG_TAG, "SplashLoginFragment, Facebook is clicked");
                break;

            case R.id.button_more_ways_to_login_fragment_splash_login:
                moreWaysToLogin();
                Log.d(LOG_TAG, "SplashLoginFragment, More Ways To Login is clicked");
                break;
        }
    }

    private void moreWaysToLogin() {
        Fragment fragment = new LoginOptionsFragment();
        FragmentManager fm = getFragmentManager();
        @SuppressLint("CommitTransaction")
        FragmentTransaction transaction = fm != null ? fm.beginTransaction() : null;
//        transaction.replace(R.id.activity_launch, fragment);
        transaction.commit();
    }

    private void createAnAccount() {
        Fragment fragment = new SignupFragment();
        FragmentManager fm = getFragmentManager();
        @SuppressLint("CommitTransaction")
        FragmentTransaction transaction = fm != null ? fm.beginTransaction() : null;
        assert transaction != null;
//        transaction.replace(R.id.activity_launch, fragment);
        transaction.commit();
    }

}
