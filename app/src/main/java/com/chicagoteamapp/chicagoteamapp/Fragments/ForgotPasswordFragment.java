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


public class ForgotPasswordFragment extends Fragment {
    private static final String LOG_TAG = "My logs";

    private ImageButton mImageButtonReturnToLaunchScreen;
    private EditText mEditTextAddEmail;
    private Button mButtonCreateAnAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        mImageButtonReturnToLaunchScreen = view.findViewById(R.id.button_return_to_launch_screen_fragment_forgot_password);
        mImageButtonReturnToLaunchScreen.setOnClickListener(this::onClick);

        mEditTextAddEmail = view.findViewById(R.id.edit_add_email_if_forgot_password_fragment_forgot_password);

        mButtonCreateAnAccount = view.findViewById(R.id.button_reset_password_fragment_forgot_password);
        mButtonCreateAnAccount.setOnClickListener(this::onClick);

        Log.d(LOG_TAG, "ForgotPasswordFragment created views");
        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_return_to_launch_screen_fragment_forgot_password:
                returnToLoginOptionsScreen();
                Log.d(LOG_TAG, "ForgotPasswordFragment, Return To Launch Screen is clicked");
                break;

            case R.id.button_reset_password_fragment_forgot_password:
                resetPassword();
                Log.d(LOG_TAG, "ForgotPasswordFragment, Reset Password is clicked");
                break;
        }
    }

    private void resetPassword() {

    }

    private void returnToLoginOptionsScreen() {
        Fragment fragment = new LoginOptionsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_launch, fragment);
        transaction.commit();
    }
}