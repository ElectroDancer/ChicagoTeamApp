package com.chicagoteamapp.chicagoteamapp.taskslist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.chicagoteamapp.chicagoteamapp.MyApp;
import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.taskslist.popup.ListsFragment;
import com.chicagoteamapp.chicagoteamapp.taskslist.popup.NewTaskFragment;
import com.furianrt.bottompopupwindow.BottomPopupWindow;

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

    @BindView(R.id.bottom_popup_window)
    BottomPopupWindow mPopupWindow;

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
        mPopupWindow.setFragment(fragment);
        mPopupWindow.show();
    }

    @OnClick(R.id.image_button_show_lists)
    public void onButtonShowListsClick() {
        ListsFragment fragment = new ListsFragment();
        mPopupWindow.setFragment(fragment);
        mPopupWindow.show();
    }

    @Override
    public void onBackPressed() {
        if (mPopupWindow.isShown()) {
            mPopupWindow.hide();
        } else {
            super.onBackPressed();
        }
    }
}
