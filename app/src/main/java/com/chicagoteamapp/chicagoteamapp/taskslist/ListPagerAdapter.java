package com.chicagoteamapp.chicagoteamapp.taskslist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chicagoteamapp.chicagoteamapp.model.MyList;

import java.util.ArrayList;
import java.util.List;

class ListPagerAdapter extends FragmentStatePagerAdapter {

    private List<MyList> mLists;

    public ListPagerAdapter(FragmentManager fm) {
        super(fm);
        mLists = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return TasksFragment.newInstance(mLists.get(position));
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    public MyList getList(int position) {
        return mLists.get(position);
    }

    public void setData(List<MyList> lists) {
        mLists = lists == null ? new ArrayList<>() : lists;
        notifyDataSetChanged();
    }
}
