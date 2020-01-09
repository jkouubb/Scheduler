package com.example.scheduler.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ActivityDao {
    @Insert
    void Insert(Activity... activities);

    @Update
    void Update(Activity... activities);

    @Delete
    void Delete(Activity... activities);

    @Query("SELECT * FROM Activity WHERE (start_week<=:nowweek AND end_week>=:nowweek) AND (week_status=0 OR :nowweek%2=week_status%2)")
    LiveData<List<Activity>> SelectActivityByWeek(int nowweek);

    @Query("SELECT * FROM Activity WHERE id=:i")
    LiveData<Activity> SelectActivityById(int i);

    @Query("DELETE FROM activity")
    void Clear();
}
