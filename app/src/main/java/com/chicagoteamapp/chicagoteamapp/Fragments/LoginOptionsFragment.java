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

import com.chicagoteamapp.chicagoteamapp.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class LoginOptionsFragment extends Fragment implements View.OnClickListener {
    private static final String LOG_TAG = "LoginOptionsFragment";
    public static final String TAG = "LoginOptionsFragmentTag";

    private GoogleApiClient mGoogleApiClient;
    private Fragment fragment;
    private FragmentTransaction ft;
    private FragmentManager fm;
    private FirebaseAuth mAuth;

    private TwitterLoginButton mLoginButtonTwitter;

    @BindView(R.id.button_return) ImageButton mImageButtonReturnToLaunchScreen;
    @BindView(R.id.button_login_with_email_fragment_login_options) Button mEmail;
    @BindView(R.id.button_fb) Button mFacebook;
    @BindView(R.id.button_facebook_login) LoginButton mLoginFacebook;
    @BindView(R.id.button_twitter_fragment_login_options) Button mTwitter;
    @BindView(R.id.button_create_an_account_fragment_login_options) Button mButtonCreateAnAccount;

    private static final int RC_SIGN_IN = 430;
    private final static String G_PLUS_SCOPE =
            "oauth2:https://www.googleapis.com/auth/plus.me";
    private final static String USERINFO_SCOPE =
            "https://www.googleapis.com/auth/userinfo.profile";
    private final static String EMAIL_SCOPE =
            "https://www.googleapis.com/auth/userinfo.email";
    private final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE + " " + EMAIL_SCOPE;
    public String id, name, email, gender, birthday;
    CallbackManager mCallbackManager;
    Profile profile;

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

//        initializeTwitter();
        initializeFacebook();

        Log.d(LOG_TAG, "onCreateView");
        return view;
    }

    private void initializeTwitter() {
        // Configure Twitter SDK
        TwitterAuthConfig authConfig =  new TwitterAuthConfig(
                getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret));

        TwitterConfig twitterConfig = new TwitterConfig.Builder(Objects.requireNonNull(getContext()))
                .twitterAuthConfig(authConfig)
                .build();
        Twitter.initialize(twitterConfig);

        mLoginButtonTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                Log.i("Session Username", String.valueOf(result.data.getUserId()));

                login(result);

            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
            }
        });
    }
    //The login function accepting the result object
    public void login(final Result<TwitterSession> result) {

        //Creating a twitter session with result's data
        TwitterSession session = result.data;

        //This code will fetch the profile image URL
        //Getting the account service of the user logged in
        TwitterCore.getInstance().getApiClient(session).getAccountService().verifyCredentials(true, false, true).enqueue(new retrofit2.Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    //If it succeeds creating a User object from userResult.data
                    User user = response.body();

                    //Getting the profile image url
                    String profileImage = user.profileImageUrl.replace("_normal", "");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the Twitter login button.
        mLoginButtonTwitter.onActivityResult(requestCode, resultCode, data);
    }

//    private void handleTwitterSession(TwitterSession session) {
//        Log.d(TAG, "handleTwitterSession:" + session);
//
//        AuthCredential credential = TwitterAuthProvider.getCredential(
//                session.getAuthToken().token,
//                session.getAuthToken().secret);
//
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
//                    if (task.isSuccessful()) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "signInWithCredential:success");
//                        FirebaseUser user = mAuth.getCurrentUser();
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "signInWithCredential:failure", task.getException());
//                        Toast.makeText(getContext(), "Authentication failed.",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

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
        Fragment f = fm.findFragmentById(R.id.main_container);
        if(fm.getBackStackEntryCount() > 0)
            fm.popBackStack();
        Log.d(LOG_TAG, "Return To Launch Screen is clicked");
    }

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

    @Override
    public void onClick(View v) {

    }

}
