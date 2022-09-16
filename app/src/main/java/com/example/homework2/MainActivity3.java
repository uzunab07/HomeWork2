package com.example.homework2;

/*
//Homework 2
//Khaled Mohamed Ali and Joseph Mauney
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MainActivity3 extends AppCompatActivity {

    Button cancelDrink, buttonAddDrink;
    SeekBar seekBar;
    RadioGroup drinkSizes;
    RadioButton radioTwelveOz, radioOneOz, radioFiveOz;
    TextView seekProgress;

    double drinkSize;
    double alcoholPercentage;
    Calendar date;

    public static final String KEY_NAME = "DRINK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        setTitle("Add Drink");

        cancelDrink = findViewById(R.id.cancelDrink);
        buttonAddDrink = findViewById(R.id.buttonAddDrink);
        seekBar = findViewById(R.id.seekBar);
        drinkSizes = findViewById(R.id.drinkSizes);
        radioTwelveOz = findViewById(R.id.radioTwelveOz);
        radioOneOz = findViewById(R.id.radioOneOz);
        radioFiveOz = findViewById(R.id.radioFiveOz);
        seekProgress = findViewById(R.id.seekProgress);

        //Setting defaults
        radioOneOz.setChecked(true);
        seekBar.setProgress(12);
        seekProgress.setText(String.valueOf(seekBar.getProgress()));
        alcoholPercentage = seekBar.getProgress();
        drinkSize = 1;

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                seekProgress.setText(String.valueOf(progress));
                alcoholPercentage = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        drinkSizes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedRadioButton) {
                if(checkedRadioButton == R.id.radioFiveOz){
                    drinkSize = 5;
                } else if (checkedRadioButton == R.id.radioOneOz){
                    drinkSize = 1;
                } else if (checkedRadioButton == R.id.radioTwelveOz){
                    drinkSize = 12;
                }
            }
        });

        cancelDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        buttonAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                date = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
                String Date1 = simpleDateFormat.format(date.getTime()).toString();
                Drink drink = new Drink(drinkSize, alcoholPercentage, Date1);


                Intent intent = new Intent();
                intent.putExtra(KEY_NAME, drink);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }
}