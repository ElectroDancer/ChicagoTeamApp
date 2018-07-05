package com.chicagoteamapp.chicagoteamapp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.chicagoteamapp.chicagoteamapp.util.DateUtil;

import java.util.Date;
import java.util.Objects;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "Tasks", indices = @Index("id_list"), foreignKeys =
@ForeignKey(entity = MyList.class,
        parentColumns = "id",
        childColumns = "id_list",
        onDelete = CASCADE))
public class MyTask {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long mId;

    @ColumnInfo(name = "id_list")
    private long mListId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "completed")
    private boolean mCompleted;

    public MyTask(@NonNull String title, long listId) {
        mTitle = title;
        mDate = DateUtil.formatDate(new Date());
        mListId = listId;
        mCompleted = false;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getListId() {
        return mListId;
    }

    public void setListId(long listId) {
        mListId = listId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@NonNull String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyTask task = (MyTask) o;
        return mId == task.mId &&
                mListId == task.mListId &&
                mCompleted == task.mCompleted &&
                Objects.equals(mTitle, task.mTitle) &&
                Objects.equals(mDescription, task.mDescription) &&
                Objects.equals(mDate, task.mDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mId, mListId, mTitle, mDescription, mDate, mCompleted);
    }
}
