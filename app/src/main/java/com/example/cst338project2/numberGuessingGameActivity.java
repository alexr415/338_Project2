package com.example.cst338project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338project2.DB.AppRepository;
import com.example.cst338project2.DB.Score;
import com.example.cst338project2.DB.User;
import com.example.cst338project2.databinding.ActivityNumberGuessingGameBinding;

import java.util.concurrent.atomic.AtomicInteger;

public class numberGuessingGameActivity extends AppCompatActivity {

    private ActivityNumberGuessingGameBinding binding;
    private int guess;
    public static final String GAME_TAG = "NumberGuessingGame";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNumberGuessingGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPref = this.getSharedPreferences(MainActivity.preference_file_key, Context.MODE_PRIVATE);


        int number = (int) (Math.random() * 100 + 1);
        AtomicInteger guessesLeft = new AtomicInteger(7);

        AppRepository repo = new AppRepository(getApplication());

        User user = repo.getUserByID(sharedPref.getInt(MainActivity.logged_in_user_key, -1)).get(0);
        if (user.isAdmin()) {
            binding.adminNumberGuessAnswerTextView.setText(String.valueOf(number));
        }

        binding.previousHighScore.setText("Your High Score: " + repo.getHighScoreByUserAndGame(user.getUserId(), GAME_TAG));


        binding.guessesLeft.setText("Guesses Left: " + guessesLeft.get());
        binding.homeButton.setOnClickListener(v -> {
            Intent intent = PlayerHomePageActivity.PlayerHomePageIntentFactory(getApplicationContext());
            startActivity(intent);
        });

        binding.NumberGuessSubmitButton.setOnClickListener(v -> {
            if (binding.NumberGuessInput.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_SHORT).show();

            } else {

                switch (checkGuess(binding.NumberGuessInput.getText().toString(), number)) {
                    case 0:
                        binding.numberGuessHint.setText("You guessed the number!");
                        binding.NumberGuessSubmitButton.setVisibility(View.INVISIBLE);
                        repo.insertScore(new Score(user.getUserId(), guessesLeft.get(), GAME_TAG));
                        binding.guessesLeft.setText("You Scored:" + guessesLeft.get());
                        break;
                    case 1:
                        binding.numberGuessHint.setText("Guess Higher!");
                        guessesLeft.getAndDecrement();
                        binding.guessesLeft.setText("Guesses Left: " + guessesLeft.get());
                        if (guessesLeft.get() == 0) {
                            binding.numberGuessHint.setText("You ran out of guesses! The number was " + number);
                            binding.NumberGuessSubmitButton.setVisibility(View.INVISIBLE);
                        }
                        break;
                    case 2:
                        binding.numberGuessHint.setText("Guess Lower!");
                        guessesLeft.getAndDecrement();
                        binding.guessesLeft.setText("Guesses Left: " + guessesLeft.get());
                        if (guessesLeft.get() == 0) {
                            binding.numberGuessHint.setText("You ran out of guesses! The number was " + number);
                            binding.NumberGuessSubmitButton.setVisibility(View.INVISIBLE);
                        }
                        break;
                }

            }

        });
    }

     static int checkGuess(String guess, int number) {
        if (guess.isEmpty()) {
            return -1;
        } else {
            int guessInt = Integer.parseInt(guess);
            if (guessInt == number) {
                return 0;
            } else if (guessInt < number) {
                return 1;
            } else {
                return 2;
            }
        }

    }


    public static Intent numberGuessingGameActivityIntentFactory(Context context) {

        return new Intent(context, numberGuessingGameActivity.class);
    }
}