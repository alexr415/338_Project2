package com.example.cst338project2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User... user);
    @Update
    void update(User... user);
    @Delete
    void delete(User... user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE userId = :id")
    List<User> getUserByID(int id);
}
