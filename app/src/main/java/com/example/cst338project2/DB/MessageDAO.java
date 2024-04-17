package com.example.cst338project2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MessageDAO {
    @Insert
    void insert(Message... message);
    @Update
    void update(Message... message);
    @Delete
    void delete(Message... message);

    @Query("SELECT * FROM " + AppDatabase.MESSAGE_TABLE + " WHERE receiverID = :receiverID")
    ArrayList<Score> getMessagesForReceiver(String receiverID);
}
