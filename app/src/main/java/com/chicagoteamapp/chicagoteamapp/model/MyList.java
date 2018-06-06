package com.chicagoteamapp.chicagoteamapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "Lists")
public class MyList implements Serializable {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long mId;

    @ColumnInfo(name = "title")
    private String mTitle;

    public MyList(@NonNull String title) {
        mTitle = title;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@NonNull String title) {
        mTitle = title;
    }
}
