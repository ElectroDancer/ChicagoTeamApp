package com.chicagoteamapp.chicagoteamapp.taskslist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chicagoteamapp.chicagoteamapp.MyApp;
import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.model.MyTask;
import com.chicagoteamapp.chicagoteamapp.model.room.StepDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewAdapter.ViewHolder> {

    private List<MyTask> mTasks;
    private OnListInteractionListener mListener;

    public TasksRecyclerViewAdapter(List<MyTask> tasks, OnListInteractionListener listener) {
        mTasks = tasks;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tasks_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bindData(mTasks.get(position));
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.check_task_complete)
        CheckBox mCheckBoxCompleted;

        @BindView(R.id.text_task_title)
        TextView mTextViewTitle;

        @BindView(R.id.text_task_date)
        TextView mTextViewDate;

        @BindView(R.id.text_task_progress)
        TextView mTextViewProgress;

        private View mView;
        private MyTask mTask;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, mView);
            mView = view;
            mView.setOnClickListener(this);
        }

        public void bindData(MyTask task) {
            mTask = task;
            mTextViewTitle.setText(mTask.getTitle());
            mTextViewDate.setText(mTask.getDate());
            mCheckBoxCompleted.setChecked(mTask.isCompleted());
            mCheckBoxCompleted.setOnCheckedChangeListener((compoundButton, b) -> {
                //mTask.setCompleted(mCompleted.isChecked());
                //mListener.onTaskCompletionChange(mTask);
            });
            mTextViewProgress.setText(parseTaskProgress(mTask));
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onListItemClick(mTask);
            }
        }

        private String parseTaskProgress(MyTask task) {
            StepDao stepDao = MyApp.getInstance().getDatabase().stepDao();
            String of = mView.getContext().getString(R.string.msg_of);
            int completedStepsCount = stepDao.getCompletedStepsCount(task.getId());
            int stepsCount = stepDao.geStepsCount(task.getId());
            return completedStepsCount + " " + of + " " + stepsCount;
        }
    }

    interface OnListInteractionListener {

        void onListItemClick(MyTask task);

        void onTaskCompletionChange(MyTask task);
    }
}
