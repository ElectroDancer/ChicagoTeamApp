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

import com.chicagoteamapp.chicagoteamapp.MyApp;
import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.model.MyList;
import com.chicagoteamapp.chicagoteamapp.model.MyTask;
import com.chicagoteamapp.chicagoteamapp.model.room.TaskDao;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chicagoteamapp.chicagoteamapp.taskslist.TasksRecyclerViewAdapter.OnListInteractionListener;

public class TasksFragment extends Fragment {

    public static final String BUNDLE_LIST = "LIST";

    @BindView(R.id.list_tasks)
    RecyclerView mRecyclerViewTasks;

    @BindView(R.id.text_list_title)
    TextView mTextViewTitle;

    @BindView(R.id.text_list_progress)
    TextView mTextViewProgress;

    private List<MyTask> mTasks;
    private MyList mList;
    private OnListInteractionListener mListener = new OnListInteractionListener() {

        @Override
        public void onListItemClick(MyTask task) {

        }

        @Override
        public void onTaskCompletionChange(MyTask task) {
            //mTasks.get(mTasks.indexOf(task)).setCompleted(task.isCompleted());
            //String progress = parseListProgress();
            //mListProgress.setText(progress);
        }
    };

    public static TasksFragment newInstance(MyList list) {
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_LIST, list);
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
            mList = (MyList) args.getSerializable(BUNDLE_LIST);
            if (mList != null)  mTextViewTitle.setText(mList.getName());
            mTasks = MyApp.getInstance().getDatabase().taskDao().getTasks(mList.getId());
            adapter = new TasksRecyclerViewAdapter(mTasks, mListener);
        } else {
            adapter = new TasksRecyclerViewAdapter(Collections.emptyList(), mListener);
        }



        String progress = parseListProgress(mList);
        mTextViewProgress.setText(progress);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mRecyclerViewTasks.getContext(),
                layoutManager.getOrientation());
        mRecyclerViewTasks.addItemDecoration(dividerItemDecoration);
        mRecyclerViewTasks.setLayoutManager(layoutManager);
        mRecyclerViewTasks.setAdapter(adapter);

        return view;
    }

    private String parseListProgress(MyList list) {
        TaskDao taskDao = MyApp.getInstance().getDatabase().taskDao();
        int count = taskDao.geTasksCount(list.getId());
        int tasksCompleted = taskDao.getCompletedTasksCount(list.getId());
        return tasksCompleted + " " + getString(R.string.msg_of) + " " + count;
    }
}
