package com.example.advancedguidelines;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int currentImage = R.drawable.hi;
    VibrationManager vib = new VibrationManager();
    int vib_freq = 0;
    final int vib_touch = 10;

    public void onClick(View v) {
        ImageView view = findViewById(R.id.imageView);
        if (currentImage == R.drawable.hi) {
            view.setImageResource(R.drawable.bye);
            currentImage = R.drawable.bye;
        }
        else {
            view.setImageResource(R.drawable.hi);
            currentImage = R.drawable.hi;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}