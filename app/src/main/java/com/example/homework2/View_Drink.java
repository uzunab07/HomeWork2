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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


public class View_Drink extends AppCompatActivity {

    TextView textEditDrinkNum, textEditDrinkAlco, textEditDrinkSize, textEditDrinkDate;
    ImageButton buttonPrevDrink, buttonTrashDrink, buttonNextDrink;
    int iter = 0;
    ArrayList<Drink> drinks;
    Drink drink;

    Button buttonViewClose;

    public static final String RETURNARRAY = "RETURNLIST";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drink);

        setTitle("View Drinks");

        //Getting intent
        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.DRINK)){
            drinks = (ArrayList<Drink>)getIntent().getSerializableExtra(MainActivity.DRINK);
        }

        textEditDrinkAlco = findViewById(R.id.textEditDrinkAlco);
        textEditDrinkSize = findViewById(R.id.textEditDrinkSize);
        textEditDrinkDate = findViewById(R.id.textEditDrinkDate);
        textEditDrinkNum = findViewById(R.id.textEditDrinkNum);

        buttonNextDrink = findViewById(R.id.buttonNextDrink);
        buttonTrashDrink = findViewById(R.id.buttonTrashDrink);
        buttonPrevDrink = findViewById(R.id.buttonPrevDrink);
        buttonViewClose = findViewById(R.id.buttonViewClose);

        drink = drinks.get(iter);
        Log.d("demo", "onCreate:" + drink.getSize1() + " ");
        //textEditDrinkNum.setText("1");
        //textEditDrinkSize.setText("5");
        //textEditDrinkAlco.setText("30%");
        //textEditDrinkDate.setText("55-555");


        textEditDrinkNum.setText((iter+1) + " of " +drinks.size());
        textEditDrinkSize.setText(drink.getSize1()+" oz");
        textEditDrinkAlco.setText(drink.getAlcoholPercentage() + "% Alcohol");
        textEditDrinkDate.setText(" "+drink.getDate());


        //Previous Select
        findViewById(R.id.buttonPrevDrink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iter < 1){
                    iter = drinks.size()-1;
                    drink = drinks.get(iter);
                } else {
                    iter--;
                    drink = drinks.get(iter);
                }
                textEditDrinkNum.setText((iter+1) + " of " +drinks.size());
                textEditDrinkSize.setText(drink.getSize1()+" oz");
                textEditDrinkAlco.setText(drink.getAlcoholPercentage() + "% Alcohol");
                textEditDrinkDate.setText(" "+drink.getDate());
            }
        });
        //Forward Select
        findViewById(R.id.buttonNextDrink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iter++;
                if(iter > drinks.size()-1){
                    iter = 0;
                    drink = drinks.get(iter);
                } else {
                    drink = drinks.get(iter);
                }
                textEditDrinkNum.setText((iter+1) + " of " +drinks.size());
                textEditDrinkSize.setText(drink.getSize1()+" oz");
                textEditDrinkAlco.setText(drink.getAlcoholPercentage() + "% Alcohol");
                textEditDrinkDate.setText(" "+drink.getDate());
            }
        });
        //Delete
        findViewById(R.id.buttonTrashDrink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drinks.size() == 1){
                    drinks.clear();
                    Intent intent = new Intent();
                    intent.putExtra(RETURNARRAY, drinks);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {

                    drinks.remove(iter);
                    if (iter < 1) {
                        iter = drinks.size() - 1;
                    } else {
                        iter--;
                    }
                    drink = drinks.get(iter);
                    textEditDrinkNum.setText((iter + 1) + " of " + drinks.size());
                    textEditDrinkSize.setText(drink.getSize1()+" oz");
                    textEditDrinkAlco.setText(drink.getAlcoholPercentage() + "% Alcohol");
                    textEditDrinkDate.setText(" " + drink.getDate());
                }
            }
        });

        //Close out of view
        findViewById(R.id.buttonViewClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(RETURNARRAY, drinks);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}