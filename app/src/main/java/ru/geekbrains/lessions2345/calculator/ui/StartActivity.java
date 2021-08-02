package ru.geekbrains.lessions2345.calculator.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(StartActivity.this, CalculatorKeyboardActivity.class);
        startActivity(intent);
        finish();
    }
}