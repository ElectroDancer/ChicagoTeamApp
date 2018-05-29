package com.chicagoteamapp.chicagoteamapp.taskslist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.model.Task;
import com.chicagoteamapp.chicagoteamapp.util.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewAdapter.ViewHolder> {

    private final List<Task> mTasks;
    private final OnListInteractionListener mListener;

    public TasksRecyclerViewAdapter(List<Task> tasks, OnListInteractionListener listener) {
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
        CheckBox mCompleted;

        @BindView(R.id.text_task_title)
        TextView mTitle;

        @BindView(R.id.text_task_date)
        TextView mDate;

        @BindView(R.id.text_task_progress)
        TextView mTaskProgress;

        private View mView;
        private Task mTask;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, mView);
            mView.setOnClickListener(this);
        }

        public void bindData(Task task) {
            mTask = task;
            mTitle.setText(mTask.getName());
            mDate.setText(DateUtil.formatDate(mTask.getDate()));
            mCompleted.setChecked(mTask.isCompleted());
            mCompleted.setOnCheckedChangeListener((compoundButton, b) -> {
                mTask.setCompleted(mCompleted.isChecked());
                mListener.onTaskCompletionChange(mTask);
            });
            String of = mView.getContext().getString(R.string.msg_of);
            String progress = mTask.getCompletedStepsCount() + " " + of + " " + mTask.getStepCount();
            mTaskProgress.setText(progress);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onListItemClick(mTask);
            }
        }
    }

    interface OnListInteractionListener {

        void onListItemClick(Task task);

        void onTaskCompletionChange(Task task);
    }
}
