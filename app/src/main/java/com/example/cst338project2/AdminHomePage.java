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

import com.example.cst338project2.databinding.ActivityAdminHomePageBinding;

public class AdminHomePage extends AppCompatActivity {
    private ActivityAdminHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAdminHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPref = this.getSharedPreferences(MainActivity.preference_file_key, Context.MODE_PRIVATE);

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
    public static Intent AdminHomePageIntentFactory(Context context){

        return new Intent(context, AdminHomePage.class);
    }
}