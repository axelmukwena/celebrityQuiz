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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    // Declare lists
    List<Quiz> list = new ArrayList<>();
    String jsonUrl = "https://api.jsonbin.io/b/5e8a16b941019a79b61e18eb/4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Get and load data, adapter and set data to recyclerView |
        // See function for more details
        loadData();

        QuizAdapter quizAdapter = new QuizAdapter(list, this);
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

    // Load data to from internet
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
                    String string = response.body().string();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Quiz>>(){}.getType();
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
                            //levelMode();
                            QuizAdapter quizAdapter = new QuizAdapter(list, MainActivity.this);
                            RecyclerView recyclerView = findViewById(R.id.recyclerView);
                            recyclerView.setAdapter(quizAdapter);
                        }
                    });
                }
            }
        });
    }

    // Get switchState (level of difficult) from Settings activity using boolean
    public void levelMode() {

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

        Iterator<Quiz> iterator = list.iterator();
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