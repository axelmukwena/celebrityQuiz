package com.example.celebrityquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class ScoreActivity extends AppCompatActivity{

    private int scoreValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Results");
        }

        // Interface instance to access score value from QuizActivity
        scoreValue = getIntent().getIntExtra("score", 0);
        int level = getIntent().getIntExtra("level", 1);

        // Set view and display scoreValue
        TextView scoreView = findViewById(R.id.scoreTextView);
        scoreView.setText(String.valueOf(scoreValue));

        // See function
        displayWellDone(scoreValue);
    }

    // Function to display well done image if user gets all correct | also settings for total value
    public void displayWellDone(int score) {

        // Set view for well done image
        ImageView imageView = findViewById(R.id.wellDoneImage);
        imageView.setVisibility(View.INVISIBLE); // set image invisible

        TextView scoreView = findViewById(R.id.scoreTotalTextView);
        scoreView.setText(String.valueOf(5));

        // display well done image if user gets all correct
        if (score == 5) imageView.setVisibility(View.VISIBLE);
    }
}
