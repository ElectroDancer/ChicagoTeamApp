package com.chicagoteamapp.chicagoteamapp.taskslist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.chicagoteamapp.chicagoteamapp.MyApp;
import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.model.MyList;
import com.chicagoteamapp.chicagoteamapp.model.MyStep;
import com.chicagoteamapp.chicagoteamapp.model.MyTask;
import com.chicagoteamapp.chicagoteamapp.model.room.ListDao;
import com.chicagoteamapp.chicagoteamapp.model.room.StepDao;
import com.chicagoteamapp.chicagoteamapp.model.room.TaskDao;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TasksActivity extends AppCompatActivity {

    @BindView(R.id.tab_dots)
    TabLayout mTabDots;

    @BindView(R.id.pager_lists)
    ViewPager mPagerLists;

    @BindView(R.id.button_add_task)
    Button mButtonAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        ButterKnife.bind(this);

        mButtonAddTask.setOnClickListener(view -> {
            StepDao stepDao = MyApp.getInstance().getDatabase().stepDao();
            TaskDao taskDao = MyApp.getInstance().getDatabase().taskDao();
            ListDao listDao = MyApp.getInstance().getDatabase().listDao();

            MyList myList = new MyList("List one");
            myList.setId(listDao.insert(myList));

            MyTask task = new MyTask("MyTask one", myList.getId());
            task.setId(taskDao.insert(task));

            MyStep myStep = new MyStep("MyStep one", task.getId());
            myStep.setId(stepDao.insert(myStep));

        });

        ListDao listDao = MyApp.getInstance().getDatabase().listDao();

        ListPagerAdapter adapter =
                new ListPagerAdapter(getSupportFragmentManager(), listDao.getAllLists());
        mPagerLists.setAdapter(adapter);
        mTabDots.setupWithViewPager(mPagerLists, true);
    }
}
