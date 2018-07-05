package com.chicagoteamapp.chicagoteamapp.Fragments;


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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chicagoteamapp.chicagoteamapp.LaunchActivity;
import com.chicagoteamapp.chicagoteamapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginOptionsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "LoginOptionsFragment";

    private Fragment fragment;
    private FragmentTransaction ft;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private TwitterAuthClient client;

    @BindView(R.id.button_return) ImageButton mImageButtonReturnToLaunchScreen;
    @BindView(R.id.button_login_with_email_fragment_login_options) Button mEmail;
    @BindView(R.id.button_fb) Button mFacebook;
    @BindView(R.id.button_facebook_login) LoginButton mLoginFacebook;
    @BindView(R.id.button_twitter_fragment_login_options) Button mTwitter;
    @BindView(R.id.twitterLogin) TwitterLoginButton mTwitterLoginButton;
    @BindView(R.id.button_create_an_account_fragment_login_options) Button mButtonCreateAnAccount;

    @BindView(R.id.user_profile_image_view) ImageView userProfileImageView;
    @BindView(R.id.user_details_label) TextView userDetailsLabel;

    public String id, name, email;
    CallbackManager mCallbackManager;

    public LoginOptionsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_options, container, false);
        ButterKnife.bind(this, view);

        mAuth = FirebaseAuth.getInstance();
        initializeTwitter();
        initializeFirebaseFacebook();

        Log.d(TAG, "onCreateView");
        return view;
    }

    private void initializeFirebaseFacebook() {
        mCallbackManager = CallbackManager.Factory.create();
        mLoginFacebook.setReadPermissions("email", "public_profile");
        mLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
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

    @Override
    public void onStart() {
        super.onStart();
        mUser = mAuth.getCurrentUser();
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
                        Toast.makeText(getContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (client != null){
            client.onActivityResult(requestCode, resultCode, data);
        } else
        mTwitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void initializeTwitter() {
        client = new TwitterAuthClient();
        mTwitter.setOnClickListener(v -> client.authorize(Objects.requireNonNull(getActivity()),
                new com.twitter.sdk.android.core.Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> twitterSessionResult) {
                    }

                    @Override
                    public void failure(TwitterException e) {
                        e.printStackTrace();
                    }
                }));

        mTwitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                if(mAuth.getCurrentUser() == null) {
                    handleTwitterSession(result.data);
                }
            }
            @Override
            public void failure(TwitterException exception) {
            }
        });
    }

    private void handleTwitterSession(TwitterSession session) {
        Log.d(TAG, "handleTwitterSession:" + session);

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        mUser = mAuth.getCurrentUser();
                        Intent intent = new Intent(getActivity(), LaunchActivity.class);
                        startActivity(intent);

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(getActivity(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }

                });
    }

    @OnClick(R.id.button_return)
    void returnToLaunchScreen() {
        assert getFragmentManager() != null;
        FragmentManager fm = getFragmentManager();
        fm.findFragmentById(R.id.main_container);
        if(fm.getBackStackEntryCount() > 0)
            fm.popBackStack();
        Log.d(TAG, "Return To Launch Screen is clicked");
    }

    @OnClick(R.id.button_login_with_email_fragment_login_options)
    void loginWithEmail() {
        fragment = new LoginWithEmailFragment();
        assert getFragmentManager() != null;
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_container, fragment, fragment.getClass().getName())
                .addToBackStack("LoginWithEmailFragment")
                .commit();
    }

    @OnClick(R.id.button_fb)
    void loginFacebook() {
        mLoginFacebook.performClick();
        Log.d(TAG, "loginFacebook");
    }

    @OnClick(R.id.button_create_an_account_fragment_login_options)
    void callCreateAnAccountScreen() {
        fragment = new SignupFragment();
        assert getFragmentManager() != null;
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_container, fragment)
                .addToBackStack("SignupFragment")
                .commit();
        Log.d(TAG, "Create an account");
    }

    @Override
    public void onClick(View v) {

    }

}
