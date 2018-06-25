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
import android.widget.Toast;

import com.chicagoteamapp.chicagoteamapp.LaunchActivity;
import com.chicagoteamapp.chicagoteamapp.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginOptionsFragment extends Fragment implements View.OnClickListener {
    private static final String LOG_TAG = "LoginOptionsFragment";
    public static final String TAG = "LoginOptionsFragmentTag";

    private Fragment fragment;
    private FragmentTransaction ft;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TwitterAuthClient client;

    @BindView(R.id.button_return) ImageButton mImageButtonReturnToLaunchScreen;
    @BindView(R.id.button_login_with_email_fragment_login_options) Button mEmail;
    @BindView(R.id.button_fb) Button mFacebook;
    @BindView(R.id.button_facebook_login) LoginButton mLoginFacebook;
    @BindView(R.id.button_twitter_fragment_login_options) Button mTwitter;
    @BindView(R.id.twitterLogin) TwitterLoginButton mTwitterLoginButton;
    @BindView(R.id.button_create_an_account_fragment_login_options) Button mButtonCreateAnAccount;

    public String id, name, email;
    CallbackManager mCallbackManager;
    Profile profile;

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

        initializeTwitter();
        initializeFacebook();

        Log.d(LOG_TAG, "onCreateView");
        return view;
    }

//    private TwitterSession getTwitterSession() {
//        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
//        TwitterAuthToken authToken = session.getAuthToken();
//        String token = authToken.token;
//        String secret = authToken.secret;
//        return session;
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the Twitter login button.
        mTwitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void initializeTwitter() {
    client = new TwitterAuthClient();
    mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

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
                    handleTwitterSession(result.data);
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
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getActivity(), LaunchActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
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

    @OnClick(R.id.button_return)
    void returnToLaunchScreen() {
        fragment = new SplashLoginFragment();
        FragmentManager fm = getFragmentManager();
        assert fm != null;
        if(fm.getBackStackEntryCount() > 0)
            fm.popBackStack();
        Log.d(LOG_TAG, "Return To Launch Screen is clicked");
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
        Log.d(LOG_TAG, "loginFacebook");
    }

//    @OnClick(R.id.button_twitter_fragment_login_options)
//    void loginTwitter() {
//        mTwitterLoginButton.performClick();
//        Log.d(LOG_TAG, "loginTwitter");
//    }

    @OnClick(R.id.button_create_an_account_fragment_login_options)
    void callCreateAnAccountScreen() {
        fragment = new SignupFragment();
        assert getFragmentManager() != null;
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_container, fragment)
                .addToBackStack("SignupFragment")
                .commit();
        Log.d(LOG_TAG, "Create an account");
    }

    @Override
    public void onClick(View v) {

    }

}
