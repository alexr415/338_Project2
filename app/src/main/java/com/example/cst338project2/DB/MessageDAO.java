package com.example.cst338project2.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessageDAO {
    @Insert
    void insert(Message... message);

    @Update
    void update(Message... message);

    @Delete
    void delete(Message... message);

    @Query("SELECT * FROM " + AppDatabase.MESSAGE_TABLE + " WHERE receiverId = :receiverID")
    LiveData<List<Message>> getMessagesForReceiverLiveData(int receiverID);

    @Query("SELECT * FROM " + AppDatabase.MESSAGE_TABLE + " WHERE receiverId = :receiverID")
    List<Message> getMessagesForReceiver(int receiverID);

    @Query("delete FROM " + AppDatabase.MESSAGE_TABLE + " WHERE receiverId = :receiverID")
    void deleteMessagesForReceiver(int receiverID);

}
