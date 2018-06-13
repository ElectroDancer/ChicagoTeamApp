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
import android.widget.Toast;

import com.chicagoteamapp.chicagoteamapp.Account;
import com.chicagoteamapp.chicagoteamapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignupFragment extends Fragment implements View.OnClickListener {
    private static final String LOG_TAG = "SignupFragment";
    public static final String TAG = "SignupFragmentTag";

    @BindView(R.id.button_return_to_launch_screen_fragment_signup) ImageButton mImageButtonReturnToLaunchScreen;
    @BindView(R.id.edit_add_name_fragment_signup) EditText mEditTextAddName;
    @BindView(R.id.edit_add_email_fragment_signup) EditText mEditTextAddEmail;
    @BindView(R.id.editTextPassword_fragment_signup) EditText mEditTextAddPassword;
    @BindView(R.id.button_create_an_account_fragment_signup) Button mButtonCreateAnAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        ButterKnife.bind(this, view);


        Log.d(LOG_TAG, "onCreateView");
        return view;
    }

    @OnClick({R.id.button_return_to_launch_screen_fragment_signup,
            R.id.button_create_an_account_fragment_signup})
    void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.button_return_to_launch_screen_fragment_signup:
                returnToLaunchScreen();
                Log.d(LOG_TAG, "Return To Launch Screen is clicked");
                break;

            case R.id.button_create_an_account_fragment_signup:
                createAnAccount();
                Log.d(LOG_TAG, "Create An Account is clicked");
                break;
        }
    }

    private void createAnAccount() {
        String name = getNameFromEditText();
        String email = getEmailFromEditText();
        String pass = getPasswordFromEditText();
        Account account = new Account(name, email, pass);
    }

    private void returnToLaunchScreen() {
        Fragment fragment = new LoginOptionsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    @NonNull
    private String getNameFromEditText(){
        String name = mEditTextAddName.getText().toString();
        return verifyName(name);
    }

    private String verifyName(String name) {
        if (!name.matches("[a-zA-Z0-9]{4,12}")){
            Toast.makeText(getContext(), "Name format is wrong", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
        return name;
    }

    @NonNull
    private String getEmailFromEditText(){
        String email = mEditTextAddEmail.getText().toString();
        return verifyEmail(email);
    }

    private String verifyEmail(String email) {
        if (!email.matches(".+@.+\\..+")){
            Toast.makeText(getContext(), "Email format is wrong", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getContext(), email, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Name format is wrong", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getContext(), password, Toast.LENGTH_SHORT).show();
        return password;
    }

    @Override
    public void onClick(View v) {

    }
}
