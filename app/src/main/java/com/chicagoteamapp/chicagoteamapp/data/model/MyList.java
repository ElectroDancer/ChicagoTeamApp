package com.chicagoteamapp.chicagoteamapp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * 1 ver of DB: the table was added.
 * 2 ver of DB: there was added id_user column
 */

@Entity(tableName = "Lists", indices = @Index("id_user"), foreignKeys =
@ForeignKey(entity = MyUser.class,
        parentColumns = "id",
        childColumns = "id_user",
        onDelete = CASCADE))
public class MyList implements Serializable {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long mId;

    @ColumnInfo(name = "id_user")
    private String mUserId;

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

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@NonNull String title) {
        mTitle = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyList myList;
        myList = (MyList) o;
        return mId == myList.mId &&
                Objects.equals(mTitle, myList.mTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mTitle);
    }
}
