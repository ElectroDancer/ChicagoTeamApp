package com.chicagoteamapp.chicagoteamapp.taskslist.popup;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chicagoteamapp.chicagoteamapp.MyApp;
import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.model.MyList;
import com.chicagoteamapp.chicagoteamapp.model.room.TaskDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListsRecyclerViewAdapter extends
        RecyclerView.Adapter<ListsRecyclerViewAdapter.ViewHolder> {

    private List<MyList> myLists;
    private OnListInteractionListener mListener;

    public ListsRecyclerViewAdapter(List<MyList> lists, OnListInteractionListener listener) {
        myLists = lists;
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
        holder.bindData(myLists.get(position));
    }

    @Override
    public int getItemCount() {
        return myLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
    }

    interface OnListInteractionListener {

        void onListItemClick(MyList list);
    }
}
