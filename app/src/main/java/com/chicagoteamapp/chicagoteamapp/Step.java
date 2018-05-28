package com.chicagoteamapp.chicagoteamapp;

import android.support.annotation.NonNull;

public class Step {

    private String mName;
    private boolean mCompleted;

    public Step(@NonNull String name) {
        mName = name;
    }

    public Step(@NonNull String name, boolean completed) {
        mName = name;
        mCompleted = completed;
    }

    public String getName() {
        return mName;
    }

    public void setName(@NonNull String name) {
        mName = name;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }
}
