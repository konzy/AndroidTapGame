package com.example.konzy.tapguessinggame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.Random;

// TODO: 3/18/2017 make alert for finished game
// TODO: 3/18/2017 pretty up ppt
// TODO: 3/18/2017 change onTouchEvent to onTapConfirmed or whatever

public class MainActivity extends AppCompatActivity {

    private Random mRandom;
    private double longestDistancePossible;
    private double secretLocationX;
    private double secretLocationY;
    private int x;
    private int y;
    private int guesses;

    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTextView = (TextView) findViewById(R.id.statusTextView);

        mRandom = new Random();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        y = metrics.heightPixels;
        x = metrics.widthPixels;

        longestDistancePossible = distanceFormula(x, y);

        createSecretLocation();

    }

    private double distanceFormula(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    private void createSecretLocation() {
        guesses = 0;
        secretLocationX = mRandom.nextDouble() * x;
        secretLocationY = mRandom.nextDouble() * y;
    }

    private String getTemperature(double distance){
        double percentage = distance / longestDistancePossible;
        if (percentage > 0.8) {
            return "Very Cold";
        } else if (percentage > 0.6) {
            return "Cold";
        } else if (percentage > 0.4) {
            return "Lukewarm";
        } else if (percentage > 0.2) {
            return "Warm";
        } else if (percentage > 0.1) {
            return "Hot";
        } else {
            createSecretLocation();
            return "You found it!";
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        guesses++;
        double distance = distanceFormula(event.getX() - secretLocationX, event.getY() - secretLocationY);
        statusTextView.setText(getTemperature(distance) + " Guessed " + guesses + " times");
        return false;
    }
}
