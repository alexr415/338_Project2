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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.cst338project2.DB.AppRepository;
import com.example.cst338project2.DB.Message;
import com.example.cst338project2.databinding.ActivityMessageCenterBinding;

import java.util.List;

public class MessageCenter extends AppCompatActivity {
    ActivityMessageCenterBinding binding;
    LiveData<List<Message>> messageLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMessageCenterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        StringBuilder sb = new StringBuilder();
        AppRepository repo = new AppRepository(getApplication());
        SharedPreferences sharedPref = this.getSharedPreferences(MainActivity.preference_file_key, Context.MODE_PRIVATE);
        messageLiveData = repo.getMessagesForReceiverLiveData(sharedPref.getInt(MainActivity.logged_in_user_key, -1));

        messageLiveData.observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                StringBuilder sb1 = new StringBuilder();
                for (Message message : messages) {
                    sb1.append("Sender ID: " + message.getSenderId() + "\nSubject:" + message.getSubject() + "\nMessage: " + message.getMessageBody() + "\n\n");
                }
                binding.InboxTextView.setText(sb1);
            }
        });

        List<Message> messages = repo.getMessagesForReceiver(sharedPref.getInt(MainActivity.logged_in_user_key, -1));
        for (Message message : messages) {
            sb.append("Sender ID: " + message.getSenderId() + "\nSubject:" + message.getSubject() + "\nMessage: " + message.getMessageBody() + "\n\n");
        }
        binding.InboxTextView.setText(sb.toString());

        binding.deleteInboxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repo.deleteMessagesForReceiver(sharedPref.getInt(MainActivity.logged_in_user_key, -1));
            }
        });

        binding.ComposeMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ComposeMessage.ComposeMessageIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });


    }

    public static Intent MessageCenterIntentFactory(Context context) {

        return new Intent(context, MessageCenter.class);
    }
}