package com.chicagoteamapp.chicagoteamapp.taskslist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.chicagoteamapp.chicagoteamapp.MyApp;
import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.taskslist.popup.ListsFragment;
import com.chicagoteamapp.chicagoteamapp.taskslist.popup.NewTaskFragment;
import com.chicagoteamapp.chicagoteamapp.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TasksActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout_dots)
    TabLayout mTabDots;

    @BindView(R.id.pager_lists)
    ViewPager mPagerLists;

    @BindView(R.id.button_add_task)
    Button mButtonAddTask;

    @BindView(R.id.frame_layout_popup)
    FrameLayout mLayoutPopup;

    @BindView(R.id.frame_layout_dimming)
    FrameLayout mLayoutDimming;

    private ListPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        ButterKnife.bind(this);

        mAdapter = new ListPagerAdapter(getSupportFragmentManager());
        mPagerLists.setAdapter(mAdapter);
        mTabDots.setupWithViewPager(mPagerLists, true);

        MyApp.getInstance()
                .getDatabase()
                .listDao()
                .getAllLists()
                .observe(this, lists -> {
                    mAdapter.setData(lists);
                    if (lists == null || lists.isEmpty()) {
                        mButtonAddTask.setVisibility(View.INVISIBLE);
                    } else {
                        mButtonAddTask.setVisibility(View.VISIBLE);
                    }
                });
    }

    @OnClick(R.id.button_add_task)
    public void onButtonAddTaskClick() {
        NewTaskFragment fragment =
                NewTaskFragment.newInstance(mAdapter.getList(mPagerLists.getCurrentItem()).getId());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_popup, fragment, fragment.getTag())
                .commit();

        ViewUtil.slideUp(mLayoutPopup);
        mLayoutDimming.setVisibility(View.VISIBLE);
        ViewUtil.increaseAlpha(mLayoutDimming);
    }

    @OnClick(R.id.image_button_show_lists)
    public void onButtonShowListsClick() {
        ListsFragment fragment = new ListsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_popup, fragment, fragment.getTag())
                .commit();

        ViewUtil.slideUp(mLayoutPopup);
        mLayoutDimming.setVisibility(View.VISIBLE);
        ViewUtil.increaseAlpha(mLayoutDimming);
    }

    @Override
    public void onBackPressed() {
        if (mLayoutPopup.getY() != 0.0f) {
            ViewUtil.hideKeyboard(this);
            ViewUtil.slideDown(mLayoutPopup);
            ViewUtil.decreaseAlpha(mLayoutDimming);
        } else {
            super.onBackPressed();
        }
    }
}
