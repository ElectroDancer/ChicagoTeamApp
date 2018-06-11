package com.chicagoteamapp.chicagoteamapp.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.chicagoteamapp.chicagoteamapp.data.model.MyTask;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    long insert(MyTask task);

    @Update
    void update(MyTask task);

    @Delete
    void delete(MyTask task);

    @Query("SELECT * FROM tasks WHERE id_list=:listId")
    LiveData<List<MyTask>> getTasks(final long listId);

    @Query("SELECT COUNT(*) FROM tasks WHERE id_list=:listId")
    int geTasksCount(final long listId);

    @Query("SELECT COUNT(*) FROM tasks WHERE id_list=:listId AND completed=1")
    int getCompletedTasksCount(final long listId);
}
