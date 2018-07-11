package com.chicagoteamapp.chicagoteamapp.fragments;

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
    public String id, name, email;

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
        initializeFacebook();
        Log.d(TAG, "onCreateView");
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    private void initializeFacebook() {
        mCallbackManager = CallbackManager.Factory.create();
        mLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                Toast.makeText(getActivity(), "Welcome " + mUser.getProviderData().get(0).getDisplayName(),
                        Toast.LENGTH_SHORT).show();
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
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(getActivity(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
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

        quitDialog.setNegativeButton("No", (dialog, which) -> {
        });

        quitDialog.show();
    }
}