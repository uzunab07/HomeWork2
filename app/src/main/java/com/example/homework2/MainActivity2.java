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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    EditText weightNumber;
    RadioGroup gender;
    RadioButton female, male;
    Button weightCancel, weightSetFinish;
    String genderType, value = null;
    int weight;

    final static public String USER_KEY = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("Set Weight/Gender");

        weightNumber = findViewById(R.id.weightNumber);
        gender = findViewById(R.id.gender);
        female = findViewById(R.id.female);
        male = findViewById(R.id.male);
        weightCancel = findViewById(R.id.weightCancel);
        weightSetFinish = findViewById(R.id.weightSetFinish);

        //Initialized values
        female.setChecked(true);
        genderType = "Female";

        findViewById(R.id.weightCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.female){
                    genderType = "Female";
                } else if (i == R.id.male) {
                    genderType = "Male";
                }
            }
            });
        findViewById(R.id.weightSetFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = weightNumber.getText().toString();
                if(value.isEmpty()){
                    Toast.makeText(MainActivity2.this, "Insert a weight to Continue", Toast.LENGTH_SHORT).show();
                } else {
                    weight = Integer.valueOf(value);

                    Intent intent = new Intent();
                    intent.putExtra(USER_KEY, new Profile(weight, genderType));
                    setResult(RESULT_OK, intent);
                    finish();
                }
                }
        });
    }
}