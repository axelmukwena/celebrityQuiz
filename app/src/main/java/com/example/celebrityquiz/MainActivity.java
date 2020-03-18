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


public class MainActivity extends AppCompatActivity {
    private ArrayList<Object> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        for (int i = 0; i < 10; i++)
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
        arrayList.add(new Quiz("Châu Âu gồm những nước nào?",
                new String[]{"Mỹ", "Pháp", "Anh", "Trung Quốc"}, 2));
        arrayList.add(new Quiz("|x|=1 thi x=?",
                new String[]{"x=1", "x=-1", "x=0"},  1));
        arrayList.add(new Quiz("2 * 3 = ?",
                new String[]{"48", "6", "28777"}, 1));
        arrayList.add(new Quiz("10 - 2 + |3|=?",
                new String[]{"172", "126", "32", "213", "43"}, 1));
    }
}