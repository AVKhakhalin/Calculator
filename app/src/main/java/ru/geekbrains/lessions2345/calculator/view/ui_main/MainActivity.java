package ru.geekbrains.lessions2345.calculator.view.ui_main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import ru.geekbrains.lessions2345.calculator.R;
import ru.geekbrains.lessions2345.calculator.core.Constants;
import ru.geekbrains.lessions2345.calculator.presenter.main.MainPresenter;
import ru.geekbrains.lessions2345.calculator.view.ui_menu.MenuActivity;

public class MainActivity extends Activity implements View.OnClickListener,
        Constants, ViewMainContract {
    private TextView outputResultText;
    private TextView inputedHistoryText;
    private Button button_0;
    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private Button button_5;
    private Button button_6;
    private Button button_7;
    private Button button_8;
    private Button button_9;
    private Button button_equal;
    private Button button_zapitay;
    private Button button_bracket_close;
    private Button button_backspace;
    private Button button_backspace_one;
    private Button button_backspace_two;
    private Button button_bracket_open;
    private Button button_divide;
    private Button button_minus;
    private Button button_multiply;
    private Button button_percent;
    private Button button_plus;
    private Button button_plus_minus;
    private Button button_sqrt;
    private Button button_stepen;
    private Button button_change_theme;

    private THEMES currentTheme;

    private MainPresenter mainPresenter = new MainPresenter();
    private int koeff_DP;
    private int curRadiusButtons;
    private boolean doChangeRadius = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Установка темы
        koeff_DP = (int) getApplicationContext().getResources().getDisplayMetrics().density;
        currentTheme = getSettings();
        setCalculatorTheme(currentTheme);
        setContentView(R.layout.calc_keyboard_layout);
        // Отслеживание первичного запуска или перезапуска активити
        if (savedInstanceState == null) {
            // Умножаем на 2, потому что ширина чисел вдвое меньше величины EMS
            mainPresenter.setMaxNumberSymbolsInOutputTextField(
                    getResources().getInteger(R.integer.number_output_symbols_forEMS) * 2);
        } else {
            // Восстановление класса mainPresenter после поворота экрана
            if ((MainPresenter) getLastNonConfigurationInstance() != null) {
                mainPresenter = (MainPresenter) getLastNonConfigurationInstance();
            }
        }
        // Передача MainActivity в MainPresenter
        mainPresenter.onAttach(this);
        // Инициализация текстовых полей
        initTextFields();
        // Инициализация кнопок
        initButtons();
        // Проверка режима ввода вещественных чисел
        buttonZapitayChange();
        // Установка обновлённого максимального значения высоты текстового поля с историей ввода
        // (_input_history и _input_history_night)
        setNewMaxHeightForInputHistory();
        // Установка обновлённого значения радиуса окружности кнопок
        setNewRadiusButtons(curRadiusButtons * koeff_DP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onDetach();
    }

    // Установка новых значений высоты для полей _input_history и _input_history_night
    // (важно при повороте экрана)
    private void setNewMaxHeightForInputHistory()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        int newMaxHeight_dp = Math.round(convertPixelsToDp(getApplicationContext(),
                Math.round(height / KOEFF_RESIZE_HEIGHT)));

        // Смена значения поля в constraintLayout
        ConstraintLayout constraintLayout = findViewById(R.id.run_calculator);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.constrainMaxHeight(R.id.input_history, newMaxHeight_dp);
        constraintSet.constrainMaxHeight(R.id.input_history_night, newMaxHeight_dp);
        constraintSet.applyTo(constraintLayout);
    }

    // Конвертирование px в dp
    private float convertPixelsToDp(Context context, float pixels) {
        return pixels / context.getResources().getDisplayMetrics().density;
    }

    // Установка темы калькулятора
    private void setCalculatorTheme(THEMES currentTheme) {
        if (currentTheme == THEMES.DAY_THEME) {
            setTheme(R.style.Day);
        } else {
            setTheme(R.style.Night);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Установка темы
        THEMES savedTheme = getSettings();
        if ((currentTheme != savedTheme) || (doChangeRadius)) {
            currentTheme = savedTheme;
            doChangeRadius = false;
            saveSettings(currentTheme);
            recreate();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Сохранение класса calcLogic перед поворотом экрана
        onRetainNonConfigurationInstance();
    }

    // Метод для сохранения ссылки на класс calcLogic при повороте экрана
    public Object onRetainNonConfigurationInstance() {
        return mainPresenter;
    }

    @Override
    public void setInputedHistoryText(String newText) {
        inputedHistoryText.setText(newText);
    }

    @Override
    public void setOutputResultText(String newText) {
        outputResultText.setText(newText);
    }

    @Override
    public void setErrorText(ERRORS error) {
        // Отобразить информацию о текущих ошибках
        switch (error) {
            case BRACKET_DISBALANCE:
                Toast.makeText(this, getResources().getString(
                                R.string.error_different_number_brackets),
                        Toast.LENGTH_SHORT).show();
                break;
            case SQRT_MINUS:
                Toast.makeText(this, getResources().getString(
                                R.string.error_undersquare_low_zero),
                        Toast.LENGTH_SHORT).show();
                break;
            case ZERO_DIVIDE:
                Toast.makeText(this, getResources().getString(
                                R.string.error_divide_on_zero),
                        Toast.LENGTH_SHORT).show();
                break;
            case BRACKETS_EMPTY:
                Toast.makeText(this, getResources().getString(
                                R.string.error_inside_brackets_empty),
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button_0.getId()) {
            mainPresenter.addNumeral(0);
        } else if (v.getId() == button_1.getId()) {
            mainPresenter.addNumeral(1);
        } else if (v.getId() == button_2.getId()) {
            mainPresenter.addNumeral(2);
        } else if (v.getId() == button_3.getId()) {
            mainPresenter.addNumeral(3);
        } else if (v.getId() == button_4.getId()) {
            mainPresenter.addNumeral(4);
        } else if (v.getId() == button_5.getId()) {
            mainPresenter.addNumeral(5);
        } else if (v.getId() == button_6.getId()) {
            mainPresenter.addNumeral(6);
        } else if (v.getId() == button_7.getId()) {
            mainPresenter.addNumeral(7);
        } else if (v.getId() == button_8.getId()) {
            mainPresenter.addNumeral(8);
        } else if (v.getId() == button_9.getId()) {
            mainPresenter.addNumeral(9);
        } else if (v.getId() == button_equal.getId()) {
            mainPresenter.setEqual();
        } else if (v.getId() == button_zapitay.getId()) {
            mainPresenter.setCurZapitay();
        } else if (v.getId() == button_bracket_open.getId()) {
            mainPresenter.setBracketOpen();
        } else if (v.getId() == button_bracket_close.getId()) {
            mainPresenter.setBracketClose();
        } else if (v.getId() == button_backspace.getId()) {
            mainPresenter.clearAll();
        } else if (v.getId() == button_backspace_one.getId()) {
            mainPresenter.clearOne();
        } else if (v.getId() == button_backspace_two.getId()) {
            mainPresenter.clearTwo();
        } else if (v.getId() == button_divide.getId()) {
            mainPresenter.setNewAction(ACTIONS.ACT_DIV);
        } else if (v.getId() == button_minus.getId()) {
            mainPresenter.setNewAction(ACTIONS.ACT_MINUS);
        } else if (v.getId() == button_multiply.getId()) {
            mainPresenter.setNewAction(ACTIONS.ACT_MULTY);
        } else if (v.getId() == button_plus.getId()) {
            mainPresenter.setNewAction(ACTIONS.ACT_PLUS);
        } else if (v.getId() == button_percent.getId()) {
            // Задаётся универсальное значение ACT_PERS_MULTY и оно уточняется в методе setNewAction
            mainPresenter.setNewAction(ACTIONS.ACT_PERS_MULTY);
        } else if (v.getId() == button_plus_minus.getId()) {
            mainPresenter.changeSign();
        } else if (v.getId() == button_stepen.getId()) {
            mainPresenter.setNewAction(ACTIONS.ACT_STEP);
        } else if (v.getId() == button_sqrt.getId()) {
            mainPresenter.setNewFunction(FUNCTIONS.FUNC_SQRT);
        } else if (v.getId() == button_change_theme.getId()) {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        }
        buttonZapitayChange();
    }

    // Инициализаци текстовых полей
    private void initTextFields() {
        if (currentTheme == THEMES.NIGHT_THEME) {
            // Инициализация текстовых полей
            outputResultText = findViewById(R.id._result_night);
            inputedHistoryText = findViewById(R.id.inputed_history_text_night);
            // Показать текстовые поля с ночной темой
            outputResultText.setVisibility(View.VISIBLE);
            inputedHistoryText.setVisibility(View.VISIBLE);
            findViewById(R.id.input_history_night).setVisibility(View.VISIBLE);
            // Спрятать (убрать с поля) текстовые поля с дневной темой
            findViewById(R.id.result).setVisibility(View.GONE);
            findViewById(R.id.inputed_history_text).setVisibility(View.GONE);
            findViewById(R.id.input_history).setVisibility(View.GONE);
        } else {
            // Инициализация текстовых полей
            outputResultText = findViewById(R.id.result);
            inputedHistoryText = findViewById(R.id.inputed_history_text);
            // Показать текстовые поля с дневной темой
            outputResultText.setVisibility(View.VISIBLE);
            inputedHistoryText.setVisibility(View.VISIBLE);
            findViewById(R.id.input_history).setVisibility(View.VISIBLE);
            // Спрятать (убрать с поля) текстовые поля с ночной темой
            findViewById(R.id._result_night).setVisibility(View.GONE);
            findViewById(R.id.inputed_history_text_night).setVisibility(View.GONE);
            findViewById(R.id.input_history_night).setVisibility(View.GONE);
        }
        mainPresenter.calculate();
        mainPresenter.getError();
        mainPresenter.getInit();
    }

    // Инициализация кнопок
    private void initButtons() {
        if (currentTheme == THEMES.NIGHT_THEME) {
            // Установка кнопок с числами в ночном режиме
            button_0 = findViewById(R.id.zero_night);
            button_0.setOnClickListener(this);
            button_0.setVisibility(View.VISIBLE);
            button_1 = findViewById(R.id.one_night);
            button_1.setOnClickListener(this);
            button_2 = findViewById(R.id.two_night);
            button_2.setOnClickListener(this);
            button_3 = findViewById(R.id.three_night);
            button_3.setOnClickListener(this);
            button_4 = findViewById(R.id.four_night);
            button_4.setOnClickListener(this);
            button_5 = findViewById(R.id.five_night);
            button_5.setOnClickListener(this);
            button_6 = findViewById(R.id.six_night);
            button_6.setOnClickListener(this);
            button_7 = findViewById(R.id.seven_night);
            button_7.setOnClickListener(this);
            button_8 = findViewById(R.id.eight_night);
            button_8.setOnClickListener(this);
            button_9 = findViewById(R.id.nine_night);
            button_9.setOnClickListener(this);

            // Установка кнопок с действиями в ночном режиме
            button_equal = findViewById(R.id.equal_night);
            button_equal.setOnClickListener(this);
            button_zapitay = findViewById(R.id.zapitay_night);
            button_zapitay.setOnClickListener(this);
            button_bracket_close = findViewById(R.id.bracket_close_night);
            button_bracket_close.setOnClickListener(this);
            button_backspace = findViewById(R.id.backspace_night);
            button_backspace.setOnClickListener(this);
            button_backspace_one = findViewById(R.id.backspace_one_night);
            button_backspace_one.setOnClickListener(this);
            button_backspace_two = findViewById(R.id.backspace_two_night);
            button_backspace_two.setOnClickListener(this);
            button_bracket_open = findViewById(R.id.bracket_open_night);
            button_bracket_open.setOnClickListener(this);
            button_divide = findViewById(R.id.divide_night);
            button_divide.setOnClickListener(this);
            button_minus = findViewById(R.id.minus_night);
            button_minus.setOnClickListener(this);
            button_multiply = findViewById(R.id.multiply_night);
            button_multiply.setOnClickListener(this);
            button_percent = findViewById(R.id.percent_night);
            button_percent.setOnClickListener(this);
            button_plus = findViewById(R.id.plus_night);
            button_plus.setOnClickListener(this);
            button_plus_minus = findViewById(R.id.plus_minus_night);
            button_plus_minus.setOnClickListener(this);
            button_sqrt = findViewById(R.id.sqrt_night);
            button_sqrt.setOnClickListener(this);
            button_stepen = findViewById(R.id.stepen_night);
            button_stepen.setOnClickListener(this);
            button_change_theme = findViewById(R.id.menu_theme_night);
            button_change_theme.setOnClickListener(this);

            // Показать все кнопки в ночном режиме
            findViewById(R.id.zero_night).setVisibility(View.VISIBLE);
            findViewById(R.id.one_night).setVisibility(View.VISIBLE);
            findViewById(R.id.two_night).setVisibility(View.VISIBLE);
            findViewById(R.id.three_night).setVisibility(View.VISIBLE);
            findViewById(R.id.four_night).setVisibility(View.VISIBLE);
            findViewById(R.id.five_night).setVisibility(View.VISIBLE);
            findViewById(R.id.six_night).setVisibility(View.VISIBLE);
            findViewById(R.id.seven_night).setVisibility(View.VISIBLE);
            findViewById(R.id.eight_night).setVisibility(View.VISIBLE);
            findViewById(R.id.nine_night).setVisibility(View.VISIBLE);
            findViewById(R.id.equal_night).setVisibility(View.VISIBLE);
            findViewById(R.id.zapitay_night).setVisibility(View.VISIBLE);
            findViewById(R.id.bracket_close_night).setVisibility(View.VISIBLE);
            findViewById(R.id.backspace_night).setVisibility(View.VISIBLE);
            findViewById(R.id.backspace_one_night).setVisibility(View.VISIBLE);
            findViewById(R.id.backspace_two_night).setVisibility(View.VISIBLE);
            findViewById(R.id.bracket_open_night).setVisibility(View.VISIBLE);
            findViewById(R.id.divide_night).setVisibility(View.VISIBLE);
            findViewById(R.id.minus_night).setVisibility(View.VISIBLE);
            findViewById(R.id.multiply_night).setVisibility(View.VISIBLE);
            findViewById(R.id.percent_night).setVisibility(View.VISIBLE);
            findViewById(R.id.plus_night).setVisibility(View.VISIBLE);
            findViewById(R.id.plus_minus_night).setVisibility(View.VISIBLE);
            findViewById(R.id.sqrt_night).setVisibility(View.VISIBLE);
            findViewById(R.id.stepen_night).setVisibility(View.VISIBLE);
            findViewById(R.id.menu_theme_night).setVisibility(View.VISIBLE);

            // Спрятать (урать с поля) кнопки в дневном режиме
            findViewById(R.id.zero).setVisibility(View.GONE);
            findViewById(R.id.one).setVisibility(View.GONE);
            findViewById(R.id.two).setVisibility(View.GONE);
            findViewById(R.id.three).setVisibility(View.GONE);
            findViewById(R.id.four).setVisibility(View.GONE);
            findViewById(R.id.five).setVisibility(View.GONE);
            findViewById(R.id.six).setVisibility(View.GONE);
            findViewById(R.id.seven).setVisibility(View.GONE);
            findViewById(R.id.eight).setVisibility(View.GONE);
            findViewById(R.id.nine).setVisibility(View.GONE);
            findViewById(R.id.equal).setVisibility(View.GONE);
            findViewById(R.id.zapitay).setVisibility(View.GONE);
            findViewById(R.id.bracket_close).setVisibility(View.GONE);
            findViewById(R.id.backspace).setVisibility(View.GONE);
            findViewById(R.id.backspace_one).setVisibility(View.GONE);
            findViewById(R.id.backspace_two).setVisibility(View.GONE);
            findViewById(R.id.bracket_open).setVisibility(View.GONE);
            findViewById(R.id.divide).setVisibility(View.GONE);
            findViewById(R.id.minus).setVisibility(View.GONE);
            findViewById(R.id.multiply).setVisibility(View.GONE);
            findViewById(R.id.percent).setVisibility(View.GONE);
            findViewById(R.id.plus).setVisibility(View.GONE);
            findViewById(R.id.plus_minus).setVisibility(View.GONE);
            findViewById(R.id.sqrt).setVisibility(View.GONE);
            findViewById(R.id.stepen).setVisibility(View.GONE);
            findViewById(R.id.menu_theme).setVisibility(View.GONE);
        } else {
            // Установка кнопок с числами в дневном режиме
            button_0 = findViewById(R.id.zero);
            button_0.setOnClickListener(this);
            button_1 = findViewById(R.id.one);
            button_1.setOnClickListener(this);
            button_2 = findViewById(R.id.two);
            button_2.setOnClickListener(this);
            button_3 = findViewById(R.id.three);
            button_3.setOnClickListener(this);
            button_4 = findViewById(R.id.four);
            button_4.setOnClickListener(this);
            button_5 = findViewById(R.id.five);
            button_5.setOnClickListener(this);
            button_6 = findViewById(R.id.six);
            button_6.setOnClickListener(this);
            button_7 = findViewById(R.id.seven);
            button_7.setOnClickListener(this);
            button_8 = findViewById(R.id.eight);
            button_8.setOnClickListener(this);
            button_9 = findViewById(R.id.nine);
            button_9.setOnClickListener(this);

            // Установка кнопок с действиями в дневном режиме
            button_equal = findViewById(R.id.equal);
            button_equal.setOnClickListener(this);
            button_zapitay = findViewById(R.id.zapitay);
            button_zapitay.setOnClickListener(this);
            button_bracket_close = findViewById(R.id.bracket_close);
            button_bracket_close.setOnClickListener(this);
            button_backspace = findViewById(R.id.backspace);
            button_backspace.setOnClickListener(this);
            button_backspace_one = findViewById(R.id.backspace_one);
            button_backspace_one.setOnClickListener(this);
            button_backspace_two = findViewById(R.id.backspace_two);
            button_backspace_two.setOnClickListener(this);
            button_bracket_open = findViewById(R.id.bracket_open);
            button_bracket_open.setOnClickListener(this);
            button_divide = findViewById(R.id.divide);
            button_divide.setOnClickListener(this);
            button_minus = findViewById(R.id.minus);
            button_minus.setOnClickListener(this);
            button_multiply = findViewById(R.id.multiply);
            button_multiply.setOnClickListener(this);
            button_percent = findViewById(R.id.percent);
            button_percent.setOnClickListener(this);
            button_plus = findViewById(R.id.plus);
            button_plus.setOnClickListener(this);
            button_plus_minus = findViewById(R.id.plus_minus);
            button_plus_minus.setOnClickListener(this);
            button_sqrt = findViewById(R.id.sqrt);
            button_sqrt.setOnClickListener(this);
            button_stepen = findViewById(R.id.stepen);
            button_stepen.setOnClickListener(this);
            button_change_theme = findViewById(R.id.menu_theme);
            button_change_theme.setOnClickListener(this);

            // Показать кнопки в дневном режиме
            findViewById(R.id.zero).setVisibility(View.VISIBLE);
            findViewById(R.id.one).setVisibility(View.VISIBLE);
            findViewById(R.id.two).setVisibility(View.VISIBLE);
            findViewById(R.id.three).setVisibility(View.VISIBLE);
            findViewById(R.id.four).setVisibility(View.VISIBLE);
            findViewById(R.id.five).setVisibility(View.VISIBLE);
            findViewById(R.id.six).setVisibility(View.VISIBLE);
            findViewById(R.id.seven).setVisibility(View.VISIBLE);
            findViewById(R.id.eight).setVisibility(View.VISIBLE);
            findViewById(R.id.nine).setVisibility(View.VISIBLE);
            findViewById(R.id.equal).setVisibility(View.VISIBLE);
            findViewById(R.id.zapitay).setVisibility(View.VISIBLE);
            findViewById(R.id.bracket_close).setVisibility(View.VISIBLE);
            findViewById(R.id.backspace).setVisibility(View.VISIBLE);
            findViewById(R.id.backspace_one).setVisibility(View.VISIBLE);
            findViewById(R.id.backspace_two).setVisibility(View.VISIBLE);
            findViewById(R.id.bracket_open).setVisibility(View.VISIBLE);
            findViewById(R.id.divide).setVisibility(View.VISIBLE);
            findViewById(R.id.minus).setVisibility(View.VISIBLE);
            findViewById(R.id.multiply).setVisibility(View.VISIBLE);
            findViewById(R.id.percent).setVisibility(View.VISIBLE);
            findViewById(R.id.plus).setVisibility(View.VISIBLE);
            findViewById(R.id.plus_minus).setVisibility(View.VISIBLE);
            findViewById(R.id.sqrt).setVisibility(View.VISIBLE);
            findViewById(R.id.stepen).setVisibility(View.VISIBLE);
            findViewById(R.id.menu_theme).setVisibility(View.VISIBLE);

            // Спрятать (убрать с поля) кнопки в ночном режиме
            findViewById(R.id.zero_night).setVisibility(View.GONE);
            findViewById(R.id.one_night).setVisibility(View.GONE);
            findViewById(R.id.two_night).setVisibility(View.GONE);
            findViewById(R.id.three_night).setVisibility(View.GONE);
            findViewById(R.id.four_night).setVisibility(View.GONE);
            findViewById(R.id.five_night).setVisibility(View.GONE);
            findViewById(R.id.six_night).setVisibility(View.GONE);
            findViewById(R.id.seven_night).setVisibility(View.GONE);
            findViewById(R.id.eight_night).setVisibility(View.GONE);
            findViewById(R.id.nine_night).setVisibility(View.GONE);
            findViewById(R.id.equal_night).setVisibility(View.GONE);
            findViewById(R.id.zapitay_night).setVisibility(View.GONE);
            findViewById(R.id.bracket_close_night).setVisibility(View.GONE);
            findViewById(R.id.backspace_night).setVisibility(View.GONE);
            findViewById(R.id.backspace_one_night).setVisibility(View.GONE);
            findViewById(R.id.backspace_two_night).setVisibility(View.GONE);
            findViewById(R.id.bracket_open_night).setVisibility(View.GONE);
            findViewById(R.id.divide_night).setVisibility(View.GONE);
            findViewById(R.id.minus_night).setVisibility(View.GONE);
            findViewById(R.id.multiply_night).setVisibility(View.GONE);
            findViewById(R.id.percent_night).setVisibility(View.GONE);
            findViewById(R.id.plus_night).setVisibility(View.GONE);
            findViewById(R.id.plus_minus_night).setVisibility(View.GONE);
            findViewById(R.id.sqrt_night).setVisibility(View.GONE);
            findViewById(R.id.stepen_night).setVisibility(View.GONE);
            findViewById(R.id.menu_theme_night).setVisibility(View.GONE);
        }
    }

    // Отобразить индикатор ввода вещественного числа
    private void buttonZapitayChange() {
        if (!mainPresenter.getPressedZapitay()) {
            if (currentTheme == THEMES.NIGHT_THEME) {
                button_zapitay.setBackgroundResource(R.drawable.buttons_with_actions_mg_night);
            } else {
                button_zapitay.setBackgroundResource(R.drawable.buttons_with_actions_mg_day);
            }
        } else {
            if (currentTheme == THEMES.NIGHT_THEME) {
                button_zapitay.setBackgroundResource(R.drawable.buttons_with_numbers_mg_night);
            } else {
                button_zapitay.setBackgroundResource(R.drawable.buttons_with_numbers_mg_day);
            }
        }
    }

    // Считать информацию о текущих настройках программы (теме и радиусе кнопок)
    private THEMES getSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(KEY_SETTINGS, MODE_PRIVATE);
        int currentTheme = sharedPreferences.getInt(KEY_CURRENT_THEME, 1);
        curRadiusButtons = sharedPreferences.getInt(KEY_CURRENT_RADIUS, DEFAULT_BUTTON_RADIUS);
        doChangeRadius = sharedPreferences.getBoolean(KEY_DOCHANGE_RADIUS, false);
        if (currentTheme == 0) {
            return THEMES.NIGHT_THEME;
        } else {
            // Установка по умолчанию - дневная тема,
            // если в настройках стоит 1 или ничего не будет стоять
            return THEMES.DAY_THEME;
        }
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
        int newRadius = curRadiusButtons;
        editor.putInt(KEY_CURRENT_RADIUS, newRadius);
        // Ставим false, что говорит о том, что изменения радиуса отработали
        editor.putBoolean(KEY_DOCHANGE_RADIUS, false);
        editor.apply();
    }

    private void setNewRadiusButtons(int newRadius) {
        // Смена значения поля в constraintLayout
        ConstraintLayout constraintLayout = findViewById(R.id.run_calculator);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.constrainCircle(R.id.zero, R.id.result, newRadius, 0);
        constraintSet.constrainCircle(R.id.zero_night, R.id._result_night, newRadius, 0);
        constraintSet.constrainCircle(R.id.one, R.id.result, newRadius, 30);
        constraintSet.constrainCircle(R.id.one_night, R.id._result_night, newRadius, 30);
        constraintSet.constrainCircle(R.id.two, R.id.result, newRadius, 60);
        constraintSet.constrainCircle(R.id.two_night, R.id._result_night, newRadius, 60);
        constraintSet.constrainCircle(R.id.three, R.id.result, newRadius, 90);
        constraintSet.constrainCircle(R.id.three_night, R.id._result_night, newRadius, 90);
        constraintSet.constrainCircle(R.id.four, R.id.result, newRadius, 120);
        constraintSet.constrainCircle(R.id.four_night, R.id._result_night, newRadius, 120);
        constraintSet.constrainCircle(R.id.five, R.id.result, newRadius, 150);
        constraintSet.constrainCircle(R.id.five_night, R.id._result_night, newRadius, 150);
        constraintSet.constrainCircle(R.id.six, R.id.result, newRadius, 180);
        constraintSet.constrainCircle(R.id.six_night, R.id._result_night, newRadius, 180);
        constraintSet.constrainCircle(R.id.seven, R.id.result, newRadius, 210);
        constraintSet.constrainCircle(R.id.seven_night, R.id._result_night, newRadius, 210);
        constraintSet.constrainCircle(R.id.eight, R.id.result, newRadius, 240);
        constraintSet.constrainCircle(R.id.eight_night, R.id._result_night, newRadius, 240);
        constraintSet.constrainCircle(R.id.nine, R.id.result, newRadius, 270);
        constraintSet.constrainCircle(R.id.nine_night, R.id._result_night, newRadius, 270);
        constraintSet.constrainCircle(R.id.divide, R.id.result, newRadius, 300);
        constraintSet.constrainCircle(R.id.divide_night, R.id._result_night, newRadius, 300);
        constraintSet.constrainCircle(R.id.minus, R.id.result, newRadius, 330);
        constraintSet.constrainCircle(R.id.minus_night, R.id._result_night, newRadius, 330);
        constraintSet.constrainCircle(R.id.minus, R.id.result, newRadius, 330);
        constraintSet.constrainCircle(R.id.minus_night, R.id._result_night, newRadius, 330);
        constraintSet.applyTo(constraintLayout);
    }
}