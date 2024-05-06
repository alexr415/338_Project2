package com.example.cst338project2.DB;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = AppDatabase.SCORE_TABLE, foreignKeys = @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userId"))
public class Score {
    @PrimaryKey(autoGenerate = true)
    private int scoreId;
    private int userId;
    private int score;
    private String game;

    public Score(int userId, int score, String game) {
        this.userId = userId;
        this.score = score;
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score1 = (Score) o;
        return getScoreId() == score1.getScoreId() && getUserId() == score1.getUserId() && getScore() == score1.getScore() && Objects.equals(getGame(), score1.getGame());
    }

    @Override
    public int hashCode() {
        int result = getScoreId();
        result = 31 * result + getUserId();
        result = 31 * result + getScore();
        result = 31 * result + Objects.hashCode(getGame());
        return result;
    }

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
