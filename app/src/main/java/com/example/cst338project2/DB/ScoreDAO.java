package com.example.cst338project2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ScoreDAO {
    @Insert
    void insert(Score... score);
    @Update
    void update(Score... score);
    @Delete
    void delete(Score... score);

    @Query("SELECT * FROM " + AppDatabase.SCORE_TABLE)
    List<Score> getScores();

    @Query("SELECT * FROM " + AppDatabase.SCORE_TABLE + " WHERE game = :game")
    List<Score> getScoreByGame (String game);

    @Query("SELECT max(score) FROM " + AppDatabase.SCORE_TABLE + " WHERE userId = :user_id AND game=:game")
    int getHighScoreByUserAndGame(int user_id, String game);

    @Query("SELECT max(score) FROM " + AppDatabase.SCORE_TABLE + " WHERE game=:game")
    int getHighScoreByGame(String game);

    @Query("DELETE FROM " + AppDatabase.SCORE_TABLE + " WHERE userId = :id")
    void deleteScoresByUserId(int id);

}
