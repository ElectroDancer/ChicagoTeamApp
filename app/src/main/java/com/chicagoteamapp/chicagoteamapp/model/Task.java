package com.chicagoteamapp.chicagoteamapp.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task implements Serializable {

    private String mName;
    private Date mDate;
    private List<Step> mSteps;
    private boolean mCompleted;

    public Task() {
        mName = "";
        mDate = new Date();
        mSteps = new ArrayList<>();
        mCompleted = false;
    }

    public Task(@NonNull String name, @NonNull List<Step> steps) {
        this.mName = name;
        this.mSteps = steps;
        mDate = new Date();
        mCompleted = false;
    }

    public String getName() {
        return mName;
    }

    public void setName(@NonNull String name) {
        mName = name;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public List<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(@NonNull List<Step> steps) {
        mSteps = steps;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }

    public int getStepCount() {
        return mSteps.size();
    }

    public int getCompletedStepsCount() {
        int count = 0;
        for (Step step : mSteps) {
            if (step.isCompleted()) {
                count++;
            }
        }
        return count;
    }
}
