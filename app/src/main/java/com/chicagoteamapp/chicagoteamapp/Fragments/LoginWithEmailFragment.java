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
import android.widget.ImageButton;
import android.widget.TextView;

import com.chicagoteamapp.chicagoteamapp.R;


public class LoginWithEmailFragment extends Fragment {
    private static final String LOG_TAG = "My logs";

    private ImageButton mImageButtonReturnToLaunchScreen;
    private Button mButtonCreateAnAccount;
    private TextView mTextForgotThePassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_with_email, container, false);
        mImageButtonReturnToLaunchScreen = view.findViewById(R.id.button_return_to_launch_screen_fragment_login_with_email);
        mImageButtonReturnToLaunchScreen.setOnClickListener(this::onClick);

        mButtonCreateAnAccount = view.findViewById(R.id.button_create_an_account_fragment_login_with_email);
        mButtonCreateAnAccount.setOnClickListener(this::onClick);

        mTextForgotThePassword = view.findViewById(R.id.text_forgot_the_password_fragment_login_with_email);
        mTextForgotThePassword.setOnClickListener(this::onClick);

        Log.d(LOG_TAG, "LoginWithEmailFragment created views");
        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_return_to_launch_screen_fragment_login_with_email:
                returnToLaunchScreen();
                Log.d(LOG_TAG, "LoginWithEmailFragment, Return To Launch Screen is clicked");
                break;

            case R.id.button_create_an_account_fragment_login_with_email:

                Log.d(LOG_TAG, "LoginWithEmailFragment, Create An Account is clicked");
                break;

            case R.id.text_forgot_the_password_fragment_login_with_email:
                recoveryPasswordScreen();
                Log.d(LOG_TAG, "LoginWithEmailFragment, Forgot The Password is clicked");
                break;
        }
    }

    private void recoveryPasswordScreen() {
        Fragment fragment = new ForgotPasswordFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_launch, fragment);
        transaction.commit();
    }

    private void returnToLaunchScreen() {
        Fragment fragment = new LoginOptionsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_launch, fragment);
        transaction.commit();
    }
}
