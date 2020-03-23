package com.example.celebrityquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        // Interface instance to access score value from QuizAdapter
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int scoreValue = sharedPreferences.getInt("score", 0);

        // Set view and display scoreValue
        TextView scoreView = findViewById(R.id.scoreTextView);
        scoreView.setText(String.valueOf(scoreValue));

        // See function
        displayWellDone(scoreValue);

        // Remove score value from interface/*memory before leaving activity
        sharedPreferences.edit().remove("score").apply();

        // Create back button and start mainActivity once clicked
        ImageButton backArrow = findViewById(R.id.backArrowScore);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                ScoreActivity.this.startActivity(intent);
            }
        });
    }

    // Function to display well done image if user gets all correct | also settings for total value
    public void displayWellDone(int score) {
        // Get switchState from Settings activity
        SharedPreferences mPrefs = getSharedPreferences("saveLevel", MODE_PRIVATE);
        boolean switchState = mPrefs.getBoolean("value", false);

        // Set view for well done image
        ImageView imageView = findViewById(R.id.wellDoneImage);
        imageView.setVisibility(View.INVISIBLE); // set image invisible

        // Based on switch state / level of difficulty, set total value and
        // display well done image if user gets all correct
        if (!switchState) {
            TextView scoreView = findViewById(R.id.scoreTotalTextView);
            scoreView.setText(String.valueOf(6));
            if (score == 6) {
                imageView.setVisibility(View.VISIBLE);
            }
        }
        else {
            TextView scoreView = findViewById(R.id.scoreTotalTextView);
            scoreView.setText(String.valueOf(10));
            if (score == 10) {
                imageView.setVisibility(View.VISIBLE);
            }
        }
    }
}
