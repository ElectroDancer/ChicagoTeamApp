package com.chicagoteamapp.chicagoteamapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chicagoteamapp.chicagoteamapp.Account;
import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.taskslist.TasksActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginWithEmailFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "LoginWithEmailFragment";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @BindView(R.id.image_button_return)
    ImageButton mImageButtonReturnToLaunchScreen;
    @BindView(R.id.edit_text_add_email_fragment_login_with_email)
    EditText mEditTextAddEmail;
    @BindView(R.id.edit_text_password_fragment_login_with_email)
    EditText mEditTextAddPassword;
    @BindView(R.id.button_login_fragment_login_with_email)
    Button mButtonLogin;
    @BindView(R.id.text_forgot_the_password_fragment_login_with_email)
    TextView mTextForgotThePassword;

//    private String name, email, password, mUserID = null;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_with_email,
                container, false);

        ButterKnife.bind(this, view);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG, "onCreateView");
        return view;
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.button_login_fragment_login_with_email)
    void signIn() {
        hideKeyboard();
        SplashLoginFragment.email = mEditTextAddEmail.getText().toString().trim();
        SplashLoginFragment.password = mEditTextAddPassword.getText().toString().trim();
        Log.d(TAG, "signIn:" + SplashLoginFragment.email);
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(SplashLoginFragment.email, SplashLoginFragment.password)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
                    if (task.isSuccessful()) {
                        mUser = mAuth.getCurrentUser();
                        assert mUser != null;
                        mDatabase.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                SplashLoginFragment.name = (Objects.requireNonNull(dataSnapshot.child("Account")
                                        .getValue(Account.class)).getName()); //set the name
                                if (SplashLoginFragment.name != null) {
                                    Toast.makeText(getActivity(), "Welcome " + SplashLoginFragment.name,
                                            Toast.LENGTH_SHORT).show();
                                }
                                Intent intent = new Intent(getContext(), TasksActivity.class);
                                startActivity(intent);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Log.d(TAG, "signInWithEmail:success");
                    } else {
                        if (Objects.equals(task.getException(), FirebaseAuthUserCollisionException.class)) {
                            Toast.makeText(getContext(), "The email address is already in use by another account",
                                    Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "The email address is already in use by another account ", task.getException());
                        }
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(getContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;
        if (!validateEmail(SplashLoginFragment.email)) {
            mEditTextAddEmail.setError("Not a valid email address!");
            valid = false;
        } else {
            mEditTextAddEmail.setError(null);
        }

        if (!validatePassword(SplashLoginFragment.password)) {
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

    private void hideKeyboard() {
        View view = Objects.requireNonNull(getActivity()).getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) Objects.requireNonNull(Objects.requireNonNull(getContext())
                    .getSystemService(Context.INPUT_METHOD_SERVICE))).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @OnClick(R.id.text_forgot_the_password_fragment_login_with_email)
    void recoveryPasswordScreen() {
        Fragment fragment = new ForgotPasswordFragment();
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container,
                new ForgotPasswordFragment(), fragment.getClass().getName())
                .addToBackStack("ForgotPasswordFragment")
                .commit();
        Log.d(TAG, "Forgot The Password is clicked");
    }

    @OnClick(R.id.image_button_return)
    void returnToLaunchScreen() {
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
