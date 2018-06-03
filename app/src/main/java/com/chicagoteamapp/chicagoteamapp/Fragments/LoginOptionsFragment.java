package com.chicagoteamapp.chicagoteamapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.chicagoteamapp.chicagoteamapp.R;


public class LoginOptionsFragment extends Fragment {
    private static final String LOG_TAG = "My logs";

    private ImageButton mImageButtonReturnToLaunchScreen;
    private FrameLayout mEmail;
    private FrameLayout mFacebook;
    private FrameLayout mTwitter;
    private Button mButtonCreateAnAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_options, container, false);
        mImageButtonReturnToLaunchScreen = (ImageButton) view.findViewById(R.id.button_return_to_launch_screen_fragment_login_options);
        mImageButtonReturnToLaunchScreen.setOnClickListener(this::onClick);

        mEmail =  view.findViewById(R.id.layout_email_fragment_login_options);
        mFacebook =  view.findViewById(R.id.layout_facebook_fragment_login_options);
        mTwitter =  view.findViewById(R.id.layout_twitter_fragment_login_options);

        mButtonCreateAnAccount = view.findViewById(R.id.button_create_an_account_fragment_login_options);
        mButtonCreateAnAccount.setOnClickListener(this::onClick);

        Log.d(LOG_TAG, "LoginOptionsFragment created views");
        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_return_to_launch_screen_fragment_login_options:
                returnToLaunchScreen();
                Log.d(LOG_TAG, "LoginOptionsFragment, Return To Launch Screen is clicked");
                break;

            case R.id.layout_email_fragment_login_options:

                Log.d(LOG_TAG, "LoginOptionsFragment, Email is clicked");
                break;

            case R.id.layout_facebook_fragment_login_options:

                Log.d(LOG_TAG, "LoginOptionsFragment, Facebook is clicked");
                break;

            case R.id.layout_twitter_fragment_login_options:

                Log.d(LOG_TAG, "LoginOptionsFragment, Twitter is clicked");
                break;

            case R.id.button_create_an_account_fragment_login_options:
                callCreateAnAccountScreen();
                Log.d(LOG_TAG, "LoginOptionsFragment, Create An Account Screen is clicked");
                break;
        }
    }

    private void callCreateAnAccountScreen() {
        Fragment fragment = new SignupFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.act, fragment);
        transaction.commit();
    }

    private void returnToLaunchScreen() {
        Fragment fragment = new LoginOptionsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.activity_launch, fragment);
        transaction.commit();
    }
}
