package ru.geekbrains.lessions2345.calculator.view.ui_menu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ru.geekbrains.lessions2345.calculator.R;
import ru.geekbrains.lessions2345.calculator.core.Constants;
import ru.geekbrains.lessions2345.calculator.presenter.menu.MenuPresenter;
import ru.geekbrains.lessions2345.calculator.view.ui_main.MainActivity;

public class MenuActivity extends AppCompatActivity implements Constants, View.OnClickListener,
        ViewMenuContract {

    Button button_setDayTheme;
    Button button_setNightTheme;
    Button button_returnToCalculator;
    EditText editTextWithNewRadius;

    private THEMES currentTheme;

    private int curRadiusButtons = DEFAULT_BUTTON_RADIUS;

    private final MenuPresenter menuPresenter = new MenuPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curRadiusButtons *= curRadiusButtons;
        currentTheme = getSettings();
        setCalculatorTheme(currentTheme);
        setContentView(R.layout.menu_layout);

        // Передача MainActivity в MainPresenter
        menuPresenter.onAttach(this);
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
            findViewById(R.id._return_day).setVisibility(View.INVISIBLE);
        } else {
            button_returnToCalculator = findViewById(R.id._return_day);
            findViewById(R.id._return_night).setVisibility(View.INVISIBLE);
        }
        button_returnToCalculator.setVisibility(View.VISIBLE);
        button_returnToCalculator.setOnClickListener(this);
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
        if (newRadius < MIN_RADIUS_BUTTONS)
        {
            newRadius = MIN_RADIUS_BUTTONS;
        } else if (newRadius > MAX_RADIUS_BUTTONS)
        {
            newRadius = MAX_RADIUS_BUTTONS;
        }
        editor.putInt(KEY_CURRENT_RADIUS, newRadius);
        // Ставим true, что говорит о том, что изменения радиуса ещё не отработали
        editor.putBoolean(KEY_DOCHANGE_RADIUS, true);
        editor.apply();
    }

    private THEMES getSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(KEY_SETTINGS, MODE_PRIVATE);
        int currentTheme = sharedPreferences.getInt(KEY_CURRENT_THEME, 1);
        curRadiusButtons = sharedPreferences.getInt(KEY_CURRENT_RADIUS,
                MainActivity.DEFAULT_BUTTON_RADIUS);
        if (currentTheme == 0) {
            return THEMES.NIGHT_THEME;
        } else {
            // Установка по умолчанию - дневная тема,
            // если в настройках стоит 1 или ничего не будет стоять
            return THEMES.DAY_THEME;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button_returnToCalculator.getId()) {
            saveSettings(currentTheme);
            finish();
        } else if (v.getId() == button_setDayTheme.getId()) {
            currentTheme = THEMES.DAY_THEME;
            button_returnToCalculator = findViewById(R.id._return_day);
            button_returnToCalculator.setOnClickListener(this);
            button_returnToCalculator.setVisibility(View.VISIBLE);
            findViewById(R.id._return_night).setVisibility(View.INVISIBLE);
        } else if (v.getId() == button_setNightTheme.getId()) {
            currentTheme = THEMES.NIGHT_THEME;
            button_returnToCalculator = findViewById(R.id._return_night);
            button_returnToCalculator.setOnClickListener(this);
            button_returnToCalculator.setVisibility(View.VISIBLE);
            findViewById(R.id._return_day).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuPresenter.onDetach();
    }
}