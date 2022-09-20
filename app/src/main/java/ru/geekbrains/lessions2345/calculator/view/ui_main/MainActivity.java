package ru.geekbrains.lessions2345.calculator.view.ui_main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.geekbrains.lessions2345.calculator.R;
import ru.geekbrains.lessions2345.calculator.core.Constants;
import ru.geekbrains.lessions2345.calculator.view.ViewConstants;
import ru.geekbrains.lessions2345.calculator.view.ui_menu.MenuActivity;

public class MainActivity extends Activity implements View.OnClickListener,
        ViewConstants, Constants, ViewMainContract {
    private TextView outputResultText;
    private TextView inputtedHistoryText;
    private Button buttonZero;
    private Button buttonOne;
    private Button buttonTwo;
    private Button buttonThree;
    private Button buttonFour;
    private Button buttonFive;
    private Button buttonSix;
    private Button buttonSeven;
    private Button buttonEight;
    private Button buttonNine;
    private Button buttonEqual;
    private Button buttonZapitay;
    private Button buttonBracketClose;
    private Button buttonBackspace;
    private Button buttonBackspaceOne;
    private Button buttonBackspaceTwo;
    private Button buttonBracketOpen;
    private Button buttonDivide;
    private Button buttonMinus;
    private Button buttonMultiply;
    private Button buttonPercent;
    private Button buttonPlus;
    private Button buttonPlusMinus;
    private Button buttonSqrt;
    private Button buttonStepen;
    private Button buttonChangeTheme;
    private final List<Button> buttonsNumbersGroups = new ArrayList();
    private final List<Button> buttonsActionsGroups = new ArrayList();

    private THEMES currentTheme;

    private MainPresenter mainPresenter = new MainPresenter();
    private float koeff_DP;
    private int curRadiusButtons;
    private boolean doChangeRadius = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Установка темы
        koeff_DP = this.getResources().getDisplayMetrics().density;
        currentTheme = getSettings();
        setCalculatorTheme(currentTheme);
        setContentView(R.layout.calc_keyboard_layout);
        // Отслеживание первичного запуска или перезапуска активити
        if (savedInstanceState == null) {
            // Умножаем на 2, потому что ширина чисел вдвое меньше величины EMS
            if (this.getResources().getDisplayMetrics().widthPixels >= BORDER_WIDTH)
                mainPresenter.setMaxNumberSymbolsInOutputTextField(
                getResources().getInteger(R.integer.number_output_symbols_forEMS) * 2);
            else
                mainPresenter.setMaxNumberSymbolsInOutputTextField(
                        getResources().getInteger(R.integer.number_output_symbols_forEMS_small) * 2);
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
        // Установка обновлённых значений размеров элементов сцены
        setNewSizesElements(Math.round(curRadiusButtons * koeff_DP));
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
        int newMaxHeightDp = Math.round(convertPixelsToDp(getApplicationContext(),
            Math.round(height / KOEFF_RESIZE_HEIGHT)));

        // Смена значения поля в constraintLayout
        ConstraintLayout constraintLayout = findViewById(R.id.run_calculator);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.constrainMaxHeight(R.id.input_history, newMaxHeightDp);
        constraintSet.constrainMaxHeight(R.id.input_history_night, newMaxHeightDp);
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
    public void setInputtedHistoryText(String newText) {
        inputtedHistoryText.setText(newText);
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
        if (v.getId() == buttonZero.getId()) {
            mainPresenter.addNumeral(0);
        } else if (v.getId() == buttonOne.getId()) {
            mainPresenter.addNumeral(1);
        } else if (v.getId() == buttonTwo.getId()) {
            mainPresenter.addNumeral(2);
        } else if (v.getId() == buttonThree.getId()) {
            mainPresenter.addNumeral(3);
        } else if (v.getId() == buttonFour.getId()) {
            mainPresenter.addNumeral(4);
        } else if (v.getId() == buttonFive.getId()) {
            mainPresenter.addNumeral(5);
        } else if (v.getId() == buttonSix.getId()) {
            mainPresenter.addNumeral(6);
        } else if (v.getId() == buttonSeven.getId()) {
            mainPresenter.addNumeral(7);
        } else if (v.getId() == buttonEight.getId()) {
            mainPresenter.addNumeral(8);
        } else if (v.getId() == buttonNine.getId()) {
            mainPresenter.addNumeral(9);
        } else if (v.getId() == buttonEqual.getId()) {
            mainPresenter.setEqual();
        } else if (v.getId() == buttonZapitay.getId()) {
            mainPresenter.setCurZapitay();
        } else if (v.getId() == buttonBracketOpen.getId()) {
            mainPresenter.setBracketOpen();
        } else if (v.getId() == buttonBracketClose.getId()) {
            mainPresenter.setBracketClose();
        } else if (v.getId() == buttonBackspace.getId()) {
            mainPresenter.clearAll();
        } else if (v.getId() == buttonBackspaceOne.getId()) {
            mainPresenter.clearOne();
        } else if (v.getId() == buttonBackspaceTwo.getId()) {
            mainPresenter.clearTwo();
        } else if (v.getId() == buttonDivide.getId()) {
            mainPresenter.setNewAction(ACTIONS.ACT_DIV);
        } else if (v.getId() == buttonMinus.getId()) {
            mainPresenter.setNewAction(ACTIONS.ACT_MINUS);
        } else if (v.getId() == buttonMultiply.getId()) {
            mainPresenter.setNewAction(ACTIONS.ACT_MULTY);
        } else if (v.getId() == buttonPlus.getId()) {
            mainPresenter.setNewAction(ACTIONS.ACT_PLUS);
        } else if (v.getId() == buttonPercent.getId()) {
            // Задаётся универсальное значение ACT_PERS_MULTY и оно уточняется в методе setNewAction
            mainPresenter.setNewAction(ACTIONS.ACT_PERS_MULTY);
        } else if (v.getId() == buttonPlusMinus.getId()) {
            mainPresenter.changeSign();
        } else if (v.getId() == buttonStepen.getId()) {
            mainPresenter.setNewAction(ACTIONS.ACT_STEP);
        } else if (v.getId() == buttonSqrt.getId()) {
            mainPresenter.setNewFunction(FUNCTIONS.FUNC_SQRT);
        } else if (v.getId() == buttonChangeTheme.getId()) {
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
            inputtedHistoryText = findViewById(R.id.inputted_history_text_night);
            // Показать текстовые поля с ночной темой
            outputResultText.setVisibility(View.VISIBLE);
            inputtedHistoryText.setVisibility(View.VISIBLE);
            findViewById(R.id.input_history_night).setVisibility(View.VISIBLE);
            // Спрятать (убрать с поля) текстовые поля с дневной темой
            findViewById(R.id.result).setVisibility(View.GONE);
            findViewById(R.id.inputted_history_text).setVisibility(View.GONE);
            findViewById(R.id.input_history).setVisibility(View.GONE);
        } else {
            // Инициализация текстовых полей
            outputResultText = findViewById(R.id.result);
            inputtedHistoryText = findViewById(R.id.inputted_history_text);
            // Показать текстовые поля с дневной темой
            outputResultText.setVisibility(View.VISIBLE);
            inputtedHistoryText.setVisibility(View.VISIBLE);
            findViewById(R.id.input_history).setVisibility(View.VISIBLE);
            // Спрятать (убрать с поля) текстовые поля с ночной темой
            findViewById(R.id._result_night).setVisibility(View.GONE);
            findViewById(R.id.inputted_history_text_night).setVisibility(View.GONE);
            findViewById(R.id.input_history_night).setVisibility(View.GONE);
        }
        mainPresenter.calculate();
        mainPresenter.getError();
        mainPresenter.getInit();
    }

    // Инициализация кнопок
    private void initButtons() {
        if (currentTheme == THEMES.NIGHT_THEME) {
            // Инициализация кнопок с цифрами в ночном режиме
            buttonZero = findViewById(R.id.zero_night);
            buttonOne = findViewById(R.id.one_night);
            buttonTwo = findViewById(R.id.two_night);
            buttonThree = findViewById(R.id.three_night);
            buttonFour = findViewById(R.id.four_night);
            buttonFive = findViewById(R.id.five_night);
            buttonSix = findViewById(R.id.six_night);
            buttonSeven = findViewById(R.id.seven_night);
            buttonEight = findViewById(R.id.eight_night);
            buttonNine = findViewById(R.id.nine_night);

            // Установка кнопок с действиями в ночном режиме
            buttonEqual = findViewById(R.id.equal_night);
            buttonZapitay = findViewById(R.id.zapitay_night);
            buttonBracketClose = findViewById(R.id.bracket_close_night);
            buttonBackspace = findViewById(R.id.backspace_night);
            buttonBackspaceOne = findViewById(R.id.backspace_one_night);
            buttonBackspaceTwo = findViewById(R.id.backspace_two_night);
            buttonBracketOpen = findViewById(R.id.bracket_open_night);
            buttonDivide = findViewById(R.id.divide_night);
            buttonMinus = findViewById(R.id.minus_night);
            buttonMultiply = findViewById(R.id.multiply_night);
            buttonPercent = findViewById(R.id.percent_night);
            buttonPlus = findViewById(R.id.plus_night);
            buttonPlusMinus = findViewById(R.id.plus_minus_night);
            buttonSqrt = findViewById(R.id.sqrt_night);
            buttonStepen = findViewById(R.id.stepen_night);
            buttonChangeTheme = findViewById(R.id.menu_theme_night);

            // Спрятать (убрать с поля) кнопки в дневном режиме
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
            buttonZero = findViewById(R.id.zero);
            buttonOne = findViewById(R.id.one);
            buttonTwo = findViewById(R.id.two);
            buttonThree = findViewById(R.id.three);
            buttonFour = findViewById(R.id.four);
            buttonFive = findViewById(R.id.five);
            buttonSix = findViewById(R.id.six);
            buttonSeven = findViewById(R.id.seven);
            buttonEight = findViewById(R.id.eight);
            buttonNine = findViewById(R.id.nine);

            // Установка кнопок с действиями в дневном режиме
            buttonEqual = findViewById(R.id.equal);
            buttonZapitay = findViewById(R.id.zapitay);
            buttonBracketClose = findViewById(R.id.bracket_close);
            buttonBackspace = findViewById(R.id.backspace);
            buttonBackspaceOne = findViewById(R.id.backspace_one);
            buttonBackspaceTwo = findViewById(R.id.backspace_two);
            buttonBracketOpen = findViewById(R.id.bracket_open);
            buttonDivide = findViewById(R.id.divide);
            buttonMinus = findViewById(R.id.minus);
            buttonMultiply = findViewById(R.id.multiply);
            buttonPercent = findViewById(R.id.percent);
            buttonPlus = findViewById(R.id.plus);
            buttonPlusMinus = findViewById(R.id.plus_minus);
            buttonSqrt = findViewById(R.id.sqrt);
            buttonStepen = findViewById(R.id.stepen);
            buttonChangeTheme = findViewById(R.id.menu_theme);

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

        // Установка слушателей событий на кнопки
        buttonsNumbersGroups.addAll(Arrays.asList(buttonZero, buttonOne, buttonTwo, buttonThree,
                buttonFour, buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine));
        buttonsNumbersGroups.forEach( button -> {
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(this);
        });
        buttonsActionsGroups.addAll(Arrays.asList(buttonEqual, buttonZapitay,
                buttonBracketClose, buttonBackspace, buttonBackspaceOne, buttonBackspaceTwo,
                buttonBracketOpen, buttonDivide, buttonMinus, buttonMultiply, buttonPercent,
                buttonPlus, buttonPlusMinus, buttonSqrt, buttonStepen));
        buttonsActionsGroups.forEach( button -> {
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(this);
        });
        buttonChangeTheme.setOnClickListener(this);
        buttonChangeTheme.setVisibility(View.VISIBLE);
    }

    // Отобразить индикатор ввода вещественного числа
    private void buttonZapitayChange() {
        if (!mainPresenter.getPressedZapitay()) {
            if (currentTheme == THEMES.NIGHT_THEME) {
                buttonZapitay.setBackgroundResource(R.drawable.buttons_with_actions_mg_night);
            } else {
                buttonZapitay.setBackgroundResource(R.drawable.buttons_with_actions_mg_day);
            }
        } else {
            if (currentTheme == THEMES.NIGHT_THEME) {
                buttonZapitay.setBackgroundResource(R.drawable.buttons_with_numbers_mg_night);
            } else {
                buttonZapitay.setBackgroundResource(R.drawable.buttons_with_numbers_mg_day);
            }
        }
    }

    // Считать информацию о текущих настройках программы (теме и радиусе кнопок)
    private THEMES getSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(KEY_SETTINGS, MODE_PRIVATE);
        int currentTheme = sharedPreferences.getInt(KEY_CURRENT_THEME, 1);
        if (this.getResources().getDisplayMetrics().widthPixels >= BORDER_WIDTH)
            curRadiusButtons = sharedPreferences.getInt(KEY_CURRENT_RADIUS, DEFAULT_BUTTON_RADIUS);
        else
            curRadiusButtons = sharedPreferences.getInt(
                    KEY_CURRENT_RADIUS, DEFAULT_BUTTON_RADIUS_SMALL);
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
        editor.putInt(KEY_CURRENT_RADIUS, curRadiusButtons);
        // Ставим false, что говорит о том, что изменения радиуса отработали
        editor.putBoolean(KEY_DOCHANGE_RADIUS, false);
        editor.apply();
    }

    private void setNewSizesElements(int newRadius) {
        ConstraintLayout constraintLayout = findViewById(R.id.run_calculator);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        int buttonsNumbersWidth = (int) getResources().getDimension(
                R.dimen.button_number_width_standard);
        int buttonsNumbersHeight = (int) getResources().getDimension(
                R.dimen.button_number_height_standard);
        int buttonsActionsWidth = (int) getResources().getDimension(
                R.dimen.button_action_width_standard);
        int buttonsActionsHeight = (int) getResources().getDimension(
                R.dimen.button_action_height_standard);
        // Задание новых размеров для кнопок
        if (this.getResources().getDisplayMetrics().widthPixels < BORDER_WIDTH) {
            buttonsNumbersWidth = (int) getResources().getDimension(
                    R.dimen.button_number_width_small);
            buttonsNumbersHeight = (int) getResources().getDimension(
                    R.dimen.button_number_height_small);
            buttonsActionsWidth = (int) getResources().getDimension(
                    R.dimen.small_button_action_width);
            buttonsActionsHeight = (int) getResources().getDimension(
                    R.dimen.small_button_action_height);
        }

        // Задание нового радиуса кнопок с цифрами и их новых размеров
        int curAngle = 0;
        for (Button button: buttonsNumbersGroups) {
            constraintSet.constrainCircle(button.getId(), currentTheme == THEMES.DAY_THEME ?
                R.id.result : R.id._result_night, newRadius, curAngle);
            curAngle += 30;
            constraintSet.constrainWidth(button.getId(), buttonsNumbersWidth);
            constraintSet.constrainHeight(button.getId(), buttonsNumbersHeight);
        }
        // Задание нового радиуса кнопок с действиями
        for (Button button: buttonsActionsGroups) {
            constraintSet.constrainWidth(button.getId(), buttonsActionsWidth);
            constraintSet.constrainHeight(button.getId(), buttonsActionsHeight);
        }
        // Задание размера элементам отображения входящей и результирующей информации
//        if (this.getResources().getDisplayMetrics().widthPixels >= BORDER_WIDTH) {
//            constraintSet.constrain(outputResultText, getResources().getInteger(R.integer.number_output_symbols_forEMS));
//            constraintSet.inputtedHistoryText
//        } else {
//
//        }
        constraintSet.applyTo(constraintLayout);
    }
}