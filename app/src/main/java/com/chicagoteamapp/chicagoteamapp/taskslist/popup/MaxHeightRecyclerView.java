package com.chicagoteamapp.chicagoteamapp.taskslist.popup;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.chicagoteamapp.chicagoteamapp.util.ViewUtil;

public class MaxHeightRecyclerView extends RecyclerView {

    public MaxHeightRecyclerView(Context context) {
        super(context);
    }

    public MaxHeightRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MaxHeightRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        heightSpec = MeasureSpec.makeMeasureSpec(
                ViewUtil.dpToPx(getContext(), 240), MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, heightSpec);
    }
}
