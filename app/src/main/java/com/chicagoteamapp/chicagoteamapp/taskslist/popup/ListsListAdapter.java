package com.chicagoteamapp.chicagoteamapp.taskslist.popup;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chicagoteamapp.chicagoteamapp.MyApp;
import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.data.model.MyList;
import com.chicagoteamapp.chicagoteamapp.data.room.TaskDao;

import butterknife.BindView;
import butterknife.ButterKnife;

class ListsListAdapter extends
        ListAdapter<MyList, ListsListAdapter.ViewHolder> {

    private OnListInteractionListener mListener;

    public ListsListAdapter(OnListInteractionListener listener) {
        super(new DiffUtil.ItemCallback<MyList>() {
            @Override
            public boolean areItemsTheSame(MyList oldItem, MyList newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(MyList oldItem, MyList newItem) {
                return oldItem.equals(newItem);
            }
        });
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_lists_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        @BindView(R.id.text_list_title)
        TextView mTextViewTitle;

        @BindView(R.id.text_list_progress)
        TextView mTextViewProgress;

        private View mView;
        private MyList mList;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
            mView.setOnClickListener(this);
            mView.setOnLongClickListener(this);
        }

        public void bindData(MyList list) {
            mList = list;
            mTextViewTitle.setText(mList.getTitle());
            mTextViewProgress.setText(parseListProgress(mList));
        }

        @Override
        public void onClick(View view) {
            mListener.onListItemClick(mList);
        }

        private String parseListProgress(MyList list) {
            TaskDao taskDao = MyApp.getInstance().getDatabase().taskDao();
            String of = mView.getContext().getString(R.string.msg_of);
            int count = taskDao.geTasksCount(list.getId());
            int tasksCompleted = taskDao.getCompletedTasksCount(list.getId());
            return tasksCompleted + " " + of + " " + count;
        }

        @Override
        public boolean onLongClick(View view) {
            mListener.onListItemLongClick(mList);
            return true;
        }
    }

    interface OnListInteractionListener {

        void onListItemClick(MyList list);

        void onListItemLongClick(MyList list);
    }
}
