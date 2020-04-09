package com.example.celebrityquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        // Create back button and start mainActivity once clicked
        ImageButton backArrow = findViewById(R.id.backArrowSettings);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });

        // Interface instance to access and modify level of difficulty | default mode; firstLevel
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        int level = sharedPreferences.getInt("saveLevel", 1);

        RadioButton radioButtonOne = findViewById(R.id.settingsRadioButton1);
        RadioButton radioButtonTwo = findViewById(R.id.settingsRadioButton2);
        RadioButton radioButtonThree = findViewById(R.id.settingsRadioButton3);

        if (level == 1) {
            radioButtonOne.setChecked(true);
            radioButtonTwo.setChecked(false);
            radioButtonThree.setChecked(false);
        } else if (level == 2) {
            radioButtonOne.setChecked(false);
            radioButtonTwo.setChecked(true);
            radioButtonThree.setChecked(false);
        } else {
            radioButtonOne.setChecked(false);
            radioButtonTwo.setChecked(false);
            radioButtonThree.setChecked(true);
        }

        RadioGroup radioGroup = findViewById(R.id.settingsRadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Change levels based on radioButton Selected
                int level = 1;
                if (checkedId == R.id.settingsRadioButton2) level = 2;
                else if (checkedId == R.id.settingsRadioButton3) level = 3;

                editor.putInt("saveLevel", level); // Update level interface
                editor.apply();
            }
        });
    }
}
