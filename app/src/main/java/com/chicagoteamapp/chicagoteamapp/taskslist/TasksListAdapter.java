package com.chicagoteamapp.chicagoteamapp.taskslist;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.data.model.MyTask;

import butterknife.BindView;
import butterknife.ButterKnife;

class TasksListAdapter extends ListAdapter<MyTask, TasksListAdapter.ViewHolder> {

    private OnListInteractionListener mListener;

    TasksListAdapter(OnListInteractionListener listener) {
        super(new DiffUtil.ItemCallback<MyTask>() {
            @Override
            public boolean areItemsTheSame(MyTask oldItem, MyTask newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(MyTask oldItem, MyTask newItem) {
                return oldItem.equals(newItem);
            }
        });
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

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
            ButterKnife.bind(this, view);
            mView = view;
            mView.setOnClickListener(this);
            mView.setOnLongClickListener(this);
        }

        public void bindData(MyTask task) {
            mTask = task;
            mTextViewTitle.setText(mTask.getTitle());
            mTextViewDate.setText(mTask.getDate());
            mTextViewProgress.setText(parseTaskProgress(mTask));
            mCheckBoxCompleted.setChecked(mTask.isCompleted());
            mCheckBoxCompleted.setOnCheckedChangeListener((compoundButton, b) -> {
                mTask.setCompleted(mCheckBoxCompleted.isChecked());
//                MyApp.getInstance()
//                        .getDatabase()
//                        .taskDao()
//                        .update(mTask);
            });
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onListItemClick(mTask);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            mListener.onListItemLongClick(mTask);
            return true;
        }

        private String parseTaskProgress(MyTask task) {
//            StepDao stepDao = MyApp.getInstance().getDatabase().stepDao();
//            String of = mView.getContext().getString(R.string.msg_of);
//            int completedStepsCount = stepDao.getCompletedStepsCount(task.getId());
//            int stepsCount = stepDao.geStepsCount(task.getId());
//            return completedStepsCount + " " + of + " " + stepsCount;
            return null;
        }
    }

    interface OnListInteractionListener {

        void onListItemClick(MyTask task);

        void onListItemLongClick(MyTask task);
    }
}
