package com.example.celebrityquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    // Declare variables
    private List<Quiz> quizList;
    private int level;
    private int seconds;
    private CountDownTimer countDownTimer;
    private int indexCurrentQuestion;

    private ProgressBar progressBarQuiz;
    private TextView questionView;
    private ImageView imageView;
    private RadioGroup radioGroup;
    private RadioButton radioButtonOne;
    private RadioButton radioButtonTwo;
    private RadioButton radioButtonThree;
    private RadioButton radioButtonFour;
    private Button buttonPrevious;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate((savedInstanceState));
        setContentView(R.layout.activity_quiz);

        progressBarQuiz = findViewById(R.id.progressBarQuiz);
        questionView = findViewById(R.id.celebrityQuestion);
        imageView = findViewById(R.id.celebrityImage);
        radioGroup = findViewById(R.id.celebrityOption);
        radioButtonOne = findViewById(R.id.radioButtonOne);
        radioButtonTwo = findViewById(R.id.radioButtonTwo);
        radioButtonThree = findViewById(R.id.radioButtonThree);
        radioButtonFour = findViewById(R.id.radioButtonFour);

        radioButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (quizList.get(indexCurrentQuestion).userAnswer) {
                    case R.id.radioButtonOne:
                        ((RadioButton) view).setChecked(true);
                        quizList.get(indexCurrentQuestion).userAnswer = 1;
                        break;
                    case R.id.radioButtonTwo:
                        ((RadioButton) view).setChecked(true);
                        quizList.get(indexCurrentQuestion).userAnswer = 2;
                        break;
                    case R.id.radioButtonThree:
                        ((RadioButton) view).setChecked(true);
                        quizList.get(indexCurrentQuestion).userAnswer = 3;
                        break;
                    case R.id.radioButtonFour:
                        ((RadioButton) view).setChecked(true);
                        quizList.get(indexCurrentQuestion).userAnswer = 4;
                        break;
                    default:
                        quizList.get(indexCurrentQuestion).userAnswer = 0;
                }
            }
        });

        buttonNext = findViewById(R.id.buttonNext);
        buttonPrevious = findViewById(R.id.buttonPrevious);

        Intent intent = getIntent();
        level = intent.getIntExtra("level", 0);
        seconds = intent.getIntExtra("seconds", 30);
        String string = null;

        try {
            FileInputStream fileInputStream = openFileInput("myJason");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            string = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "downloaded file cannot be found", Toast.LENGTH_SHORT).show();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Quiz>>(){}.getType();

        List<Quiz> list = gson.fromJson(string, type);
        if (level == 1) {
            assert list != null;
            quizList = list.subList(0, 5);
        } else if (level == 2) {
            assert list != null;
            quizList = list.subList(5, 10);
        } else {
            assert list != null;
            quizList = list.subList(10, 15);
        }

        indexCurrentQuestion = 0;
        Quiz currentQuestion = quizList.get(indexCurrentQuestion);
        currentQuestionView(currentQuestion);
        buttonPrevious.setEnabled(false);

        progressBarQuiz.setProgress(seconds);
        countDownTimer = new CountDownTimer(seconds * 1000, 100000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long progress = (millisUntilFinished / 1000);
                progressBarQuiz.setProgress((int) progress);
            }

            @Override
            public void onFinish() {
                int score = getScore();
                Intent i = new Intent(QuizActivity.this, ScoreActivity.class);
                i.putExtra("score", score);
                i.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP );
                startActivity(i);
            }
        };
        countDownTimer.start();
    }

    public void onButtonPrevious(View view) {
        if(indexCurrentQuestion != 0) {
            indexCurrentQuestion--;
            if(indexCurrentQuestion == 0) buttonPrevious.setEnabled(false);
            if(indexCurrentQuestion != (quizList.size() - 1)) buttonNext.setEnabled(true);
            Quiz currentQuestion = quizList.get(indexCurrentQuestion);
            currentQuestionView(currentQuestion);

            RadioGroup radioGroup = findViewById(R.id.celebrityOption);
            if(currentQuestion.userAnswer == 0) radioGroup.clearCheck();
            else {
                switch (currentQuestion.userAnswer) {
                    case 1: {
                        radioGroup.check(R.id.radioButtonOne);
                        break;
                    }
                    case 2: {
                        radioGroup.check(R.id.radioButtonTwo);
                        break;
                    }
                    case 3: {
                        radioGroup.check(R.id.radioButtonThree);
                        break;
                    }
                    case 4: {
                        radioGroup.check(R.id.radioButtonFour);
                        break;
                    }
                }
            }
        }
    }

    public void onButtonNext(View view) {
        if(indexCurrentQuestion != (quizList.size() - 1)) {
            indexCurrentQuestion++;
            if(indexCurrentQuestion == (quizList.size() - 1)) buttonNext.setEnabled(false);
            if(indexCurrentQuestion != 0) buttonPrevious.setEnabled(true);
            Quiz currentQuestion = quizList.get(indexCurrentQuestion);
            currentQuestionView(currentQuestion);

            RadioGroup radioGroup = findViewById(R.id.celebrityOption);
            if(currentQuestion.userAnswer == 0) radioGroup.clearCheck();
            else {
                switch (currentQuestion.userAnswer) {
                    case 1: {
                        radioGroup.check(R.id.radioButtonOne);
                        break;
                    }
                    case 2: {
                        radioGroup.check(R.id.radioButtonTwo);
                        break;
                    }
                    case 3: {
                        radioGroup.check(R.id.radioButtonThree);
                        break;
                    }
                    case 4: {
                        radioGroup.check(R.id.radioButtonFour);
                        break;
                    }
                }
            }
        }
    }

    public void onButtonSubmit(View view) {
        int score = getScore();
        Intent i = new Intent(QuizActivity.this, ScoreActivity.class);
        i.putExtra("score", score);
        i.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP );
        startActivity(i);
    }

    public void currentQuestionView(Quiz currentQuestion) {
        questionView.setText(String.format("%s. %s", indexCurrentQuestion + 1, currentQuestion.question));
        radioButtonOne.setText(currentQuestion.one);
        radioButtonTwo.setText(currentQuestion.two);
        radioButtonThree.setText(currentQuestion.three);
        radioButtonFour.setText(currentQuestion.four);
        Glide.with(imageView.getContext()).load(currentQuestion.imageUrl).into(imageView);
    }

    public int getScore() {
        int score = 0;
        for (int i = 0; i < quizList.size(); i++) {
            if (quizList.get(i).userAnswer == quizList.get(i).correctAnswer) score++;
        }
        return score;
    }
}
