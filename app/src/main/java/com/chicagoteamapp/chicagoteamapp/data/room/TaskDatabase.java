package com.chicagoteamapp.chicagoteamapp.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.chicagoteamapp.chicagoteamapp.data.model.MyFile;
import com.chicagoteamapp.chicagoteamapp.data.model.MyImage;
import com.chicagoteamapp.chicagoteamapp.data.model.MyLink;
import com.chicagoteamapp.chicagoteamapp.data.model.MyList;
import com.chicagoteamapp.chicagoteamapp.data.model.MyStep;
import com.chicagoteamapp.chicagoteamapp.data.model.MyTask;

@Database(entities = {MyStep.class, MyTask.class, MyList.class, MyFile.class, MyLink.class,
        MyImage.class}, version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract StepDao stepDao();

    public abstract TaskDao taskDao();

    public abstract ListDao listDao();

    public abstract FileDao fileDao();

    public abstract ImageDao imageDao();

    public abstract LinkDao linkDao();
}
