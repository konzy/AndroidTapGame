package com.example.konzy.tapguessinggame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Random mRandom;
    private double longestDistancePossible;
    private double secretLocationX;
    private double secretLocationY;
    private int x;
    private int y;
    private int guesses;

    private TextView statusTextView;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        statusTextView = (TextView) findViewById(R.id.statusTextView);
        view = findViewById(R.id.view);

        mRandom = new Random();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        x = metrics.widthPixels;
        y = metrics.heightPixels;


        longestDistancePossible = distanceFormula(x, y);

        createSecretLocation();

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    guesses++;
                    double distance = distanceFormula(event.getX() - secretLocationX, event.getY() - secretLocationY);
                    statusTextView.setText(getTemperature(distance) + " Guessed " + guesses + " times");
                }
                return true;
            }
        });
    }

    private double distanceFormula(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    private void createSecretLocation() {
        guesses = 0;
        statusTextView.setText("");
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
            Alert();
            return "Found it, ";
        }
    }

    private void Alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You found the location in " + guesses + " guesses!");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        createSecretLocation();
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
