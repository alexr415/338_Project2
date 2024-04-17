package com.example.cst338project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cst338project2.DB.AppDatabase;
import com.example.cst338project2.DB.AppRepository;
import com.example.cst338project2.DB.User;
import com.example.cst338project2.DB.UserDAO;
import com.example.cst338project2.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    UserDAO userDAO;
    protected static final String preference_file_key = "com.example.cst338project2.preference_file_key";
    protected static final String logged_in_user_key = "com.example.cst338project2.logged_in_user_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
        AppRepository repo = new AppRepository(getApplication());
        if(sharedPref.getInt(logged_in_user_key,-1) != -1){
            Intent intent = PlayerHomePageActivity.PlayerHomePageIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        //initialize the database

//        userDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
//                .allowMainThreadQueries()
//                .build()
//                .userDAO();


//        User admin  = new User("admin","admin",true);
//        User user = new User("user","user",false);


        repo.getAllUsers();

        //add admin and user to the database if they do not exist
//        if(userDAO.getUserByUsername(admin.getUsername()).isEmpty()) {
//            userDAO.insert(admin);
//        }
//        if(userDAO.getUserByUsername(user.getUsername()).isEmpty()) {
//            userDAO.insert(user);
//        }

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()){
                    List<User> users = userDAO.getUsers();
                    for (User user: users){
                        if(user.getUsername().equals(binding.usernameInput.getText().toString())){
                            Toast.makeText(MainActivity.this, "Username already exists!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    userDAO.insert(createUserFromInput());

                }
                Toast.makeText(MainActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()){
                    AppRepository appRepository = new AppRepository(getApplication());
                    List<User> users = appRepository.getAllUsers();
                    //check if the user exists in the database

                    //if the user and password match, check if the user is an admin
                    for(User user: users) {
                        if(user.getUsername().equals(binding.usernameInput.getText().toString()) && user.getPassword().equals(binding.passwordInput.getText().toString())){

                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putInt(logged_in_user_key, user.getUserId());
                                editor.apply();
                                Intent intent = PlayerHomePageActivity.PlayerHomePageIntentFactory(getApplicationContext());
                                startActivity(intent);

                            return;

                        }
                    }
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    //if the user is an admin, display the admin activity
                    //if the user is not an admin, display the user activity
                    //if the user and password do not match, display an error message
                }
            }
        });
    }
    private boolean validateInput(){
        String username = binding.usernameInput.getText().toString();
        String password = binding.passwordInput.getText().toString();
        //Toast.makeText(this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
        return !username.isEmpty() && !password.isEmpty();
    }

    private User createUserFromInput(){
        return new User(binding.usernameInput.getText().toString(),binding.passwordInput.getText().toString(),false);
    }

    public static Intent MainActivityIntentFactory(Context context) {
        return new Intent(context, MainActivity.class);
    }
}