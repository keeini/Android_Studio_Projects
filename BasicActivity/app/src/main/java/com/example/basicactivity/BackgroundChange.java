package com.example.basicactivity;

import static androidx.core.view.ViewCompat.setBackground;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class BackgroundChange extends AppCompatActivity {
    private int currentBackground = R.drawable.sukuna;
    public void onClick(View v) {
        ConstraintLayout main = findViewById(R.id.main);

        // Make sure to change the value of the currentBackground variable to the new background
        if (currentBackground == R.drawable.sukuna) {
            main.setBackgroundResource(R.drawable.gojo);
            currentBackground = R.drawable.gojo;
        } else {
            main.setBackgroundResource(R.drawable.sukuna);
            currentBackground = R.drawable.sukuna;
        }
    }

    public void mainMenu(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_background_change);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}