package ru.geekbrains.lessions2345.calculator.view.ui_start;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.geekbrains.lessions2345.calculator.view.ui_main.MainActivity;

public class StartActivity extends AppCompatActivity implements ViewStartContract {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}