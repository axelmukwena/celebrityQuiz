package com.example.celebrityquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public int level;
    List<Quiz> firstList = new ArrayList<>();
    List<Quiz> secondList = new ArrayList<>();
    List<Quiz> thirdList = new ArrayList<>();
    QuizAdapter quizAdapter;
    String jsonUrl = "https://api.jsonbin.io/b/5e8f60bb172eb6438960f731";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Get and load data, adapter and set data to recyclerView
        // See function for more details
        loadData();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        level = sharedPreferences.getInt("saveLevel", 1);

        if (level == 1) {
            quizAdapter = new QuizAdapter(firstList);
        } else if (level == 2) {
            quizAdapter = new QuizAdapter(secondList);
        } else quizAdapter = new QuizAdapter(thirdList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(quizAdapter);
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
        switch (item.getItemId()) {
            case R.id.scoreButton:
                int score = quizAdapter.getScore();
                Intent i = new Intent(this, ScoreActivity.class);
                i.putExtra("score", score);
                this.startActivity(i);
                break;
            case R.id.settings:
                Intent j = new Intent(this, SettingsActivity.class);
                this.startActivity(j);
                break;
        }
        return (super.onOptionsItemSelected(item));
    }

    // Load data to from internet and save it to database
    public void loadData() {

        LitePal.initialize(this);
        LitePal.deleteAll(Quiz.class);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(jsonUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = Objects.requireNonNull(response.body()).string();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Quiz>>() {}.getType();
                    List<Quiz> list = gson.fromJson(string, type);

                    // Save to database
                    for (int i = 0; i < list.size(); i++) {
                        Quiz quiz = list.get(i);
                        quiz.save();
                    }

                    // Load data from database, set adapter and recyclerView layout
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<Quiz> list = LitePal.findAll(Quiz.class);
                            firstList = list.subList(0, 5);
                            secondList = list.subList(5, 10);
                            thirdList = list.subList(10, 15);

                            SharedPreferences sharedPreferences = PreferenceManager.
                                    getDefaultSharedPreferences(MainActivity.this);
                            level = sharedPreferences.getInt("saveLevel", 1);

                            if (level == 1) {
                                quizAdapter = new QuizAdapter(firstList);
                            } else if (level == 2) {
                                quizAdapter = new QuizAdapter(secondList);
                            } else quizAdapter = new QuizAdapter(thirdList);

                            RecyclerView recyclerView = findViewById(R.id.recyclerView);
                            recyclerView.setAdapter(quizAdapter);
                        }
                    });
                }
            }
        });
    }
}