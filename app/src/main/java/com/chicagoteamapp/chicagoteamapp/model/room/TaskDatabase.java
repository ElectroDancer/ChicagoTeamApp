package com.chicagoteamapp.chicagoteamapp.model.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.chicagoteamapp.chicagoteamapp.model.MyFile;
import com.chicagoteamapp.chicagoteamapp.model.MyImage;
import com.chicagoteamapp.chicagoteamapp.model.MyLink;
import com.chicagoteamapp.chicagoteamapp.model.MyList;
import com.chicagoteamapp.chicagoteamapp.model.MyStep;
import com.chicagoteamapp.chicagoteamapp.model.MyTask;

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
