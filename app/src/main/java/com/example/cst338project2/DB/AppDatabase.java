package com.example.cst338project2.DB;

import android.content.Context;
import android.view.View;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cst338project2.User;

@Database(entities = {User.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public static final String USER_TABLE = "user_table";
    public static final String DATABASE_NAME = "UserDB";
    private static volatile  AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract UserDAO userDAO();

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
