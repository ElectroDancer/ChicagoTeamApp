package com.chicagoteamapp.chicagoteamapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chicagoteamapp.chicagoteamapp.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SplashLoginFragment extends Fragment implements View.OnClickListener {
    final String LOG_TAG = "SplashLoginFragment";
    public static final String TAG = "SplashLoginFragmentTag";
    private static final int RC_SIGN_IN = 430;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    private Fragment fragment;
    private android.support.v4.app.FragmentTransaction ft;
    @BindView(R.id.button_create_an_account_fragment_splash_login) Button mCreateAnAccount;
    @BindView(R.id.button_more_ways_to_login_fragment_splash_login) Button mMoreWaysToLogin;
    @BindView(R.id.button_fb) Button mFacebook;
    @BindView(R.id.button_login_with_email_fragment_splash_login) Button mEmail;
    @BindView(R.id.button_facebook_login) LoginButton mLoginFacebook;

    CallbackManager mCallbackManager;
    public String id, name, email, gender, birthday;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(getContext());
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
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
        currentUser = mAuth.getCurrentUser();

        initializeFacebook();
        Log.d(LOG_TAG, "onCreateView");
        return view;
    }

    private void initializeFacebook() {
        mCallbackManager = CallbackManager.Factory.create();
        mLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {@Override
        public void onSuccess(LoginResult loginResult) {
            System.out.println("onSuccess");
            String accessToken = loginResult.getAccessToken()
                    .getToken();
            Log.i("accessToken", accessToken);
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    (object, response) -> {
                        Log.i("LoginActivity",
                                response.toString());
                        try {
                            id = object.getString("id");
                            try {
                                URL profile_pic = new URL(
                                        "http://graph.facebook.com/" + id + "/picture?type=large");
                                Log.i("profile_pic",
                                        profile_pic + "");

                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            Log.e("UserDate", String.valueOf(object));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields","id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }
            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }
            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
                Log.v("LoginActivity", exception.getCause().toString());
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
        Log.d(LOG_TAG, "loginFacebook");
    }

    @Override
    public void onClick(View v) {
    }
}