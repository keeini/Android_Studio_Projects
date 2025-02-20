package com.example.basicactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class axisChange extends AppCompatActivity {

    // Establishes a float array with two elements to store the x and y coordinates
    private float[] touchDownXY = new float[2];



    // Touch listener will store the touch x and y coordinates
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getActionMasked() == MotionEvent.ACTION_MOVE || event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                touchDownXY[0] = event.getX();
                touchDownXY[1] = event.getY();
            }

            float x = touchDownXY[0];
            float y = touchDownXY[1];
            ((TextView)findViewById(R.id.textView)).setText("Coordinates, X: " + x + " Y: " + y);

            return true;
        }
    };

    /*
    // Click Listener will display the touch x and y coordinates in a text view that will constantly be updated
    // This is off a click
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            float x = touchDownXY[0];
            float y = touchDownXY[1];

            ((TextView)findViewById(R.id.textView)).setText("Coordinates, X: " + x + " Y: " + y);
        }
    };

     */


    public void mainMenu(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_axis_change);

        // Sets the touch and click listeners to the main view, which is a Constraint Layout.
        View v = findViewById(R.id.main);
        v.setOnTouchListener(touchListener);

        // v.setOnClickListener(clickListener);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}