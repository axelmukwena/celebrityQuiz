package com.example.celebrityquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {
    // Declare lists
    ArrayList<Object> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Load all details | see function for more details
        loadData();

        // Get adapter and set data to recyclerView
        QuizAdapter quizAdapter = new QuizAdapter(arrayList, this);
        recyclerView.setAdapter(quizAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Create menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Switch case for menu options based on user selection
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

    // Load data to bridge class Quiz.
    public void loadData() {

        /* Each arrayList added contains 3 arguments: Question, Image, arrayList
         * of answerOptions and correctAnswer | see string source choice.xml and
         * images in drawables */
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

        // Get switchState (level of difficult) from Settings activity using boolean
        // Default state is Easy Mode
        SharedPreferences mPrefs = getSharedPreferences("saveLevel", MODE_PRIVATE);
        boolean switchState = mPrefs.getBoolean("value", false);

        /* Set limit of arrayList objects (6 or 10) depending on level of difficulty (switchState)
         * by removing some elements from arrayList (i.e if limit is 6, remove until count is not less
         * than limit) */
        int limit;
        if (!switchState)
            limit = 6;
         else
            limit = 10;

        Iterator<Object> iterator = arrayList.iterator();
        int count = 0;

        // Iterating through the list of integers
        while (iterator.hasNext()) {
            iterator.next();
            count++;
            // Check if limit is reached
            if (count > limit)
                iterator.remove();
        }
    }
}