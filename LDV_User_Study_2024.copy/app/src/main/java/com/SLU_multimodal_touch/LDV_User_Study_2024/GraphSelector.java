package com.SLU_multimodal_touch.LDV_User_Study_2024;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GraphSelector extends AppCompatActivity {

    /**************************************************************************************
     * GLOBAL VARIABLES
     **************************************************************************************/
    String TAG = "GraphSelector: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_selector);

        /**************************************************************************************
         * LOCK THE ORIENTATION, HIDE NAVIGATION BUTTONS
         **************************************************************************************/
        // Lock the orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Hide the navigation buttons
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        /**************************************************************************************
         * STOP LOCK TACK MODE (in case you are coming back from a graph)
         **************************************************************************************/
        // Stop Lock Task mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stopLockTask();
        }

        /**************************************************************************************
         * LINE CHART BUTTONS
         **************************************************************************************/
        Button s9_training_line_chart = findViewById(R.id.training_line);
        s9_training_line_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Move to the next activity
                Intent next_activity = new Intent(GraphSelector.this, training_line_s9.class);
                startActivity(next_activity);
                finish(); // Close this activity when moving to a new one
            }
        });

        Button s9_line_chart = findViewById(R.id.s9_line_chart);
        s9_line_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Move to the next activity
                Intent next_activity = new Intent(GraphSelector.this, s9_line_chart.class);
                startActivity(next_activity);
                finish(); // Close this activity when moving to a new one
            }
        });

        Button p11_line_chart = findViewById(R.id.p11_line_chart);
        p11_line_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the next activity
                Intent next_activity = new Intent(GraphSelector.this, p11_line_chart.class);
                startActivity(next_activity);
                finish(); // Close this activity when moving to a new one
            }
        });
        Button s21_line_chart = findViewById(R.id.s21_line_chart);
        s21_line_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the next activity
                Intent next_activity = new Intent(GraphSelector.this, s21_line_chart.class);
                startActivity(next_activity);
                finish(); // Close this activity when moving to a new one
            }
        });

        /**************************************************************************************
         * SCATTER PLOT BUTTONS
         **************************************************************************************/
        Button series_off = findViewById(R.id.series_off);
        series_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Move to the next activity
                Intent next_activity = new Intent(GraphSelector.this, series_off.class);
                startActivity(next_activity);
                finish(); // Close this activity when moving to a new one
            }
        });

        Button series_on = findViewById(R.id.series_on);
        series_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Move to the next activity
                Intent next_activity = new Intent(GraphSelector.this, series_on_p11.class);
                startActivity(next_activity);
                finish(); // Close this activity when moving to a new one
            }
        });

        Button s21_training_scatter = findViewById(R.id.training_scatter);
        s21_training_scatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Move to the next activity
                Intent next_activity = new Intent(GraphSelector.this, training_scatter_s21.class);
                startActivity(next_activity);
                finish(); // Close this activity when moving to a new one
            }
        });

        Button s9_combinations = findViewById(R.id.s9_combinations);
        s9_combinations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Move to the next activity
                Intent next_activity = new Intent(GraphSelector.this, s9_combinations.class);
                startActivity(next_activity);
                finish(); // Close this activity when moving to a new one
            }
        });
        Button p11_combinations = findViewById(R.id.p11_combinations);
        p11_combinations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the next activity
                Intent next_activity = new Intent(GraphSelector.this, p11_combinations.class);
                startActivity(next_activity);
                finish(); // Close this activity when moving to a new one
            }
        });
        Button series_on_s9 = findViewById(R.id.series_on_s9);
        series_on_s9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the next activity
                Intent next_activity = new Intent(GraphSelector.this, series_on_s9.class);
                startActivity(next_activity);
                finish(); // Close this activity when moving to a new one
            }
        });
        Button series_on_s21 = findViewById(R.id.series_on_s21);
        series_on_s21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the next activity
                Intent next_activity = new Intent(GraphSelector.this, series_on_s21.class);
                startActivity(next_activity);
                finish(); // Close this activity when moving to a new one
            }
        });
        Button s21_combinations = findViewById(R.id.s21_combinations);
        s21_combinations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the next activity
                Intent next_activity = new Intent(GraphSelector.this, s21_combinations.class);
                startActivity(next_activity);
                finish(); // Close this activity when moving to a new one
            }
        });


        /**************************************************************************************
         * EXIT BUTTON
         **************************************************************************************/
        Button exit_button = findViewById(R.id.exit);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the App
                finish();
            }
        });

    }
}