package com.chicagoteamapp.chicagoteamapp.taskslist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.chicagoteamapp.chicagoteamapp.MyApp;
import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.data.model.MyUser;
import com.chicagoteamapp.chicagoteamapp.data.room.TaskDatabase;
import com.chicagoteamapp.chicagoteamapp.taskslist.popup.ListsFragment;
import com.chicagoteamapp.chicagoteamapp.taskslist.popup.NewTaskFragment;
import com.furianrt.bottompopupwindow.BottomPopupWindow;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TasksActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    public String userId;
    public String userName;

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
        FirebaseApp.initializeApp(this);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
//        userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
//        userName = Objects.requireNonNull(mAuth.getCurrentUser()).getDisplayName();

        mAdapter = new ListPagerAdapter(getSupportFragmentManager());
        mPagerLists.setAdapter(mAdapter);
        mTabDots.setupWithViewPager(mPagerLists, true);
        TaskDatabase db = MyApp.getInstance().getDatabase();

        if (db.userDao().getCurrentUser(userId) != null) {
            MyApp.getInstance()
                    .getDatabase()
                    .listDao()
                    .getAllLists(userId)
                    .observe(this, lists -> {
                        mAdapter.setData(lists);
                        if (lists == null || lists.isEmpty()) {
                            mButtonAddTask.setVisibility(View.INVISIBLE);
                        } else {
                            mButtonAddTask.setVisibility(View.VISIBLE);
                        }
                    });
        } else {
            MyApp.getInstance()
                    .getDatabase()
                    .userDao()
                    .insert(new MyUser(userId, userName));

            MyApp.getInstance()
                    .getDatabase()
                    .listDao()
                    .getAllLists(userId)
                    .observe(this, lists -> {
                        mAdapter.setData(lists);
                        if (lists == null || lists.isEmpty()) {
                            mButtonAddTask.setVisibility(View.INVISIBLE);
                        } else {
                            mButtonAddTask.setVisibility(View.VISIBLE);
                        }
                    });
        }

//        MyApp.getInstance()
//                .getDatabase()
//                .listDao()
//                .getAllLists()
//                .observe(this, lists -> {
//                    mAdapter.setData(lists);
//                    if (lists == null || lists.isEmpty()) {
//                        mButtonAddTask.setVisibility(View.INVISIBLE);
//                    } else {
//                        mButtonAddTask.setVisibility(View.VISIBLE);
//                    }
//                });
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
