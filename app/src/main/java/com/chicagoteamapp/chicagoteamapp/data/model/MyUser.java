package com.chicagoteamapp.chicagoteamapp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * 2 ver of DB: the table was added, containing id, user_name columns, primary key is id
 */

@Entity(tableName = "Users",
        indices = {@Index(value = {"id"}, unique = true)})
public class MyUser implements Serializable {

    @ColumnInfo(name = "id")
    @PrimaryKey
    @NonNull
    private String mId;

    @ColumnInfo(name = "user_name")
    @NonNull
    private String mName;

    public MyUser(@NonNull String mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyUser myUser = (MyUser) o;
        return mId.equals(myUser.mId) &&
                Objects.equals(mName, myUser.mName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mName);
    }

}
