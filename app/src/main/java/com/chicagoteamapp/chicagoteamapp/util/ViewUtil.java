package com.chicagoteamapp.chicagoteamapp.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ViewUtil {

    public static void slideUp(View view){
        view.setTranslationY(view.getHeight());
        view.animate()
                .translationYBy(view.getHeight())
                .translationY(0)
                .setDuration(300)
                .start();
    }

    public static void slideDown(View view){
        view.animate()
                .translationYBy(0)
                .translationY(view.getHeight())
                .setDuration(300)
                .start();
    }

    public static void increaseAlpha(View view){
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

    public static void decreaseAlpha(View view){
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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = activity.getCurrentFocus();
        if (v != null) inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
