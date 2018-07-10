package com.chicagoteamapp.chicagoteamapp;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public abstract class BackableFragment extends android.support.v4.app.Fragment implements View.OnKeyListener {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(this);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                onBackButtonPressed();
                return true; } }
        return false; }

    public abstract void onBackButtonPressed();
}