package com.chicagoteamapp.chicagoteamapp.taskslist.popup;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.chicagoteamapp.chicagoteamapp.MyApp;
import com.chicagoteamapp.chicagoteamapp.R;
import com.chicagoteamapp.chicagoteamapp.model.MyList;
import com.chicagoteamapp.chicagoteamapp.model.room.ListDao;
import com.chicagoteamapp.chicagoteamapp.util.ViewUtil;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListsFragment extends Fragment {

    @BindView(R.id.button_add_list)
    Button mButtonAddList;

    @BindView(R.id.list_lists)
    RecyclerView mRecyclerView;

    private ListsRecyclerViewAdapter mAdapter;
    private OnDataChangeListener mListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lists, container, false);

        ButterKnife.bind(this, view);

        ListDao listDao = MyApp.getInstance().getDatabase().listDao();
        List<MyList> lists = listDao.getAllLists();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ListsRecyclerViewAdapter(lists, list -> {

        });
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDataChangeListener) {
            mListener = (OnDataChangeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDataChangeListener");
        }
    }

    @OnClick(R.id.button_add_list)
    public void onButtonAddListClick() {

    }

    @OnClick(R.id.button_profile)
    public void onButtonProfileClick() {

    }

    @OnClick(R.id.image_button_close)
    public void onButtonCloseClick() {
        ViewUtil.slideDown(Objects.requireNonNull(getView()));
        FrameLayout layout =
                Objects.requireNonNull(getActivity()).findViewById(R.id.frame_layout_dimming);
        ViewUtil.decreaseAlpha(layout);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
