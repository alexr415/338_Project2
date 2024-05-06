package com.example.cst338project2.DB;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository {
    private final UserDAO userDAO;
    private final ScoreDAO scoreDAO;
    private List<User> allUsers;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.scoreDAO = db.scoreDAO();

    }

    public ArrayList<User> getUserByID(int id) {
        Future<ArrayList<User>> future = AppDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<User>>() {
                    @Override
                    public ArrayList<User> call() throws Exception {
                        allUsers = userDAO.getUserByID(id);
                        return (ArrayList<User>) allUsers;
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    public int getHighScoreByUserAndGame(int user_id, String game) {
        Future<Integer> future = AppDatabase.databaseWriteExecutor.submit(
                new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return scoreDAO.getHighScoreByUserAndGame(user_id, game);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public int getHighScoreByGame(String game) {
        Future<Integer> future = AppDatabase.databaseWriteExecutor.submit(
                new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return scoreDAO.getHighScoreByGame(game);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void deleteUserById(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.deleteUserById(id);
        });
    }

    public void deleteScoresByUserId(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            scoreDAO.deleteScoresByUserId(id);
        });
    }

    public LiveData<List<User>> getUsersLiveData() {
        return userDAO.getUsersLiveData();
    }

    public ArrayList<User> getAllUsers() {
        Future<ArrayList<User>> future = AppDatabase.databaseWriteExecutor.submit(new Callable<ArrayList<User>>() {
                                                                                      @Override
                                                                                      public ArrayList<User> call() throws Exception {
                                                                                          allUsers = userDAO.getUsers();
                                                                                          return (ArrayList<User>) allUsers;
                                                                                      }
                                                                                  }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void insertUser(User... user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

    public void insertScore(Score... score) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            scoreDAO.insert(score);
        });
    }
}

