package com.chicagoteamapp.chicagoteamapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chicagoteamapp.chicagoteamapp.Account;
import com.chicagoteamapp.chicagoteamapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignupFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SignupFragment";
    private String name;
    private String email;
    private String password;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private String mUserID;
    @BindView(R.id.button_return) ImageButton mImageButtonReturnToLaunchScreen;
    @BindView(R.id.layout_name_fragment_signup) TextInputLayout mTextInputLayoutAddName;
    @BindView(R.id.edit_add_name_fragment_signup) EditText mEditTextAddName;
    @BindView(R.id.edit_add_email_fragment_signup) EditText mEditTextAddEmail;
    @BindView(R.id.layout_password_fragment_signup) TextInputLayout mTextInputLayoutAddPassword;
    @BindView(R.id.editTextPassword_fragment_signup) EditText mEditTextAddPassword;
    @BindView(R.id.button_create_an_account_fragment_signup) Button mButtonCreateAnAccount;

    public SignupFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mAuthListener = firebaseAuth -> {
//            mUser = firebaseAuth.getCurrentUser();
//            if (mUser != null) {
//                // User is signed in
//                Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
//                Toast.makeText(getContext(), "Successfully signed in with: " + mUser.getEmail(),
//                        Toast.LENGTH_SHORT).show();
//            } else {
//                // User is signed out
//                Log.d(TAG, "onAuthStateChanged:signed_out");
//                Toast.makeText(getContext(), "Successfully signed out.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        };
////        mUserID = mUser.getUid();
        Log.d(TAG, "onCreateView");
        return view;
    }

    private void writeNewUser(String name, String email) {
        Account account = new Account(name, email);
        String mUserID = mUser.getUid();
        mDatabase.child(mUserID).child("Account").setValue(account);
    }

    @OnClick(R.id.button_create_an_account_fragment_signup)
    void createAccount() {
        Log.d(TAG, "Create An Account is clicked");
        hideKeyboard();
        name = mEditTextAddName.getText().toString().trim();
        email = mEditTextAddEmail.getText().toString().trim();
        password = mEditTextAddPassword.getText().toString().trim();
        Log.d(TAG, "createAccount:" + email +
                "\n" + password);
        if (!validateForm()) {
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(getContext(), Objects.requireNonNull(task.getException()).getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.i("TAG", "Registration Error " +
                        Objects.requireNonNull(task.getException()).getMessage());
            } else {
                mUser = mAuth.getCurrentUser();
                assert mUser != null;
                mUser.sendEmailVerification().addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful()){
                        boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                        if (isNewUser) {
                            writeNewUser(name, email);
                            Log.i("TAG", "Successfully registered");
                            Toast.makeText(getContext(), "Successfully registered",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("TAG", "This mail already exists!");
                            Toast.makeText(getContext(), "This mail already exists!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void hideKeyboard() {
        View view = Objects.requireNonNull(getActivity()).getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) Objects.requireNonNull(Objects.requireNonNull(getContext())
                    .getSystemService(Context.INPUT_METHOD_SERVICE))).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private boolean validateForm() {
        boolean valid = true;
        if (!validateName(name)) {
            mEditTextAddName.setError("Not a valid name!");
            valid = false;
        } else {
            mEditTextAddName.setError(null);
        }

        if (!validateEmail(email)) {
            mEditTextAddEmail.setError("Not a valid email address!");
            valid = false;
        } else {
            mEditTextAddEmail.setError(null);
        }

        if (!validatePassword(password)) {
            mEditTextAddPassword.setError("Not a valid password!");
            valid = false;
        } else {
            mEditTextAddPassword.setError(null);
        }
        return valid;
    }

    private boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return (password.length() >= 4 & password.length() <= 20);
    }

    public boolean validateName(String name) {
        return name.length() >= 4 & name.length() <= 16;
    }

    @OnClick(R.id.button_return)
    void returnToLoginOptionsScreen() {
        assert getFragmentManager() != null;
        FragmentManager fm = getFragmentManager();
        fm.findFragmentById(R.id.main_container);
        if(fm.getBackStackEntryCount() > 0)
            fm.popBackStack();
        Log.d(TAG, "Return To Launch Screen is clicked");
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }

    @Override
    public void onClick(View v) {

    }
}
