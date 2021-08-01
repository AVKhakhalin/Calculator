package ru.geekbrains.lessions2345.calculator.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import ru.geekbrains.lessions2345.calculator.R;
import ru.geekbrains.lessions2345.calculator.calculator_logic.Constants;

public class MenuActivity extends AppCompatActivity implements Constants, View.OnClickListener {

    Button button_setDayTheme;
    Button button_setNightTheme;
    Button button_returnToCalculator;

    static final String KEY_SETTINGS = "Settings";
    static final String KEY_CURRENT_THEME = "CurrentTheme";
    private THEMES currentTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentTheme = getCurrentTheme();
        setCalculatorTheme(currentTheme);
        setContentView(R.layout.menu_layout);

        // Инициализация кнопок
        initButtons();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    // Установка темы калькулятора
    private void setCalculatorTheme(THEMES currentTheme) {
        if (currentTheme == THEMES.DAY_THEME)
        {
            setTheme(R.style.Day);
        }
        else
        {
            setTheme(R.style.Night);
        }
    }

    private void initButtons() {
        button_setDayTheme = findViewById(R.id._day_theme);
        button_setDayTheme.setOnClickListener(this);
        button_setNightTheme = findViewById(R.id._night_theme);
        button_setNightTheme.setOnClickListener(this);
        if (currentTheme == THEMES.NIGHT_THEME)
        {
            button_returnToCalculator = findViewById(R.id._return_night);
            button_returnToCalculator.setOnClickListener(this);
            button_returnToCalculator.setVisibility(View.VISIBLE);
            findViewById(R.id._return_day).setVisibility(View.INVISIBLE);
        }
        else
        {
            button_returnToCalculator = findViewById(R.id._return_day);
            button_returnToCalculator.setOnClickListener(this);
            button_returnToCalculator.setVisibility(View.VISIBLE);
            findViewById(R.id._return_night).setVisibility(View.INVISIBLE);
        }
    }

    private void saveCurrentTheme (Constants.THEMES currentTheme)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(KEY_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (currentTheme == THEMES.DAY_THEME)
        {
            editor.putInt(KEY_CURRENT_THEME, 1);
        }
        else if (currentTheme == THEMES.NIGHT_THEME)
        {
            editor.putInt(KEY_CURRENT_THEME, 0);
        }
        editor.apply();
    }

    private THEMES getCurrentTheme()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(KEY_SETTINGS, MODE_PRIVATE);
        int currentTheme = sharedPreferences.getInt(KEY_CURRENT_THEME, -1);
        if (currentTheme == 0)
        {
            return THEMES.NIGHT_THEME;
        }
        else
        {
            return THEMES.DAY_THEME; // Установка по умолчанию - дневная тема, если в настройках стоит 1 или ничего не будет стоять
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button_returnToCalculator.getId())
        {
            Intent intent = new Intent(MenuActivity.this, CalculatorKeyboardActivity.class);
            startActivity(intent);
            finish();
        }
        else if (v.getId() == button_setDayTheme.getId())
        {
            saveCurrentTheme(THEMES.DAY_THEME);
            button_returnToCalculator = findViewById(R.id._return_day);
            button_returnToCalculator.setOnClickListener(this);
            button_returnToCalculator.setVisibility(View.VISIBLE);
            findViewById(R.id._return_night).setVisibility(View.INVISIBLE);
        }
        else if (v.getId() == button_setNightTheme.getId())
        {
            saveCurrentTheme(THEMES.NIGHT_THEME);
            button_returnToCalculator = findViewById(R.id._return_night);
            button_returnToCalculator.setOnClickListener(this);
            button_returnToCalculator.setVisibility(View.VISIBLE);
            findViewById(R.id._return_day).setVisibility(View.INVISIBLE);
        }
    }
}