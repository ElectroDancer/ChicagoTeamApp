package com.chicagoteamapp.chicagoteamapp.taskslist.popup;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chicagoteamapp.chicagoteamapp.LaunchActivity;
import com.chicagoteamapp.chicagoteamapp.MyApp;
import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.util.ViewUtil;
import com.furianrt.bottompopupwindow.BottomPopupWindow;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private FirebaseUser mUser;

    @BindView(R.id.text_your_name)
    TextView mTextYourName;
    @BindView(R.id.text_your_email)
    TextView mTextYourEmail;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        FirebaseApp.initializeApp(getContext());
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        assert mUser != null;
        mTextYourName.setText(mUser.getDisplayName());
        mTextYourEmail.setText(mUser.getEmail());

        return view;
    }

    @OnClick(R.id.image_button_return_to_lists)
    void returnToLists() {
        BottomPopupWindow popupWindow =
                Objects.requireNonNull(getActivity()).findViewById(R.id.bottom_popup_window);
        popupWindow.setFragment(new ListsFragment());
        popupWindow.show();
        Log.d(TAG, "Return to lists is clicked");
    }

    @OnClick(R.id.image_button_exit_to_splash_login)
    void returnToSplashLogin() {
//        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), LaunchActivity.class);
        startActivity(intent);
        Log.d(TAG, "Return to splash login is clicked");
    }

    @OnClick(R.id.button_save)
    void saveChangesInProfile() {

    }

    @OnClick(R.id.text_do_you_want_to_delete_your_account)
    void deleteAccount() {
        String userId = mUser.getUid();
        MyApp.getInstance()
                .getDatabase()
                .userDao()
                .delete(MyApp.getInstance()
                        .getDatabase()
                        .userDao()
                        .getCurrentUser(userId));
    }

    @OnClick(R.id.image_button_close)
    void onButtonCloseClick() {
        ViewUtil.hideKeyboard(Objects.requireNonNull(getActivity()));
        BottomPopupWindow popupWindow =
                Objects.requireNonNull(getActivity()).findViewById(R.id.bottom_popup_window);
        popupWindow.hide();
    }
}
