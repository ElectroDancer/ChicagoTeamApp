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
import android.widget.EditText;
import android.widget.ImageButton;

import com.chicagoteamapp.chicagoteamapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ForgotPasswordFragment extends Fragment implements View.OnClickListener{
    private static final String LOG_TAG = "ForgotPasswordFragment";
    public static final String TAG = "ForgotPasswordFragmentTag";

    private Fragment fragment;
    private FragmentManager fm;
    @BindView(R.id.button_return_to_launch_screen_fragment_forgot_password) ImageButton mImageButtonReturnToLaunchScreen;
    @BindView(R.id.edit_add_email_if_forgot_password_fragment_forgot_password) EditText mEditTextAddEmail;
    @BindView(R.id.button_reset_password_fragment_forgot_password) Button mButtonCreateAnAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        ButterKnife.bind(this, view);

        Log.d(LOG_TAG, "onCreateView");
        return view;
    }

    @OnClick({R.id.button_return_to_launch_screen_fragment_forgot_password,
            R.id.button_reset_password_fragment_forgot_password})
    void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.button_return_to_launch_screen_fragment_forgot_password:
                returnToLoginOptionsScreen();
                Log.d(LOG_TAG, "Return To Launch Screen is clicked");
                break;

            case R.id.button_reset_password_fragment_forgot_password:
                resetPassword();
                Log.d(LOG_TAG, "Reset Password is clicked");
                break;
        }
    }

    private void resetPassword() {

    }

    private void returnToLoginOptionsScreen() {
        fragment = new LoginOptionsFragment();
        fm = getFragmentManager();
        assert fm != null;
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment, fragment.getClass().getName())
                .commit();
    }

    @Override
    public void onClick(View v) {

    }
}