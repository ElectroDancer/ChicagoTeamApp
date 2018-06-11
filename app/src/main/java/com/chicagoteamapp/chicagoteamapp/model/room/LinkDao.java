package com.chicagoteamapp.chicagoteamapp.model.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.chicagoteamapp.chicagoteamapp.model.MyLink;

import java.util.List;

@Dao
public interface LinkDao {

    @Insert
    long insert(MyLink image);

    @Update
    void update(MyLink image);

    @Delete
    void delete(MyLink image);

    @Query("SELECT * FROM links WHERE id_task=:taskId")
    LiveData<List<MyLink>> getLinks(final long taskId);

    @Query("SELECT COUNT(*) FROM links WHERE id_task=:taskId")
    int geLinksCount(final long taskId);
}
