package com.example.basicactivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AudioChange extends AppCompatActivity {
    private int currentSound = R.raw.sound2;

    public void mainMenu(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void onClickSound1(View v) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.sound1);
        mp.start();
    }

    public void onClickSound2(View v) {
        MediaPlayer mp = MediaPlayer.create(this, currentSound);
        mp.start();
    }


    public void onClickChange(View v) {
        if (currentSound == R.raw.sound2) {
            currentSound = R.raw.sound3;
        }
        else {
            currentSound = R.raw.sound2;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_audio_change);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}