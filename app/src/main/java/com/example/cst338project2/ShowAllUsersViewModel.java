package com.example.cst338project2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.cst338project2.DB.AppRepository;
import com.example.cst338project2.DB.User;

import java.util.List;

public class ShowAllUsersViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private AppRepository repo ;

    private final LiveData<List<User>> allUsers;

    public ShowAllUsersViewModel(Application application){
        super(application);
        repo = new AppRepository(application);
        allUsers = repo.getUsersLiveData();
    }

    LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insert(User user){
        repo.insertUser(user);
    }
}