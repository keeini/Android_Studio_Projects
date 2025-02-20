package com.example.basicactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ColorChange extends AppCompatActivity {


    public void onClick(View v) {
        // Changes the text of the TextView
        TextView tv = findViewById(R.id.textViewColor);
        tv.setText("I am cool now!");

        // Changes the color of the TextView and the background of the TextView
        tv.setBackgroundColor(Color.GREEN);
        tv.setTextColor(Color.RED);

        // Changes the color of the Button and the text of the Button
        Button button = findViewById(R.id.buttonColor);
        button.setBackgroundColor(Color.RED);
        button.setTextColor(Color.GREEN);
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
        setContentView(R.layout.activity_color_change);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}