package com.chicagoteamapp.chicagoteamapp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "Images", indices = @Index("id_task"), foreignKeys =
@ForeignKey(entity = MyTask.class,
        parentColumns = "id",
        childColumns = "id_task",
        onDelete = CASCADE))
public class MyImage {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long mId;

    @ColumnInfo(name = "id_task")
    private long mTaskId;

    @ColumnInfo(name = "reference")
    private String mReference;

    public MyImage(long taskId, String reference) {
        mTaskId = taskId;
        mReference = reference;
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

    public String getReference() {
        return mReference;
    }

    public void setReference(String reference) {
        mReference = reference;
    }
}
