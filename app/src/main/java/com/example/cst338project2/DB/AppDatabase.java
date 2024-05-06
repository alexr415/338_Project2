package com.example.cst338project2.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.cst338project2.numberGuessingGameActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Score.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    public static final String USER_TABLE = "user";
    public static final String DATABASE_NAME = "AppDB";
    public static final String SCORE_TABLE = "score" ;
    public static final String MESSAGE_TABLE = "message";
    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();


    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public abstract UserDAO userDAO();

    public static AppDatabase getDatabase(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class,
                                    DATABASE_NAME)
                            .addCallback(addDefaultValues)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                UserDAO userDAO = instance.userDAO();
                ScoreDAO scoreDAO = instance.scoreDAO();
                // add delete alls for all tables
                User admin  = new User("admin1","admin1",true);
                User user = new User("testuser1","testuser1",false);
                Score score = new Score(1,1, numberGuessingGameActivity.GAME_TAG);
                Score score2 = new Score(2,2,numberGuessingGameActivity.GAME_TAG);

                userDAO.insert(admin);
                userDAO.insert(user);
                scoreDAO.insert(score2);
                scoreDAO.insert(score);
            });
        }
    };

    public abstract ScoreDAO scoreDAO();
}
