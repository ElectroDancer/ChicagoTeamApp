package com.chicagoteamapp.chicagoteamapp.data.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import com.chicagoteamapp.chicagoteamapp.data.model.MyFile;
import com.chicagoteamapp.chicagoteamapp.data.model.MyImage;
import com.chicagoteamapp.chicagoteamapp.data.model.MyLink;
import com.chicagoteamapp.chicagoteamapp.data.model.MyList;
import com.chicagoteamapp.chicagoteamapp.data.model.MyStep;
import com.chicagoteamapp.chicagoteamapp.data.model.MyTask;
import com.chicagoteamapp.chicagoteamapp.data.model.MyUser;

@Database(entities = {MyUser.class, MyStep.class, MyTask.class, MyList.class, MyFile.class, MyLink.class,
        MyImage.class}, version = 2, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract StepDao stepDao();

    public abstract TaskDao taskDao();

    public abstract ListDao listDao();

    public abstract FileDao fileDao();

    public abstract ImageDao imageDao();

    public abstract LinkDao linkDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE 'MyUser' " +
                    "('id' TEXT NOT NULL, " +
                    "'user_name' TEXT NOT NULL, " +
                    "PRIMARY KEY('id'))");
            database.execSQL("ALTER TABLE MyList " +
                    "ADD COLUMN id_user TEXT NOT NULL");
        }
    };
}
