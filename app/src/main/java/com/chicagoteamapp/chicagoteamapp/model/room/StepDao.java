package com.chicagoteamapp.chicagoteamapp.model.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.chicagoteamapp.chicagoteamapp.model.MyStep;

import java.util.List;

@Dao
public interface StepDao {

    @Insert
    long insert(MyStep step);

    @Update
    void update(MyStep step);

    @Delete
    void delete(MyStep step);

    @Query("SELECT * FROM steps WHERE id_task=:taskId")
    LiveData<List<MyStep>> getSteps(final long taskId);

    @Query("SELECT COUNT(*) FROM steps WHERE id_task=:taskId")
    int geStepsCount(final long taskId);

    @Query("SELECT COUNT(*) FROM steps WHERE id_task=:taskId AND completed=1")
    int getCompletedStepsCount(final long taskId);
}
