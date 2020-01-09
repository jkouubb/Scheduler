package com.example.scheduler.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DeadLineDao {
    @Insert
    void Insert(DeadLine... deadLines);

    @Update
    void Update(DeadLine... deadLines);

    @Delete
    void Delete(DeadLine... deadLines);

    @Query("SELECT * FROM DeadLine WHERE month=:nowmonth AND day=:nowday AND year=:nowyear")
    LiveData<List<DeadLine>> SelectDeadLineByDate(int nowyear,int nowmonth,int nowday);

    @Query("DELETE FROM DeadLine")
    void Clear();
}
