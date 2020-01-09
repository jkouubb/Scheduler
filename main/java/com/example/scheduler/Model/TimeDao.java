package com.example.scheduler.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimeDao {
    @Insert
    void Insert(Time... times);

    @Update
    void Update(Time... times);

    @Delete
    void Delete(Time... times);

    @Query("DELETE FROM time")
    void Clear();

    @Query("SELECT * FROM time")
    LiveData<List<Time>> GetTime();

    @Query("SELECT * FROM time WHERE id=:nowid")
    LiveData<Time> SelectTime(int nowid);

}
