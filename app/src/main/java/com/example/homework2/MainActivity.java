package com.example.homework2;

/*
//Homework 2
//Khaled Mohamed Ali and Joseph Mauney
 */

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView currentWeight, numOfDrinks, BACLevel, BACStatus;
    Button weightSet, viewDrinks, addDrinks, reset;
    ArrayList <Drink> drinks;
    double BACl = 0;
    private static final DecimalFormat df = new DecimalFormat("0.000");


    public static final String DRINK = "DRINK";

    Profile profile = new Profile();

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result != null && result.getResultCode() == RESULT_OK){
                if(result.getData() != null && result.getData().getSerializableExtra(MainActivity3.KEY_NAME) != null){
                    drinks.add((Drink)result.getData().getSerializableExtra(MainActivity3.KEY_NAME));
                    numOfDrinks.setText(String.valueOf(drinks.size()));
                    double hol = findBAC(drinks, profile);
                    BACLevel.setText(df.format(hol));
                } else if (result.getData() != null && result.getData().getSerializableExtra(View_Drink.RETURNARRAY) != null){
                    drinks = (ArrayList<Drink>) result.getData().getSerializableExtra(View_Drink.RETURNARRAY);
                    numOfDrinks.setText(String.valueOf(drinks.size()));
                    double hol = findBAC(drinks, profile);
                    BACLevel.setText(df.format(hol));
                } else if (result.getData() != null && result.getData().getSerializableExtra(MainActivity2.USER_KEY) != null){
                    profile = (Profile) result.getData().getSerializableExtra(MainActivity2.USER_KEY);
                    currentWeight.setText(profile.weight + "lbs ("+ profile.gender + ")");
                    drinks.clear();
                    BACLevel.setText("0.000");
                    BACStatus.setText("You're safe.");
                    BACStatus.setBackgroundColor(getResources().getColor(R.color.safe));
                    numOfDrinks.setText("0");
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("BAC Calculator");

        currentWeight = findViewById(R.id.currentWeight);
        drinks = new ArrayList<>();
        numOfDrinks = findViewById(R.id.numOfDrinks);
        BACLevel= findViewById(R.id.BACLevel);
        BACStatus = findViewById(R.id.BACStatus);
        addDrinks = findViewById(R.id.addDrinks);

        //Button for setting Weight
        findViewById(R.id.buttonWeightSet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startForResult.launch(intent);
            }
        });

        //Button for adding Drinks
        findViewById(R.id.addDrinks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profile.weight == 0){
                    Toast.makeText(MainActivity.this, "No profile yet", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                    startForResult.launch(intent);
                }
            }
        });

        //Button for viewing Drinks
        findViewById(R.id.viewDrinks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drinks.isEmpty()){
                    Toast.makeText(MainActivity.this, "No drinks yet", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, View_Drink.class);
                    intent.putExtra(DRINK, drinks);
                    startForResult.launch(intent);
                }
            }
        });

        //Reset Button
        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drinks.clear();
                profile = new Profile();
                currentWeight.setText("N/A");
                BACLevel.setText("0.000");
                BACStatus.setText("You're safe.");
                BACStatus.setBackgroundColor(getResources().getColor(R.color.safe));
                numOfDrinks.setText("0");
                addDrinks.setEnabled(true);
            }
        });

    }

    public double findBAC(ArrayList<Drink> drinks, Profile profile){
        Profile profiles = profile;
        double r;
        if(profiles.isGender() == "Male"){
            r = 0.73;
        } else {
            r = 0.66;
        }
        BACl = 0;
        for (Drink drunks:drinks) {
            BACl = BACl + (((drunks.getSize1() * drunks.getAlcoholPercentage()) / 100) * 5.14) / (profiles.getWeight() * r);
        }

        if(BACl <= 0.08){
            BACStatus.setText("You're safe.");
            BACStatus.setBackgroundColor(getResources().getColor(R.color.safe));
            addDrinks.setEnabled(true);
        } else if (BACl > 0.08 && BACl <= 0.2){
            BACStatus.setText("Be careful.");
            BACStatus.setBackgroundColor(getResources().getColor(R.color.careful));
            addDrinks.setEnabled(true);
        } else if (BACl > 0.2){
            BACStatus.setText("Over the limit!");
            BACStatus.setBackgroundColor(getResources().getColor(R.color.overLimit));
            Toast.makeText(this, "No more drinks for you.", Toast.LENGTH_SHORT).show();
            addDrinks.setEnabled(false);
        }
        return BACl;


    }
}