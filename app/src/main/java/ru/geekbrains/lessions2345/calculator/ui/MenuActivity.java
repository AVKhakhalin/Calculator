package ru.geekbrains.lessions2345.calculator.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.geekbrains.lessions2345.calculator.R;
import ru.geekbrains.lessions2345.calculator.calculator_logic.CalcLogic;
import ru.geekbrains.lessions2345.calculator.calculator_logic.Constants;

public class MenuActivity extends AppCompatActivity implements Constants, View.OnClickListener {

    Button button_setDayTheme;
    Button button_setNightTheme;
    Button button_returnToCalculator;
    EditText editTextWithNewRadius;

    static final String KEY_SETTINGS = "Settings";
    static final String KEY_CURRENT_THEME = "CurrentTheme";
    static final String KEY_CURRENT_RADIUS = "Radius";
    static final String KEY_DOCHANGE_RADIUS = "DoRedraw";
    private THEMES currentTheme;

    private int curRadiusButtons = 177;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curRadiusButtons *= curRadiusButtons;
        currentTheme = getSettings();
        setCalculatorTheme(currentTheme);
        setContentView(R.layout.menu_layout);

        // Инициализация кнопок и текстового поля
        initButtons();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    // Установка темы калькулятора
    private void setCalculatorTheme(THEMES currentTheme) {
        if (currentTheme == THEMES.DAY_THEME) {
            setTheme(R.style.Day);
        } else {
            setTheme(R.style.Night);
        }
    }

    private void initButtons() {
        button_setDayTheme = findViewById(R.id._day_theme);
        button_setDayTheme.setOnClickListener(this);
        button_setNightTheme = findViewById(R.id._night_theme);
        button_setNightTheme.setOnClickListener(this);
        if (currentTheme == THEMES.NIGHT_THEME) {
            button_returnToCalculator = findViewById(R.id._return_night);
            button_returnToCalculator.setOnClickListener(this);
            button_returnToCalculator.setVisibility(View.VISIBLE);
            findViewById(R.id._return_day).setVisibility(View.INVISIBLE);
        } else {
            button_returnToCalculator = findViewById(R.id._return_day);
            button_returnToCalculator.setOnClickListener(this);
            button_returnToCalculator.setVisibility(View.VISIBLE);
            findViewById(R.id._return_night).setVisibility(View.INVISIBLE);
        }
        editTextWithNewRadius = findViewById(R.id._request_radius_buttons_input);
        editTextWithNewRadius.setText(String.valueOf(curRadiusButtons));
    }

    private void saveSettings(THEMES currentTheme) {
        SharedPreferences sharedPreferences = getSharedPreferences(KEY_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Сохранение темы
        if (currentTheme == THEMES.DAY_THEME) {
            editor.putInt(KEY_CURRENT_THEME, 1);
        } else if (currentTheme == THEMES.NIGHT_THEME) {
            editor.putInt(KEY_CURRENT_THEME, 0);
        }
        // Сохранение радиуса кнопок
        int newRadius = Integer.parseInt(editTextWithNewRadius.getText().toString());
        if (newRadius < 150) // Поменять значение на константу
        {
            newRadius = 150;
        } else if (newRadius > 300)
        {
            newRadius = 300;
        }
        editor.putInt(KEY_CURRENT_RADIUS, newRadius);
        editor.putBoolean(KEY_DOCHANGE_RADIUS, true);  // Ставим true, что говорит о том, что изменения радиуса ещё не отработали
        editor.apply();
    }

    private THEMES getSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(KEY_SETTINGS, MODE_PRIVATE);
        int currentTheme = sharedPreferences.getInt(KEY_CURRENT_THEME, 1);
        curRadiusButtons = sharedPreferences.getInt(KEY_CURRENT_RADIUS, CalculatorKeyboardActivity.DEFAULT_BUTTON_RADIUS);
        if (currentTheme == 0) {
            return THEMES.NIGHT_THEME;
        } else {
            return THEMES.DAY_THEME; // Установка по умолчанию - дневная тема, если в настройках стоит 1 или ничего не будет стоять
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button_returnToCalculator.getId()) {
            saveSettings(currentTheme);
            finish();
        } else if (v.getId() == button_setDayTheme.getId()) {
            currentTheme = THEMES.DAY_THEME;
//            saveCurrentTheme(THEMES.DAY_THEME);
            button_returnToCalculator = findViewById(R.id._return_day);
            button_returnToCalculator.setOnClickListener(this);
            button_returnToCalculator.setVisibility(View.VISIBLE);
            findViewById(R.id._return_night).setVisibility(View.INVISIBLE);
        } else if (v.getId() == button_setNightTheme.getId()) {
            currentTheme = THEMES.NIGHT_THEME;
//            saveCurrentTheme(THEMES.NIGHT_THEME);
            button_returnToCalculator = findViewById(R.id._return_night);
            button_returnToCalculator.setOnClickListener(this);
            button_returnToCalculator.setVisibility(View.VISIBLE);
            findViewById(R.id._return_day).setVisibility(View.INVISIBLE);
        }
    }
}