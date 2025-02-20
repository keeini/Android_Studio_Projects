package com.example.allergygraph;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    TextToSpeech tts;
    SimpleFingerGestures_Mod sfg = new SimpleFingerGestures_Mod();

    // Define area colors for category 1
    int categoryOne_red = 0;
    int categoryOne_green = 0;
    int categoryOne_blue = 254;
    // Define area colors for category 2
    int categoryTwo_red = 254;
    int categoryTwo_green = 165;
    int categoryTwo_blue = 0;

    // Define area colors for bar 1
    int barOne_red = 0;
    int barOne_green = 32;
    int barOne_blue = 96;

    // Define area colors for bar 2
    int barTwo_red = 197;
    int barTwo_green = 90;
    int barTwo_blue = 17;

    // Variables to track tts control
    boolean hasSpoken = false;


    // Variables to track finger indexes
    int f0_index = -1;
    int f1_index = -1;
    int f2_index = -1;
    int f3_index = -1;
    int f4_index = -1;


    VibrationManager vib = new VibrationManager();
    int vib_freq = 0;
    final int vib_freq_category_one = 10;
    final int vib_freq_category_two = 25;


    int screen_width = Resources.getSystem().getDisplayMetrics().widthPixels;
    int screen_height = Resources.getSystem().getDisplayMetrics().heightPixels;

    private SoundPool soundPool;
    private int bike_bell_id;
    private int bike_bell_stream_id;
    private Boolean spatial_audio_activated = false;
    private int empty_sound_id;
    private int empty_sound_stream_id;
    int loop = -1;
    int bells_loop = 0;

    private int healing_sound_id;
    private int healing_sound_stream_id;
    private Boolean healing_sound_is_playing = false;
    private boolean bike_bell_sound_is_playing = false;
    float pitch_threshold = (float) 0.01;
    int priority;
    int healing_loop = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        // Set the graph to be displayed on running the app
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.allergy_bar);

        // Hide the navigation bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        // Locks the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        /**************************************************************************************
         * TEXT TO SPEECH (TTS) CONFIGURATION
         **************************************************************************************/
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
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
//                grtv.setText("You swiped up with " + fingers + " finger(s).");
                if (fingers == 1) {
                    //Write your code here for swiping UP with one finger
                } else if (fingers == 2) {
                    // Write your code here for swiping UP with 2 fingers
                } else if (fingers == 3) {
                    // Write your code here for swiping UP with 3 fingers
                } else if (fingers == 4) {
                    // Write your code here for swiping UP with 4 fingers
                    // Stop and CLOSE all sounds and vibrations
                    /*soundPool.stop(empty_sound_stream_id);
                    empty_sound_is_playing = false;
                    soundPool.unload(empty_sound_id);
                     */
                    vib.stop();

                    // Close the FileWriter


                } else if (fingers == 5) {
                    // Write your code here for swiping UP with 4 fingers
                }
                return false;
            }

            @Override
            public boolean onSwipeDown(int fingers, long gestureDuration, double gestureDistance) {
//                grtv.setText("You swiped down with " + fingers + " finger(s).");
                if (fingers == 1) {
                    //Write your code here for swiping DOWN with one finger
                } else if (fingers == 2) {
                    // Write your code here for swiping DOWN with 2 fingers
                } else if (fingers == 3) {
                    // Write your code here for swiping DOWN with 3 fingers
                } else if (fingers == 4) {
                    // Write your code here for swiping DOWN with 4 fingers
                    Toast.makeText(getApplicationContext(), "Swipe DOWN with 4 fingers", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onSwipeLeft(int fingers, long gestureDuration, double gestureDistance) {
//                grtv.setText("You swiped left with " + fingers + " finger(s).");
                if (fingers == 1) {
                    //Write your code here for swiping LEFT with one finger
                } else if (fingers == 2) {
                    // Write your code here for swiping LEFT with 2 fingers
                } else if (fingers == 3) {
                    // Write your code here for swiping LEFT with 3 fingers
                } else if (fingers == 4) {
                    // Write your code here for swiping LEFT with 4 fingers
                }
                return false;
            }

            @Override
            public boolean onSwipeRight(int fingers, long gestureDuration, double gestureDistance) {
//                grtv.setText("You swiped right with " + fingers + " finger(s).");
                if (fingers == 1) {
                    //Write your code here for swiping RIGHT with one finger
                } else if (fingers == 2) {
                    // Write your code here for swiping RIGHT with 2 fingers
                } else if (fingers == 3) {
                    // Write your code here for swiping RIGHT with 3 fingers
                } else if (fingers == 4) {
                    // Write your code here for swiping RIGHT with 4 fingers
                }
                return false;
            }

            @Override
            public boolean onPinch(int fingers, long gestureDuration, double gestureDistance) {
                if (fingers == 2) {
                    // Write your code here for PINCHING with 2 fingers
                    tts.stop();
                } else if (fingers == 3) {
                    // Write your code here for PINCHING with 3 fingers
                } else if (fingers == 4) {

                }
                return false;
            }

            @Override
            public boolean onUnpinch(int fingers, long gestureDuration, double gestureDistance) {
//                grtv.setText("You unpinched " + fingers + " fingers.");
                if (fingers == 2) {
                    // Write your code here for PINCHING with 2 fingers
                } else if (fingers == 3) {
                    // Write your code here for PINCHING with 3 fingers
                } else if (fingers == 4) {
                    // Write your code here for PINCHING with 4 fingers
                }
                return false;
            }

            @Override
            public boolean onDoubleTap(int fingers) {
                // Write your code here for DOUBLE TAPPING

                /*
                // Get the pixel based on the IMAGE X and Y coordinates!
                int pixel1 = image.getPixel((int)imageX1, (int)imageY1);
                // Save the RGB values
                int pixel1_red = Color.red(pixel1);
                int pixel1_green = Color.green(pixel1);
                int pixel1_blue = Color.blue(pixel1);

                if (pixel_red == barOne_red && pixel_green == barOne_green && pixel_blue == barOne_blue) {
                    tts.speak("End of Allergy A", TextToSpeech.QUEUE_FLUSH, null);
                }
                 */
                //tts.speak("End of Allergy", TextToSpeech.QUEUE_FLUSH, null);

                return false;
            }
        });

        imageView.setOnTouchListener(sfg);
        imageView.setOnHoverListener(new HoverToTouchAdapter(sfg));

        TextView textView = findViewById(R.id.textView);

        monitor(imageView, textView);

        /*
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    Bitmap bitmap = imageView.getDrawingCache();
                    int pixel = bitmap.getPixel((int) motionEvent.getX(), (int) motionEvent.getY());

                    int r = Color.red(pixel);
                    int g = Color.green(pixel);
                    int b = Color.blue(pixel);

                    textView.setText("R: " + r + " G: " + g + " B: " + b);

                }
                return true;
            }
        });

         */

    }

    public void monitor(ImageView imageview, TextView textView) {
        final Handler handler = new Handler();

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        healing_sound_id = soundPool.load(this, R.raw.healing_sound, 1);
        bike_bell_id = soundPool.load(this, R.raw.bike_bell, 1);

        handler.post(new Runnable() {
            @Override
            public void run() {
                // Your constantly running code here:

                // get percentage of screen
                //  float X_screenPercent = (float) (sfg.X_coords[0]/screen_width);
                // float Y_screenPercent = (float) (sfg.Y_coords[0]/screen_height);


                // Finger status/location indicator
                // textView.setText("There are " + sfg.finger_count + " finger(s) on screen.\nTheir X coordinates are " + Arrays.toString(sfg.X_coords) + "\nTheir Y coordinates are " + Arrays.toString(sfg.Y_coords));
                //textView.setText("Their X percentages are " + X_screenPercent + "\nTheir Y percentages are " + Y_screenPercent);

                double viewWidth = imageview.getWidth();
                double viewHeight = imageview.getHeight();

                Bitmap image = ((BitmapDrawable) imageview.getDrawable()).getBitmap();

                double imageWidth = image.getWidth();
                double imageHeight = image.getHeight();

                if (sfg.finger_count < 1) {
                    f0_index = -1;
                    f1_index = -1;
                    f2_index = -1;
                    f3_index = -1;
                    f4_index = -1;

                    if (vib.isVibrating()) {
                        vib.stop();
                    }

                    textView.setText("");

                    hasSpoken = false;

                }

                if (sfg.finger_count == 1) {
                    f0_index = -1;
                    f1_index = -1;
                    for (int i = 0; i < sfg.X_coords.length; i++) {
                        if (sfg.X_coords[i] != 0) {
                            f0_index = i;
                            break;
                        }
                    }
                    // If the finger was in X = 0.0, which means that finger_index is still -1, then scan  the Y_coords array to try and find which finger is on screen.
                    for (int i = 0; i < sfg.Y_coords.length; i++) {
                        if (sfg.Y_coords[i] != 0) {
                            f0_index = i;
                            break;
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
                        } else if (imageX < 0) {
                            imageX = 0; // Account for negative X values (bezels!)
                        }
                        if (imageY >= imageHeight) {
                            imageY = imageHeight - 0.01; // Modify it by 1/100 of a pixel to meet the imageY < bitmap.height condition!
                        } else if (imageY < 0) {
                            imageY = 0; // Account for negative X values (bezels!)
                        }
                        // Get the pixel based on the IMAGE X and Y coordinates!
                        int pixel = image.getPixel((int) imageX, (int) imageY);
                        // Save the RGB values
                        int pixel_red = Color.red(pixel);
                        int pixel_green = Color.green(pixel);
                        int pixel_blue = Color.blue(pixel);
                        textView.setText("COLOR = " + pixel_red + ", " + pixel_green + "," + pixel_blue);


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


                        // get percentage of screen
                        float X_screenPercent = (float) (imageX / imageWidth);
                        float Y_screenPercent = (float) (imageY / imageHeight);

                        //textView.setText("Their X percentages are " + X_screenPercent + "\nTheir Y percentages are " + Y_screenPercent);

                        if (X_screenPercent > 0.15 && X_screenPercent < 0.9 && Y_screenPercent < 0.04 && Y_screenPercent > 0.01) {
                            if (!hasSpoken) {
                                tts.speak("Title: Allergy A and Allergy B in Three Seasons", TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if (X_screenPercent > 0 && X_screenPercent < 0.03 && Y_screenPercent < 0.9 && Y_screenPercent > 0.1) {
                            if (!hasSpoken) {
                                tts.speak("y-axis title: Allergies", TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if (X_screenPercent > 0.07 && X_screenPercent < 0.98 && Y_screenPercent < 1 && Y_screenPercent > 0.96) {
                            if (!hasSpoken) {
                                tts.speak("x-axis title: Seasons", TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if (X_screenPercent > 0.11 && X_screenPercent < 0.25 && Y_screenPercent < 0.95 && Y_screenPercent > 0.92) {
                            if (!hasSpoken) {
                                tts.speak("Summer", TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if (X_screenPercent > 0.45 && X_screenPercent < 0.60 && Y_screenPercent < 0.95 && Y_screenPercent > 0.92) {
                            if (!hasSpoken) {
                                tts.speak("Spring", TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if (X_screenPercent > 0.80 && X_screenPercent < 0.95 && Y_screenPercent < 0.95 && Y_screenPercent > 0.92) {
                            if (!hasSpoken) {
                                tts.speak("Fall", TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if (X_screenPercent > 0.07 && X_screenPercent < 0.98 && Y_screenPercent < 0.91 && Y_screenPercent > 0.90) {
                            if (!hasSpoken) {
                                tts.speak("0", TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if (X_screenPercent > 0.07 && X_screenPercent < 0.98 && Y_screenPercent < 0.82 && Y_screenPercent > 0.79) {
                            if (!hasSpoken) {
                                tts.speak("10", TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if (X_screenPercent > 0.07 && X_screenPercent < 0.98 && Y_screenPercent < 0.70 && Y_screenPercent > 0.68) {
                            if (!hasSpoken) {
                                tts.speak("20", TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if (X_screenPercent > 0.07 && X_screenPercent < 0.98 && Y_screenPercent < 0.60 && Y_screenPercent > 0.57) {
                            if (!hasSpoken) {
                                tts.speak("30", TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if (X_screenPercent > 0.07 && X_screenPercent < 0.98 && Y_screenPercent < 0.48 && Y_screenPercent > 0.46) {
                            if (!hasSpoken) {
                                tts.speak("40", TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if (X_screenPercent > 0.07 && X_screenPercent < 0.98 && Y_screenPercent < 0.38 && Y_screenPercent > 0.36) {
                            if (!hasSpoken) {
                                tts.speak("50", TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if (X_screenPercent > 0.07 && X_screenPercent < 0.98 && Y_screenPercent < 0.27 && Y_screenPercent > 0.25) {
                            if (!hasSpoken) {
                                tts.speak("60", TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if (X_screenPercent > 0.07 && X_screenPercent < 0.98 && Y_screenPercent < 0.17 && Y_screenPercent > 0.14) {
                            if (!hasSpoken) {
                                tts.speak("70", TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else if (X_screenPercent > 0.07 && X_screenPercent < 0.98 && Y_screenPercent < 0.05 && Y_screenPercent > 0.03) {
                            if (!hasSpoken) {
                                tts.speak("80", TextToSpeech.QUEUE_FLUSH, null);
                                hasSpoken = true;
                            }
                        } else {
                            hasSpoken = false;
                        }

                        if (pixel_red == categoryOne_red && pixel_green == categoryOne_green && pixel_blue == categoryOne_blue) {

                            if (!healing_sound_is_playing) {
                                if (!spatial_audio_activated) {
                                    healing_sound_stream_id = soundPool.play(healing_sound_id, (float) 1.0, (float) 1.0, priority, healing_loop, (float) 1.0);
                                } else
                                    healing_sound_stream_id = soundPool.play(healing_sound_id, leftVolume, rightVolume, priority, healing_loop, pitch);
                            }
                            healing_sound_is_playing = true;


                            // Start vibrating
                            if (vib_freq != vib_freq_category_one) {
                                tts.speak("Allergy A", TextToSpeech.QUEUE_ADD, null);
                                vib.stop();
                                vib_freq = vib_freq_category_one;
                            }
                            if (vib != null && !vib.isVibrating()) {
                                vib.vibrateAtFrequencyForever(vib_freq);
                            }

                        } else if (pixel_red == barOne_red && pixel_green == barOne_green && pixel_blue == barOne_blue) {
                            soundPool.stop(healing_sound_stream_id);
                            healing_sound_is_playing = false;


                            if (!vib.isVibrating()) {
                                vib.vibrateForever();
                            }


                        } else if (pixel_red == categoryTwo_red && pixel_green == categoryTwo_green && pixel_blue == categoryTwo_blue) {

                            soundPool.stop(healing_sound_stream_id);
                            healing_sound_is_playing = false;

                            if (!bike_bell_sound_is_playing) {
                                if (!spatial_audio_activated) {
                                    bike_bell_stream_id = soundPool.play(bike_bell_id, (float) 1.0, (float) 1.0, priority, bells_loop, (float) 1.0);
                                } else {
                                    bike_bell_stream_id = soundPool.play(bike_bell_id, leftVolume, rightVolume, priority, bells_loop, pitch);
                                }
                                bike_bell_sound_is_playing = true;
                            }

                            if (spatial_audio_activated) {
                                soundPool.setLoop(bike_bell_stream_id, bells_loop);
                                soundPool.setVolume(bike_bell_stream_id, leftVolume, rightVolume);
                                soundPool.setRate(bike_bell_stream_id, pitch);
                            }


                            if (vib_freq != vib_freq_category_two) {
                                tts.speak("Allergy B", TextToSpeech.QUEUE_FLUSH, null);
                                vib.stop();
                                vib_freq = vib_freq_category_two;
                            }
                            if (!vib.isVibrating()) {
                                vib.vibrateAtFrequencyForever(vib_freq);
                            }


                        } else if (pixel_red == barTwo_red && pixel_green == barTwo_green && pixel_blue == barTwo_blue) {

                            soundPool.stop(healing_sound_stream_id);
                            healing_sound_is_playing = false;

                            if (!bike_bell_sound_is_playing) {
                                if (!spatial_audio_activated) {
                                    bike_bell_stream_id = soundPool.play(bike_bell_id, (float) 1.0, (float) 1.0, priority, bells_loop, (float) 1.0);
                                    bike_bell_sound_is_playing = true;
                                }
                            } else {

                                vib.stop();
                                vib_freq = 0; // Don't delete this line, will mess with vibration and TTS
                                //soundPool.stop(healing_sound_stream_id);
                                //healing_sound_is_playing = false;
                            }

                            // Keep everything above these two }
                        }
                    }


                    handler.postDelayed(this, 0);

                }
            }
        });
            }
        }







