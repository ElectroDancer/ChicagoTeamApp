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
import com.chicagoteamapp.chicagoteamapp.model.MyList;
import com.chicagoteamapp.chicagoteamapp.model.room.ListDao;
import com.chicagoteamapp.chicagoteamapp.taskslist.popup.ListsFragment;
import com.chicagoteamapp.chicagoteamapp.taskslist.popup.NewTaskFragment;
import com.chicagoteamapp.chicagoteamapp.taskslist.popup.OnDataChangeListener;
import com.chicagoteamapp.chicagoteamapp.util.ViewUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TasksActivity extends AppCompatActivity implements OnDataChangeListener {

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

        mLayoutDimming.setOnClickListener(view -> {
            ViewUtil.slideDown(mLayoutPopup);
            ViewUtil.decreaseAlpha(mLayoutDimming);
        });

        onListChanged();
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
    public void onTaskChanged() {
        TasksFragment fragment = mAdapter.getRegisteredFragment(mPagerLists.getCurrentItem());
        fragment.invalidateList();
    }

    @Override
    public void onListChanged() {
        ListDao listDao = MyApp.getInstance().getDatabase().listDao();
        List<MyList> lists = listDao.getAllLists();
        mAdapter = new ListPagerAdapter(getSupportFragmentManager(), lists);
        mPagerLists.setAdapter(mAdapter);
        mTabDots.setupWithViewPager(mPagerLists, true);
        if (lists.isEmpty()) {
            mButtonAddTask.setVisibility(View.INVISIBLE);
        } else {
            mButtonAddTask.setVisibility(View.VISIBLE);
        }
    }
}
