package com.chicagoteamapp.chicagoteamapp.taskslist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chicagoteamapp.chicagoteamapp.model.TaskList;

import java.util.List;

class ListPagerAdapter extends FragmentStatePagerAdapter {

    private List<TaskList> mTaskLists;

    public ListPagerAdapter(FragmentManager fm, List<TaskList> taskLists) {
        super(fm);
        mTaskLists = taskLists;
    }

    @Override
    public Fragment getItem(int position) {
        return TasksFragment.newInstance(mTaskLists.get(position));
    }

    @Override
    public int getCount() {
        return mTaskLists.size();
    }
}
