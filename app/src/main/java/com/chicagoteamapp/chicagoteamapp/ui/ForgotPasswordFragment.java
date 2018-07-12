package com.chicagoteamapp.chicagoteamapp.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chicagoteamapp.chicagoteamapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ForgotPasswordFragment extends Fragment implements View.OnClickListener{
    public static final String TAG = "ForgotPasswordFragment";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @BindView(R.id.image_button_return) ImageButton mImageButtonReturnToLaunchScreen;
    @BindView(R.id.edit_text_add_email_if_forgot_password_fragment_forgot_password) EditText mEditTextAddEmail;
    @BindView(R.id.button_reset_password_fragment_forgot_password) Button mButtonCreateAnAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView");
        return view;
    }

    @SuppressLint("ShowToast")
    @OnClick(R.id.button_reset_password_fragment_forgot_password)
    void resetPassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email = mEditTextAddEmail.getText().toString();
        if (!validateEmail(email)) {
            mEditTextAddEmail.setError("Not a valid email address!");
            return;
        } else {
            mEditTextAddEmail.setError(null);
        }
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "We have sent you instructions to reset your password!");
                        Toast.makeText(getContext(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT)
                        .show();
                    } else {
                        Log.d(TAG, "Failed to send reset email!");
                        Toast.makeText(getContext(), "Failed to send reset email!", Toast.LENGTH_SHORT)
                        .show();
                    }
                });
        Log.d(TAG, "Reset Password is clicked");
    }

    private boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @OnClick(R.id.image_button_return)
    void returnToLoginOptionsScreen() {
        assert getFragmentManager() != null;
        FragmentManager fm = getFragmentManager();
        fm.findFragmentById(R.id.main_container);
        if(fm.getBackStackEntryCount() > 0)
            fm.popBackStack();
        Log.d(TAG, "Return To Launch Screen is clicked");
    }

    @Override
    public void onClick(View v) {

    }
}