package com.chicagoteamapp.chicagoteamapp.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class TaskList implements Serializable {

    private String mName;
    private List<Task> mTasks;

    public TaskList(@NonNull String name, @NonNull List<Task> tasks) {
        mName = name;
        mTasks = tasks;
    }

    public String getName() {
        return mName;
    }

    public void setName(@NonNull String name) {
        mName = name;
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public void setTasks(@NonNull List<Task> tasks) {
        mTasks = tasks;
    }

    public int getTaskCount() {
        return mTasks.size();
    }

    public int getCompletedTasksCount() {
        int count = 0;
        for (Task task : mTasks) {
            if (task.isCompleted()) {
                count++;
            }
        }
        return count;
    }
}
