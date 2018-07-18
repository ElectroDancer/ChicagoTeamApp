package com.chicagoteamapp.chicagoteamapp.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.chicagoteamapp.chicagoteamapp.data.model.MyUser;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    long insert(MyUser user);

    @Update
    void update(MyUser user);

    @Delete
    void delete(MyUser user);

    @Query("SELECT * FROM Users")
    LiveData<List<MyUser>> getAllUsers();

    @Query("SELECT * " +
            "FROM Users " +
            "WHERE id = :userId")
    MyUser getCurrentUser(String userId);
}
