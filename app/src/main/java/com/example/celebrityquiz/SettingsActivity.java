package com.example.celebrityquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.CompoundButton;

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

        final Switch switchValue = findViewById(R.id.switchLevel);

        // Interface instance to access and modify switchState / level of difficulty | default mode; false
        SharedPreferences sharedPreferences = getSharedPreferences("saveLevel", MODE_PRIVATE);
        switchValue.setChecked(sharedPreferences.getBoolean("value", false));

        // Use switch to change level of difficulty, once clicked
        switchValue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences(
                            "saveLevel", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    switchValue.setChecked(true); // Set saved value (hard) forever
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences(
                            "saveLevel", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    switchValue.setChecked(false); // Set saved value (easy) forever
                }
            }
        });
    }
}
