package com.example.celebrityquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity{
    //int score;

    QuizAdapter.CurrentScore currentScore = new QuizAdapter.CurrentScore() {
        @Override
        public void CurrentScoreMethod(QuizAdapter.QuizHolder quizHolder, int score) {
            TextView scoreView = findViewById(R.id.scoreTextView);
            scoreView.setText(String.valueOf(score));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        currentScore = (QuizAdapter.CurrentScore) this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        ImageButton backArrow = findViewById(R.id.backArrowScore);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                ScoreActivity.this.startActivity(intent);
            }
        });
    }
}
