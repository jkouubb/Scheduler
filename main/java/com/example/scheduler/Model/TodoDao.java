package com.example.scheduler.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {
    @Insert
    void Insert(Todo... todos);

    @Update
    void Update(Todo... todos);

    @Delete
    void Delete(Todo... todos);

    @Query("SELECT * FROM Todo WHERE name LIKE :key")
    LiveData<List<Todo>> SelectTodo(String key);

    @Query("SELECT * FROM Todo WHERE id=:id")
    LiveData<Todo> SelectTodoById(int id);

    @Query("DELETE FROM Todo")
    void Clear();
}
