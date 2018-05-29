package com.chicagoteamapp.chicagoteamapp.taskslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.model.Task;
import com.chicagoteamapp.chicagoteamapp.model.TaskList;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chicagoteamapp.chicagoteamapp.taskslist.TasksRecyclerViewAdapter.OnListInteractionListener;

public class TasksFragment extends Fragment {

    public static final String BUNDLE_TASK_LIST = "TASK_LIST";

    @BindView(R.id.list_tasks)
    RecyclerView mList;

    @BindView(R.id.text_list_title)
    TextView mListTitle;

    @BindView(R.id.text_list_progress)
    TextView mListProgress;

    private TaskList mTaskList;
    private OnListInteractionListener mListener = new OnListInteractionListener() {

        @Override
        public void onListItemClick(Task task) {

        }

        @Override
        public void onTaskCompletionChange(Task task) {
            List<Task> tasks = mTaskList.getTasks();
            tasks.get(tasks.indexOf(task)).setCompleted(task.isCompleted());
            String progress = parseListProgress();
            mListProgress.setText(progress);
        }
    };

    public static TasksFragment newInstance(TaskList taskList) {
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_TASK_LIST, taskList);
        TasksFragment fragment = new TasksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        ButterKnife.bind(this, view);

        TasksRecyclerViewAdapter adapter;

        Bundle args = getArguments();
        if (args != null) {
            mTaskList = (TaskList) args.getSerializable(BUNDLE_TASK_LIST);
            adapter = new TasksRecyclerViewAdapter(mTaskList.getTasks(), mListener);
        } else {
            adapter = new TasksRecyclerViewAdapter(Collections.emptyList(), mListener);
        }

        mListTitle.setText(mTaskList.getName());

        String progress = parseListProgress();
        mListProgress.setText(progress);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mList.getContext(),
                layoutManager.getOrientation());
        mList.addItemDecoration(dividerItemDecoration);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(adapter);

        return view;
    }

    private String parseListProgress() {
        return mTaskList.getCompletedTasksCount()
                + " "
                + getString(R.string.msg_of)
                + " "
                + mTaskList.getTaskCount();
    }
}
