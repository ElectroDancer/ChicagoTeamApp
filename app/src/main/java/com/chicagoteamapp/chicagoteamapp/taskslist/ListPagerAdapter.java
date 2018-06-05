package com.chicagoteamapp.chicagoteamapp.taskslist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chicagoteamapp.chicagoteamapp.model.MyList;

import java.util.List;

class ListPagerAdapter extends FragmentStatePagerAdapter {

    private List<MyList> mLists;

    public ListPagerAdapter(FragmentManager fm, List<MyList> myLists) {
        super(fm);
        mLists = myLists;
    }

    @Override
    public Fragment getItem(int position) {
        return TasksFragment.newInstance(mLists.get(position));
    }

    @Override
    public int getCount() {
        return mLists.size();
    }
}
