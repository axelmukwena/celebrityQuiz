package com.example.celebrityquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    ArrayList<Object> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        loadData();

        QuizAdapter quizAdapter = new QuizAdapter(arrayList, this);
        recyclerView.setAdapter(quizAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.scoreButton:
                Intent i = new Intent(this, ScoreActivity.class);
                this.startActivity(i);
                break;
            case R.id.settings:
                Intent j = new Intent(this, SettingsActivity.class);
                this.startActivity(j);
                break;
        }
        return(super.onOptionsItemSelected(item));
    }

    public void loadData() {
        arrayList.add(new Quiz(getResources().getString(R.string.questionOne),
                getResources().getDrawable(R.drawable.celebrity_one_image, null),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.celebrityOne))),
                getResources().getString(R.string.answerOne)));
        arrayList.add(new Quiz(getResources().getString(R.string.questionTwo),
                getResources().getDrawable(R.drawable.celebrity_two_image, null),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.celebrityTwo))),
                getResources().getString(R.string.answerTwo)));
        arrayList.add(new Quiz(getResources().getString(R.string.questionThree),
                getResources().getDrawable(R.drawable.celebrity_three_image, null),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.celebrityThree))),
                getResources().getString(R.string.answerThree)));
        arrayList.add(new Quiz(getResources().getString(R.string.questionFour),
                getResources().getDrawable(R.drawable.celebrity_four_image, null),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.celebrityFour))),
                getResources().getString(R.string.answerFour)));
        arrayList.add(new Quiz(getResources().getString(R.string.questionFive),
                getResources().getDrawable(R.drawable.celebrity_five_image, null),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.celebrityFive))),
                getResources().getString(R.string.answerFive)));
        arrayList.add(new Quiz(getResources().getString(R.string.questionSix),
                getResources().getDrawable(R.drawable.celebrity_six_image, null),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.celebritySix))),
                getResources().getString(R.string.answerSix)));
        arrayList.add(new Quiz(getResources().getString(R.string.questionSeven),
                getResources().getDrawable(R.drawable.celebrity_seven_image, null),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.celebritySeven))),
                getResources().getString(R.string.answerSeven)));
        arrayList.add(new Quiz(getResources().getString(R.string.questionEight),
                getResources().getDrawable(R.drawable.celebrity_eight_image, null),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.celebrityEight))),
                getResources().getString(R.string.answerEight)));
        arrayList.add(new Quiz(getResources().getString(R.string.questionNine),
                getResources().getDrawable(R.drawable.celebrity_nine_image, null),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.celebrityNine))),
                getResources().getString(R.string.answerNine)));
        arrayList.add(new Quiz(getResources().getString(R.string.questionTen),
                getResources().getDrawable(R.drawable.celebrity_ten_image, null),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.celebrityTen))),
                getResources().getString(R.string.answerTen)));
    }
}