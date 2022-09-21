package ru.geekbrains.lessions2345.calculator.view.ui_menu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import ru.geekbrains.lessions2345.calculator.R;
import ru.geekbrains.lessions2345.calculator.view.ViewConstants;

public class MenuActivity extends AppCompatActivity implements ViewConstants, View.OnClickListener,
        ViewMenuContract {
    /** Исходные данные */ //region
    Button button_setDayTheme;
    Button button_setNightTheme;
    Button button_returnToCalculator;
    EditText editTextWithNewRadius;
    private final MenuPresenter menuPresenter = new MenuPresenter();
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Передача вью MenuActivity в MenuPresenter
        menuPresenter.onAttach(this);

        setCalculatorTheme(menuPresenter.getSettings(this));
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
    @Override
    public void setCalculatorTheme(THEMES currentTheme) {
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
        if (menuPresenter.currentTheme == THEMES.NIGHT_THEME) {
            button_returnToCalculator = findViewById(R.id._return_night);
            findViewById(R.id._return_day).setVisibility(View.INVISIBLE);
        } else {
            button_returnToCalculator = findViewById(R.id._return_day);
            findViewById(R.id._return_night).setVisibility(View.INVISIBLE);
        }
        button_returnToCalculator.setVisibility(View.VISIBLE);
        button_returnToCalculator.setOnClickListener(this);
        editTextWithNewRadius = findViewById(R.id._request_radius_buttons_input);
        editTextWithNewRadius.setText(String.valueOf(menuPresenter.curRadiusButtons));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button_returnToCalculator.getId()) {
            menuPresenter.saveSettings(this);
            finish();
        } else if (v.getId() == button_setDayTheme.getId()) {
            menuPresenter.currentTheme = THEMES.DAY_THEME;
            button_returnToCalculator = findViewById(R.id._return_day);
            button_returnToCalculator.setOnClickListener(this);
            button_returnToCalculator.setVisibility(View.VISIBLE);
            findViewById(R.id._return_night).setVisibility(View.INVISIBLE);
        } else if (v.getId() == button_setNightTheme.getId()) {
            menuPresenter.currentTheme = THEMES.NIGHT_THEME;
            button_returnToCalculator = findViewById(R.id._return_night);
            button_returnToCalculator.setOnClickListener(this);
            button_returnToCalculator.setVisibility(View.VISIBLE);
            findViewById(R.id._return_day).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public String getNewRadius() {
        return editTextWithNewRadius.getText().toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuPresenter.onDetach();
    }
}