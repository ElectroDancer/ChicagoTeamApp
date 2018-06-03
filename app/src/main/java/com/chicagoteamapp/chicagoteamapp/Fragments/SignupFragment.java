package com.chicagoteamapp.chicagoteamapp.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chicagoteamapp.chicagoteamapp.R;


public class SignupFragment extends Fragment {

    private static final String LOG_TAG = "My logs";

    private ImageButton mImageButtonReturnToLaunchScreen;
    private EditText mEditTextAddName;
    private EditText mEditTextAddEmail;
    private EditText mEditTextAddPassword;
    private Button mButtonCreateAnAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        mImageButtonReturnToLaunchScreen =  view.findViewById(R.id.button_return_to_launch_screen_fragment_signup);
        mImageButtonReturnToLaunchScreen.setOnClickListener(this::onClick);

        mEditTextAddName =  view.findViewById(R.id.edit_add_name_fragment_signup);
        mEditTextAddEmail =  view.findViewById(R.id.edit_add_email_fragment_signup);
        mEditTextAddPassword =  view.findViewById(R.id.editTextPassword_fragment_signup);

        mButtonCreateAnAccount = view.findViewById(R.id.button_create_an_account_fragment_signup);
        mButtonCreateAnAccount.setOnClickListener(this::onClick);

        Log.d(LOG_TAG, "SignupFragment created views");
        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_return_to_launch_screen_fragment_signup:
                returnToLaunchScreen();
                Log.d(LOG_TAG, "SignupFragment, Return To Launch Screen is clicked");
                break;

            case R.id.button_create_an_account_fragment_signup:
                createAnAccount();
                Log.d(LOG_TAG, "SignupFragment, Create An Account is clicked");
                break;
        }
    }

    private void createAnAccount() {
        String name = getNameFromEditText();
        String email = getEmailFromEditText();
        String pass = getPasswordFromEditText();
//                Account account = new Account(name, email, pass);
    }

    private void returnToLaunchScreen() {
        Fragment fragment = new LoginOptionsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_launch, fragment);
        transaction.commit();
    }

    @NonNull
    private String getNameFromEditText(){
        String name = mEditTextAddName.getText().toString();
        return verifyName(name);
    }

    private String verifyName(String name) {
        if (!name.matches("[a-zA-Z0-9]{4,12}")){
            Toast.makeText(getContext(), "Name format is wrong", Toast.LENGTH_SHORT);
        }
        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT);
        return name;
    }

    @NonNull
    private String getEmailFromEditText(){
        String email = mEditTextAddEmail.getText().toString();
        return verifyEmail(email);
    }

    private String verifyEmail(String email) {
        if (!email.matches(".+@.+\\..+")){
            Toast.makeText(getContext(), "Email format is wrong", Toast.LENGTH_SHORT);
        }
        Toast.makeText(getContext(), email, Toast.LENGTH_SHORT);
        return email;
    }

@NonNull
    private String getPasswordFromEditText(){
        String password = mEditTextAddPassword.getText().toString();
        return verifyPassword(password);
    }

    /**
     *
     * @param password must contain at least one digit, one lowercase character and one uppercase character.
     * Its length at least 6 characters and maximum 16 characters.
     * @return password if matching complete successfully, else throws the toast with message.
     */
    private String verifyPassword(String password) {
        if (!password.matches("(?=.*\\d+)(?=.*[a-z]+)(?=.*[A-Z]+){6,16}")){
            Toast.makeText(getContext(), "Name format is wrong", Toast.LENGTH_SHORT);
        }
        Toast.makeText(getContext(), password, Toast.LENGTH_SHORT);
        return password;
    }
}
