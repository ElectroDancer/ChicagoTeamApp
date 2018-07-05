package com.chicagoteamapp.chicagoteamapp.taskslist.popup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chicagoteamapp.chicagoteamapp.MyApp;
import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.data.model.MyTask;
import com.chicagoteamapp.chicagoteamapp.data.room.TaskDao;
import com.chicagoteamapp.chicagoteamapp.util.ViewUtil;
import com.furianrt.bottompopupwindow.BottomPopupWindow;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewTaskFragment extends Fragment {

    private static final String BUNDLE_LIST_ID = "LIST_ID";

    @BindView(R.id.image_button_close)
    ImageButton mButtonClose;

    @BindView(R.id.edit_text_title)
    EditText mEditTextTitle;

    @BindView(R.id.edit_text_date)
    EditText mEditTextDate;

    @BindView(R.id.edit_text_description)
    EditText mEditTextDescription;

    private long mListId;

    public static NewTaskFragment newInstance(long listId) {
        Bundle args = new Bundle();
        args.putLong(BUNDLE_LIST_ID, listId);
        NewTaskFragment fragment = new NewTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mListId = args.getLong(BUNDLE_LIST_ID, 0);
        } else {
            mListId = 0;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_task, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.button_add_task)
    public void onButtonAddTaskClick() {
        TaskDao taskDao = MyApp.getInstance().getDatabase().taskDao();
        MyTask task = new MyTask(mEditTextTitle.getText().toString(), mListId);
        task.setDate(mEditTextDate.getText().toString());
        task.setDescription(mEditTextDescription.getText().toString());
        taskDao.insert(task);
    }

    @OnClick(R.id.image_button_close)
    public void onButtonCloseClick() {
        ViewUtil.hideKeyboard(getActivity());
        BottomPopupWindow popupWindow =
                Objects.requireNonNull(getActivity()).findViewById(R.id.bottom_popup_window);
        popupWindow.hide();
    }
}
