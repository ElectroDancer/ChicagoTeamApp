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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginWithEmailFragment extends Fragment implements View.OnClickListener {
    private static final String LOG_TAG = "LoginWithEmailFragment";
    public static final String TAG = "LoginWithEmailFragmentTag";

    private Fragment fragment;
    private FragmentManager fm;
    @BindView(R.id.button_return_to_launch_screen_fragment_login_with_email) ImageButton mImageButtonReturnToLaunchScreen;
    @BindView(R.id.button_create_an_account_fragment_login_with_email) Button mButtonCreateAnAccount;
    @BindView(R.id.text_forgot_the_password_fragment_login_with_email) TextView mTextForgotThePassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_with_email, container, false);

        ButterKnife.bind(this, view);

        Log.d(LOG_TAG, "onCreateView");
        return view;
    }

    @OnClick({R.id.button_return_to_launch_screen_fragment_login_with_email,
            R.id.button_create_an_account_fragment_login_with_email,
            R.id.text_forgot_the_password_fragment_login_with_email})
    void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.button_return_to_launch_screen_fragment_login_with_email:
                returnToLaunchScreen();
                Log.d(LOG_TAG, "Return To Launch Screen is clicked");
                break;

            case R.id.button_create_an_account_fragment_login_with_email:

                Log.d(LOG_TAG, "Create An Account is clicked");
                break;

            case R.id.text_forgot_the_password_fragment_login_with_email:
                recoveryPasswordScreen();
                Log.d(LOG_TAG, "Forgot The Password is clicked");
                break;
        }
    }

    private void recoveryPasswordScreen() {
        fragment = new ForgotPasswordFragment();
        fm = getFragmentManager();
        assert fm != null;
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.activity_launch, fragment, fragment.getClass().getName())
                .commit();
    }

    private void returnToLaunchScreen() {
        fragment = new LoginOptionsFragment();
        fm = getFragmentManager();
        assert fm != null;
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.activity_launch, fragment, fragment.getClass().getName())
                .commit();
    }

    @Override
    public void onClick(View v) {

    }
}
