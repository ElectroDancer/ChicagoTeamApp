package com.chicagoteamapp.chicagoteamapp.model.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.chicagoteamapp.chicagoteamapp.model.MyList;

import java.util.List;

@Dao
public interface ListDao {

    @Insert
    long insert(MyList list);

    @Update
    void update(MyList list);

    @Delete
    void delete(MyList list);

    @Query("SELECT * FROM lists")
    List<MyList> getAllLists();

    @Query("SELECT COUNT(*) FROM lists")
    int getListsCount();
}
