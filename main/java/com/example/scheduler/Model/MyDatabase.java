package com.example.scheduler.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Activity.class,DeadLine.class,Todo.class,Time.class},version = 1,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    static private MyDatabase instance;
    static public void initDatabase(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context,MyDatabase.class,"DataBase").build();
        }
    }
    static synchronized public MyDatabase getDatabase(){
        return instance;
    }

    private ActivityDao activityDao;
    private DeadLineDao deadLineDao;
    private TodoDao todoDao;
    private TimeDao timeDao;

    public abstract ActivityDao getActivityDao();
    public abstract DeadLineDao getDeadLineDao();
    public abstract TodoDao getTodoDao();
    public abstract TimeDao getTimeDao();
}
