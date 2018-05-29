package com.chicagoteamapp.chicagoteamapp.taskslist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.model.Step;
import com.chicagoteamapp.chicagoteamapp.model.Task;
import com.chicagoteamapp.chicagoteamapp.model.TaskList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TasksActivity extends AppCompatActivity {

    @BindView(R.id.tab_dots)
    TabLayout mTabDots;

    @BindView(R.id.pager_lists)
    ViewPager mLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        ButterKnife.bind(this);

        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Step one"));
        steps.add(new Step("Step two"));
        steps.add(new Step("Step three"));
        steps.add(new Step("Step four"));
        steps.add(new Step("Step five"));

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task one", steps));
        tasks.add(new Task("Task two", steps));
        tasks.add(new Task("Task three", steps));
        tasks.add(new Task("Task three", steps));
        tasks.add(new Task("Task three", steps));
        tasks.add(new Task("Task three", steps));
        tasks.add(new Task("Task three", steps));
        tasks.add(new Task("Task three", steps));
        tasks.add(new Task("Task three", steps));
        tasks.add(new Task("Task three", steps));
        tasks.add(new Task("Task three", steps));
        tasks.add(new Task("Task three", steps));
        tasks.add(new Task("Task three", steps));
        tasks.add(new Task("Task three", steps));

        List<TaskList> lists = new ArrayList<>();
        lists.add(new TaskList("List one", tasks));
        lists.add(new TaskList("List two", tasks));
        lists.add(new TaskList("List three", tasks));
        lists.add(new TaskList("List four", tasks));

        mLists.setAdapter(new ListPagerAdapter(getSupportFragmentManager(), lists));
        mTabDots.setupWithViewPager(mLists, true);
    }
}
