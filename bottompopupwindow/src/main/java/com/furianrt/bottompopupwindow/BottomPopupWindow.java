package com.furianrt.bottompopupwindow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class BottomPopupWindow extends FrameLayout {

    private FrameLayout mLayoutPopup;
    private FrameLayout mLayoutDimming;

    private boolean mShown = false;
    private FragmentManager mFragmentManager;

    public BottomPopupWindow(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public BottomPopupWindow(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BottomPopupWindow(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public BottomPopupWindow(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        View view = inflate(context, R.layout.bottom_popup_window, this);
        mLayoutPopup = view.findViewById(R.id.frame_layout_popup);
        mLayoutDimming = view.findViewById(R.id.frame_layout_dimming);
        mFragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
    }

    public void setFragment(Fragment fragment) {
        mFragmentManager.beginTransaction()
                .replace(R.id.frame_layout_popup, fragment, fragment.getTag())
                .commit();
    }

    public boolean isShown() {
        return mShown;
    }

    public void show() {
        mShown = true;
        slideUp(mLayoutPopup);
        increaseAlpha(mLayoutDimming);
    }

    public void hide() {
        mShown = false;
        slideDown(mLayoutPopup);
        decreaseAlpha(mLayoutDimming);
    }

    private void slideUp(final View view){
        view.setTranslationY(view.getHeight());
        view.animate()
                .translationYBy(view.getHeight())
                .translationY(0)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        view.setVisibility(View.VISIBLE);
                    }
                })
                .start();
    }

    private void slideDown(final View view){
        view.animate()
                .translationYBy(0)
                .translationY(view.getHeight())
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.INVISIBLE);
                    }
                })
                .start();
    }

    private void increaseAlpha(final View view){
        view.animate()
                .alphaBy(0)
                .alpha(0.6f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        view.setVisibility(View.VISIBLE);
                    }
                })
                .start();
    }

    private void decreaseAlpha(final View view){
        view.animate()
                .alphaBy(0.6f)
                .alpha(0)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.INVISIBLE);
                    }
                })
                .start();
    }
}
