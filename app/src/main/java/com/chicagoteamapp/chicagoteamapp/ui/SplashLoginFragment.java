package com.chicagoteamapp.chicagoteamapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.chicagoteamapp.chicagoteamapp.Account;
import com.chicagoteamapp.chicagoteamapp.BackableFragment;
import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.taskslist.TasksActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SplashLoginFragment extends BackableFragment implements View.OnClickListener {
    private static final String TAG  = "SplashLoginFragment";
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private Fragment fragment;
    private android.support.v4.app.FragmentTransaction ft;
    @BindView(R.id.button_create_an_account_fragment_splash_login) Button mCreateAnAccount;
    @BindView(R.id.button_more_ways_to_login_fragment_splash_login) Button mMoreWaysToLogin;
    @BindView(R.id.button_fb) Button mFacebook;
    @BindView(R.id.button_login_with_email_fragment_splash_login) Button mEmail;
    @BindView(R.id.button_facebook_login) LoginButton mLoginFacebook;

    CallbackManager mCallbackManager;
    private DatabaseReference mDatabase;
    public static String name, email, password = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_login, container, false);
        ButterKnife.bind(this, view);

        FirebaseApp.initializeApp(getContext());
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        initializeFacebook();
        Log.d(TAG, "onCreateView");
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initializeFacebook() {
        mCallbackManager = CallbackManager.Factory.create();
        mLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                mAuth = FirebaseAuth.getInstance();
                mUser = mAuth.getCurrentUser();
                assert mUser != null;
                name = mUser.getDisplayName();
                email = mUser.getEmail();
                verifyUser(mUser);
                if (name != null) {
                Toast.makeText(getActivity(), "Welcome " + name,
                        Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getContext(), TasksActivity.class);
                startActivity(intent);
            }
            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }
            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        mAuth = FirebaseAuth.getInstance();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(getActivity(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void verifyUser(FirebaseUser mUser) {
        if (mDatabase.child(mUser.getUid()) == null) {
            writeNewUser(SplashLoginFragment.name, SplashLoginFragment.email);
        }
    }

    private void writeNewUser(String name, String email) {
        Account account = new Account(name, email);
        String mUserID = mUser.getUid();
        mDatabase.child(mUserID).child("Account").setValue(account);
    }

    @OnClick(R.id.button_create_an_account_fragment_splash_login)
    void createAnAccount() {
        fragment = new SignupFragment();
        assert getFragmentManager() != null;
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_container, fragment)
                .addToBackStack("SignupFragment")
                .commit();
    }

    @OnClick(R.id.button_login_with_email_fragment_splash_login)
    void loginWithEmail() {
        fragment = new LoginWithEmailFragment();
        assert getFragmentManager() != null;
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_container, fragment, fragment.getClass().getName())
                .addToBackStack("LoginWithEmailFragment")
                .commit();
    }

    @OnClick(R.id.button_more_ways_to_login_fragment_splash_login)
    void moreWaysToLogin() {
        fragment = new LoginOptionsFragment();
        assert getFragmentManager() != null;
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_container, fragment, fragment.getClass().getName())
                .addToBackStack("LoginOptionsFragment")
                .commit();
    }

    @OnClick(R.id.button_fb)
    void loginFacebook() {
        mLoginFacebook.performClick();
        Log.d(TAG, "loginFacebook");
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onBackButtonPressed() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                Objects.requireNonNull(getContext()));
        quitDialog.setTitle("Exit: are you sure?");

        quitDialog.setPositiveButton("Yes!", (dialog, which) ->
                Objects.requireNonNull(getActivity()).onBackPressed());
        quitDialog.setCancelable(false);
        quitDialog.setNegativeButton("No", (dialog, which) -> {
        });

        quitDialog.show();

    }
}