package com.chicagoteamapp.chicagoteamapp.taskslist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.chicagoteamapp.chicagoteamapp.model.MyList;

import java.util.List;

class ListPagerAdapter extends FragmentStatePagerAdapter {

    private List<MyList> mLists;
    private SparseArray<TasksFragment> mRegisteredFragments;

    public ListPagerAdapter(FragmentManager fm, List<MyList> myLists) {
        super(fm);
        mLists = myLists;
        mRegisteredFragments = new SparseArray<>();
    }

    @Override
    public Fragment getItem(int position) {
        return TasksFragment.newInstance(mLists.get(position));
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TasksFragment fragment = (TasksFragment) super.instantiateItem(container, position);
        mRegisteredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mRegisteredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public TasksFragment getRegisteredFragment(int position) {
        return mRegisteredFragments.get(position);
    }

    public MyList getList(int position) {
        return mLists.get(position);
    }
}
