package com.example.cst338project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.cst338project2.DB.AppRepository;
import com.example.cst338project2.DB.User;
import com.example.cst338project2.databinding.ActivityAdminHomePageBinding;

import java.util.ArrayList;
import java.util.List;

public class AdminHomePage extends AppCompatActivity {
    private ActivityAdminHomePageBinding binding;

    LiveData<List<User>> userLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPref = this.getSharedPreferences(MainActivity.preference_file_key, Context.MODE_PRIVATE);

        AppRepository repo = new AppRepository(getApplication());
        ArrayList<User> users = repo.getAllUsers();
        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append("Username: " + user.getUsername() + "\tUserID: " + user.getUserId() + "\n\n");
        }
        binding.adminUserListTextView.setText(sb.toString());

        userLiveData = repo.getUsersLiveData();

        userLiveData.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                StringBuilder sb = new StringBuilder();
                for (User user : users) {
                    sb.append("Username: " + user.getUsername() + "\tUserID: " + user.getUserId() + "\n\n");
                }
                binding.adminUserListTextView.setText(sb.toString());
            }
        });

        binding.ClearHighScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repo.deleteAllScores();
                Toast.makeText(getApplicationContext(), "All High Scores Cleared", Toast.LENGTH_SHORT).show();
            }
        });

        binding.DeleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.userToDeleteIDinput.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a UserID to delete", Toast.LENGTH_SHORT).show();

                } else if (repo.getUserByID(Integer.parseInt(binding.userToDeleteIDinput.getText().toString())).size() == 0) {
                    Toast.makeText(getApplicationContext(), "User doesn't exist", Toast.LENGTH_SHORT).show();
                } else if (repo.getUserByID(Integer.parseInt(binding.userToDeleteIDinput.getText().toString())).get(0).isAdmin()) {
                    Toast.makeText(getApplicationContext(), "ADMINS ARE FOREVER", Toast.LENGTH_SHORT).show();
                } else {
                    int id = Integer.parseInt(binding.userToDeleteIDinput.getText().toString());
                    repo.deleteScoresByUserId(id);
                    repo.deleteMessagesForReceiver(id);
                    repo.deleteUserById(id);


                }
            }
        });

        binding.adminLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
                startActivity(intent);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(MainActivity.logged_in_user_key, -1);
                editor.apply();
            }
        });
        binding.adminPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PlayerHomePageActivity.PlayerHomePageIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent AdminHomePageIntentFactory(Context context) {

        return new Intent(context, AdminHomePage.class);
    }
}