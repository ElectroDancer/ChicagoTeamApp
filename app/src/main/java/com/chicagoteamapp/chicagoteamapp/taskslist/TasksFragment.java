package com.chicagoteamapp.chicagoteamapp.taskslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    private  TasksRecyclerViewAdapter mAdapter;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mList = (MyList) args.getSerializable(BUNDLE_LIST);
            mTasks = MyApp.getInstance().getDatabase().taskDao().getTasks(mList.getId());
        } else {
            mTasks = Collections.emptyList();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        ButterKnife.bind(this, view);

        if (mList != null)  mTextViewTitle.setText(mList.getTitle());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mRecyclerViewTasks.getContext(),
                layoutManager.getOrientation());
        mRecyclerViewTasks.addItemDecoration(dividerItemDecoration);
        mRecyclerViewTasks.setLayoutManager(layoutManager);
        invalidateList();

        return view;
    }

    public void invalidateList() {
        String progress = parseListProgress(mList);
        mTextViewProgress.setText(progress);
        mTasks = MyApp.getInstance().getDatabase().taskDao().getTasks(mList.getId());
        mAdapter = new TasksRecyclerViewAdapter(mTasks, mListener);
        mRecyclerViewTasks.setAdapter(mAdapter);
    }

    private String parseListProgress(MyList list) {
        TaskDao taskDao = MyApp.getInstance().getDatabase().taskDao();
        int count = taskDao.geTasksCount(list.getId());
        int tasksCompleted = taskDao.getCompletedTasksCount(list.getId());
        return tasksCompleted + " " + getString(R.string.msg_of) + " " + count;
    }
}
