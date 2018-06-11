package com.chicagoteamapp.chicagoteamapp.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.chicagoteamapp.chicagoteamapp.data.model.MyFile;

import java.util.List;

@Dao
public interface FileDao {

    @Insert
    long insert(MyFile file);

    @Update
    void update(MyFile file);

    @Delete
    void delete(MyFile file);

    @Query("SELECT * FROM files WHERE id_task=:taskId")
    LiveData<List<MyFile>> getFiles(final long taskId);

    @Query("SELECT COUNT(*) FROM files WHERE id_task=:taskId")
    int geFilesCount(final long taskId);
}
