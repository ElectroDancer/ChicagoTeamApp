package com.chicagoteamapp.chicagoteamapp.Fragments;

import android.annotation.SuppressLint;
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

import com.chicagoteamapp.chicagoteamapp.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SplashLoginFragment extends Fragment
        implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    final String LOG_TAG = "SplashLoginFragment";
    public static final String TAG = "SplashLoginFragmentTag";
    private static final int RC_SIGN_IN = 430;
    private GoogleApiClient mGoogleApiClient;
    private Fragment fragment;
    private FragmentManager fm;
    @BindView(R.id.button_create_an_account_fragment_splash_login)
    Button mCreateAnAccount;
    @BindView(R.id.button_more_ways_to_login_fragment_splash_login)
    Button mMoreWaysToLogin;
    @BindView(R.id.button_fb_fragment_splash_login)
    Button mFacebook;
    @BindView(R.id.button_google_fragment_splash_login)
    Button mGoogle;
    @BindView(R.id.button_sign_in_fragment_splash_login)
    SignInButton mSignInButton;
    @BindView(R.id.login_button_fb_fragment_splash_login)
    LoginButton mLoginButton;

    private final static String G_PLUS_SCOPE =
            "oauth2:https://www.googleapis.com/auth/plus.me";
    private final static String USERINFO_SCOPE =
            "https://www.googleapis.com/auth/userinfo.profile";
    private final static String EMAIL_SCOPE =
            "https://www.googleapis.com/auth/userinfo.email";
    private final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE + " " + EMAIL_SCOPE;
    public String id, name, email, gender, birthday;
    CallbackManager mCallbackManager;

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

        FacebookSdk.sdkInitialize(Objects.requireNonNull(getContext()).getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        List<String> permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile", "AccessToken");

        initializeGPlusSettings();
        Log.d(LOG_TAG, "onCreateView");
        return view;
    }

    @OnClick({R.id.button_create_an_account_fragment_splash_login,
            R.id.button_google_fragment_splash_login,
            R.id.button_fb_fragment_splash_login,
            R.id.button_more_ways_to_login_fragment_splash_login})
    void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.button_create_an_account_fragment_splash_login:
                createAnAccount();
                Log.d(LOG_TAG, "Create An Account is clicked");
                break;

            case R.id.button_google_fragment_splash_login:
                signIn();
                Log.d(LOG_TAG, "Login With Email is clicked");
                break;

            case R.id.button_fb_fragment_splash_login:
                mLoginButton.performClick();
                Log.d(LOG_TAG, "Facebook is clicked");
                break;

            case R.id.button_more_ways_to_login_fragment_splash_login:
                moreWaysToLogin();
                Log.d(LOG_TAG, "More Ways To Login is clicked");
                break;
        }
    }

    private void initializeGPlusSettings(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(Objects.requireNonNull(getContext()))
                .enableAutoManage(Objects.requireNonNull(getActivity()), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mSignInButton.setSize(SignInButton.SIZE_STANDARD);
        mSignInButton.setScopes(gso.getScopeArray());
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                status -> updateUI(false));
    }

    private void handleGPlusSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            //Fetch values
            assert acct != null;
            String personName = acct.getDisplayName();
            String personPhotoUrl = Objects.requireNonNull(acct.getPhotoUrl()).toString();
            String email = acct.getEmail();
            String familyName = acct.getFamilyName();
            Log.e(TAG, "Name: " + personName + ", email: " + email + ", Image: " + personPhotoUrl + ", Family Name: " + familyName);
            updateUI(true);
        } else {
            updateUI(false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleGPlusSignInResult(result);
        } else {
            opr.setResultCallback(this::handleGPlusSignInResult);
        }
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode,
                                 Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGPlusSignInResult(result);
        } else {
            super.onActivityResult(requestCode, responseCode, data);
            mCallbackManager.onActivityResult(requestCode, responseCode, data);
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            mSignInButton.setVisibility(View.GONE);
        } else {
            mSignInButton.setVisibility(View.VISIBLE);
        }
    }


    private void moreWaysToLogin() {
        fragment = new LoginOptionsFragment();
        fm = getFragmentManager();
        assert fm != null;
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.activity_launch, fragment, fragment.getClass().getName())
                .commit();
    }

    private void createAnAccount() {
        fragment = new SignupFragment();
        fm = getFragmentManager();
        assert fm != null;
        @SuppressLint("CommitTransaction")
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.activity_launch, fragment, fragment.getClass().getName())
                .commit();
    }

    @Override
    public void onClick(View v) {

    }
}