package com.example.cst338project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cst338project2.DB.AppRepository;
import com.example.cst338project2.DB.Message;
import com.example.cst338project2.databinding.ActivityComposeMessageBinding;

public class ComposeMessage extends AppCompatActivity {
    private ActivityComposeMessageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComposeMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPref = this.getSharedPreferences(MainActivity.preference_file_key, Context.MODE_PRIVATE);
        int userID = sharedPref.getInt(MainActivity.logged_in_user_key, -1);
        AppRepository repo = new AppRepository(getApplication());

        binding.MessageSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.composeMessageSubjectInput.getText().toString().isEmpty() || binding.composeMessageBodyInput.getText().toString().isEmpty() || binding.composeMessageRecipientUserIDInput.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (repo.getUserByID(Integer.parseInt(binding.composeMessageRecipientUserIDInput.getText().toString())).isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Recipient does not exist", Toast.LENGTH_SHORT).show();
                    } else {
                        repo.insertMessage(new Message(userID, Integer.parseInt(binding.composeMessageRecipientUserIDInput.getText().toString()), binding.composeMessageBodyInput.getText().toString(), binding.composeMessageSubjectInput.getText().toString()));
                        Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_SHORT).show();


                    }
                    ;

                }
            }
        });

    }

    public static Intent ComposeMessageIntentFactory(Context context) {

        return new Intent(context, ComposeMessage.class);
    }


}