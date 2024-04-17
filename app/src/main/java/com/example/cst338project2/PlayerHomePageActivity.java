package com.example.cst338project2;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cst338project2.DB.AppRepository;
import com.example.cst338project2.DB.User;
import com.example.cst338project2.databinding.ActivityPlayerHomePageBinding;

public class PlayerHomePageActivity extends AppCompatActivity {
    ActivityPlayerHomePageBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPlayerHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppRepository repo = new AppRepository(getApplication());


        SharedPreferences sharedPref = this.getSharedPreferences(MainActivity.preference_file_key, Context.MODE_PRIVATE);
        User user = repo.getUserByID(sharedPref.getInt(MainActivity.logged_in_user_key,-1)).get(0);
        if(!user.isAdmin()){
            binding.adminPageButton.setVisibility(View.GONE);
        }
        binding.playerGreetingTextView.setText("Welcome, " + user.getUsername() + "!");

        binding.adminPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminHomePage.AdminHomePageIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.playGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.gameOptionsFragmentView.setVisibility(View.VISIBLE);
                binding.highScoresFragmentContainerView.setVisibility(View.INVISIBLE);
            }
        });
        binding.showHighScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.gameOptionsFragmentView.setVisibility(View.INVISIBLE);
                binding.highScoresFragmentContainerView.setVisibility(View.VISIBLE);
            }
        });


        binding.playerLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
                startActivity(intent);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(MainActivity.logged_in_user_key, -1);
                editor.apply();
            }
        });
    }

    public static Intent PlayerHomePageIntentFactory(Context context){
        return new Intent(context, PlayerHomePageActivity.class);
    }
}