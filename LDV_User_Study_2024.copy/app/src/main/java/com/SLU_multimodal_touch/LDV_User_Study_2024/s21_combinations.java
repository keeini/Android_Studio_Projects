package com.SLU_multimodal_touch.LDV_User_Study_2024;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class s21_combinations extends AppCompatActivity {

    /**************************************************************************************
     * DISABLE THE BACK BUTTON
     **************************************************************************************/
    @Override
    public void onBackPressed() {
        // Do nothing
    }

    /**************************************************************************************
     * Global Variables
     **************************************************************************************/
    // TTS and the multi-finger gesture tracker
    TextToSpeech tts; // Initiate the TextToSpeech object
    SimpleFingerGestures_Mod sfg = new SimpleFingerGestures_Mod(); //SimpleFingerGestures_Mod object

    // To get screen dimensions, just in case
    int screen_width = Resources.getSystem().getDisplayMetrics().widthPixels;
    int screen_height = Resources.getSystem().getDisplayMetrics().heightPixels;

    // Define area colors
    //2.5Hz white
    int scatter1_red = 254;
    int scatter1_green = 255;
    int scatter1_blue = 255;
    //10Hz slight white
    int scatter2_red = 253;
    int scatter2_green = 255;
    int scatter2_blue = 255;
    //Natural Hz slighter white
    int scatter3_red = 252;
    int scatter3_green = 255;
    int scatter3_blue = 255;

    // RED, SLIGHT RED, AND SLIGHTER RED TAKEN FROM xxx_trial1_scatterplots.py
    int red_red = 254;
    int red_green = 0;
    int red_blue = 0;
    //slight red
    int slight_red_red = 253;
    int slight_red_green = 0;
    int slight_red_blue = 0;
    //slighter red
    int slighter_red_red = 252;
    int slighter_red_green = 0;
    int slighter_red_blue = 0;

    // GREEN, SLIGHT GREEN, AND SLIGHTER GREEN TAKEN FROM xxx_trial2_scatterplots.py
    int green_red = 0;
    int green_green = 254;
    int green_blue = 0;
    //slight green
    int slight_green_red = 0;
    int slight_green_green = 253;
    int slight_green_blue = 0;
    //slighter green
    int slighter_green_red = 0;
    int slighter_green_green = 252;
    int slighter_green_blue = 0;

    // BLUE, SLIGHT BLUE, AND SLIGHTER BLUE TAKEN FROM xxx_trial3_scatterplots.py
    int blue_red = 0;
    int blue_green = 0;
    int blue_blue = 254;
    //slight blue
    int slight_blue_red = 0;
    int slight_blue_green = 0;
    int slight_blue_blue = 253;
    //slighter blue
    int slighter_blue_red = 0;
    int slighter_blue_green = 0;
    int slighter_blue_blue = 252;


    // counter variable to track current image in series
    int currentSeries = 1;


    // For Vibration Control
    VibrationManager vib = new VibrationManager();
    double vib_freq = 0;
    double vib_freq_two_five = 2.5; // Hz
    double vib_freq_ten = 10; // Hz
    double vib_freq_forever = 5; // Hz



    // To track WHICH finger is on screen
    int f0_index = -1;
    int f1_index = -1;
    int f2_index = -1;
    int f3_index = -1;
    int f4_index = -1;

    // For SoundPool control
    private SoundPool soundPool;
    private int empty_sound_id;
    private int empty_sound_stream_id;
    int priority;
    int loop = -1; // Loop forever
    private Boolean empty_sound_is_playing = false;
    private Boolean spatial_audio_activated = false;
    float pitch_threshold = (float) 0.01;

    // Define variables to track tts control for both fingers
    boolean hasSpoken = false;
    boolean insideSpoken = false;
    boolean outsideSpoken = false;

    // LONG PRESS CONTROL Variables
    CountDownTimer timer;
    final int time_delay = 2000; // milliseconds
    boolean timer_finished;
    double lastX; // Memory variable for X coordinate
    double lastY; // Memory variable for Y coordinate
    double movement_threshold = 68; // The point size is 68 pixels, hence we make this equal to that

    // Define variables to track Double Tap color control for two fingers. These will be given values later.
    int tapped_color_red;
    int tapped_color_green;
    int tapped_color_blue;
    double tapped_X_percent;
    double tapped_Y_percent;
    double tapped_X_location;
    double tapped_Y_location;

    // Graph constants
    final double minX_data_units = 0; // REAL-WORLD MIN value of your X axis (10000 kg, for example)
    final double maxX_data_units = 100; // REAL-WORLD MAX value of your X axis
    final double minY_data_units = 0; // REAL-WORLD MIN value of your Y axis
    final double maxY_data_units = 100; // REAL-WORLD MAX value of your Y axis
    final double graph_loc_minX = 0.11; // % of the screen where your graph STARTS its X axis
    final double graph_loc_maxX = 0.96; // % of the screen where your graph END its X axis
    final double graph_loc_minY = 0.83; // % of the screen where your graph STARTS its Y axis
    final double graph_loc_maxY = 0.11; // % of the screen where your graph ENDS its Y axis
    final String graph_title = "Graph Title: Box Office Revenue vs Movie Budget for Three Genres. "; // Title of the graph
    final String graph_x_axis_title = "X Axis Title: Movie Budget (Millions). "; // Title of the X axis
    final String graph_y_axis_title = "Y Axis Title: Box Office Revenue (Millions). "; // Title of the Y axis
    final int graph_x_axis_tick_origin = 0;
    final int graph_x_axis_tick_end = 100;
    final int graph_y_axis_tick_origin = 0;
    final int graph_y_axis_tick_end = 100;

    // For Logging Info
    int logger_delay = 100; //Logger Delay in ms
    String participant_number;
    String TAG = "s21_combinations";
    String file_name;
    FileWriter writer;
    Boolean writer_active = true;
    String TAG_trial = "off"; // Set the starting value for the TAG specific to the scatter plot trial being executed. This will help you keep track of the finger location on EACH of the different trials!


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Specify which layout this activity uses
        setContentView(R.layout.scatter);

        /**************************************************************************************
         * GET THE PARTICIPANT NUMBER AND FILE NAME
         **************************************************************************************/
        SharedPreferences sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        participant_number = sharedPref.getString("participant_number", "yeeted");
        file_name = participant_number + "_" + TAG + ".csv";

        /**************************************************************************************
         * SET THE BACKGROUND IMAGE FOR EACH ORIENTATION
         **************************************************************************************/

        // Get a reference to the ImageView, based on the phone orientation
        ImageView mv = findViewById(R.id.mt_view);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            mv.setImageResource(R.drawable.series_off_s21);
        } else {
            // In portrait
            mv.setImageResource(R.drawable.series_off_s21);
        }
        // Initiate the textView objects for the screen and for each finger
        TextView coord_view = findViewById(R.id.coordinate_view);

        /**************************************************************************************
         * LOCK THE ORIENTATION, HIDE NAVIGATION BUTTONS, AND START LOCK TASK MODE
         **************************************************************************************/
        // Lock the orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Hide the navigation buttons
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // Start Lock Task mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startLockTask();
        }

        /**************************************************************************************
         * TEXT TO SPEECH (TTS) CONFIGURATION
         **************************************************************************************/
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
        // Set the speed of the TTS
        tts.setSpeechRate(1);

        /**************************************************************************************
         * VIBRATION MANAGER
         **************************************************************************************/
        //Tell the VibrationManager that THIS is the activity we are referencing
        vib.setActivity(this); // "this" references this entire activity

        /**************************************************************************************
         * LONG PRESS CONTROL: TIMER CONFIGURATION
         **************************************************************************************/
        timer = new CountDownTimer(time_delay, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Simply update the global variable to false
                timer_finished = false;
            }
            @Override
            public void onFinish() {
                timer_finished = true;
            }

        }.start();


        /**************************************************************************************
         * MULTI-TOUCH LIBRARY
         **************************************************************************************/
        // Taken from: https://github.com/championswimmer/SimpleFingerGestures_Android_Library/blob/master/sample/src/main/java/in/championswimmer/sfg/sample/MainActivity.java
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        sfg.setDebug(false);
        sfg.setConsumeTouchEvents(true);

        sfg.setOnFingerGestureListener(new SimpleFingerGestures_Mod.OnFingerGestureListener() {
            @Override
            public boolean onSwipeUp(int fingers, long gestureDuration, double gestureDistance) {
                if (fingers == 1) {
                    //Write your code here for swiping UP with one finger
                }
                else if (fingers == 2) {
                    // Write your code here for swiping UP with 2 fingers
                }
                else if (fingers == 3) {
                    // Write your code here for swiping UP with 3 fingers
                }
                else if (fingers == 4) {
                    // Write your code here for swiping UP with 4 fingers
                    // Stop and CLOSE all sounds and vibrations
                    soundPool.stop(empty_sound_stream_id);
                    empty_sound_is_playing = false;
                    soundPool.unload(empty_sound_id);
                    vib.stop();

                    // Close the FileWriter
                    try {
                        if (writer_active) {
                            writer_active = false;
                            writer.close();
                        }
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    // Go back to GraphSelector
                    Intent back_to_graph_selector = new Intent(s21_combinations.this, GraphSelector.class);
                    startActivity(back_to_graph_selector);
                    finish(); // Close this activity when moving to a new one
                }
                else if (fingers == 5) {
                    // Write your code here for swiping UP with 4 fingers
                }
                return false;
            }

            @Override
            public boolean onSwipeDown(int fingers, long gestureDuration, double gestureDistance) {
                if (fingers == 1) {
                    //Write your code here for swiping DOWN with one finger
                }
                else if (fingers == 2) {
                    // Write your code here for swiping DOWN with 2 fingers
                }
                else if (fingers == 3) {
                    // Write your code here for swiping DOWN with 3 fingers
                }
                else if (fingers == 4) {
                    // Write your code here for swiping DOWN with 4 fingers
                }
                return false;
            }

            @Override
            public boolean onSwipeLeft(int fingers, long gestureDuration, double gestureDistance) {
                if (fingers == 1) {
                    //Write your code here for swiping LEFT with one finger
                }
                else if (fingers == 2) {
                    // Write your code here for swiping LEFT with 2 fingers
                    currentSeries++;
                    if (currentSeries > 4){
                        currentSeries = 1;
                    }

                    switch (currentSeries){
                        case 1:
                            mv.setImageResource(R.drawable.series_off_s21);
                            TAG_trial = "off";
                            coord_view.setText(TAG_trial);
                            break;
                        case 2:
                            mv.setImageResource(R.drawable.trial_1_s21);
                            TAG_trial = "1";
                            coord_view.setText(TAG_trial);
                            break;
                        case 3:
                            mv.setImageResource(R.drawable.trial_2_s21);
                            TAG_trial = "2";
                            coord_view.setText(TAG_trial);
                            break;
                        case 4:
                            mv.setImageResource(R.drawable.trial_3_s21);
                            TAG_trial = "3";
                            coord_view.setText(TAG_trial);
                            break;
                    }

                }
                else if (fingers == 3) {
                    // Write your code here for swiping LEFT with 3 fingers
                }
                else if (fingers == 4) {
                    // Write your code here for swiping LEFT with 4 fingers
                }
                return false;
            }

            @Override
            public boolean onSwipeRight(int fingers, long gestureDuration, double gestureDistance) {
                if (fingers == 1) {
                    //Write your code here for swiping RIGHT with one finger
                }
                else if (fingers == 2) {
                    // Write your code here for swiping RIGHT with 2 fingers
                }
                else if (fingers == 3) {
                    // Write your code here for swiping RIGHT with 3 fingers
                }
                else if (fingers == 4) {
                    // Write your code here for swiping RIGHT with 4 fingers
                }
                return false;
            }

            @Override
            public boolean onPinch(int fingers, long gestureDuration, double gestureDistance) {
                if (fingers == 2) {
                    // Write your code here for PINCHING with 2 fingers
                }
                else if (fingers == 3) {
                    // Write your code here for PINCHING with 3 fingers
                }
                else if (fingers == 4) {

                }
                return false;
            }

            @Override
            public boolean onUnpinch(int fingers, long gestureDuration, double gestureDistance) {
                if (fingers == 2) {
                    // Write your code here for PINCHING with 2 fingers
                }
                else if (fingers == 3) {
                    // Write your code here for PINCHING with 3 fingers
                }
                else if (fingers == 4) {
                    // Write your code here for PINCHING with 4 fingers
                }
                return false;
            }

            @Override
            public boolean onDoubleTap(int fingers) {
                // Write your code here for DOUBLE TAPPING
                return false;
            }

        });

        // Set the OnTouch (no TalkBack) and onHover (with TalkBack) listeners
        mv.setOnTouchListener(sfg);
        mv.setOnHoverListener(new HoverToTouchAdapter(sfg));

        // Initiate the textView objects for the screen and for each finger
        //TextView coord_view = findViewById(R.id.coordinate_view);
        TextView output_view = findViewById(R.id.output_view);

        // Now for the code to be executed constantly during the duration of this activity
        monitor(mv, coord_view, output_view);

        // Log the required information, by getting the number from the participant activity
        try {
            logger(file_name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**************************************************************************************
     * CONSTANTLY RUNNING CODE
     **************************************************************************************/
    public void monitor(ImageView mv, TextView coord_view, TextView output_view) {
        // Variables to be used in any part of this function
        final Handler handler = new Handler();

        /**************************************************************************************
         * SOUNDPOOL CONFIGURATION
         **************************************************************************************/
        // Give each of the raw files an ID to be used by the SoundPool later on
        soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC, 0);

        // Write your constantly running code here
        handler.post(new Runnable() {
            @Override
            public void run() {
                // Your constantly running code here:

                // Finger status/location indicator
                //coord_view.setText("There are " + sfg.finger_count + " finger(s) on screen.\nTheir X coordinates are " + Arrays.toString(sfg.X_coords) + "\nTheir Y coordinates are " + Arrays.toString(sfg.Y_coords));


                /**************************************************************************************
                 * IMAGE SCALING PART 1. Based on: https://stackoverflow.com/questions/67078759/imageview-get-color-of-touched-pixel
                 **************************************************************************************/
                // Part 2 is done in later sections of the code, depending on the amount of fingers on screen
                // Obtain the VIEW and IMAGE dimensions. This will be used later to obtain the color of a pixel each finger is touching
                // Get the VIEW width and height
                double viewWidth = mv.getWidth();
                double viewHeight = mv.getHeight();
                // Get the IMAGE info as a bitmap
                Bitmap image = ((BitmapDrawable)mv.getDrawable()).getBitmap();
                // Get the IMAGE width and height
                double imageWidth = image.getWidth();
                double imageHeight = image.getHeight();

                /**************************************************************************************
                 * ZERO FINGERS
                 **************************************************************************************/
                // Reset all finder indexes
                f0_index = -1;
                f1_index = -1;
                f2_index = -1;
                f3_index = -1;
                f4_index = -1;
                if (sfg.finger_count == 0){
                    // Stop any ongoing vibrations
                    if (vib.isVibrating()) {
                        vib.stop();
                    }

                    // Clean the output view
                    output_view.setText("");

                    // SoundPool
                    soundPool.stop(empty_sound_stream_id);
                    empty_sound_is_playing = false;

                    // LONG PRESS CONTROL. Reset variables
                    timer.cancel();
                    timer_finished = false;
                    lastX = 0;
                    lastY = 0;
                    tts.stop();
                    hasSpoken = false;
                    outsideSpoken = false;
                }

                /**************************************************************************************
                 * ONE FINGER
                 **************************************************************************************/
                if (sfg.finger_count == 1){

                    // Get the coordinates and pixel color the fingers are touching, considering the different scaling factors of the VIEW and the IMAGE itself.

                    //First check WHICH FINGER is on screen!
                    // Scan the X_coords array to try and find which finger is on screen.
                    if (f0_index == -1) {
                        for (int i = 0; i < sfg.X_coords.length; i++) {
                            if (sfg.X_coords[i] != 0) {
                                f0_index = i;
                                break;
                            }
                        }
                    }
                    // If the finger was in X = 0.0, which means that finger_index is still -1, then scan  the Y_coords array to try and find which finger is on screen.
                    if (f0_index == -1) {
                        for (int i = 0; i < sfg.Y_coords.length; i++) {
                            if (sfg.Y_coords[i] != 0) {
                                f0_index = i;
                                break;
                            }
                        }
                    }
                    // IF the finger was found, THEN continue with the rest of the code. Otherwise, do nothing. The finger is in EXACTLY (0.0 , 0.0).
                    if (f0_index != -1) {
                        // Get the VIEW X and Y coords for FINGER0
                        double viewX = sfg.X_coords[f0_index];
                        double viewY = sfg.Y_coords[f0_index];
                        // Rule of 3 to scale the pixel values
                        double imageX = (viewX * (imageWidth / viewWidth));
                        double imageY = (viewY * (imageHeight / viewHeight));
                        // Limit the X and Y values to the bitmap max dimensions to avoid IllegalArgumentException when providing the pixel coordinate
                        if (imageX >= imageWidth) {
                            imageX = imageWidth - 0.01; // Modify it by 1/100 of a pixel to meet the imageX < bitmap.width condition!
                        }
                        else if (imageX < 0) {
                            imageX = 0; // Account for negative X values (bezels!)
                        }
                        if (imageY >= imageHeight) {
                            imageY = imageHeight - 0.01; // Modify it by 1/100 of a pixel to meet the imageY < bitmap.height condition!
                        }
                        else if (imageY < 0) {
                            imageY = 0; // Account for negative X values (bezels!)
                        }
                        // Get the pixel based on the IMAGE X and Y coordinates!
                        int pixel = image.getPixel((int)imageX, (int)imageY);
                        // Save the RGB values
                        int pixel_red = Color.red(pixel);
                        int pixel_green = Color.green(pixel);
                        int pixel_blue = Color.blue(pixel);
                        //coord_view.setText("COLOR = " + pixel_red + ", " + pixel_green + "," + pixel_blue);

                        // For AUDIO CHANNEL proportional control based on X axis coordinate
                        float audio_x = (float) (imageX / imageWidth);
                        float leftVolume = (1 - audio_x);
                        float rightVolume = audio_x;

                        // For AUDIO PITCH proportional control based on Y axis coordinate
                        float audio_y = (float) (imageY / imageHeight);
                        float pitch = (float) (1 - audio_y); // <1 = lower pitch, >1 = higher pitch. We use 1 - audio_y because y = 0 is the TOP of the screen, not the BOTTOM of the screen, and we want lower pitches at the BOTTOM of the screen
                        // Keep the pitch above the tablet threshold, otherwise we get an IllegalArgumentException
                        if (pitch < pitch_threshold) {
                            pitch = pitch_threshold;
                        }

                        // Also save them in a global variable for double tap control to use in another function
                        tapped_color_red = pixel_red;
                        tapped_color_green = pixel_green;
                        tapped_color_blue = pixel_blue;

                        // Do the same for the screen locations
                        tapped_X_percent = (imageX/imageWidth);
                        tapped_Y_percent = (imageY/imageHeight);
                        // Round up and show on screen if needed for debugging
//                        coord_view.setText("X % = " + String.format("%.2f", tapped_X_percent) + "\nY % = " + String.format("%.2f", tapped_Y_percent));

                        // get the current graph numerical location on screen. Consider that the graph does not cover the entire screen, hence these scaling equations!
                        // Equation derivation can be found in notes of May 28, 2024
                        tapped_X_location = minX_data_units + (1 - ((graph_loc_maxX - tapped_X_percent)/(graph_loc_maxX - graph_loc_minX)))*(maxX_data_units - minX_data_units);
                        tapped_Y_location = minY_data_units + (1 - ((graph_loc_maxY - tapped_Y_percent)/(graph_loc_maxY - graph_loc_minY)))*(maxY_data_units - minY_data_units);

                        /**************************************************************************************
                         * ONE FINGER: TTS OUTPUTS INSIDE THE GRAPH
                         **************************************************************************************/
                        // Define their states if the fingers are INSIDE the graph!
                        if (tapped_X_percent >= graph_loc_minX && tapped_X_percent <= graph_loc_maxX &&
                            tapped_Y_percent >= graph_loc_maxY && tapped_Y_percent <= graph_loc_minY) {

                            // Reset tts control variable for outside the graph and silence TTS if coming from outside
                            if (outsideSpoken) {
                                outsideSpoken = false;
                                tts.stop();
                            }

                            // TTS output to notify user that we are inside the graph
                            if (!insideSpoken) {
//                                tts.speak("Chart area.", TextToSpeech.QUEUE_FLUSH, null);
                                insideSpoken = true;
                            }

                            // LONG PRESS CONTROL. If significant movement has happened, reset the timer. Otherwise, let it continue!
                            if ((Math.abs(viewX - lastX) > movement_threshold) || (Math.abs(viewY - lastY) > movement_threshold)) {
                                lastX = viewX;
                                lastY = viewY;
                                timer.cancel();
                                timer_finished = false;
                                timer.start();
                                hasSpoken = false;
                            }
                        }

                        /**************************************************************************************
                         * ONE FINGER: TTS OUTPUTS OUTSIDE THE GRAPH
                         **************************************************************************************/
                        else {
                            // Stop all inside reactions if outside the graph
                            vib.stop();
                            insideSpoken = false;
                            timer.cancel();

                            // Titles and labels to be read if in TOP OR BOTTOM of graph, OUTSIDE the graph area
                            // TITLE
                            if (tapped_X_percent > graph_loc_minX && tapped_X_percent < graph_loc_maxX && tapped_Y_percent < graph_loc_maxY) {
                                if (!outsideSpoken) {
                                    tts.speak(graph_title, TextToSpeech.QUEUE_FLUSH, null);
                                    outsideSpoken = true;
                                }
                            }
                            // X AXIS TITLE
                            else if (tapped_X_percent > graph_loc_minX && tapped_X_percent < graph_loc_maxX && tapped_Y_percent > graph_loc_minY) {
                                if (!outsideSpoken) {
                                    tts.speak(graph_x_axis_title + ". Minimum: " + graph_x_axis_tick_origin + ". Maximum: " + graph_x_axis_tick_end, TextToSpeech.QUEUE_FLUSH, null);
                                    outsideSpoken = true;
                                }
                            }
                            // Y AXIS TITLE
                            else if (tapped_X_percent < graph_loc_minX && tapped_Y_percent < graph_loc_minY && tapped_Y_percent > graph_loc_maxY) {
                                if (!outsideSpoken) {
                                    tts.speak(graph_y_axis_title + ". Minimum: " + graph_y_axis_tick_origin + ". Maximum: " + graph_y_axis_tick_end, TextToSpeech.QUEUE_FLUSH, null);
                                    outsideSpoken = true;
                                }
                            }
                            else {
                                // Not on any labels. Reset control variable.
                                outsideSpoken = false;
                            }
                        }

                        /**************************************************************************************
                         * ONE FINGER: COLOR REACTIONS INSIDE THE GRAPH
                         **************************************************************************************/
                        if(pixel_red == red_red && pixel_green == red_green && pixel_blue == red_blue) {
                            // Start vibrating
                            if (vib_freq != vib_freq_two_five) {
                                // Stop the previous vibration if it is different from the one we are supposed to do
                                vib.stop();
                                // Update the vibration frequency
                                vib_freq = vib_freq_two_five;
                                // This only happens ONCE when the vibration frequency CHANGES value, to avoid the motor having to STOPGOSTOPGOSTOPGOSTOPGO
                            }
                            if (!vib.isVibrating()) {
                                vib.vibrateAtFrequencyForever(vib_freq);
                                //vib.vibrateForever();
                            }

                            // LONG PRESS CONTROL. TTS output if long press detected
                            if (timer_finished && !hasSpoken) {
//                                tts.speak("Data point. X " + Math.round(tapped_X_location) + ", Y " + Math.round(tapped_Y_location), TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }

                        } else if(pixel_red == slight_red_red && pixel_green == slight_red_green && pixel_blue == slight_red_blue){
                            // Start vibrating
                            if (vib_freq != vib_freq_ten) {
                                // Stop the previous vibration if it is different from the one we are supposed to do
                                vib.stop();
                                // Update the vibration frequency
                                vib_freq = vib_freq_ten;
                                // This only happens ONCE when the vibration frequency CHANGES value, to avoid the motor having to STOPGOSTOPGOSTOPGOSTOPGO
                            }
                            if (!vib.isVibrating()) {
                                vib.vibrateAtFrequencyForever(vib_freq);
                                //vib.vibrateForever();
                            }
                            // LONG PRESS CONTROL. TTS output if long press detected
                            if (timer_finished && !hasSpoken) {
//                                tts.speak("Data point. X " + Math.round(tapped_X_location) + ", Y " + Math.round(tapped_Y_location), TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if(pixel_red == slight_green_red && pixel_green == slight_green_green && pixel_blue == slight_green_blue){
                            // Start vibrating
                            if (vib_freq != vib_freq_ten) {
                                // Stop the previous vibration if it is different from the one we are supposed to do
                                vib.stop();
                                // Update the vibration frequency
                                vib_freq = vib_freq_ten;
                                // This only happens ONCE when the vibration frequency CHANGES value, to avoid the motor having to STOPGOSTOPGOSTOPGOSTOPGO
                            }
                            if (!vib.isVibrating()) {
                                vib.vibrateAtFrequencyForever(vib_freq);
                                //vib.vibrateForever();
                            }
                            // LONG PRESS CONTROL. TTS output if long press detected
                            if (timer_finished && !hasSpoken) {
//                                tts.speak("Data point. X " + Math.round(tapped_X_location) + ", Y " + Math.round(tapped_Y_location), TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }

                        } else if(pixel_red == slighter_green_red && pixel_green == slighter_green_green && pixel_blue == slighter_green_blue) {
                            // Start vibrating
                            if (vib_freq != vib_freq_forever) {
                                // Stop the previous vibration if it is different from the one we are supposed to do
                                vib.stop();
                                // Update the vibration frequency
                                vib_freq = vib_freq_forever;
                                // This only happens ONCE when the vibration frequency CHANGES value, to avoid the motor having to STOPGOSTOPGOSTOPGOSTOPGO
                            }
                            if (!vib.isVibrating()) {
                                vib.vibrateForever();
                            }
                            // LONG PRESS CONTROL. TTS output if long press detected
                            if (timer_finished && !hasSpoken) {
//                                tts.speak("Data point. X " + Math.round(tapped_X_location) + ", Y " + Math.round(tapped_Y_location), TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if(pixel_red == blue_red && pixel_green == blue_green && pixel_blue == blue_blue) {
                            // Start vibrating
                            if (vib_freq != vib_freq_two_five) {
                                // Stop the previous vibration if it is different from the one we are supposed to do
                                vib.stop();
                                // Update the vibration frequency
                                vib_freq = vib_freq_two_five;
                                // This only happens ONCE when the vibration frequency CHANGES value, to avoid the motor having to STOPGOSTOPGOSTOPGOSTOPGO
                            }
                            if (!vib.isVibrating()) {
                                vib.vibrateAtFrequencyForever(vib_freq);
                                //vib.vibrateForever();
                            }
                            // LONG PRESS CONTROL. TTS output if long press detected
                            if (timer_finished && !hasSpoken) {
//                                tts.speak("Data point. X " + Math.round(tapped_X_location) + ", Y " + Math.round(tapped_Y_location), TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if(pixel_red == slighter_blue_red && pixel_green == slighter_blue_green && pixel_blue == slighter_blue_blue) {
                            // Start vibrating
                            if (vib_freq != vib_freq_forever) {
                                // Stop the previous vibration if it is different from the one we are supposed to do
                                vib.stop();
                                // Update the vibration frequency
                                vib_freq = vib_freq_forever;
                                // This only happens ONCE when the vibration frequency CHANGES value, to avoid the motor having to STOPGOSTOPGOSTOPGOSTOPGO
                            }
                            if (!vib.isVibrating()) {
                                vib.vibrateForever();
                            }
                            // LONG PRESS CONTROL. TTS output if long press detected
                            if (timer_finished && !hasSpoken) {
//                                tts.speak("Data point. X " + Math.round(tapped_X_location) + ", Y " + Math.round(tapped_Y_location), TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else {
                            // Stop all other sounds or vibrations
                            vib.stop();
                            // LONG PRESS CONTROL. TTS output if long press detected
                            if (timer_finished && !hasSpoken) {
                                tts.speak("X " + Math.round(tapped_X_location) + ", Y " + Math.round(tapped_Y_location), TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        }
                    }
                }

                handler.postDelayed(this, 0);
            }
        });
    }

    /**************************************************************************************
     * LOGGER CODE
     **************************************************************************************/
    public void logger(String file_name) throws IOException {
        final Handler handler = new Handler();

        /**************************************************************************************
         * LOGGER CONFIGURATION
         **************************************************************************************/
        // Set the file paths, delay, and timestamp format
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS); //Point to the Documents folder
        File output_file_dir = new File(path + "/LDV_User_Study_2024/"); // Folder where CSV file will be saved inside the Documents folder
        File output_file = new File(output_file_dir, file_name); //Point to the output file
        String header = "time,x0,x1,x2,x3,x4,y0,y1,y2,y3,y4,trial\n"; // header for CSV file
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS"); // Set the date for later constant updating

        // Make the output directory in case it does not exist yet, and write the header for the file
        if (!output_file_dir.exists()) {
            output_file_dir.mkdir();
        }

        // Initiate the FileWriter
        writer = new FileWriter(output_file);

        // Write the header for the file
        try{
            writer.append(header);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                // Write your constantly running code here

                /**************************************************************************************
                 * OUTPUT FILE CONFIGURATION
                 **************************************************************************************/
                //Ask for permission to manage External Storage
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR2) {
                    if (ContextCompat.checkSelfPermission(s21_combinations.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(s21_combinations.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    }
                }
                else {
                    // Permission has already been granted
                    // Get the current time
                    String current_time = sdf.format(new Date());

                    // Write the time + finger coordinates to the file and remove the square brackets
                    String file_content = current_time + "," + Arrays.toString(sfg.X_coords).replace("[", "").replace("]", "")
                            + "," + Arrays.toString(sfg.Y_coords).replace("[", "").replace("]", "") + "," + TAG_trial + "\n";
                    try {
                        if (writer_active) { // This section will always be accessed UNTIL the gesture for leaving the activity is called!
                            writer.append(file_content);
                            writer.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                handler.postDelayed(this, logger_delay);
            }
        };
        // Start the Runnable
        handler.post(runnable);
    }

}