package com.chicagoteamapp.chicagoteamapp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * 1 ver of DB: the table was added
 */

@Entity(tableName = "Steps", indices = @Index("id_task"), foreignKeys =
@ForeignKey(entity = MyTask.class,
        parentColumns = "id",
        childColumns = "id_task",
        onDelete = CASCADE))
public class MyStep {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long mId;

    @ColumnInfo(name = "id_task")
    private long mTaskId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "completed")
    private boolean mCompleted;

    public MyStep(@NonNull String title, long taskId) {
        mTitle = title;
        mTaskId = taskId;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getTaskId() {
        return mTaskId;
    }

    public void setTaskId(long taskId) {
        mTaskId = taskId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@NonNull String title) {
        mTitle = title;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }
}
