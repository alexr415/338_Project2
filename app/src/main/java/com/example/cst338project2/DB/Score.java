package com.example.cst338project2.DB;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = AppDatabase.SCORE_TABLE, foreignKeys = @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userId"))
public class Score{
    @PrimaryKey(autoGenerate = true)
    private int scoreId;
    private int userId;
    private int score;
    private int game;

    public Score(int userId, int score, int game) {
        this.userId = userId;
        this.score = score;
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score1 = (Score) o;
        return getScoreId() == score1.getScoreId() && getUserId() == score1.getUserId() && getScore() == score1.getScore() && getGame() == score1.getGame();
    }

    @Override
    public int hashCode() {
        int result = getScoreId();
        result = 31 * result + getUserId();
        result = 31 * result + getScore();
        result = 31 * result + getGame();
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

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }
}
