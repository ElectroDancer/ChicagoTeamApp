package com.chicagoteamapp.chicagoteamapp.model.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.chicagoteamapp.chicagoteamapp.model.MyImage;

import java.util.List;

@Dao
public interface ImageDao {

    @Insert
    long insert(MyImage image);

    @Update
    void update(MyImage image);

    @Delete
    void delete(MyImage image);

    @Query("SELECT * FROM images WHERE id_task=:taskId")
    LiveData<List<MyImage>> getImages(final long taskId);

    @Query("SELECT COUNT(*) FROM images WHERE id_task=:taskId")
    int geImagesCount(final long taskId);
}
